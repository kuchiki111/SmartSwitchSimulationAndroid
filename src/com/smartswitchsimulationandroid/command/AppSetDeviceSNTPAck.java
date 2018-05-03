package com.smartswitchsimulationandroid.command;

import java.util.Calendar;

public class AppSetDeviceSNTPAck {
	byte[] data;
	Calendar cal = Calendar.getInstance(); 
	
	public AppSetDeviceSNTPAck(int OperationState){
		data  = new byte [9];
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x09; // PackLength =0x03
		data[5] = 0x00; // Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x0b; // CommandWord = 0x000b
		data[8] = (byte) OperationState;
		
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int dataLength() {
		return data.length;
	}
}
