package com.smartswitchsimulationandroid.command;

/**
 * 
 * @ClassName: AppSetDeviceSNTPAck
 * @Description: 5.5.2.1 ����SNTP��������Ϣ
 * @author wx
 * @date 2016��12��21��
 *
 */
public class AppSetDeviceSNTPAck {
	int ProtocolVer;
	int VarLen;
	int Flag;
	int Cmd;
	public int SntpNameLength;
	public String SntpNameValue;
	public boolean result;// ��ʾ����sntp�Ƿ�ɹ���true���ɹ���false ��ʧ��

	public AppSetDeviceSNTPAck(byte[] receiveData) {

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
		// Sntp_Name_ Length
		SntpNameLength = ((receiveData[9] & 0xff) << 8) | (receiveData[10] & 0xff);
		// Sntp_Name_Value
		byte[] sntpNameBytes = new byte[SntpNameLength];
		System.arraycopy(receiveData, 11, sntpNameBytes, 0, SntpNameLength);
		SntpNameValue = new String(sntpNameBytes);
	}
}
