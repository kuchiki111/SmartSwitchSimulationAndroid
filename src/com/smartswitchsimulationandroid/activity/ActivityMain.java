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
	/** �豸ע�ᰴť*/
	private Button btRegister;
	/** ���봰�� */
	private ProgressDialog loadingDialog;
	/** ʵ������̨���� */
	private HttpClient httpClient = new HttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setTitle("�豸ע����...");
		loadingDialog.setCancelable(false);
		
		initViews();
		initEvents();
	}
	
	/**
	 * ��ʼ��ҳ��
	 */
	private void initViews() {
		btRegister = (Button) findViewById(R.id.btRegister);
		
	}
	
	/**
	 * ��������ť����
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
	 * �豸ע��
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
			Toast.makeText(getApplicationContext(), "������", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(ActivityMain.this, "���豸ע��", Toast.LENGTH_SHORT).show();
				break;
			case ActivityHandlerParams.DeviceReRegister:
				loadingDialog.dismiss();
				btRegister.setEnabled(false);
				Toast.makeText(ActivityMain.this, "�豸����", Toast.LENGTH_SHORT).show();
				break;
			case ActivityHandlerParams.DeviceRegisterError:
				loadingDialog.dismiss();
				Toast.makeText(ActivityMain.this, "��������", Toast.LENGTH_SHORT).show();
				break;
			case ActivityHandlerParams.DeviceRegisterTimeout:
				loadingDialog.dismiss();
				Toast.makeText(ActivityMain.this, "ע�ᳬʱ", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	/**
	 * UDP�㲥���߰�
	 */
}
