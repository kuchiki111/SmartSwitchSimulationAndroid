package com.smartswitchsimulationandroid.command;

/**
 * 6.4.1APP的登录请求，TCP端口12416
 * @author kuchiki111
 * @create_date 2018年4月27日上午3:38:12
 */
public class AppLoginDevice {
	public int PackHeader;
	public int PackLength;
	public int Flag;
	public int CommandWord;
	public int PassCodeLength;
	public String PassCode;
	
	public AppLoginDevice(byte[] receiveData){
		PackHeader = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		PackLength = receiveData[4] & 0xff;
		Flag = receiveData[5] & 0xff;
		CommandWord = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
		//PassCode
		PassCodeLength = ((receiveData[8] & 0xff) << 8) | (receiveData[9] & 0xff);
		byte[] PassCodeValue = new byte[PassCodeLength];
		PassCode = "";
		for(int i=0;i<PassCodeValue.length;i++){
			if(i == 0){
				PassCode += String.format("%02d", PassCodeValue[i]);
			}else{
				PassCode += String.format(".%02d", PassCodeValue[i]);
			}
		}
	}
}
