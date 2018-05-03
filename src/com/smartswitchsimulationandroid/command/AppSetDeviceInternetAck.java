package com.smartswitchsimulationandroid.command;

import java.util.Calendar;

import com.smartswitchsimulationandroid.udptcp.UDPData;

public class AppSetDeviceInternetAck {
	byte[] data;
	
	public AppSetDeviceInternetAck(int OperationState, int MQTTEnable){
		data  = new byte [10];
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x0a; // PackLength =0x03
		data[5] = 0x00; // Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x13; // CommandWord = 0x0013
		data[8] = (byte) OperationState;
		data[9] = (byte) MQTTEnable;
		
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int dataLength() {
		return data.length;
	}
}
