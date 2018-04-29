package com.smartswitchsimulationandroid.command;

/**
 * 5.4.2 �豸��Ӧ��¼�������Ϣ��<br>
 * TCP���ݰ����ͣ�Ŀ��˿�12416
 */
public class AppLoginDeviceAck {
	int ProtocolVer;
	int VarLen;
	int Flag;
	int Cmd;
	public byte time[];
	public boolean result;// ��ʾ��¼�Ƿ�ɹ���true���ɹ���false ��ʧ��

	public AppLoginDeviceAck(byte[] receiveData) {

		// Pack_Header
		ProtocolVer = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		// Pack_Length
		VarLen = receiveData[4] & 0xff;
		// Flag
		Flag = receiveData[5] & 0xff;
		// Command_Type
		Cmd = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
		if ((receiveData[8] & 0xff) == 0)
			result = true;
		else
			result = false;
		time = new byte[6];
		System.arraycopy(receiveData, 9, time, 0, 6);
	}
}
