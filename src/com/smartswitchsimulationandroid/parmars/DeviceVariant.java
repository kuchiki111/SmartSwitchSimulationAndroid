package com.smartswitchsimulationandroid.parmars;

import java.util.List;

import com.smartswitchsimulationandroid.udptcp.ReceiveDataHandle;

//import com.chinesetimer.device.AllDeviceList;
//import com.chinesetimer.mqtt.MyMqtt;
//import com.chinesetimer.updtcp.ReceiveDataHandle;
//import com.chinesetimer.updtcp.ReceivePoolUDP;
//import com.chinesetimer.updtcp.SendPoolUDP;
//import com.chinesetimer.updtcp.TCPorMQTTInterface;

import android.R.integer;
import android.media.SoundPool;

/** 设置系统级别的常量 */
public class DeviceVariant {
	/** 搜索wifi模块的UDP广播发送次数，每次间隔1秒 */
	public static final int SearchWifiMaxTimes = 3;
	/** 存放搜索wifi的UDP广播发送之后，接收到的所有响应设备信息列表，存放设备的IP地址，以及DID、MACAdd、ProduceKey */
//	public static AllDeviceList ctAllDeivceList = new AllDeviceList();
//
//	public static ReceivePoolUDP umReceivePoolUdp;
//	public static ReceiveDataHandle umReceiveDataHandle;
//	public static MyMqtt umMqtt;
//
//	// 在设备控制页面和设备设置页面直接传递tcp、mqtt链接。
//	public static TCPorMQTTInterface mTcPorMQTTInterface;
	/** UDP/TCP接收发送的包最大字长 */
	public static final int MessageLength = 1024;
	public static final int MaxResendCount = 30;

	/** app监听UDP协议端口号 */
	public static final int AppUDPListeningPort = 12414;

	/** 设备UDP协议端口号 */
	public static final int DeviceUDPListeningPort = 2415;
	
	/** 设备TCP协议端口号 */
	public static final int DeviceTCPServerPort = 12416;

	public static final String SNTPHost_CN = "pool.ntp.org";
	public static final String SNTPHost_US = "us.ntp.org.cn";
	public static final String SNTPHost_SJTU = "202.120.2.101";// 上海交通大学网络中心NTP服务器地址

	// 时区对应的值
	public static final int UTC_ADD_8 = 0x08;
	public static final int UTC_SUB_10 = 0x8a;
	public static final int UTC_SUB_9 = 0x89;
	public static final int UTC_SUB_8 = 0x88;
	public static final int UTC_SUB_7 = 0x87;
	public static final int UTC_SUB_6 = 0x86;
	public static final int UTC_SUB_5 = 0x85;

	//
	public static final String UTC_ADD_8_TEXT = "北京时间";
	public static final String UTC_SUB_10_TEXT = "夏威夷时间";
	public static final String UTC_SUB_9_TEXT = "阿拉斯加时间";
	public static final String UTC_SUB_8_TEXT = "太平洋时间";
	public static final String UTC_SUB_7_TEXT = "山地时间";
	public static final String UTC_SUB_6_TEXT = "中部时间";
	public static final String UTC_SUB_5_TEXT = "东部时间";

	/** 获取要显示的时区的值 */
	public static final int[] TimeZoneValue() {
		return new int[] { UTC_ADD_8, UTC_SUB_10, UTC_SUB_9, UTC_SUB_8, UTC_SUB_7, UTC_SUB_6, UTC_SUB_5 };
	}

	/** 获取要显示的时区的文本 */
	public static final String[] TimeZoneText() {
		return new String[] { UTC_ADD_8_TEXT, UTC_SUB_10_TEXT, UTC_SUB_9_TEXT, UTC_SUB_8_TEXT, UTC_SUB_7_TEXT, UTC_SUB_6_TEXT, UTC_SUB_5_TEXT };
	}

	/***/
	public static final String[] SNTOHost() {
		return new String[] { DeviceVariant.SNTPHost_CN, DeviceVariant.SNTPHost_US, DeviceVariant.SNTPHost_SJTU };
	}

	
	/** 查询设备状态 */
	public static final int QueryDeviceStatus = 0x01;
	public static final int QueryDeviceStatusAck = 0x02;

	/** 查询设备参数,包括循环定时情况和倒计时情况 */
	public static final int QueryDeviceParams = 0x03;
	public static final int QueryDeviceParamsAck = 0x04;

	/** 设备模式切换指令 */
	public static final int DeviceModeChangeAck = 0x06;

	/** 设备循环定时指令 */
	public static final int DeviceCycleTimingAck = 0x08;

	/** 设备倒计时指令 */
	public static final int DeviceCountDownAck = 0x0a;

	/** 工作模式，手动 */
	public static final int WorkModeManual = 0x00;

	/** 工作模式，循环定时 */
	public static final int WorkModeSchedule = 0x01;

	/** 工作模式，倒计时 */
	public static final int WorkModeCountdown = 0x02;

	/** 输出状态，开启 */
	public static final int OutputStateOpen = 0x01;

	/** 输出状态，关闭 */
	public static final int OutputStateClose = 0x00;

	/** 远程控制设备关闭 */
	public static final int InternetDisable = 0x00;

	/** 远程控制设备开启 */
	public static final int InternetEnable = 0x01;
}
