package com.smartswitchsimulationandroid.parmars;

public class UrlParams {

	/** 是否为测试状态 */
	private static Boolean TEST = true;

	/** 测试IP地址以及端口 */
	private static final String TESTHOST = "http://118.178.184.73:8080/";
	
	/** 服务器IP地址以及端口 */
	private static final String HOST = "http://118.178.184.73:8080/";
	/** MQTT服务器IP地址以及端口 */
	public static final String MqttHost = "tcp://118.178.184.73:1883";
	/** 基础URL路径 */
	private static final String BASE_URL = "chinatimer/serviceClient/";
	
	/** 注册或重置接口 */
	private static final String REG_URL = "bainian_reg";
	/** 获取mqtt服务器 */
	private static final String MQTT_URL = "bainian_mqtt";

	public static String getHostBaseUrl() {
		if (TEST)
			return TESTHOST + BASE_URL;
		else
			return HOST + BASE_URL;
	}

	/**
	 * 获取注册或重置接口的url
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
	 * 获取mqtt服务器的url
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
