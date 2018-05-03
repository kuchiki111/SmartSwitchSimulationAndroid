package com.smartswitchsimulationandroid.command;

public class AppSetDeviceSNTPSyncAck {
	byte[] data;
	
	public AppSetDeviceSNTPSyncAck(int OperationState, int SNTPEnable){
		data  = new byte [10];
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x0a; // PackLength =0x03
		data[5] = 0x00; // Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x0d; // CommandWord = 0x000d
		data[8] = (byte) OperationState;
		data[9] = (byte) SNTPEnable;
		
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int dataLength() {
		return data.length;
	}
}
