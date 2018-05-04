package com.smartswitchsimulationandroid.command;

/**
 * 6.5.2.1 设置SNTP服务器,TCP/MQTT
 * @author kuchiki111
 * @create_date 2018年4月27日上午4:28:35
 */
public class AppSetDeviceSNTP {
	public int PackHeader;
	public int PackLength;
	public int Flag;
	public int CommandWord;
	public String KeyCode;
	public byte SntpNumber;
	public int SntpURLLength;
	public String SntpURL;
	
	public AppSetDeviceSNTP(byte[] receiveData){
		PackHeader = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		PackLength = receiveData[4] & 0xff;
		Flag = receiveData[5] & 0xff;
		CommandWord = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
		//KeyCode
		byte [] KeyCodeValue = new byte[15];
		KeyCode = "";
		for(int i=0;i<KeyCodeValue.length;i++){
			KeyCode += String.format("%02d", KeyCodeValue[i]);
		}
		//SntpNumber
		SntpNumber = receiveData[24];
		//SntpURL
		SntpURLLength = ((receiveData[25] & 0xff) << 8) | (receiveData[26] & 0xff);
		byte[] SntpURLValue = new byte[SntpURLLength];
		SntpURL = "";
		for(int i=0;i<SntpURLValue.length;i++){
			SntpURL += String.format("%02d", SntpURLValue[i]);	
		}
	}
}
