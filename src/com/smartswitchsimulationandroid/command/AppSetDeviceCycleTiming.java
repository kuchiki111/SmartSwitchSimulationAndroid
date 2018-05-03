package com.smartswitchsimulationandroid.command;

import java.util.ArrayList;
import java.util.List;

import com.smartswitchsimulationandroid.device.TimeSettingInfo;

public class AppSetDeviceCycleTiming extends AppControlSocket {
	/** 定时信息的数量，最大0x14 */
	public int timeSettingCount;
	/** 定时信息设置参数 */
	public List<TimeSettingInfo> timeSettingInfos;

	// /**当前工作模式。0：手动；1：循环定时；2：倒计时*/
	// public int workMode;
	// /**输出状态;0：关闭；1：开启。该值只在工作模式为0x00时有效*/
	// public int outputState;
	public AppSetDeviceCycleTiming(byte[] receiveData) {
		super(receiveData);
		timeSettingCount = receiveData[hearderLength + delta] & 0xff;
		timeSettingInfos = new ArrayList<TimeSettingInfo>();
		// int validParamterSN =0;//有效的定时器编号，这里需要对定时器编号按有效无效重新调整。新协议，不修改原有编号
		byte[] info = new byte[9];
		// 设备的状态
		for (int i = 0; i < timeSettingCount; i++) {
			System.arraycopy(receiveData, hearderLength + 1 + delta + i * 9, info, 0, 9);
			TimeSettingInfo timeInfo = new TimeSettingInfo(info);
			if (timeInfo.isValid()) {
				// timeInfo.setParameterNO(validParamterSN++);//有效的定时器编号，这里需要对定时器编号按有效无效重新调整。
				timeSettingInfos.add(timeInfo);
			}
		}
		// 工作模式 31 + 8 = 39
		// workMode = receiveData[39 + appControlSocketAck.delta] & 0xff;
		// //输出状态
		// workMode = receiveData[40 + appControlSocketAck.delta] & 0xff;
	}
}
