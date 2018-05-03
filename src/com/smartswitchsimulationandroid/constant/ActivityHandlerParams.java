package com.smartswitchsimulationandroid.constant;

public class ActivityHandlerParams {
	/** 从http接口获取用户的所有设备 */
	public static final int LocalNetWorkSearchDevice = 1;
	public static final int DeviceOnlineEnd = 2;
	public static final int InternetSearchDevice = 3;
	public static final int AppGetDeviceStateUDP = 4;
	
	public static final int AppGetDeviceStateMQTT = 5;
	public static final int AppGetDeviceParams = 6;
	
	/** 设备注册*/
	public static final int DeviceRegister = 1000;
	public static final int DeviceReRegister = 1001;
	public static final int DeviceRegisterError = 1002;
	public static final int DeviceRegisterTimeout = 1003;
	
	/** 设备获取mqtt服务器*/
	public static final int DeviceGetMqttServer = 2000;
	public static final int DeviceGetMqttServerTimeout = 2001;
}
