package com.smartswitchsimulationandroid.command;
/**
 * 6.6.1.1.1 APP查询设备当前状态的请求
 * @author admin
 * @create_date 2018年5月3日下午4:27:43
 */
public class AppGetDeviceState extends AppControlSocket {
	
	public AppGetDeviceState(byte[] receiveData) {
		super(receiveData);
	}
}
