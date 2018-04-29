package com.smartswitchsimulationandroid.command;

/**
 * 6.5.2.4 启动/停止时间同步,MQTT/TCP
 * @author kuchiki111
 * @create_date 2018年4月27日上午4:41:20
 */
public class AppSetDeviceSNTPSync {
	public int PackHeader;
	public int PackLength;
	public int Flag;
	public int CommandWord;
	public String KeyCode;
	public byte SNTPEnable;
	
	public AppSetDeviceSNTPSync(byte[] receiveData){
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
		//SNTPEnable
		SNTPEnable = receiveData[24];
	}
}
