package com.smartswitchsimulationandroid.command;

/**
 * 
 * @ClassName: AppSetDeviceSNTPSyncAck
 * @Description : 6.5.2.5 �豸������/ֹͣʱ��ͬ������Ӧ
 * @author wx
 * @date 2016��12��21��
 *
 */
public class AppSetDeviceSNTPSyncAck {
	int ProtocolVer;
	int VarLen;
	int Flag;
	int Cmd;
	public boolean result;// ��ʾ����/ֹͣʱ��ͬ���Ƿ�ɹ���true���ɹ���false ��ʧ��
	public byte SNTPEnable;

	public AppSetDeviceSNTPSyncAck(byte[] receiveData) {

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
		SNTPEnable = receiveData[9];
	}
}
