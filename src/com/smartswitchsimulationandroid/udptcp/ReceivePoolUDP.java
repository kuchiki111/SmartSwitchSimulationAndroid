//package com.smartswitchsimulationandroid.udptcp;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
//import com.chinesetimer.command.AppControlSocketAckBase;
//import com.chinesetimer.command.AppGetDeviceParamsAck;
//import com.chinesetimer.command.AppGetDeviceStateAck;
//import com.chinesetimer.command.AppGetDeviceStateUDPAck;
//import com.chinesetimer.command.AppSearchWifiAck;
//import com.chinesetimer.command.AppSetDeviceCountDownAck;
//import com.chinesetimer.command.AppSetDeviceCycleTimingAck;
//import com.chinesetimer.command.AppSetDeviceInternetAck;
//import com.chinesetimer.command.AppSetDeviceModeAck;
//import com.chinesetimer.command.AppSetDeviceModeUDPAck;
//import com.chinesetimer.constant.MainActivityHandlerParams;
//import com.chinesetimer.device.DeviceInfo;
//import com.chinesetimer.params.AppVariant;
//
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
///**
// * udp�����߳� �ṩ�����̣߳������̣߳��ͽ�����Ϣ�����߳�3���߳�
// * 
// * @author hxc
// *
// */
//public class ReceivePoolUDP {
//	/** ������ */
//	protected final int MAX_AVAILABLE = 100;
//	public DatagramSocket s = null;
//	/** Ŀ���ַ */
//	// private InetAddress mDestIP;
//	/** udp���Ͷ˿� */
//	private int mDestPort;
//	/** �Ƿ�����߳� */
//	protected boolean isStopped = false;
//	/** udp���ݽ��ն��� */
//	BlockingQueue<UDPData> receiveQueue = new LinkedBlockingQueue<UDPData>();
//	/** udp���ݷ��Ͷ��� */
//	BlockingQueue<SendBuffClass> sendQueue = new LinkedBlockingQueue<SendBuffClass>();
//
//	private SendThread mSendThread;
//	private ReciveThread mReciveThread;
//	private ReceiveDataHandleThread mReceiveDataHandleThread;
//
//	private class SendBuffClass {
//		/** �Ƿ��ط� */
//		boolean needResend;
//		/** ������ */
//		byte[] item;
//		String ip;
//
//		/**
//		 * ���캯��
//		 */
//		public SendBuffClass(int dataLen, String ip) {
//			item = new byte[dataLen];
//			this.ip = ip;
//		}
//	}
//
//	/**
//	 * ���캯��
//	 * 
//	 * @param destationIP
//	 *            Ŀ��ip������㲥����д255.255.255.255
//	 * @param destationPort
//	 *            Ŀ��Ķ˿ڣ�����ϵͳҪ����д�Է��Ķ˿�
//	 * @param localPort
//	 *            ����ϵͳҪ����д������udp�����˿�
//	 * @param handler
//	 */
//	public ReceivePoolUDP(int destationPort, int localPort, Handler handler) {
//		this.mDestPort = destationPort;
//		// try {
//		// mDestIP = InetAddress.getByName(destationIP);
//		// } catch (UnknownHostException e1) {
//		// // TODO Auto-generated catch block
//		// e1.printStackTrace();
//		// }
//		try {
//			if (localPort == 0)
//				s = new DatagramSocket();
//			else
//				s = new DatagramSocket(localPort);
//			// s.setBroadcast(true);
//			isStopped = false;
//
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		mReciveThread = new ReciveThread();// ���������߳�
//		mSendThread = new SendThread();// ���������߳�
//		mReceiveDataHandleThread = new ReceiveDataHandleThread(handler);// ����������Ϣ������߳�
//		mReciveThread.start();
//		mSendThread.start();
//		mReceiveDataHandleThread.start();
//	}
//
//	/**
//	 * �ж��̵߳�ִ�У��ͷ�socket���ӣ������������ʱ��Ҫ�ֹ����� ����ֵ����
//	 */
//	public void stop() {
//		isStopped = true;
//		try {
//			receiveQueue.put(new UDPData(new byte[] { 00, 00, 00, 00 }));
//			sendQueue.put(new SendBuffClass(0, "255.255.255.255"));
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		mReciveThread.interrupt();
//		mSendThread.interrupt();
//		mReceiveDataHandleThread.interrupt();
//		if (s != null) {
//			s.close();
//			s.disconnect();
//			s = null;
//		}
//
//		Log.d("UDP", "Receive thread end");
//	}
//
//	/**
//	 * ����ջ�����β��׷��һ�����ݰ��������ǰ���������ݰ��Ѿ�����MAX_AVAILABLE�����޷����� ����ֵ����
//	 */
//	private synchronized void InsertItem(byte[] item, String remoteIP) {
//
//		UDPData tempItem = new UDPData();
//		tempItem.data = item.clone();
//		tempItem.IP = remoteIP;
//
//		if (receiveQueue.size() < MAX_AVAILABLE) {
//			Log.d("RecievePool", Hex.encodeHexStr(tempItem.data));
//			try {
//				receiveQueue.put(tempItem);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public void run() {
//	}
//
//	public synchronized void InsertSendItem(byte[] item, String deviceIP) {
//		Log.i("aaa", "InsertSendItem");
//		if (sendQueue.size() < MAX_AVAILABLE) {
//			SendBuffClass senditem = new SendBuffClass(item.length, deviceIP);
//			senditem.needResend = false;
//			senditem.item = item.clone();
//			try {
//				sendQueue.put(senditem);
//				Log.i("aaa", "InsertSendItem1");
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/** ����udp������Ϣ�Ĵ��� */
//	public class ReceiveDataHandleThread extends Thread {
//
//		private boolean isOver = false;
//		private Handler mHandler = null;
//
//		public ReceiveDataHandleThread(Handler handler) {
//			mHandler = handler;
//		}
//
//		public void run() {
//			while (!Thread.currentThread().isInterrupted() && !isOver) {
//				try {
//
//					// Log.i("main", "AppVariant.umReceivePoolUdp != null");
//					UDPData udata = receiveQueue.take();
//					Log.i("main.udata", Hex.encodeHexStr(udata.data));
//					// Intent intent=new Intent();
//					if (udata != null) {
//						Log.i("main.command", String.valueOf(udata.getCommand()));
//						switch (udata.getCommand()) {
//						case UDPTCPCommand.APP_SEARCH_WIFIMODULE_ACK: {
//							Message msg = new Message();
//							AppSearchWifiAck ack = new AppSearchWifiAck(udata);
//
//							// AppVariant.ctAllDeivceList.insertItem(new
//							// DeviceInfo(ack),false,DeviceInfo.LocalConnected);
//							// AppVariant.IS_INTERNET=false;//�ھ������������豸���л��ɾ�����ģʽ
//							Log.d("UDP", "localnetwork model" + Hex.encodeHexStr(udata.data));
//							msg.what = MainActivityHandlerParams.LocalNetWorkSearchDevice;
//							msg.obj = ack;
//							mHandler.sendMessage(msg);
//						}
//							break;
//						case UDPTCPCommand.DEVICE_NOTICE_EXIST: {
//
//							if (mHandler != null)// ���mainactivity�����ڽ��շ����豸״̬����������
//							{
//								// Ŀǰ6.1�豸�����߹㲥����6.2.2�����豸��app��ѯ����Ӧ������ʹ��ͬһ������г�������������ǰ����passcode������û��passcode
//								Message msg = new Message();
//								AppSearchWifiAck ack = new AppSearchWifiAck(udata);
//
//								msg.what = MainActivityHandlerParams.DeviceNoticeExist;
//								msg.obj = ack;
//								mHandler.sendMessage(msg);
//							}
//						}
//							break;
//						case UDPTCPCommand.APP_GET_DEVICE_STATE_UDP_ACK: {
//							Message msg = new Message();
//							AppGetDeviceStateUDPAck ack = new AppGetDeviceStateUDPAck(udata);
//							msg.what = MainActivityHandlerParams.AppGetDeviceStateUDP;
//							// HashMap<String, Object> infos = new
//							// HashMap<String, Object>();
//							// infos.put("ip",udata.IP);
//							// byte[] status = new byte[8];
//							// System.arraycopy(ack.time, 0, status, 0, 6);
//							// //�豸��ʱ����Ϣ
//							// status[6] = (byte) ack.workMode;
//							// status[7] = (byte) ack.outputState;
//
//							// infos.put("status", ack.status);
//
//							msg.obj = ack;
//							mHandler.sendMessage(msg);
//						}
//							break;
//						case UDPTCPCommand.APP_SET_DEVICE_INTERNET_ACK: {
//							Message msg = new Message();
//							AppSetDeviceInternetAck ack = new AppSetDeviceInternetAck(udata);
//							msg.what = MainActivityHandlerParams.SetDeviceInternet;
//							msg.obj = ack;
//							mHandler.sendMessage(msg);
//						}
//							break;
//						case UDPTCPCommand.APP_CONTROL_SOCKET_ACK: {
//							AppControlSocketAckBase appControlSocketAck = new AppControlSocketAckBase(udata.data);
//							Message msg = new Message();
//							switch (appControlSocketAck.actionType) {
//
//							case AppVariant.DeviceModeChangeAck:
//								AppSetDeviceModeUDPAck ack = new AppSetDeviceModeUDPAck(udata);
//								if (ack.operationState == 0) {
//									msg.obj = ack;
//									msg.what = MainActivityHandlerParams.DeviceModeChange;
//									mHandler.sendMessage(msg);
//								} else {
//									mHandler.sendEmptyMessage(MainActivityHandlerParams.DeviceModeChangeFailed);
//								}
//								break;
//							}
//							break;
//						}
//						}
//					}
//				} catch (InterruptedException e) {
//
//					e.printStackTrace();
//				}
//
//			}
//
//		}
//	}
//
//	private class ReciveThread extends Thread {
//
//		@Override
//		public void run() {
//			byte[] message = new byte[AppVariant.MessageLength];
//			try {
//
//				while (!isStopped && !Thread.currentThread().isInterrupted()) {
//					// ׼����������
//					// AppVariant.WIFI_LOCK.acquire();
//					if (s != null) {
//						Arrays.fill(message, (byte) 0);
//						DatagramPacket datagramPacket = new DatagramPacket(message, AppVariant.MessageLength);
//						s.receive(datagramPacket);
//
//						// Log.i("RecievePool","CMD="+Integer.toHexString(datagramPacket.getData()[4]
//						// & 0xff));
//
//						String remoteIP = datagramPacket.getAddress().toString();
//						remoteIP = remoteIP.substring(1, remoteIP.length());// ȥ����ǰ���\
//						InsertItem(datagramPacket.getData(), remoteIP);
//
//						datagramPacket = null;
//
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//				Log.d("RecievePool", "ReceiveUPD error");
//			}
//		}
//
//	}
//
//	private class SendThread extends Thread {
//
//		@Override
//		public void run() {
//			while (!Thread.currentThread().isInterrupted() && !isStopped) {
//
//				byte[] item;
//				try {
//					SendBuffClass sendBuffClass = sendQueue.take();
//					item = sendBuffClass.item;
//					InetAddress destIP = InetAddress.getByName(sendBuffClass.ip);
//					if (item != null) {
//						item = Arrays.copyOf(item, item.length);
//						Log.d("SendPool", Hex.encodeHexStr(item));
//
//						// ����UDP���ݰ�����Ҫָ��Ŀ���ַ���˿ں�
//						DatagramPacket packet = new DatagramPacket(item, item.length, destIP, mDestPort);
//
//						if (s != null && !s.isClosed())
//							s.send(packet);
//					}
//
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//			}
//			Log.d("SendPool", "SendPool end");
//		}
//
//	}
//
//	public ReceiveDataHandleThread getmReceiveDataHandleThread() {
//		return mReceiveDataHandleThread;
//	}
//
//	public void setmReceiveDataHandleThread(ReceiveDataHandleThread mReceiveDataHandleThread) {
//		this.mReceiveDataHandleThread = mReceiveDataHandleThread;
//	}
//
//}
