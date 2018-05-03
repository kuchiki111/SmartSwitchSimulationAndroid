package com.smartswitchsimulationandroid.command;

import java.util.Calendar;

public class AppGetDeviceStateUDPAck {
	byte[] data;
	Calendar cal = Calendar.getInstance(); 
	
	public AppGetDeviceStateUDPAck(int WorkMode, int OutputState, int MQTTEnable){
		data  = new byte [17];
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x11; // PackLength =0x03
		data[5] = 0x00; // Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x11; // CommandWord = 0x0011
		data[8] = (byte) (cal.get(Calendar.YEAR)-2000);
		data[9] = (byte) (cal.get(Calendar.MONTH)+1);
		data[10] = (byte) cal.get(Calendar.DAY_OF_MONTH);
		data[11] = (byte) cal.get(Calendar.HOUR_OF_DAY);
		data[12] = (byte) cal.get(Calendar.MINUTE);
		data[13] = (byte) cal.get(Calendar.SECOND);
		data[14] = (byte) WorkMode;
		data[15] = (byte) OutputState;
		data[16] = (byte) MQTTEnable;
		
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int dataLength() {
		return data.length;
	}
}
