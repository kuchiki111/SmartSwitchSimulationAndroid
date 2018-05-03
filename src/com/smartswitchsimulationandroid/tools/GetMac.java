package com.smartswitchsimulationandroid.tools;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;



public class GetMac {

	public static String getMacAddr() {  
        try {  
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());  
            for (NetworkInterface nif : all) {  
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;  
  
                byte[] macBytes = nif.getHardwareAddress();  
                if (macBytes == null) {  
                    return "";  
                }  
  
                StringBuilder res1 = new StringBuilder();  
                for (byte b : macBytes) {  
                    res1.append(String.format("%02X",b));  
                }  
  
                return res1.toString();  
            }  
        } catch (Exception ex) {  
        }  
        return "020000000000";  
    }   
}
