package com.smartswitchsimulationandroid.udptcp;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.util.concurrent.Semaphore;

/** ����Э���������࣬��������ͬ������ */
public class ReceiveDataHandle implements Runnable {

	private boolean isOver = false;
	private Application application;
	public Semaphore ReceivePoolSem = new Semaphore(0, true);
	private Handler mHandler = null;

	public ReceiveDataHandle(Application application) {
		this.application = application;
		new Thread(this).start();
	}

	public void Over() {
		isOver = true;
		Thread.currentThread().interrupt();
		Log.d("test", "ReceiveHandle thread end");
	}

	/**
	 * �˷���ֻ��Mainactivity�е���
	 * 
	 * @param handler
	 */
	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted() && !isOver) {
			try {
				ReceivePoolSem.acquire();
				Log.i("main", "ReceivePoolSem.acquire()");
				// if (AppVariant.umReceivePoolUdp != null) {
				// Log.i("main","AppVariant.umReceivePoolUdp != null");
				// UDPData udata = AppVariant.umReceivePoolUdp.GetItem();
				// Log.i("main.udata",Hex.encodeHexStr(udata.data));
				// //Intent intent=new Intent();
				// if (udata != null) {
				// Log.i("main.command",String.valueOf(udata.getCommand()));
				// switch (udata.getCommand()) {
				// case UDPTCPCommand.APP_SEARCH_WIFIMODULE_ACK:
				// {
				// AppSearchWifiAck ack = new AppSearchWifiAck(udata);
				//
				// AppVariant.umAllDeivceList.insertItem(new
				// DeviceInfo(ack),false,DeviceInfo.LocalConnected);
				// AppVariant.IS_INTERNET=false;//�ھ������������豸���л��ɾ�����ģʽ
				// Log.d("UDP",
				// "localnetwork model"+Hex.encodeHexStr(udata.data));
				// }
				// break;
				// case UDPTCPCommand.DEVICE_NOTICE_EXIST:
				// {
				// Log.i("DEVICE_NOTICE_EXIST", "True");
				// if(mHandler!=null)//���mainactivity�����ڽ��շ����豸״̬����������
				// {
				// // AppSearchWifiAck ack = new AppSearchWifiAck(udata);
				// // AppVariant.umAllDeivceList.insertItem(new
				// DeviceInfo(ack),false);
				// // AppVariant.IS_INTERNET=false;//�ھ������������豸���л��ɾ�����ģʽ
				// // Log.d("UDP", "find device"+Hex.encodeHexStr(udata.data));
				// //
				// mHandler.sendEmptyMessage(MainActivityHandlerParams.DeviceNoticeExist);
				// }
				// }
				// break;
				// //��Ӱ��������
				// // case CommandCode.DVC_STA:
				// // //Handle_DVC_STA(buf);
				// // break;
				// }
				// //application.sendBroadcast(intent);
				//
				// }
				// }
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

	}
}
