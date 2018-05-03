package com.smartswitchsimulationandroid.command;

import java.util.Calendar;
import java.util.List;

import com.smartswitchsimulationandroid.device.TimeSettingInfo;

public class AppControlSocketAckBase {
	byte[] data;
	static int sn = 0;
	int delta = 0;
	Calendar cal = Calendar.getInstance();
	
	public void init(int dataLength,int OperationState){
		
		if (dataLength < 128 + 5)
			data = new byte[dataLength];
		else if (dataLength <= 16383 + 5)
			data = new byte[dataLength + 1];
		else if (dataLength >= 2097151 + 5)
			data = new byte[dataLength + 2];
		else if (dataLength >= 268435455 + 5)
			data = new byte[dataLength + 3];
		else {
			data = new byte[dataLength + 3];
			dataLength = 268435455 + 5;
		}
		
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x03; // PackHeader =0x00000003;
		data[4] = 0x36; // PackLength =0x03
		data[6] = 0x00;
		data[7] = -0x6b; // CommandWord = 0x0094
		data[8] = (byte) OperationState;
		data[9 + delta] = (byte) (sn & 0xff); // sn,消息的序号，用于消息的ACK
		data[10 + delta] = (byte) ((sn >> 8) & 0xff);
		data[11 + delta] = (byte) ((sn >> 16) & 0xff);
		data[12 + delta] = (byte) ((sn >> 24) & 0xff);
	}
	
	public AppControlSocketAckBase(int OperationState,int WorkMode, int OutputState, int MQTTEnable){
		init(24, OperationState);
		
		data[13 + delta] = 0x01;
		data[14 + delta] = 0x02;	//ActionCode
		data[15 + delta] = (byte) (cal.get(Calendar.YEAR)-2000);
		data[16 + delta] = (byte) (cal.get(Calendar.MONTH)+1);
		data[17 + delta] = (byte) cal.get(Calendar.DAY_OF_MONTH);	//DriverDate
		data[18 + delta] = (byte) cal.get(Calendar.HOUR_OF_DAY);
		data[19 + delta] = (byte) cal.get(Calendar.MINUTE);
		data[20 + delta] = (byte) cal.get(Calendar.SECOND);			//DriverTime
		data[21 + delta] = (byte) WorkMode;
		data[22 + delta] = (byte) OutputState;
		data[23 + delta] = (byte) MQTTEnable;
	}
	
	public AppControlSocketAckBase(int OperationState,List<TimeSettingInfo> timeSettingInfos ){
		int timesCount = timeSettingInfos.size();
		byte[] timeInfoBytes = new byte[timesCount * 9];

		for (int i = 0; i < timesCount; i++) {
			System.arraycopy(timeSettingInfos.get(i).getData(), 0, timeInfoBytes, i * 9, 9);
		}
		
		init(15 + timeInfoBytes.length, OperationState); 
		
		data[13 + delta] = 0x01;
		data[14 + delta] = 0x04;	//ActionCode
		System.arraycopy(timeInfoBytes, 0, data, 15 + delta, timeInfoBytes.length);
	}
	
	public AppControlSocketAckBase(int OperationState,int WorkMode, int OutputState){
		
		init(23, OperationState);
		
		data[13 + delta] = 0x01;
		data[14 + delta] = 0x06;	//ActionCode
		data[15 + delta] = (byte) (cal.get(Calendar.YEAR)-2000);
		data[16 + delta] = (byte) (cal.get(Calendar.MONTH)+1);
		data[17 + delta] = (byte) cal.get(Calendar.DAY_OF_MONTH);	//DriverDate
		data[18 + delta] = (byte) cal.get(Calendar.HOUR_OF_DAY);
		data[19 + delta] = (byte) cal.get(Calendar.MINUTE);
		data[20 + delta] = (byte) cal.get(Calendar.SECOND);			//DriverTime
		data[21 + delta] = (byte) WorkMode;
		data[22 + delta] = (byte) OutputState;
	}
	
	public AppControlSocketAckBase(int OperationState, int ParameterCount, List<TimeSettingInfo> timeSettingInfos ){
		int timesCount = timeSettingInfos.size();
		byte[] timeInfoBytes = new byte[timesCount * 9];

		for (int i = 0; i < timesCount; i++) {
			System.arraycopy(timeSettingInfos.get(i).getData(), 0, timeInfoBytes, i * 9, 9);
		}
		
		init(16 + timeInfoBytes.length, OperationState); 
		
		data[13 + delta] = 0x01;
		data[14 + delta] = 0x08;	//ActionCode
		data[15 + delta] = (byte) ParameterCount;
		System.arraycopy(timeInfoBytes, 0, data, 16 + delta, timeInfoBytes.length);

	}
	
	public AppControlSocketAckBase(int OperationState,byte[] delayTime, byte[] duration, int completedMode, int completedOutputState){		
		init(21, OperationState); 
		
		data[13 + delta] = 0x01;
		data[14 + delta] = 0x0a;	//ActionCode
		System.arraycopy(delayTime, 0, data, 15, 2); // Delay 延迟x小时y分钟后执行倒计时
		System.arraycopy(duration, 0, data, 17, 2); // Duration 倒计时持续时间x小时，y分钟
		data[19 + delta] = (byte) completedMode;
		data[20 + delta] = (byte) completedOutputState;

	}
	
	public byte[] getData() {
		return data;
	}
}
