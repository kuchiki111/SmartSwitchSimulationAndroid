package com.smartswitchsimulationandroid.command;

/**
 * 6.5.1.1APP调整时间的请求,TCP/MQTT
 * @author kuchiki11
 * @create_date 2018年4月27日上午4:08:56
 */
public class AppSetDeviceTime {
	public int PackHeader;
	public int PackLength;
	public int Flag;
	public int CommandWord;
	public String KeyCode;
	public byte[] time;
	public byte TimeZone;
	
	public AppSetDeviceTime(byte[] receiveData){
		PackHeader = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		PackLength = receiveData[4] & 0xff;
		Flag = receiveData[5] & 0xff;
		CommandWord = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
		//KeyCode
		byte [] KeyCodeValue = new byte[15];
		KeyCode = "";
		for(int i=0;i<KeyCodeValue.length;i++){
			if(i == 0){
				KeyCode += String.format("%02d", KeyCodeValue[i]);
			}else {
				KeyCode += String.format(".%02d", KeyCodeValue[i]);
			}
		}
		//time
		time = new byte[5];
		System.arraycopy(receiveData, 24, time, 0, 5);
		//TimeZone
		TimeZone = receiveData[30];
	}
}
