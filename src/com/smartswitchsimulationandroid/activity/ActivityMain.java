package com.smartswitchsimulationandroid.activity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.smartswitchsimulationandroid.command.AppControlSocket;
import com.smartswitchsimulationandroid.command.AppControlSocketAckBase;
import com.smartswitchsimulationandroid.command.AppGetDeviceParams;
import com.smartswitchsimulationandroid.command.AppGetDeviceStateUDPAck;
import com.smartswitchsimulationandroid.command.AppSearchWifiAck;
import com.smartswitchsimulationandroid.command.DeviceOnlineUDP;
import com.smartswitchsimulationandroid.constant.ActivityHandlerParams;
import com.smartswitchsimulationandroid.datatransmission.HttpClient;
import com.smartswitchsimulationandroid.device.TimeSettingInfo;
import com.smartswitchsimulationandroid.mqtt.MyMqtt;
import com.smartswitchsimulationandroid.parmars.DeviceVariant;
import com.smartswitchsimulationandroid.parmars.SharedPreferencesParams;
import com.smartswitchsimulationandroid.parmars.ToolParams;
import com.smartswitchsimulationandroid.tools.GetMac;
import com.smartswitchsimulationandroid.udptcp.ReceivePoolUDP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class ActivityMain extends Activity {
	/** 设备注册按钮*/
	private Button btRegister;
	/** 设备注册按钮*/
	private Button btConnectMQTT;
	/** 载入窗口 */
	private ProgressDialog loadingDialog;
	/** 实例化后台请求 */
	private HttpClient httpClient = new HttpClient();
	/** 发送UDP，发现设备指令每1秒发送一次，发送3次结束 */
	private int mTimeCount = 0;
	/** device基本信息*/
	private String DEVICE_MAC ;
	private String DEVICE_PID ;
	private String DEVICE_DID ;
	private String DEVICE_VER ;
	private String DEVICE_PASSCODE ;
	
	private int WorkMode ;
	private int OutputState;
	private int MQTTEnable;
	
	private boolean deviceBind;
	
	SharedPreferences deviceInfo;
	SharedPreferences deviceStatus;
	
	List<TimeSettingInfo> timeSettingInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setTitle("设备注册中...");
		loadingDialog.setCancelable(false);
		
		initViews();
		initEvents();
		initParam();
	}
	
	/**
	 * 初始化页面
	 */
	private void initViews() {
		btRegister = (Button) findViewById(R.id.btRegister);
		btConnectMQTT = (Button) findViewById(R.id.btConnectMqtt);
		
	}
	
	/**
	 * 控制器按钮监听
	 */
	private void initEvents() {
		btRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toDeviceRegister();
			}
		});
		
		btConnectMQTT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				connectMQTT();
				
			}
		});
		
	}
	
	/**
	 * 初始化各类数据
	 */
	public void initParam(){
		deviceInfo = getSharedPreferences(SharedPreferencesParams.CONFIG_INFORMATION, Context.MODE_PRIVATE);
		deviceStatus = getSharedPreferences(SharedPreferencesParams.DEVICE_STATUS, Context.MODE_PRIVATE);
		boolean isFirst = deviceInfo.getBoolean(SharedPreferencesParams.FIRST_IN, true);
		if (isFirst){
			DEVICE_MAC = GetMac.getMacAddr();
			DEVICE_PID = "0000000100000001";
			DEVICE_DID = DEVICE_MAC;
			DEVICE_VER = "1999";
			DEVICE_PASSCODE = "TEST";
			Editor editor = deviceInfo.edit();
			editor.putString(SharedPreferencesParams.DEVICE_MAC, DEVICE_MAC);
			editor.putString(SharedPreferencesParams.DEVICE_PID, DEVICE_PID);
			editor.putString(SharedPreferencesParams.DEVICE_DID, DEVICE_DID);
			editor.putString(SharedPreferencesParams.DEVICE_VER, DEVICE_VER);
			editor.putString(SharedPreferencesParams.DEVICE_PASSCODE, DEVICE_PASSCODE);
			editor.putBoolean(SharedPreferencesParams.FIRST_IN, false);
			editor.commit();
			
			editor = deviceStatus.edit();
			editor.putInt(SharedPreferencesParams.DEVICE_WORK_MODE, 0);
			editor.putInt(SharedPreferencesParams.DEVICE_OUTPUT_STATE, 1);
			editor.putInt(SharedPreferencesParams.DEVICE_MQTT_ENABLE, 1);
			editor.commit();
		}
		
		DEVICE_MAC = deviceInfo.getString(SharedPreferencesParams.DEVICE_MAC, "");
		DEVICE_PID = deviceInfo.getString(SharedPreferencesParams.DEVICE_PID, "");
		DEVICE_DID = DEVICE_MAC;
		DEVICE_PASSCODE = deviceInfo.getString(SharedPreferencesParams.DEVICE_PASSCODE, "TEST");
		
		WorkMode = deviceStatus.getInt(SharedPreferencesParams.DEVICE_WORK_MODE, 0);
		OutputState = deviceStatus.getInt(SharedPreferencesParams.DEVICE_OUTPUT_STATE, 1);
		MQTTEnable = deviceStatus.getInt(SharedPreferencesParams.DEVICE_MQTT_ENABLE, 1);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				initUDPTCP();
			}
		}, 100);
		
		connectMQTT();
	}
	
	/**
	 * 设备注册
	 */
	protected void toDeviceRegister() {
		String PID = "0000000100000001";
		if (ToolParams.isNetWork(getApplicationContext())) {
			loadingDialog.show();
			httpClient.doRegister(ActivityMain.this, DEVICE_DID, DEVICE_MAC, PID, DEVICE_PASSCODE, handler);
		} else {
			Toast.makeText(getApplicationContext(), "无网络", Toast.LENGTH_SHORT).show();
		}
	}
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		
		@Override
		public void handleMessage(Message msg){
			switch (msg.what) {
			case ActivityHandlerParams.DeviceRegister:
				loadingDialog.dismiss();
				btRegister.setEnabled(false);
				Toast.makeText(ActivityMain.this, "新设备注册", Toast.LENGTH_SHORT).show();
				httpClient.getMqttServer(ActivityMain.this, DEVICE_DID, handler);
				startUDPOnline();
				break;
			case ActivityHandlerParams.DeviceReRegister:
				loadingDialog.dismiss();
				btRegister.setEnabled(false);
				Toast.makeText(ActivityMain.this, "设备重置", Toast.LENGTH_SHORT).show();
				httpClient.getMqttServer(ActivityMain.this, DEVICE_DID, handler);
				startUDPOnline();
				break;
			case ActivityHandlerParams.DeviceRegisterError:
				loadingDialog.dismiss();
				Toast.makeText(ActivityMain.this, "操作出错", Toast.LENGTH_SHORT).show();
				break;
			case ActivityHandlerParams.DeviceRegisterTimeout:
				loadingDialog.dismiss();
				Toast.makeText(ActivityMain.this, "注册超时", Toast.LENGTH_SHORT).show();
				break;
				
			case ActivityHandlerParams.DeviceGetMqttServer:
				
				break;
			case ActivityHandlerParams.DeviceGetMqttServerTimeout:
				
				break;
				
			case ActivityHandlerParams.LocalNetWorkSearchDevice:
				
				AppSearchWifiAck appSearchWifiAck = new AppSearchWifiAck(DEVICE_MAC, DEVICE_PID, DEVICE_DID, DEVICE_PASSCODE);
				if (DeviceVariant.umReceivePoolUdp != null){
					DeviceVariant.umReceivePoolUdp.InsertSendItem(appSearchWifiAck.getData(), msg.obj.toString());
				}
				break;
				
			case ActivityHandlerParams.AppGetDeviceStateUDP:
				AppGetDeviceStateUDPAck appGetDeviceStateUDPAck = new AppGetDeviceStateUDPAck(WorkMode,OutputState,MQTTEnable);
				if (DeviceVariant.umReceivePoolUdp != null){
					DeviceVariant.umReceivePoolUdp.InsertSendItem(appGetDeviceStateUDPAck.getData(), msg.obj.toString());
				}
				break;
			
			case ActivityHandlerParams.AppGetDeviceStateMQTT:
				AppGetDeviceStateUDPAck appGetDeviceStateMQTT = new AppGetDeviceStateUDPAck(WorkMode,OutputState,MQTTEnable);
				DeviceVariant.umMqtt.insertItem(appGetDeviceStateMQTT.getData(), false);
				break;
				
			case ActivityHandlerParams.AppGetDeviceParams:
				AppControlSocketAckBase appGetDeviceParams = new AppControlSocketAckBase(0,timeSettingInfos);
				DeviceVariant.umMqtt.insertItem(appGetDeviceParams.getData(), false);
				break;
			}
		}
	};
	
	/**
	 * UDP广播上线包
	 */
	private void startUDPOnline(){
		mTimeCount = 0;
		final DeviceOnlineUDP DeviceOnlineUDP = new DeviceOnlineUDP(DEVICE_MAC, DEVICE_PID, DEVICE_DID, DEVICE_PASSCODE);
		
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				mTimeCount ++; 
				if (mTimeCount >= DeviceVariant.DeviceOnlineMaxTimes + 1){
					timer.cancel();
					handler.sendEmptyMessage(ActivityHandlerParams.DeviceOnlineEnd);
				}
				if(mTimeCount <= DeviceVariant.DeviceOnlineMaxTimes)
					if(DeviceVariant.umReceivePoolUdp != null)
						DeviceVariant.umReceivePoolUdp.InsertSendItem(DeviceOnlineUDP.getData(), "255.255.255.255");
				
			}
		}, 110,1000);
	}
	
	private void initUDPTCP(){
		if (DeviceVariant.umReceivePoolUdp != null)
			DeviceVariant.umReceivePoolUdp.stop();
		
		DeviceVariant.umReceivePoolUdp = new ReceivePoolUDP(DeviceVariant.AppUDPListeningPort, DeviceVariant.DeviceUDPListeningPort, handler);
	}
	
	private void connectMQTT(){
		if (DeviceVariant.umMqtt == null){
			DeviceVariant.umMqtt = new MyMqtt("main");
		}
		
		String[] publishTopic = new String[1];
		String[] inSubTopics = new String[1];
		publishTopic[0] = "device2app/"+DEVICE_DID;
		inSubTopics[0] = "app2device/" + DEVICE_DID;
		
		DeviceVariant.umMqtt.init(ActivityMain.this, publishTopic, inSubTopics, handler);
	}
}
