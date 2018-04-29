package com.smartswitchsimulationandroid.command;

/**
 * 6.3.1APP���豸�����İ�����TCP�˿�12416
 * @author kuchiki111
 * @create_date 2018��4��27������3:31:11
 */
public class AppBindDevice {
	public int PackHeader;
	public int PackLength;
	public int Flag;
	public int CommandWord;
	
	public AppBindDevice(byte[] receiveData){
		PackHeader = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		PackLength = receiveData[4] & 0xff;
		Flag = receiveData[5] & 0xff;
		CommandWord = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
	}
}
