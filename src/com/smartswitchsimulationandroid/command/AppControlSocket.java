package com.smartswitchsimulationandroid.command;
/**
 * 6.6 业务指令 TCP/MQTT
 * @author kuchiki111
 * @create_date 2018年4月27日上午5:54:07
 */
public class AppControlSocket {
	public int PackHeader;
	public int PackLength;
	public int Flag;
	public int CommandWord;
	public String KeyCode;
	public int Sn;
	public byte FunctionType;
	public byte ActionCode;
	
	public AppControlSocket(byte[] receiveData){
		PackHeader = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
//		PackLength = receiveData[4] & 0xff;
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
		Sn = (receiveData[24] & 0xff << 24) | (receiveData[25] & 0xff << 16) | (receiveData[26] & 0xff << 8) | (receiveData[27] & 0xff);
		
		FunctionType = receiveData[28];
		ActionCode = receiveData[29];
		
	}
}
