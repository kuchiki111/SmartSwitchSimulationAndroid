package com.smartswitchsimulationandroid.command;

import com.smartswitchsimulationandroid.udptcp.UDPData;

/***
 * 6.5.4.2 设备对设置 为 远程控制 设备的响应
 * 
 * @author wx
 * @date 2017年2月16日
 *
 */
public class AppSetDeviceInternetAck {

	int ProtocolVer;
	public int VarLen;
	public int Flag;
	public int Cmd;
	/** 操作状态:0 成功，1 失败 */
	public int OperationState;
	/** 远程设备 0 关闭；1 开启 */
	public int MQTTEnable;

	public AppSetDeviceInternetAck(UDPData udpData) {
		byte[] receiveData = udpData.data;
		// Pack_Header
		ProtocolVer = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		// Pack_Length
		VarLen = receiveData[4] & 0xff;
		// Flag
		Flag = receiveData[5] & 0xff;
		// Command_Type
		Cmd = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);

		OperationState = receiveData[8] & 0xff;

		MQTTEnable = receiveData[9] & 0xff;
	}
}
