package com.smartswitchsimulationandroid.parmars;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesParams {

	/**
	 * LOGIN_INFORMATION 登录总信息 LOGIN_USERNAME 登录名 LOGIN_PASSWORD 登录密码 LOGIN_AUTO
	 * 是否自动登录 自动登录 setLoginInformation() 缓存存储账户密码
	 */
	public static final String CONFIG_INFORMATION = "device.config";
	public static final String DEVICE_STATUS = "device.status";

	public static final String LOGIN_USERNAME = "LOGIN_USERNAME";

	public static final String LOGIN_PASSWORD = "LOGIN_PASSWORD";

	public static final String LOGIN_AUTO = "LOGIN_AUTO";
	/** 表示第一进入app */
	public static final String FIRST_IN = "FIRST_IN";

	/** 上次配网成功的ssid */
	public static final String SSID = "SSID";
	/** 上次配网成功的密码 */
	public static final String SSID_PASSWORD = "SSID_PASSWORD";
	
	/** device基本信息*/
	public static final String DEVICE_MAC = "DEVICE_MAC";
	public static final String DEVICE_PID = "DEVICE_PID";
	public static final String DEVICE_DID = "DEVICE_DID";
	public static final String DEVICE_VER = "DEVICE_VER";
	public static final String DEVICE_PASSCODE = "DEVICE_PASSCODE";
	public static final String DEVICE_BIND= "DEVICE_BIND";
	
	public static final String DEVICE_WORK_MODE= "DEVICE_WORKMODE";
	public static final String DEVICE_OUTPUT_STATE= "DEVICE_OUTPUT_STATE";
	public static final String DEVICE_MQTT_ENABLE= "DEVICE_OUTPUT_STATE";
}
