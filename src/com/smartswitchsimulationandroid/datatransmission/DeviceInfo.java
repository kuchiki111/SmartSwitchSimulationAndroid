package com.smartswitchsimulationandroid.datatransmission;

import com.smartswitchsimulationandroid.tools.GetMac;

public class DeviceInfo {
	
	public String did;
	public String pid;
	public String mac;
	public String productkey;
	public String passcode;
	public String version;
	
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMac() {
//		GetMac.getMac(context);
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getProductkey() {
		return productkey;
	}
	public void setProductkey(String productkey) {
		this.productkey = productkey;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
