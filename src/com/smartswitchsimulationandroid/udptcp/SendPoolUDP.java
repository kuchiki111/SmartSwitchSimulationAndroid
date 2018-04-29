package com.smartswitchsimulationandroid.udptcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import android.os.StrictMode;
import android.util.Log;

/**
 * udp�����߳�
 * 
 * @author hxc
 *
 */
public class SendPoolUDP extends PoolUdp {

	/** �����ź���,�û� */
	private final Semaphore SendPoolSem = new Semaphore(0, true);
	/** �������б� */
	protected List<SendBuffClass> items = new ArrayList<SendBuffClass>();

	int destPort;// udp���Ͷ˿�

	/** ������Ϣ�� */
	private class SendBuffClass {
		/** �Ƿ��ط� */
		boolean needResend;
		/** ������ */
		byte[] item;

		/**
		 * ���캯��
		 */
		public SendBuffClass(int dataLen) {
			item = new byte[dataLen];
		}
	}

	/** Ŀ���ַ */
	private InetAddress DestIP;
	/** �Ƿ���� */
	private volatile boolean isStopped = false;

	/**
	 * ���캯��
	 */
	public SendPoolUDP(String destationIP, int destationPort, int localPort) {
		super(localPort);

		try {
			this.destPort = destationPort;
			DestIP = InetAddress.getByName(destationIP);
			new Thread(this).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * �����߳�
	 */
	public void stop() {
		Log.i("SendPool", "is Stopped");
		isStopped = true;
		Thread.currentThread().interrupt();
		if (s != null) {
			s.close();
			s.disconnect();
			s = null;
		}
	}

	/**
	 * ����SendPool�л������еĵ�һ�����ݣ���ɾ��������
	 */
	private synchronized void SendItem() {
		if (items.size() > 0) {
			byte[] item = items.get(0).item;
			// if(items.get(0).needResend)
			// {
			// switch (item[1])
			// {
			// //�Ƿ�����ط����У��Ժ������Ҫ������ɾ��
			// // case CommandCode.PSW_REQ:
			// //
			// // AppVariant.RESEND_POOL.insertItem(item);
			// // break;
			// }
			// }
			item = Arrays.copyOf(item, item.length);

			Log.d("SendPool", Hex.encodeHexStr(item));

			// ����UDP���ݰ�����Ҫָ��Ŀ���ַ���˿ں�
			DatagramPacket packet = new DatagramPacket(item, item.length, DestIP, destPort);
			try {
				if (s != null && !s.isClosed())
					s.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			items.remove(0);
		}

	}

	// /**
	// *
	// * @param item byte[]����
	// * @param needResend
	// ��ʾ�Ƿ���Ҫ��������ط����У��ط������л�������жϸ�Э�������Ƿ������ط������ĳЩ�����ط�������β�Ҫ�ط�������Ϊfalse��
	// */
	// public synchronized void InsertItem(byte[] item,boolean needResend) {
	//
	// if (items.size()<MAX_AVAILABLE)
	// {
	// SendBuffClass senditem=new SendBuffClass();
	// senditem.needResend=needResend;
	// senditem.item=item.clone();
	// items.add(senditem);
	// SendPoolSem.release();
	// }
	// }
	public synchronized void InsertItem(byte[] item, int itemLen) {

		if (items.size() < MAX_AVAILABLE) {
			SendBuffClass senditem = new SendBuffClass(itemLen);
			senditem.needResend = false;
			senditem.item = item.clone();
			items.add(senditem);
			SendPoolSem.release();
		}
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted() && !isStopped) {
			try {
				if (SendPoolSem.tryAcquire(3, TimeUnit.SECONDS)) {
					Thread.sleep(200);
					SendItem();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.d("SendPool", "SendPool end");
	}

}
