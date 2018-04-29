package com.smartswitchsimulationandroid.udptcp;

import java.net.DatagramSocket;
import java.net.SocketException;

import android.os.StrictMode;

public class PoolUdp implements Runnable {
	public static DatagramSocket s = null;
	/** 最大包数 */
	protected final int MAX_AVAILABLE = 100;
	/** 是否结束线程 */
	protected boolean isStopped = false;

	public PoolUdp(int port) {
		if (s == null) {
			// StrictMode.setThreadPolicy(new
			// StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
			// StrictMode.setVmPolicy(new
			// StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
			//
			// StrictMode.setThreadPolicy(new
			// StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
			// StrictMode.setVmPolicy(new
			// StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
			//

			try {
				s = new DatagramSocket(port);
				// s.setBroadcast(true);
				isStopped = false;

			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
