package com.smartswitchsimulationandroid.device;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.smartswitchsimulationandroid.parmars.ToolParams;

import android.R.integer;
import android.text.Html;
import android.text.Spanned;

public class TimeSettingInfo implements Comparable<TimeSettingInfo> {
	private int parameterNO;
	private int startTimeHour;
	private int startTimeMinute;
	private int startTimeSecond;
	private int endTimeHour;
	private int endTimeMinute;
	private int endTimeSecond;
	private byte timeEnable;
	private byte outputState;

	public TimeSettingInfo(int parameterNO, int startTimeHour, int startTimeMinute, int startTimeSecond, int endTimeHour, int endTimeMinute, int endTimeSecond,
			byte timeEnable, byte outputState) {
		this.parameterNO = parameterNO;
		this.startTimeHour = startTimeHour;
		this.startTimeMinute = startTimeMinute;
		this.startTimeSecond = startTimeSecond;
		this.endTimeHour = endTimeHour;
		this.endTimeMinute = endTimeMinute;
		this.endTimeSecond = endTimeSecond;
		this.timeEnable = timeEnable;
		this.outputState = outputState;
	}

	public TimeSettingInfo(int parameterNo, String startTime, String endTime, byte timeEnable, byte outputState) {
		this.parameterNO = parameterNo;
		String[] startTimeStrings = startTime.split(":");
		String[] endTimeStrings = endTime.split(":");
		startTimeHour = Integer.parseInt(startTimeStrings[0]);
		startTimeMinute = Integer.parseInt(startTimeStrings[1]);
		startTimeSecond = Integer.parseInt(startTimeStrings[2]);
		endTimeHour = Integer.parseInt(endTimeStrings[0]);
		endTimeMinute = Integer.parseInt(endTimeStrings[1]);
		endTimeSecond = Integer.parseInt(endTimeStrings[2]);
		this.timeEnable = timeEnable;
		this.outputState = outputState;

	}

	public TimeSettingInfo(byte[] info) {
		parameterNO = info[0] & 0xff;
		startTimeHour = info[1] & 0xff;
		startTimeMinute = info[2] & 0xff;
		startTimeSecond = info[3] & 0xff;
		endTimeHour = info[4] & 0xff;
		endTimeMinute = info[5] & 0xff;
		endTimeSecond = info[6] & 0xff;
		timeEnable = info[7];
		outputState = info[8];
	}

	/**
	 * 定时器编号
	 * 
	 * @return
	 */
	public int getParameterNO() {
		return parameterNO;
	}

	public int getStartTimeHour() {
		return startTimeHour;
	}

	public int getStartTimeMinute() {
		return startTimeMinute;
	}

	public int getStartTimeSecond() {
		return startTimeSecond;
	}

	public int getEndTimeHour() {
		return endTimeHour;
	}

	public int getEndTimeMinute() {
		return endTimeMinute;
	}

	public int getEndTimeSecond() {
		return endTimeSecond;
	}

	public byte getTimeEnable() {
		return timeEnable;
	}

	public byte[] getData() {
		byte[] data = new byte[9];
		data[0] = (byte) (parameterNO & 0xff);
		data[1] = (byte) (startTimeHour & 0xff);
		data[2] = (byte) (startTimeMinute & 0xff);
		data[3] = (byte) (startTimeSecond & 0xff);
		data[4] = (byte) (endTimeHour & 0xff);
		data[5] = (byte) (endTimeMinute & 0xff);
		data[6] = (byte) (endTimeSecond & 0xff);
		data[7] = timeEnable;
		data[8] = outputState;
		return data;
	}

	public CharSequence toText() {
		return Html.fromHtml(String.format("<font color='#%x'>Start:</font>%02d:%02d:%02d &nbsp;&nbsp" + "<font color='#%x'>End</font>:%02d:%02d:%02d",
				0xf78d3e, startTimeHour, startTimeMinute, startTimeSecond, 0xf78d3e, endTimeHour, endTimeMinute, endTimeSecond));
	}

	public CharSequence toStartText() {
		// String ampm ;
		// int hour=startTimeHour;
		//
		// if(hour==0){
		// ampm = "PM";
		// hour=12;
		// }
		// else if(hour>=1 && hour<=12)
		// ampm ="AM";
		// else{
		// ampm = "PM";
		// hour -= 12;
		// }
		// //return
		// Html.fromHtml(String.format("<small ><font color='#%x'>Start&nbsp;</font></small>%02d:%02d %s&nbsp;",
		// // 0xbfbfbf,hour,startTimeMinute,ampm));
		// return String.format("Start %02d:%02d %s ",
		// hour,startTimeMinute,ampm);
		return ToolParams.setHour24_2_Hour12(startTimeHour, startTimeMinute);
	}

	public CharSequence toEndText() {
		// int hour=endTimeHour;
		// String ampm ;
		// if(hour==0){
		// ampm = "PM";
		// hour=12;
		// }
		// else if(hour>=1 && hour<=12)
		// ampm ="AM";
		// else{
		// ampm = "PM";
		// hour -= 12;
		// }
		// // return
		// Html.fromHtml(String.format("<small ><font color='#%x'>End&nbsp;</font></small>%02d:%02d %s",
		// // 0xbfbfbf,hour,endTimeMinute,ampm));
		// return String.format("End %02d:%02d %s ", hour,endTimeMinute,ampm);
		return ToolParams.setHour24_2_Hour12(endTimeHour, endTimeMinute);
	}

	public void setParameterNO(int parameterNO) {
		this.parameterNO = parameterNO;
	}

	public void setStartTimeHour(int startTimeHour) {
		this.startTimeHour = startTimeHour;
	}

	public void setStartTimeMinute(int startTimeMinute) {
		this.startTimeMinute = startTimeMinute;
	}

	public void setStartTimeSecond(int startTimeSecond) {
		this.startTimeSecond = startTimeSecond;
	}

	public void setEndTimeHour(int endTimeHour) {
		this.endTimeHour = endTimeHour;
	}

	public void setEndTimeMinute(int endTimeMinute) {
		this.endTimeMinute = endTimeMinute;
	}

	public void setEndTimeSecond(int endTimeSecond) {
		this.endTimeSecond = endTimeSecond;
	}

	public void setTimeEnable(byte timeEnable) {
		this.timeEnable = timeEnable;
	}

	/**
	 * 
	 * @Title: isEnable
	 * @Description: 如果所有数据为0，则不启用，列表中不显示
	 * @author wx
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean isValid() {
		if ((startTimeHour == 0 && startTimeMinute == 0 && startTimeSecond == 0)
				&& (endTimeHour == 0 && endTimeMinute == 0 && endTimeSecond == 0 && timeEnable == 0)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除一项
	 * @author wx
	 * @param
	 * @return void
	 * @throws
	 */
	public void delete() {
		startTimeHour = 0;
		startTimeMinute = 0;
		startTimeSecond = 0;
		endTimeHour = 0;
		endTimeMinute = 0;
		endTimeSecond = 0;
		timeEnable = 0;
	}

	@Override
	public int compareTo(TimeSettingInfo another) {
		if (this.getParameterNO() > another.getParameterNO()) {
			return 1;
		} else if (this.getParameterNO() == another.getParameterNO()) {
			return 0;
		} else {
			return -1;
		}
	}

	public byte getOutputState() {
		return outputState;
	}

	public void setOutputState(byte outputState) {
		this.outputState = outputState;
	}

}
