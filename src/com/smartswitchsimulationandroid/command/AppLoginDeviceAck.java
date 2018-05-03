package com.smartswitchsimulationandroid.command;

import java.util.Calendar;

public class AppLoginDeviceAck {
	byte [] data ;
	Calendar cal = Calendar.getInstance(); 
	
	public  AppLoginDeviceAck(int LoginState){
		data = new byte[15];
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; 				// PackHeader =0x00000003;
		data[4] = 0x0f; 				// PackLength =0x0e
		data[5] = 0x00; 				// Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x07; 				// CommandWord = 0x0007
		data[8] = (byte) LoginState;	//LoginState
		data[9] = (byte) (cal.get(Calendar.YEAR)-2000);
		data[10] = (byte) (cal.get(Calendar.MONTH)+1);
		data[11] = (byte) cal.get(Calendar.DAY_OF_MONTH);
		data[12] = (byte) cal.get(Calendar.HOUR_OF_DAY);
		data[13] = (byte) cal.get(Calendar.MINUTE);
		data[14] = (byte) cal.get(Calendar.SECOND);
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int dataLength() {
		return data.length;
	}
}
