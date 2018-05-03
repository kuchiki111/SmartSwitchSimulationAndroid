package com.smartswitchsimulationandroid.command;

/**
 * App��Device����TCP��Ϣ��Device���ص����ݰ���<br>
 * ��TCP.java�и��ݽ��յ�ʵ���������ݰ��������Ͷ�Ӧ��Handler��Ϣ������Ϣ��whatֵ�������£�
 * */
public class DeviceToAppResponse {

	/** ��ʧ����Ϣ */
	public static final int BindingFail = 0x00;
	/** �󶨳ɹ���Ϣ */
	public static final int BindingSuccess = 0x01;

	/** ��¼ʧ����Ϣ */
	public static final int LoginFail = 0x10;
	/** ��¼�ɹ���Ϣ */
	public static final int LoginSuccess = 0x11;

	/** ��ѯ�豸״̬ */
	public static final int SearchDeviceState = 0x01;

	/** ��ѯ�豸��ʱ������Ϣ */
	public static final int SearchDeviceTimeSetting = 0x03;

	/** �豸״̬��ȡ�ɹ� */
	// public static final int GetDeviceStatus = 0x20;

	public static final int DeviceTcpShutDown = 0xff;

}
