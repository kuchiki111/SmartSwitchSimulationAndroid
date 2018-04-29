package com.smartswitchsimulationandroid.udptcp;

/**
 * �洢UDP��Device���ص�APP����Ϣ���洢������Ϣ��Device��IP��ַ������getCommand���Է��ض�Ӧ��CMD
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