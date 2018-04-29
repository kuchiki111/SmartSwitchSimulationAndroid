package com.smartswitchsimulationandroid.command;

import com.smartswitchsimulationandroid.udptcp.UDPData;

public class AppGetDeviceStateUDPAck {
	public byte[] status;
	public byte MQTTEnable;
	/** ���ڿ���Э�鲿�ֵ�varlen���֣�������128ʱ��Ҫʹ��2λ������16384��Ҫʹ��3λ..... */
	protected int delta = 0;
	/** ͷ���̶��ĳ��� */
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
		System.arraycopy(receiveData, hearderLength + delta, status, 0, 8); // �豸��ʱ����Ϣ
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

		// ����ͷ���ܹ�8�ֽ�
		hearderLength = 8;
	}
	// public byte[] time;
	// /**���ڿ���Э�鲿�ֵ�varlen���֣�������128ʱ��Ҫʹ��2λ������16384��Ҫʹ��3λ.....*/
	// protected int delta = 0;
	// /**ͷ���̶��ĳ���*/
	// protected int hearderLength = 0;
	// protected int ProtocolVer;
	// public int VarLen;
	// public int Flag;
	// public int Cmd;
	// /**��ǰ����ģʽ��0���ֶ���1��ѭ����ʱ��2������ʱ*/
	// public int workMode;
	// /**���״̬;0���رգ�1��������*/
	// public int outputState;
	// public AppGetDeviceStateUDPAck(UDPData udpData){
	// byte[] receiveData =udpData.data;
	// init(receiveData);
	// time = new byte[6];
	// System.arraycopy(receiveData, hearderLength
	// + delta, time, 0, 6); //�豸��ʱ����Ϣ
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
	// //����ͷ���ܹ�8�ֽ�
	// hearderLength = 8;
	// }
}
