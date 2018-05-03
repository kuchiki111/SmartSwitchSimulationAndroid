package com.smartswitchsimulationandroid.command;

public class AppSetDeviceCountDown extends AppControlSocket {

	/** ����ʱ�ӳ�ʱ�� */
	public byte[] delayTime;
	/** ����ʱ����ʱ�� */
	public byte[] duration;
	/** ����ʱ��ɺ�����ģʽ���ֶ� 0x00,ѭ����ʱ 0x01 */
	public int completedMode;
	/** ����ʱ��ɺ�����״̬���ر� 0x00,���� 0x01 */
	public int completedOutputState;

	public AppSetDeviceCountDown(byte[] receiveData) {
		super(receiveData);

		delayTime = new byte[2];
		System.arraycopy(receiveData, hearderLength + delta, delayTime, 0, 2); // ����ʱ��ʼʱ��
		duration = new byte[2];
		System.arraycopy(receiveData, hearderLength + 2 + delta, duration, 0, 2); // ����ʱ����ʱ��

		// ����ʱ��ɺ�����ģʽ
		completedMode = receiveData[hearderLength + 4 + delta] & 0xff;
		// ����ʱ��ɺ�����״̬
		completedOutputState = receiveData[hearderLength + 5 + delta] & 0xff;
	}
}
