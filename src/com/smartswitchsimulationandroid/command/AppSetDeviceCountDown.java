package com.smartswitchsimulationandroid.command;

public class AppSetDeviceCountDown extends AppControlSocket {

	/** 倒计时延迟时间 */
	public byte[] delayTime;
	/** 倒计时持续时间 */
	public byte[] duration;
	/** 倒计时完成后进入的模式：手动 0x00,循环定时 0x01 */
	public int completedMode;
	/** 倒计时完成后的输出状态：关闭 0x00,开启 0x01 */
	public int completedOutputState;

	public AppSetDeviceCountDown(byte[] receiveData) {
		super(receiveData);

		delayTime = new byte[2];
		System.arraycopy(receiveData, hearderLength + delta, delayTime, 0, 2); // 倒计时开始时间
		duration = new byte[2];
		System.arraycopy(receiveData, hearderLength + 2 + delta, duration, 0, 2); // 倒计时结束时间

		// 倒计时完成后进入的模式
		completedMode = receiveData[hearderLength + 4 + delta] & 0xff;
		// 倒计时完成后的输出状态
		completedOutputState = receiveData[hearderLength + 5 + delta] & 0xff;
	}
}
