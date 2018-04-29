package com.smartswitchsimulationandroid.udptcp;

import android.R.integer;

/**
 * ��Ӳ������UDP/TCPͨѶ������ʹ�õĸ���ָ���CMDֵ
 * 
 * @author hxc
 *
 */
public class UDPTCPCommand {
	// /**1.5 �豸�㲥�Լ���ͨ��Airlink��ʽ�ɹ�������������*/
	// public static final int DEVICE_JOIN_IN_WIFI = 0x0019;
	//
	/** 1.6�豸�㲥�Լ��Ĵ��ڣ�UDP�㲥 */
	public static final int DEVICE_NOTICE_EXIST = 0x0001;

	/** 1.7App����wifiģ�飬UDP�㲥 */
	public static final int APP_SEARCH_WIFIMODULE = 0x0002;
	/** 1.7App����wifiģ����Ӧ����UDP���ӣ���Ե� */
	public static final int APP_SEARCH_WIFIMODULE_ACK = 0x0003;

	/** APPʹ��UDP�㲥��ѯ�����豸��״̬ */
	public static final int APP_GET_DEVICE_STATE_UDP = 0x0010;
	/** APPʹ��UDP�㲥��ѯ�����豸��״̬��Ӧ�� */
	public static final int APP_GET_DEVICE_STATE_UDP_ACK = 0x0011;
	/** APP���豸�������豸����ΪԶ�̿����豸������ */
	public static final int APP_SET_DEVICE_INTERNET = 0x0012;
	/** APP���豸�������豸����ΪԶ�̿����豸��������Ӧ */
	public static final int APP_SET_DEVICE_INTERNET_ACK = 0x0013;

	/** 5.3.1 �û����豸,TCP������ */
	public static final int APP_BIND_DEVICE = 0x0004;
	/** 5.3.2 �û����豸��Ӧ,TCP������ */
	public static final int APP_BIND_DEVICE_ACK = 0x0005;

	/** 5.4.1 APP��¼�豸,TCP������ */
	public static final int APP_LOGIN_DEVICE = 0x0006;
	/** 5.4.2 APP��¼�豸��Ӧ��,TCP������ */
	public static final int APP_LOGIN_DEVICE_ACK = 0x0007;

	/** 5.5.1 �豸ʱ����� */
	public static final int APP_ADJUST_DEVICE_TIME = 0x0008;
	/** 5.5.1 �豸ʱ�������Ӧ����TCP������ */
	public static final int APP_ADJUST_DEVICE_TIME_ACK = 0x0009;

	/** 5.5.2.1 ����SNTP��������Ϣ */
	public static final int APP_SETTING_DEVICE_SNTP = 0x000A;
	/** 5.5.2.2 �豸�յ�������Ϣ������Ӧ��Ϣ�� */
	public static final int APP_SETTING_DEVICE_SNTP_ACK = 0x000B;

	/** 5.5.2.4 ����/ֹͣʱ��ͬ�� */
	public static final int APP_SETTING_DEVICE_SNTP_SYNC = 0x000C;
	/** 5.5.2.5 �豸������/ֹͣʱ��ͬ������Ӧ��Ϣ�� */
	public static final int APP_SETTING_DEVICE_SNTP_SYNC_ACK = 0x000D;

	/** 5.5.3.1 ���� */
	public static final int APP_CONNECT_DEVICE_HEARTBEAT = 0x000E;
	/** 5.5.3.2 ������Ӧ��Ϣ�� */
	public static final int APP_CONNECT_DEVICE_HEARTBEAT_ACK = 0x000F;

	/** 5.5.4 ҵ��ָ��(��ACK) */
	public static final int APP_CONTROL_SOCKET = 0x0093;
	/** 5.5.4 ҵ��ָ����Ӧ��Ϣ�� */
	public static final int APP_CONTROL_SOCKET_ACK = 0x0094;
	/** 6.5.5.1 ����ʱ����Ϣ ��ɾ�� */
	// public static final int APP_SET_TIME_ZONE = 0x0014;
	/** 6.5.5.2 ����ʱ����Ϣ����Ӧ ��ɾ�� */
	// public static final int APP_SET_TIME_ZONE_ACK = 0x0015;
	/** 6.5.6.1 ���ù̼�������ַ��Ϣ������ */
	public static final int APP_SET_BIN = 0x0016;
	/** 6.5.6.2 ���ù̼�������ַ��Ϣ����Ӧ */
	public static final int APP_SET_BIN_ACK = 0x0017;

	public static final int APP_GET_DEVICE_RSSI = 0x0018;
	public static final int APP_GET_DEVICE_RSSI_ACK = 0x019;
	// /**1.10 APP��MCUͨ��wifiģ��͸��ָ��,TCP������*/
	// public static final int APP_MCU_TRANSMISSION= 0x00090;
	// /**1.10 APP��MCUͨ��wifiģ��͸��ָ����Ӧ��,TCP������*/
	// public static final int APP_MCU_TRANSMISSION_ACK= 0x00091;
	//
	// /**1.11 APP��MCUͨ��wifiģ��͸��ָ�������ţ�,TCP������*/
	// public static final int APP_MCU_TRANSMISSION_SN= 0x00093;
	// /**1.11 APP��MCUͨ��wifiģ��͸��ָ����Ӧ��������ţ�,TCP������*/
	// public static final int APP_MCU_TRANSMISSION_SN_ACK= 0x00094;
	//
	// /*******������͸��ָ���й���ҵ��ָ���action���ֵĶ���******/
	// public static final int ACTION_CONTROL_DEVICE = 0X01;
	// public static final int ACTION_GET_DEVICE_STATUS = 0X02;
	// public static final int ACTION_GET_DEVICE_STATUS_ACK = 0X03;
	// public static final int ACTION_DEVICE_REPORT_STATUS = 0X04;
	// public static final int ACTION_AIR = 0X12;
	// public static final int ACTION_ALARM = 0X13;
	// /*************
	// ͸��ָ�������ܲ����еİ����Ϳյ�������functionType��Action��ɣ�functionType�̶�Ϊ0x12***********
	// * ���¾�Ϊ�յ�Э����action���ֶ����ԭ�ȵ�action��ͬ*******/
	// /**���ÿյ����ͺ�*/
	// public static final int ACTION_AIR_SET_BRANDCODE_ACK = 0X02;
	// /**�յ�������Ӧ��*/
	// public static final int ACTION_AIR_CONTROL_ACK = 0X04;
	// /**����������Ӧ��*/
	// public static final int ACTION_ALARM_SET_STATUS_ACK = 0x04;
	// /**���������ϱ�״̬��*/
	// public static final int ACTION_ALARM_INITATIVE_STATUS_ACK = 0x04;
	// /**���������ϱ�״̬��*/
	// public static final int ACTION_ALARM_QUERY_STATUS_ACK = 0x06;
	// /************* */
	//
	// /******��ָ������ͬһ�û���ε�¼ʱ��������ǰ��¼��app���棬mqttָ��ʹ��*/
	// public static final int APP_LOGIN_VERIFICATION =0XF1;
	// /***�Զ���Э�飬����app��app֮�䷢��ĳ��������������Ϣ���ͺ;����������ã���ʾ����app������Щshezhi*/
	// public static final int APP_CHANGE_ALARM_CONFIG =0XF2;

}
