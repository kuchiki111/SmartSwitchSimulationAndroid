package com.smartswitchsimulationandroid.command;

/**
 * 5.3.2 设备响应APP绑定命令的信息包格式<br>
 * TCP数据包发送，目标端口12416
 */

public class AppBindDeviceAck {

	int ProtocolVer;
	public int VarLen;
	public int Flag;
	public int Cmd;
	public int PasscodeLen;
	public String Passcode;
	public boolean result;// 额外增加，表示绑定成功与否，协议中无此字段

	public AppBindDeviceAck(byte[] receiveData) {

		// Pack_Header
		ProtocolVer = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		// Pack_Length
		VarLen = receiveData[4] & 0xff;
		// Flag
		Flag = receiveData[5] & 0xff;
		// Command_Type
		Cmd = ((receiveData[6] & 0xff) << 8) | (receiveData[7] & 0xff);
		PasscodeLen = ((receiveData[8] & 0xff) << 8) | (receiveData[9] & 0xff);
		// passCodeLen为0时表示不可绑定
		if (PasscodeLen == 0) {
			Passcode = null;
			result = false;
		} else {
			byte[] bytePasscode = new byte[PasscodeLen];
			System.arraycopy(receiveData, 10, bytePasscode, 0, PasscodeLen);
			Passcode = new String(bytePasscode);
			result = true;
		}
	}
}
