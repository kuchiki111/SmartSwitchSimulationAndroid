package com.smartswitchsimulationandroid.udptcp;

import android.R.integer;

/**
 * 和硬件进行UDP/TCP通讯过程中使用的各条指令的CMD值
 * 
 * @author hxc
 *
 */
public class UDPTCPCommand {
	// /**1.5 设备广播自己已通过Airlink方式成功连入无线网络*/
	// public static final int DEVICE_JOIN_IN_WIFI = 0x0019;
	//
	/** 1.6设备广播自己的存在，UDP广播 */
	public static final int DEVICE_NOTICE_EXIST = 0x0001;

	/** 1.7App搜索wifi模组，UDP广播 */
	public static final int APP_SEARCH_WIFIMODULE = 0x0002;
	/** 1.7App搜索wifi模组响应包，UDP连接，点对点 */
	public static final int APP_SEARCH_WIFIMODULE_ACK = 0x0003;

	/** APP使用UDP广播查询在线设备的状态 */
	public static final int APP_GET_DEVICE_STATE_UDP = 0x0010;
	/** APP使用UDP广播查询在线设备的状态响应包 */
	public static final int APP_GET_DEVICE_STATE_UDP_ACK = 0x0011;
	/** APP向设备发出向设备发设为远程控制设备的请求 */
	public static final int APP_SET_DEVICE_INTERNET = 0x0012;
	/** APP向设备发出向设备发设为远程控制设备的请求响应 */
	public static final int APP_SET_DEVICE_INTERNET_ACK = 0x0013;

	/** 5.3.1 用户绑定设备,TCP连接用 */
	public static final int APP_BIND_DEVICE = 0x0004;
	/** 5.3.2 用户绑定设备响应,TCP连接用 */
	public static final int APP_BIND_DEVICE_ACK = 0x0005;

	/** 5.4.1 APP登录设备,TCP连接用 */
	public static final int APP_LOGIN_DEVICE = 0x0006;
	/** 5.4.2 APP登录设备响应包,TCP连接用 */
	public static final int APP_LOGIN_DEVICE_ACK = 0x0007;

	/** 5.5.1 设备时间调整 */
	public static final int APP_ADJUST_DEVICE_TIME = 0x0008;
	/** 5.5.1 设备时间调整响应包，TCP连接用 */
	public static final int APP_ADJUST_DEVICE_TIME_ACK = 0x0009;

	/** 5.5.2.1 设置SNTP服务器信息 */
	public static final int APP_SETTING_DEVICE_SNTP = 0x000A;
	/** 5.5.2.2 设备收到设置信息包后响应消息包 */
	public static final int APP_SETTING_DEVICE_SNTP_ACK = 0x000B;

	/** 5.5.2.4 启动/停止时间同步 */
	public static final int APP_SETTING_DEVICE_SNTP_SYNC = 0x000C;
	/** 5.5.2.5 设备对启动/停止时间同步的响应消息包 */
	public static final int APP_SETTING_DEVICE_SNTP_SYNC_ACK = 0x000D;

	/** 5.5.3.1 心跳 */
	public static final int APP_CONNECT_DEVICE_HEARTBEAT = 0x000E;
	/** 5.5.3.2 心跳响应消息包 */
	public static final int APP_CONNECT_DEVICE_HEARTBEAT_ACK = 0x000F;

	/** 5.5.4 业务指令(带ACK) */
	public static final int APP_CONTROL_SOCKET = 0x0093;
	/** 5.5.4 业务指令响应消息包 */
	public static final int APP_CONTROL_SOCKET_ACK = 0x0094;
	/** 6.5.5.1 设置时区信息 已删除 */
	// public static final int APP_SET_TIME_ZONE = 0x0014;
	/** 6.5.5.2 设置时区信息的响应 已删除 */
	// public static final int APP_SET_TIME_ZONE_ACK = 0x0015;
	/** 6.5.6.1 设置固件升级地址信息的请求 */
	public static final int APP_SET_BIN = 0x0016;
	/** 6.5.6.2 设置固件升级地址信息的响应 */
	public static final int APP_SET_BIN_ACK = 0x0017;

	public static final int APP_GET_DEVICE_RSSI = 0x0018;
	public static final int APP_GET_DEVICE_RSSI_ACK = 0x019;
	// /**1.10 APP与MCU通过wifi模块透传指令,TCP连接用*/
	// public static final int APP_MCU_TRANSMISSION= 0x00090;
	// /**1.10 APP与MCU通过wifi模块透传指令响应包,TCP连接用*/
	// public static final int APP_MCU_TRANSMISSION_ACK= 0x00091;
	//
	// /**1.11 APP与MCU通过wifi模块透传指令（带包序号）,TCP连接用*/
	// public static final int APP_MCU_TRANSMISSION_SN= 0x00093;
	// /**1.11 APP与MCU通过wifi模块透传指令响应包（带序号）,TCP连接用*/
	// public static final int APP_MCU_TRANSMISSION_SN_ACK= 0x00094;
	//
	// /*******以下是透传指令中关于业务指令的action部分的定义******/
	// public static final int ACTION_CONTROL_DEVICE = 0X01;
	// public static final int ACTION_GET_DEVICE_STATUS = 0X02;
	// public static final int ACTION_GET_DEVICE_STATUS_ACK = 0X03;
	// public static final int ACTION_DEVICE_REPORT_STATUS = 0X04;
	// public static final int ACTION_AIR = 0X12;
	// public static final int ACTION_ALARM = 0X13;
	// /*************
	// 透传指令中智能插座中的安防和空调部分由functionType和Action组成，functionType固定为0x12***********
	// * 以下均为空调协议中action部分定义和原先的action不同*******/
	// /**设置空调的型号*/
	// public static final int ACTION_AIR_SET_BRANDCODE_ACK = 0X02;
	// /**空调控制响应包*/
	// public static final int ACTION_AIR_CONTROL_ACK = 0X04;
	// /**安防设置响应包*/
	// public static final int ACTION_ALARM_SET_STATUS_ACK = 0x04;
	// /**安防主动上报状态包*/
	// public static final int ACTION_ALARM_INITATIVE_STATUS_ACK = 0x04;
	// /**安防被动上报状态包*/
	// public static final int ACTION_ALARM_QUERY_STATUS_ACK = 0x06;
	// /************* */
	//
	// /******改指令用于同一用户多次登录时，弹掉以前登录的app界面，mqtt指令使用*/
	// public static final int APP_LOGIN_VERIFICATION =0XF1;
	// /***自定义协议，用于app到app之间发送某个安防更改了消息推送和警报声音设置，提示其他app更新这些shezhi*/
	// public static final int APP_CHANGE_ALARM_CONFIG =0XF2;

}
