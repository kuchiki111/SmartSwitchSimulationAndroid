package com.smartswitchsimulationandroid.command;

import java.util.ArrayList;
import java.util.List;

import com.smartswitchsimulationandroid.device.TimeSettingInfo;

public class AppSetDeviceCycleTiming extends AppControlSocket {
	/** ��ʱ��Ϣ�����������0x14 */
	public int timeSettingCount;
	/** ��ʱ��Ϣ���ò��� */
	public List<TimeSettingInfo> timeSettingInfos;

	// /**��ǰ����ģʽ��0���ֶ���1��ѭ����ʱ��2������ʱ*/
	// public int workMode;
	// /**���״̬;0���رգ�1����������ֵֻ�ڹ���ģʽΪ0x00ʱ��Ч*/
	// public int outputState;
	public AppSetDeviceCycleTiming(byte[] receiveData) {
		super(receiveData);
		timeSettingCount = receiveData[hearderLength + delta] & 0xff;
		timeSettingInfos = new ArrayList<TimeSettingInfo>();
		// int validParamterSN =0;//��Ч�Ķ�ʱ����ţ�������Ҫ�Զ�ʱ����Ű���Ч��Ч���µ�������Э�飬���޸�ԭ�б��
		byte[] info = new byte[9];
		// �豸��״̬
		for (int i = 0; i < timeSettingCount; i++) {
			System.arraycopy(receiveData, hearderLength + 1 + delta + i * 9, info, 0, 9);
			TimeSettingInfo timeInfo = new TimeSettingInfo(info);
			if (timeInfo.isValid()) {
				// timeInfo.setParameterNO(validParamterSN++);//��Ч�Ķ�ʱ����ţ�������Ҫ�Զ�ʱ����Ű���Ч��Ч���µ�����
				timeSettingInfos.add(timeInfo);
			}
		}
		// ����ģʽ 31 + 8 = 39
		// workMode = receiveData[39 + appControlSocketAck.delta] & 0xff;
		// //���״̬
		// workMode = receiveData[40 + appControlSocketAck.delta] & 0xff;
	}
}
