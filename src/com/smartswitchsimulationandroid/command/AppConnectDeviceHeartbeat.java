package com.smartswitchsimulationandroid.command;

/**
 * 6.5.3.1 在局域网中APP向设备发出的心跳，TCP
 * @author kuchiki111
 * @create_date 2018年4月27日上午4:45:46
 */
public class AppConnectDeviceHeartbeat {
	public int PackHeader;
	public int PackLength;
	public int Flag;
	public int CommandWord;
	
	public AppConnectDeviceHeartbeat(byte[] receiveData){
		PackHeader = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		PackLength = receiveData[4] & 0xff;
		Flag = receiveData[5] & 0xff;
		CommandWord = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
	}
}
