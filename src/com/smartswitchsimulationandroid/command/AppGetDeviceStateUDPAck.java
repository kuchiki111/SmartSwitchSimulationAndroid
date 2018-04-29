package com.smartswitchsimulationandroid.command;

import com.smartswitchsimulationandroid.udptcp.UDPData;

public class AppGetDeviceStateUDPAck {
	public byte[] status;
	public byte MQTTEnable;
	/** 用于控制协议部分的varlen部分，当超过128时需要使用2位，超过16384需要使用3位..... */
	protected int delta = 0;
	/** 头部固定的长度 */
	protected int hearderLength = 0;
	protected int ProtocolVer;
	public int VarLen;
	public int Flag;
	public int Cmd;
	public String ip;

	public AppGetDeviceStateUDPAck(UDPData udpData) {
		byte[] receiveData = udpData.data;
		init(receiveData);

		status = new byte[8];
		System.arraycopy(receiveData, hearderLength + delta, status, 0, 8); // 设备的时间信息
		ip = udpData.IP;
		MQTTEnable = receiveData[hearderLength + delta + 8];
	}

	private void init(byte[] receiveData) {
		// Pack_Header
		ProtocolVer = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		// Pack_Length
		do {
			int num = receiveData[4 + delta] & 0x7f;
			int i = delta;
			while (i > 0) {
				num *= 128;
				i--;
			}
			VarLen += num;
		} while ((receiveData[4 + delta++] & 0x80) == 0x80);
		delta--;
		// Flag
		Flag = receiveData[5 + delta] & 0xff;
		// Command_Type
		Cmd = ((receiveData[6 + delta] & 0xff) << 8) | (receiveData[7 + delta] & 0xff);

		// 以上头部总共8字节
		hearderLength = 8;
	}
	// public byte[] time;
	// /**用于控制协议部分的varlen部分，当超过128时需要使用2位，超过16384需要使用3位.....*/
	// protected int delta = 0;
	// /**头部固定的长度*/
	// protected int hearderLength = 0;
	// protected int ProtocolVer;
	// public int VarLen;
	// public int Flag;
	// public int Cmd;
	// /**当前工作模式。0：手动；1：循环定时；2：倒计时*/
	// public int workMode;
	// /**输出状态;0：关闭；1：开启。*/
	// public int outputState;
	// public AppGetDeviceStateUDPAck(UDPData udpData){
	// byte[] receiveData =udpData.data;
	// init(receiveData);
	// time = new byte[6];
	// System.arraycopy(receiveData, hearderLength
	// + delta, time, 0, 6); //设备的时间信息
	// workMode = receiveData[hearderLength + 6 + delta] & 0xff;
	// outputState = receiveData[hearderLength + 7 + delta] & 0xff;
	// }
	// private void init(byte[] receiveData){
	// // Pack_Header
	// ProtocolVer = (receiveData[0] & 0xff << 24)
	// | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8)
	// | (receiveData[3] & 0xff);
	// // Pack_Length
	// do {
	// int num = receiveData[4 + delta] & 0x7f;
	// int i = delta;
	// while (i > 0) {
	// num *= 128;
	// i--;
	// }
	// VarLen += num;
	// } while ((receiveData[4 + delta++] & 0x80) == 0x80);
	// delta--;
	// // Flag
	// Flag = receiveData[5 + delta] & 0xff;
	// // Command_Type
	// Cmd = ((receiveData[6 + delta] & 0xff) << 8)
	// | (receiveData[7 + delta] & 0xff);
	//
	// //以上头部总共8字节
	// hearderLength = 8;
	// }
}
