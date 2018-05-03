package com.smartswitchsimulationandroid.command;

public class AppConnectDeviceHeartbeatAck {
	byte [] data ;
	
	public  AppConnectDeviceHeartbeatAck(){
		data = new byte[8];
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x08; // PackLength =0x0e
		data[5] = 0x00; // Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x0f; // CommandWord = 0x000f

	}
	
	public byte[] getData(){
		return data;
	}
	
	public int dataLength() {
		return data.length;
	}
}
