package com.smartswitchsimulationandroid.command;


public class AppSetDeviceMode extends AppControlSocket {
	/** ��ǰ����ģʽ��0���ֶ���1��ѭ����ʱ��2������ʱ */
	public int workMode;
	/** ���״̬;0���رգ�1����������ֵֻ�ڹ���ģʽΪ0x00ʱ��Ч */
	public int outputState;

	public AppSetDeviceMode(byte[] receiveData) {
		super(receiveData);
		workMode = receiveData[hearderLength + delta] & 0xff; // ����ģʽ
		outputState = receiveData[hearderLength + 1 + delta] & 0xff; // ���״̬
	}
}
