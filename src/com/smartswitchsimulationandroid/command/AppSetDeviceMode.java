package com.smartswitchsimulationandroid.command;


public class AppSetDeviceMode extends AppControlSocket {
	/** 当前工作模式。0：手动；1：循环定时；2：倒计时 */
	public int workMode;
	/** 输出状态;0：关闭；1：开启。该值只在工作模式为0x00时有效 */
	public int outputState;

	public AppSetDeviceMode(byte[] receiveData) {
		super(receiveData);
		workMode = receiveData[hearderLength + delta] & 0xff; // 工作模式
		outputState = receiveData[hearderLength + 1 + delta] & 0xff; // 输出状态
	}
}
