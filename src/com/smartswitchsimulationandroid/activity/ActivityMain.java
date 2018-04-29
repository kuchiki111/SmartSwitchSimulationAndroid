package com.smartswitchsimulationandroid.activity;

import com.smartswitchsimulationandroid.constant.ActivityHandlerParams;
import com.smartswitchsimulationandroid.datatransmission.HttpClient;
import com.smartswitchsimulationandroid.parmars.ToolParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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
	/** 载入窗口 */
	private ProgressDialog loadingDialog;
	/** 实例化后台请求 */
	private HttpClient httpClient = new HttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setTitle("设备注册中...");
		loadingDialog.setCancelable(false);
		
		initViews();
		initEvents();
	}
	
	/**
	 * 初始化页面
	 */
	private void initViews() {
		btRegister = (Button) findViewById(R.id.btRegister);
		
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
		
	}
	
	/**
	 * 设备注册
	 */
	protected void toDeviceRegister() {
		String did = "test";
		String mac = "test";
		String passcode = "test";
		String productkey = "test";
		if (ToolParams.isNetWork(getApplicationContext())) {
			loadingDialog.show();
			httpClient.doRegister(ActivityMain.this, did, mac, productkey, passcode, handler);
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
				break;
			case ActivityHandlerParams.DeviceReRegister:
				loadingDialog.dismiss();
				btRegister.setEnabled(false);
				Toast.makeText(ActivityMain.this, "设备重置", Toast.LENGTH_SHORT).show();
				break;
			case ActivityHandlerParams.DeviceRegisterError:
				loadingDialog.dismiss();
				Toast.makeText(ActivityMain.this, "操作出错", Toast.LENGTH_SHORT).show();
				break;
			case ActivityHandlerParams.DeviceRegisterTimeout:
				loadingDialog.dismiss();
				Toast.makeText(ActivityMain.this, "注册超时", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	/**
	 * UDP广播上线包
	 */
}
