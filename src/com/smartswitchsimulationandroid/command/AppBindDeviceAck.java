package com.smartswitchsimulationandroid.command;

public class AppBindDeviceAck {
	byte [] data ;
	
	public  AppBindDeviceAck(String PassCode){
		data = new byte[14];
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x0e; // PackLength =0x0e
		data[5] = 0x00; // Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x05; // CommandWord = 0x0005
		data[8] = 0x00;
		data[9] = 0x04;//PassCodeLength
		System.arraycopy(PassCode.getBytes(), 0, data, 10, 4);
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int dataLength() {
		return data.length;
	}
}
