package com.smartswitchsimulationandroid.command;
/**
 * 6.6.1.2.1 APP查询设备当前参数的请求
 * @author admin
 * @create_date 2018年5月3日下午4:29:41
 */
public class AppGetDeviceParams extends AppControlSocket {

	public AppGetDeviceParams(byte[] receiveData) {
		super(receiveData);
		
	}
}
