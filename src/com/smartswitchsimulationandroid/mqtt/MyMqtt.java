package com.smartswitchsimulationandroid.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.smartswitchsimulationandroid.command.DeviceToAppResponse;
import com.smartswitchsimulationandroid.parmars.ToolParams;
import com.smartswitchsimulationandroid.parmars.UrlParams;
import com.smartswitchsimulationandroid.udptcp.TCPorMQTTInterface;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

public class MyMqtt extends TCPorMQTTInterface {

	private String deviceID;
	private SharedPreferences sp;
	private String userName = "bainian_plug";
	private String passWord = "bndqrjbm";
	private MqttClient client;
	private MqttConnectOptions options;
	private String[] publishTopic;
	private MqttTopic topic;
	private String[] subTopics;
	private int[] myQos;
	private String mPreClientID;// Client的前缀，目前有main和con两个,即主页使用和控制页面使用

	public MyMqtt(String preClientID) {
		mPreClientID = preClientID;
	}

	/**
	 * 
	 * @param msg
	 * @param withRelatedDeivces
	 *            表示是否需要同时控制关联设备
	 */
	public void sendMessage(byte[] msg, boolean withRelatedDeivces) {
		// Log.d("MQTT Send",Hex.encodeHexStr(msg));
		try {

			if (client.isConnected()) {
				MqttMessage message = new MqttMessage();
				message.setQos(0);
				message.setRetained(false);
				message.setPayload(msg);

				for (String itTopic : publishTopic) {
					topic = client.getTopic(itTopic);
					MqttDeliveryToken token = topic.publish(message);
					token.waitForCompletion();
					// if (withRelatedDeivces == false)//
					// 如果不需要控制关联设备，控制一个主设备后，直接退出
					// break;
				}
			} else {
				if (mHandler != null)
					mHandler.sendEmptyMessage(DeviceToAppResponse.DeviceTcpShutDown);
			}
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public Boolean init(final Context context, String[] publishTopic, String[] inSubTopics, Handler handler) {
		this.publishTopic = publishTopic;

		mHandler = handler;
		// sp=context.getSharedPreferences(SharedPreferencesParams.TELEPHONE_INFORMATION,
		// context.MODE_PRIVATE);
		mContext = context;
		// 获取手机设备id
		deviceID = ToolParams.getTelephoneId(context);
		// clientId=sp.getString(SharedPreferencesParams.TELEPHONE_ID, "");
		// if(clientId==null || clientId.trim().length()==0){
		// TelephonyManager
		// telephonyManager=(TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		// clientId=telephonyManager.getDeviceId();
		// Editor editor=sp.edit();
		// editor.putString(SharedPreferencesParams.TELEPHONE_ID, clientId);
		// editor.apply();
		// }

		if (client == null || !client.isConnected()) {
			try {
				String dvcID = deviceID.length() > 20 ? deviceID.substring(deviceID.length() - 20, deviceID.length()) : deviceID;

				client = new MqttClient(UrlParams.MqttHost, mPreClientID + dvcID, new MemoryPersistence());

				options = new MqttConnectOptions();
				options.setCleanSession(true);
				options.setUserName(userName);
				options.setPassword(passWord.toCharArray());
				options.setConnectionTimeout(10);
				options.setKeepAliveInterval(20);
				// if (publishTopic != null)
				// options.setWill(publishTopic[0],(deviceID +
				// "destroy").getBytes(), 0, true);

				client.setCallback(new MqttCallback() {

					@Override
					public void messageArrived(String topicName, MqttMessage message) throws Exception {
						// TODO Auto-generated method stub

						byte[] data = message.getPayload();
						if (topicName.contains("/"))
							handler(data, mHandler, topicName.split("/")[1]);

					}

					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						// TODO Auto-generated method stub

					}

					@Override
					public void connectionLost(Throwable cause) {
						// TODO Auto-generated method stub
						Log.d("MQTT", "连接断开");
						if (mHandler != null)
							mHandler.sendEmptyMessage(DeviceToAppResponse.DeviceTcpShutDown);
						//
					}
				});
				client.setTimeToWait(3000);
				client.connect(options);
				// 进行订阅主题绑定
				subScribe(inSubTopics);

			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		(new ResendThread()).start();

		return true;
	}

	public void insertItem(byte[] item, boolean withRelatedDevices) {
		// TODO Auto-generated method stub
		try {
			sendMessage(item, withRelatedDevices);
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertItemWithResend(byte[] item, boolean withRelatedDevices) {

		try {
			sendMessage(item.clone(), withRelatedDevices);
			Thread.sleep(10);
			ResendItem resendItem = new ResendItem(item.clone(), publishTopic);
			resendList.add(0, resendItem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void stop() {
		super.stop();
		try {
			if (client != null) {
				if (subTopics != null)
					client.unsubscribe(subTopics);
				client.disconnect();
				client.close();
			}
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean init(String serverIP, int serverPort, Handler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	public void changePublishTopic(String[] topics) {
		publishTopic = topics;
	}

	/**
	 * 绑定需要的订阅主题
	 * 
	 * @param newScribe
	 *            String[]，需要绑定所有的订阅主题，device2app开头
	 * @return
	 */
	public boolean subScribe(String[] newScribe) {
		try {
			// if(AppVariant.umAllDeivceList!=null)
			{

				if (subTopics != null)
					client.unsubscribe(subTopics);// 先把已监听的subscribe释放掉

				int[] qos = new int[newScribe.length];
				client.subscribe(newScribe, qos);

				subTopics = newScribe.clone();
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
