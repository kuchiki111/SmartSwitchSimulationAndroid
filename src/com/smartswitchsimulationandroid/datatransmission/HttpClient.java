package com.smartswitchsimulationandroid.datatransmission;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.smartswitchsimulationandroid.constant.ActivityHandlerParams;
import com.smartswitchsimulationandroid.parmars.UrlParams;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class HttpClient {
	private static AsyncHttpClient client;
	private static String url;
	private static int timerSet = 5;

	public AsyncHttpClient getClient() {
		client = new AsyncHttpClient();
		client.setConnectTimeout(timerSet);

		return client;
	}

	public HttpClient() {
		client = new AsyncHttpClient();
	}
	
	/**
	 * 设备注册
	 * @param context
	 * @param did
	 * @param mac
	 * @param productkey
	 * @param passcode
	 * @param handler
	 */
	public void doRegister(final Context context, String did, String mac, String productkey, String passcode, final Handler handler){
		url = UrlParams.getRegUrl();
		
		RequestParams params = new RequestParams();
		params.put("did", did);
		params.put("mac", mac);
		params.put("productkey", productkey);
		params.put("passcode",passcode);
		
		client.post(url,params,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				try {
					int code = 2;
					String Strcode = "";
					if(arg2!=null){
						Strcode = new String(arg2);
					}
					
					if("code=0".equals(Strcode)){
						code = 0;
					}else if("code=1".equals(Strcode)) {
						code = 1;
					}
					
//					JSONObject jo = new JSONObject(new String(arg2));
					Message msg = new Message();
					switch (code) {
					case 0:
						msg.what = ActivityHandlerParams.DeviceRegister;
						msg.obj = new String(arg2);
						handler.sendMessage(msg);
						break;
					case 1:
						msg.what = ActivityHandlerParams.DeviceReRegister;
						msg.obj = new String(arg2);
						handler.sendMessage(msg);
						break;
					case 2:
						msg.what = ActivityHandlerParams.DeviceRegisterError;
						msg.obj = new String(arg2);
						handler.sendMessage(msg);
						break;
					default:
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(ActivityHandlerParams.DeviceRegisterTimeout);
			}
		});
	}
	
	/**
	 * 获取mqtt服务器
	 * @param context
	 * @param did
	 * @param handler
	 */
	public void getMqttServer(final Context context, String did, final Handler handler) {
		url = UrlParams.getRegUrl();
		
		RequestParams params = new RequestParams();
		params.put(did, did);
		
		client.post(url, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				try {
					String mqttInfo = new String(arg2);
					Message msg = new Message();
					msg.what = ActivityHandlerParams.DeviceGetMqttServer;
					msg.obj = new String(mqttInfo);
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(ActivityHandlerParams.DeviceGetMqttServerTimeout);
			}
		});
	}
}
