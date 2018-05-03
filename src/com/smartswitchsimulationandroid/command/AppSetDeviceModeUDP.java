package com.smartswitchsimulationandroid.command;

import com.smartswitchsimulationandroid.udptcp.UDPData;

public class AppSetDeviceModeUDP extends AppControlSocket {
	public byte[] time;
	/** 当前工作模式。0：手动；1：循环定时；2：倒计时 */
	public int workMode;
	/** 输出状态;0：关闭；1：开启。该值只在工作模式为0x00时有效 */
	public int outputState;
	public String ip;

	public AppSetDeviceModeUDP(UDPData receiveData) {
		super(receiveData.data);
		ip = receiveData.IP;
		time = new byte[6];
		workMode = receiveData.data[hearderLength + delta] & 0xff; // 工作模式
		outputState = receiveData.data[hearderLength + 1 + delta] & 0xff; // 输出状态
	}
}
