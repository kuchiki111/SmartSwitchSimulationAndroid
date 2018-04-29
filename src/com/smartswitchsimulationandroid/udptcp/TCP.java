//package com.smartswitchsimulationandroid.udptcp;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.Serializable;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.net.SocketAddress;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
//import com.chinesetimer.command.DeviceToAppResponse;
//import com.chinesetimer.params.AppVariant;
//
//import android.content.Context;
//import android.content.res.Resources.Theme;
//import android.os.Handler;
//import android.os.Message;
//import android.os.StrictMode;
//import android.util.Log;
//
//public class TCP extends TCPorMQTTInterface implements Runnable {
//	private Socket mClient;
//	InputStream mInput;
//	OutputStream mOutput;
//	Handler mHandler;
//	BlockingQueue<byte[]> mSendQueue;
//
//	// //�ط����е�����
//	// private List<ResendItem> resendList = new ArrayList<ResendItem>();
//
//	public TCP(Context context) {
//		mContext = context;
//		mSendQueue = new LinkedBlockingQueue<byte[]>();
//	}
//
//	// private class ResendItem
//	// {
//	// public byte[] item;
//	// /**�ط�����*/
//	// public int resendCount;
//	// /**��ʱ��������Ϊ4,2,0������һ�Σ�ÿ��500�����ۼ�һ�Σ���ʼֵ6���Ա����Ѽ����ط����У����Ϸ���һ�ε�����*/
//	// public int timeCount;
//	// public ResendItem(byte[] item)
//	// {
//	// this.item = item;
//	// this.resendCount = 0;
//	// timeCount = 6;
//	// }
//	// }
//
//	// /**
//	// * �ط��߳���
//	// * @author hxc
//	// *
//	// */
//	// private class ResendThread extends Thread
//	// {
//	//
//	// @Override
//	// public void run() {
//	//
//	// while(!isStopped)
//	// {
//	// try {
//	// Thread.sleep(500);//ÿ��500ms��ѯһ��
//	// //Log.i("TCP","�ط����");
//	// for(int i=0;i<resendList.size();i++){
//	// ResendItem resend_item = resendList.get(i);
//	// resend_item.timeCount--;
//	// if(resend_item.timeCount==4 || resend_item.timeCount==2 ||
//	// resend_item.timeCount==0)
//	// {
//	// insertItem(resend_item.item);
//	// Thread.sleep(10);//���η���֮����10ms
//	// if(++resend_item.resendCount==3){//�ط�������3�Σ����ط�����ɾ��
//	// i--;
//	// resendList.remove(i);
//	// }
//	// }
//	// }
//	// } catch (InterruptedException e1) {
//	// // TODO Auto-generated catch block
//	// e1.printStackTrace();
//	// }
//	// }
//	// }
//	//
//	// }
//	/**
//	 * ��ʼ��TCP��Client���ӣ�����ʼ������������
//	 * 
//	 * @param serverIP
//	 *            TCP��������ip��ַ
//	 * @param serverPort
//	 *            TCP�������Ķ˿�
//	 */
//	public Boolean init(String serverIP, int serverPort, Handler handler) {
//
//		try {
//			mClient = new Socket();
//			InetSocketAddress addr = new InetSocketAddress(serverIP, serverPort);
//			mClient.connect(addr, 5000);
//			// mClient = new Socket(serverIP,serverPort);
//			mInput = mClient.getInputStream();
//			mOutput = mClient.getOutputStream();
//			mHandler = handler;
//			new Thread(this).start();
//			// (new ResendThread()).start();
//			(new SendThread()).start();
//
//			return true;
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//
//	}
//
//	public void insertItem(byte[] item, boolean withRelatedDevices) {
//		if (mClient != null && mOutput != null && !mClient.isClosed() && mClient.isConnected()) {
//			// mSendQueue.add(item);
//			try {
//				// mOutput.write(item.clone());
//				mSendQueue.add(item);
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			Log.i("TCP", "TCP is closed");
//			if (mHandler != null)
//				mHandler.sendEmptyMessage(DeviceToAppResponse.DeviceTcpShutDown);
//		}
//	}
//
//	public void insertItemWithResend(byte[] item, boolean withRelatedDevices) {
//		if (mClient != null && mOutput != null && !mClient.isClosed() && mClient.isConnected()) {
//			try {
//				// mOutput.write(item.clone());
//				mSendQueue.add(item);
//				ResendItem resendItem = new ResendItem(item.clone());
//				resendList.add(0, resendItem);
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			Log.i("TCP", "TCP is closed");
//			if (mHandler != null)
//				mHandler.sendEmptyMessage(DeviceToAppResponse.DeviceTcpShutDown);
//		}
//	}
//
//	/**
//	 * �ж��̵߳�ִ�У��ͷ�socket���ӣ������������ʱ��Ҫ�ֹ����� ����ֵ����
//	 */
//	@Override
//	public void stop() {
//		super.stop();
//		isStopped = true;
//
//		// mHandler = null;
//
//		if (mClient != null) {
//			try {
//				if (mInput != null)
//					mInput.close();
//				if (mOutput != null)
//					mOutput.close();
//				mClient.close();
//				mClient = null;
//				Thread.currentThread().interrupt();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		Log.d("TCP", "TCP Receive thread end");
//	}
//
//	@Override
//	public void run() {
//		byte[] message = new byte[AppVariant.MessageLength];
//		while (!isStopped && !Thread.currentThread().isInterrupted()) {
//			if (mClient.isConnected()) {
//				try {
//					int len = mInput.read(message);
//					if (len == -1) {
//						if (mHandler != null)
//							mHandler.sendEmptyMessage(DeviceToAppResponse.DeviceTcpShutDown);
//						stop();
//						return;
//					}
//					Log.i("TCP receive", Hex.encodeHexStr(message));
//					handler(message.clone(), mHandler, null);
//					Arrays.fill(message, (byte) 0);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	private class SendThread extends Thread {
//
//		@Override
//		public void run() {
//			while (!this.isInterrupted() && !isStopped) {
//				try {
//					byte[] item = mSendQueue.take();
//					if (item != null && item.length > 0) {
//						try {
//							Log.i("tcp send", Hex.encodeHexStr(item));
//							mOutput.write(item.clone());
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public Boolean init(Context context, String[] publishTopic, String[] myTopics, Handler handler) {
//		// mqtt��Ҫ�˷�����TCP����Ч
//
//		return null;
//	}
//
//	public void changePublishTopic(String[] topics) {
//
//	}
//}
