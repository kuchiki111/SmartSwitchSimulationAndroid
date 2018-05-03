package com.smartswitchsimulationandroid.command;

/**
 * App向Device发送TCP信息后，Device返回的数据包，<br>
 * 在TCP.java中根据接收的实际数据数据包，将发送对应的Handler消息，各消息的what值定义如下：
 * */
public class DeviceToAppResponse {

	/** 绑定失败消息 */
	public static final int BindingFail = 0x00;
	/** 绑定成功消息 */
	public static final int BindingSuccess = 0x01;

	/** 登录失败消息 */
	public static final int LoginFail = 0x10;
	/** 登录成功消息 */
	public static final int LoginSuccess = 0x11;

	/** 查询设备状态 */
	public static final int SearchDeviceState = 0x01;

	/** 查询设备定时设置信息 */
	public static final int SearchDeviceTimeSetting = 0x03;

	/** 设备状态获取成功 */
	// public static final int GetDeviceStatus = 0x20;

	public static final int DeviceTcpShutDown = 0xff;

}
