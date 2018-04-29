package com.smartswitchsimulationandroid.command;

public class AppSearchWifiAck {
	byte[] data;
	
	public AppSearchWifiAck(){
		data = new byte[7];
		
		data[0] = 0x0;
		data[1] = 0x0;
		data[2] = 0x0;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x03; // PackLength =0x03
		data[5] = 0x0;  // Flag = 0x00;
		data[6] = 0x0;
		data[7] = 0x03; // CommandWord = 0x0003
		
	}

	public byte[] getData() {
		return data;
	}

	public int dataLength(){
		return data.length;
	}
}
