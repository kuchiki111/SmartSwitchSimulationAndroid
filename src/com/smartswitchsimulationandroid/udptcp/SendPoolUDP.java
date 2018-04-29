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
 * udp发送线程
 * 
 * @author hxc
 *
 */
public class SendPoolUDP extends PoolUdp {

	/** 发包信号量,用户 */
	private final Semaphore SendPoolSem = new Semaphore(0, true);
	/** 发包类列表 */
	protected List<SendBuffClass> items = new ArrayList<SendBuffClass>();

	int destPort;// udp发送端口

	/** 发包信息类 */
	private class SendBuffClass {
		/** 是否重发 */
		boolean needResend;
		/** 包数组 */
		byte[] item;

		/**
		 * 构造函数
		 */
		public SendBuffClass(int dataLen) {
			item = new byte[dataLen];
		}
	}

	/** 目标地址 */
	private InetAddress DestIP;
	/** 是否结束 */
	private volatile boolean isStopped = false;

	/**
	 * 构造函数
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
	 * 结束线程
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
	 * 发送SendPool中缓冲区中的第一个数据，并删除该数据
	 */
	private synchronized void SendItem() {
		if (items.size() > 0) {
			byte[] item = items.get(0).item;
			// if(items.get(0).needResend)
			// {
			// switch (item[1])
			// {
			// //是否加入重发队列，以后可能需要，请勿删除
			// // case CommandCode.PSW_REQ:
			// //
			// // AppVariant.RESEND_POOL.insertItem(item);
			// // break;
			// }
			// }
			item = Arrays.copyOf(item, item.length);

			Log.d("SendPool", Hex.encodeHexStr(item));

			// 定义UDP数据包，需要指定目标地址及端口号
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
	// * @param item byte[]数据
	// * @param needResend
	// 表示是否需要加入加入重发队列，重发队列中还会继续判断该协议命令是否允许重发。如果某些允许重发的命令本次不要重发，设置为false。
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
