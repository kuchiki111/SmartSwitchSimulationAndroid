package com.smartswitchsimulationandroid.constant;

public class ActivityHandlerParams {
	/** ��http�ӿڻ�ȡ�û��������豸 */
	public static final int LocalNetWorkSearchDevice = 1;
	public static final int DeviceOnlineEnd = 2;
	public static final int InternetSearchDevice = 3;
	public static final int AppGetDeviceStateUDP = 4;
	
	public static final int AppGetDeviceStateMQTT = 5;
	public static final int AppGetDeviceParams = 6;
	
	/** �豸ע��*/
	public static final int DeviceRegister = 1000;
	public static final int DeviceReRegister = 1001;
	public static final int DeviceRegisterError = 1002;
	public static final int DeviceRegisterTimeout = 1003;
	
	/** �豸��ȡmqtt������*/
	public static final int DeviceGetMqttServer = 2000;
	public static final int DeviceGetMqttServerTimeout = 2001;
}
