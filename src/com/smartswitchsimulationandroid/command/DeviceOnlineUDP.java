package com.smartswitchsimulationandroid.command;

import com.smartswitchsimulationandroid.udptcp.Hex;

public class DeviceOnlineUDP {
	byte [] data;
	
	public DeviceOnlineUDP(String MAC, String PID, String DID, String Version, String PassCode){
		data = new byte[100];
		MAC = "F4B7E2E9FDA1";
		PID = "0000000100000001";
		DID = "F4B7E2E9FDA1";
		PassCode = "TEST";
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x03; // PackLength =0x03
		data[5] = 0x00; // Flag = 0x00;
		data[6] = 0x00;
		data[7] = 0x01; // CommandWord = 0x0001
		data[8] = 0x00;
		data[9] = 0x06;	//MacLength = 0x0006
		System.arraycopy(Hex.decodeHex(MAC), 0, data, 10, 6);
		data[16] = 0x00;
		data[17] = 0x10;//PIDLength
		System.arraycopy(Hex.decodeHex(PID), 0, data, 18, 16);
		data[34] = 0x00;
		data[35] = 0x06;//DIDLength
		System.arraycopy(Hex.decodeHex(DID), 0, data, 36, 6);
		data[42] = 0x00;
		data[43] = 0x04;//VersionLength
		System.arraycopy(new byte[]{1,9,9,9}, 0, data, 44, 4);
		data[48] = 0x00;
		data[49] = 0x04;//PassCodeLength
		System.arraycopy(PassCode.getBytes(), 0, data, 50, 4);
	}
}
