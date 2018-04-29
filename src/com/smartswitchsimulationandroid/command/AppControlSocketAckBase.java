package com.smartswitchsimulationandroid.command;


public class AppControlSocketAckBase {
	/** 用于控制协议部分的varlen部分，当超过128时需要使用2位，超过16384需要使用3位..... */
	protected int delta = 0;
	/** 头部固定的长度 */
	protected int hearderLength = 0;
	protected int ProtocolVer;
	public int VarLen;
	public int Flag;
	public int Cmd;
	public int operationState;
	public String SN;
	public int functionType;
	public int actionType;

	public AppControlSocketAckBase(byte[] receiveData) {
		// Pack_Header
		ProtocolVer = (receiveData[0] & 0xff << 24) | (receiveData[1] & 0xff << 16) | (receiveData[2] & 0xff << 8) | (receiveData[3] & 0xff);
		// Pack_Length
		do {
			int num = receiveData[4 + delta] & 0x7f;
			int i = delta;
			while (i > 0) {
				num *= 128;
				i--;
			}
			VarLen += num;
		} while ((receiveData[4 + delta++] & 0x80) == 0x80);
		delta--;
		// Flag
		Flag = receiveData[5 + delta] & 0xff;
		// Command_Type
		Cmd = ((receiveData[6 + delta] & 0xff) << 8) | (receiveData[7 + delta] & 0xff);
		// OperationState
		operationState = receiveData[8 + delta] & 0xff;
		// SN
		byte[] SNBytes = new byte[4];
		System.arraycopy(receiveData, 9 + delta, SNBytes, 0, 4);
		SN = new String(SNBytes);
		functionType = receiveData[13 + delta] & 0xff;
		actionType = receiveData[14 + delta] & 0xff;
		hearderLength = 15;
	}
}
