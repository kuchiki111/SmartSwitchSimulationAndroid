package com.smartswitchsimulationandroid.command;

import com.smartswitchsimulationandroid.udptcp.Hex;

public class DeviceOnlineUDP {
	byte [] data;
	
	public DeviceOnlineUDP(String MAC, String PID, String DID, String PassCode){
		data = new byte[76];
//		MAC = "F4B7E2E9FDA1";
//		PID = "0000000100000001";
//		DID = "F4B7E2E9FDA1";
//		PassCode = "TEST";
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x36; // PackLength =0x03
		data[5] = 0x00; // Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x01; // CommandWord = 0x0001
		data[8] = 0x00;
		data[9] = 0x0c;	//MacLength = 0x0006
		System.arraycopy(MAC.getBytes(), 0, data, 10, 12);
//		System.arraycopy(Hex.decodeHex(MAC), 0, data, 10, 6);
		data[22] = 0x00;
		data[23] = 0x10;//PIDLength
		System.arraycopy(PID.getBytes(), 0, data, 24, 16);
		data[40] = 0x00;
		data[41] = 0x0c;//DIDLength
		System.arraycopy(DID.getBytes(), 0, data, 42, 12);
		data[64] = 0x00;
		data[65] = 0x04;//VersionLength
		System.arraycopy(new byte[]{1,9,9,9}, 0, data, 66, 4);
		data[70] = 0x00;
		data[71] = 0x04;//PassCodeLength
		System.arraycopy(PassCode.getBytes(), 0, data, 72, 4);
	}
	
	public byte[] getData(){
		return data;
	}
	
	public int dataLength() {
		return data.length;
	}
}
