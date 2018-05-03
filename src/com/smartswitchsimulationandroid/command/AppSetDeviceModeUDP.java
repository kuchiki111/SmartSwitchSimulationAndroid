package com.smartswitchsimulationandroid.command;

import com.smartswitchsimulationandroid.udptcp.UDPData;

public class AppSetDeviceModeUDP extends AppControlSocket {
	public byte[] time;
	/** ��ǰ����ģʽ��0���ֶ���1��ѭ����ʱ��2������ʱ */
	public int workMode;
	/** ���״̬;0���رգ�1����������ֵֻ�ڹ���ģʽΪ0x00ʱ��Ч */
	public int outputState;
	public String ip;

	public AppSetDeviceModeUDP(UDPData receiveData) {
		super(receiveData.data);
		ip = receiveData.IP;
		time = new byte[6];
		workMode = receiveData.data[hearderLength + delta] & 0xff; // ����ģʽ
		outputState = receiveData.data[hearderLength + 1 + delta] & 0xff; // ���״̬
	}
}
