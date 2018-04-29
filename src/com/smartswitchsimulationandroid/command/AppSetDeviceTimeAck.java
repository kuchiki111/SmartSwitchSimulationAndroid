package com.smartswitchsimulationandroid.command;

/**
 * 
 * @ClassName: AppAdjustDeviceTimeAck
 * @Description:5.5.1 设备收到调整时间信息包后响应
 * @author wx
 * @date 2016年12月21日
 *
 */
public class AppSetDeviceTimeAck {
	int ProtocolVer;
	int VarLen;
	int Flag;
	int Cmd;
	public byte time[];
	public boolean result;// 表示调整时间是否成功，true：成功，false ：失败

	public AppSetDeviceTimeAck(byte[] receiveData) {
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
		System.arraycopy(receiveData, 9, time, 0, 5); // 设备返回的时间
	}
}
