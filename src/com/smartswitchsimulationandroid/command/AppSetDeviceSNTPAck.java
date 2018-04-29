package com.smartswitchsimulationandroid.command;

/**
 * 
 * @ClassName: AppSetDeviceSNTPAck
 * @Description: 5.5.2.1 设置SNTP服务器信息
 * @author wx
 * @date 2016年12月21日
 *
 */
public class AppSetDeviceSNTPAck {
	int ProtocolVer;
	int VarLen;
	int Flag;
	int Cmd;
	public int SntpNameLength;
	public String SntpNameValue;
	public boolean result;// 表示设置sntp是否成功，true：成功，false ：失败

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
