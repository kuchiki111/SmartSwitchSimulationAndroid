package com.smartswitchsimulationandroid.command;

import com.smartswitchsimulationandroid.udptcp.UDPData;

/**
 * 6.2.1 APP使用UDP广播查询在线设备的请求，本地监听端口12414
 * @author kuchiki11
 * @create_date 2018年4月27日上午3:13:53
 */

public class AppSearchWifi {
	public String IP;
	public int PackHeader;
	public int PackLength;
	public int Flag;
	public int CommandWord;
	
	public AppSearchWifi(UDPData udpData){
		byte[] receiveData = udpData.data;
		
		IP = udpData.IP;
		PackHeader = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		PackLength = receiveData[4] & 0xff;
		Flag = receiveData[5] & 0xff;
		CommandWord = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
	}

}
