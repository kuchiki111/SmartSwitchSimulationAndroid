package com.smartswitchsimulationandroid.parmars;

public class UrlParams {

	/** �Ƿ�Ϊ����״̬ */
	private static Boolean TEST = true;

	/** ����IP��ַ�Լ��˿� */
	private static final String TESTHOST = "http://118.178.184.73:8080/";
	
	/** ������IP��ַ�Լ��˿� */
	private static final String HOST = "http://118.178.184.73:8080/";
	/** MQTT������IP��ַ�Լ��˿� */
	public static final String MqttHost = "tcp://118.178.184.73:1883";
	/** ����URL·�� */
	private static final String BASE_URL = "chinatimer/serviceClient/";
	
	/** ע������ýӿ� */
	private static final String REG_URL = "bainian_reg";
	/** ��ȡmqtt������ */
	private static final String MQTT_URL = "bainian_mqtt";

	public static String getHostBaseUrl() {
		if (TEST)
			return TESTHOST + BASE_URL;
		else
			return HOST + BASE_URL;
	}

	/**
	 * ��ȡע������ýӿڵ�url
	 * 
	 * @return
	 */
	public static String getRegUrl() {
		if (TEST)
			return TESTHOST + BASE_URL + REG_URL;
		else
			return HOST + BASE_URL + REG_URL;
	}

	/**
	 * ��ȡmqtt��������url
	 * 
	 * @return
	 */
	public static String getMqttUrl() {
		if (TEST)
			return TESTHOST + BASE_URL + MQTT_URL;
		else
			return HOST + BASE_URL + MQTT_URL;
	}

}
