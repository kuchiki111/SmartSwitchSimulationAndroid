package com.smartswitchsimulationandroid.command;

/**
 * 
 * @ClassName: AppConnectDeviceHeartbeatAck
 * @Description: 5.5.3 当设备收到心跳消息返回数据包
 * @author wx
 * @date 2016年12月23日
 *
 */
public class AppConnectDeviceHeartbeatAck {
	int ProtocolVer;
	int VarLen;
	int Flag;
	int Cmd;

	public AppConnectDeviceHeartbeatAck(byte[] receiveData) {
		// Pack_Header
		ProtocolVer = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		// Pack_Length
		VarLen = receiveData[4] & 0xff;
		// Flag
		Flag = receiveData[5] & 0xff;
		// Command_Type
		Cmd = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
	}
}
