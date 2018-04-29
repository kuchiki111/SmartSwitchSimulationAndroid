package com.smartswitchsimulationandroid.udptcp;

/**
 * 存储UDP从Device返回到APP的信息，存储返回信息和Device的IP地址，调用getCommand可以返回对应的CMD
 */
public class UDPData {

	public byte[] data;
	public String IP;

	public UDPData() {

	}

	public UDPData(byte[] inData) {
		data = inData;
	}

	public int getCommand() {
		if (data.length <= 8)
			return 0;
		return ((data[6] & 0xff) << 8) | (data[7] & 0xff);
	}
}