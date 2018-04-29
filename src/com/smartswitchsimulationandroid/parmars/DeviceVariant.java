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

/** ����ϵͳ����ĳ��� */
public class DeviceVariant {
	/** ����wifiģ���UDP�㲥���ʹ�����ÿ�μ��1�� */
	public static final int SearchWifiMaxTimes = 3;
	/** �������wifi��UDP�㲥����֮�󣬽��յ���������Ӧ�豸��Ϣ�б�����豸��IP��ַ���Լ�DID��MACAdd��ProduceKey */
//	public static AllDeviceList ctAllDeivceList = new AllDeviceList();
//
//	public static ReceivePoolUDP umReceivePoolUdp;
//	public static ReceiveDataHandle umReceiveDataHandle;
//	public static MyMqtt umMqtt;
//
//	// ���豸����ҳ����豸����ҳ��ֱ�Ӵ���tcp��mqtt���ӡ�
//	public static TCPorMQTTInterface mTcPorMQTTInterface;
	/** UDP/TCP���շ��͵İ�����ֳ� */
	public static final int MessageLength = 1024;
	public static final int MaxResendCount = 30;

	/** app����UDPЭ��˿ں� */
	public static final int AppUDPListeningPort = 12414;

	/** �豸UDPЭ��˿ں� */
	public static final int DeviceUDPListeningPort = 2415;
	
	/** �豸TCPЭ��˿ں� */
	public static final int DeviceTCPServerPort = 12416;

	public static final String SNTPHost_CN = "pool.ntp.org";
	public static final String SNTPHost_US = "us.ntp.org.cn";
	public static final String SNTPHost_SJTU = "202.120.2.101";// �Ϻ���ͨ��ѧ��������NTP��������ַ

	// ʱ����Ӧ��ֵ
	public static final int UTC_ADD_8 = 0x08;
	public static final int UTC_SUB_10 = 0x8a;
	public static final int UTC_SUB_9 = 0x89;
	public static final int UTC_SUB_8 = 0x88;
	public static final int UTC_SUB_7 = 0x87;
	public static final int UTC_SUB_6 = 0x86;
	public static final int UTC_SUB_5 = 0x85;

	//
	public static final String UTC_ADD_8_TEXT = "����ʱ��";
	public static final String UTC_SUB_10_TEXT = "������ʱ��";
	public static final String UTC_SUB_9_TEXT = "����˹��ʱ��";
	public static final String UTC_SUB_8_TEXT = "̫ƽ��ʱ��";
	public static final String UTC_SUB_7_TEXT = "ɽ��ʱ��";
	public static final String UTC_SUB_6_TEXT = "�в�ʱ��";
	public static final String UTC_SUB_5_TEXT = "����ʱ��";

	/** ��ȡҪ��ʾ��ʱ����ֵ */
	public static final int[] TimeZoneValue() {
		return new int[] { UTC_ADD_8, UTC_SUB_10, UTC_SUB_9, UTC_SUB_8, UTC_SUB_7, UTC_SUB_6, UTC_SUB_5 };
	}

	/** ��ȡҪ��ʾ��ʱ�����ı� */
	public static final String[] TimeZoneText() {
		return new String[] { UTC_ADD_8_TEXT, UTC_SUB_10_TEXT, UTC_SUB_9_TEXT, UTC_SUB_8_TEXT, UTC_SUB_7_TEXT, UTC_SUB_6_TEXT, UTC_SUB_5_TEXT };
	}

	/***/
	public static final String[] SNTOHost() {
		return new String[] { DeviceVariant.SNTPHost_CN, DeviceVariant.SNTPHost_US, DeviceVariant.SNTPHost_SJTU };
	}

	
	/** ��ѯ�豸״̬ */
	public static final int QueryDeviceStatus = 0x01;
	public static final int QueryDeviceStatusAck = 0x02;

	/** ��ѯ�豸����,����ѭ����ʱ����͵���ʱ��� */
	public static final int QueryDeviceParams = 0x03;
	public static final int QueryDeviceParamsAck = 0x04;

	/** �豸ģʽ�л�ָ�� */
	public static final int DeviceModeChangeAck = 0x06;

	/** �豸ѭ����ʱָ�� */
	public static final int DeviceCycleTimingAck = 0x08;

	/** �豸����ʱָ�� */
	public static final int DeviceCountDownAck = 0x0a;

	/** ����ģʽ���ֶ� */
	public static final int WorkModeManual = 0x00;

	/** ����ģʽ��ѭ����ʱ */
	public static final int WorkModeSchedule = 0x01;

	/** ����ģʽ������ʱ */
	public static final int WorkModeCountdown = 0x02;

	/** ���״̬������ */
	public static final int OutputStateOpen = 0x01;

	/** ���״̬���ر� */
	public static final int OutputStateClose = 0x00;

	/** Զ�̿����豸�ر� */
	public static final int InternetDisable = 0x00;

	/** Զ�̿����豸���� */
	public static final int InternetEnable = 0x01;
}
