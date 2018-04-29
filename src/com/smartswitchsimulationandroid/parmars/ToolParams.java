package com.smartswitchsimulationandroid.parmars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract.Contacts.Data;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

public class ToolParams {
	// 是否为测试状态
	private static boolean isTest = false;
	private static android.os.Vibrator vibrator;
	private static SoundPool soundPool;
	

	/**
	 * 是否联通网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			if (networkInfo.isAvailable() && networkInfo.isConnected()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取手机ID
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getTelephoneId(Context context) {
		String telephoneID;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		telephoneID = telephonyManager.getDeviceId();
		telephoneID = telephoneID.length() > 20 ? telephoneID.substring(telephoneID.length() - 20, telephoneID.length()) : telephoneID;
		return telephoneID;
	}

	public static Boolean getIsTest() {
		return isTest;
	}

	/**
	 * 手机号码格式验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNumber(String mobiles) {
		String telRegex = "[1][358]\\d{9}";
		if (mobiles.isEmpty()) {
			return false;
		} else {
			return mobiles.matches(telRegex);
		}
	}

	/**
	 * 获取当前的 versionName
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			String pkName = context.getPackageName();
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(pkName, 0);
			versionName = pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}

	public static String getJsonString(Context context, String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			AssetManager assetManager = context.getAssets();
			BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
			String line;
			while ((line = bf.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	public static String getJsonChineseString(Context context, String fileName) {
		String string = new String();
		String line;
		try {
			InputStream inputStream = context.getResources().getAssets().open(fileName);
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			string = new String(buffer, "GBK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 是否是中文版本
	 * 
	 * @param context
	 * @return zh 中文 en 英文
	 */
	public static boolean isZH(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	public static int getVersionCode(Context context) {
		PackageManager nPackageManager = context.getPackageManager();// 得到包管理器
		try {
			PackageInfo nPackageInfo = nPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

			return nPackageInfo.versionCode;

		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 1;
	}

	// /**
	// * 获取设备ID
	// */
	// private void getTelephoneId() {
	// // TODO Auto-generated method stub
	// TelephonyManager
	// telephonyManager=(TelephonyManager)getSystemService(context.TELEPHONY_SERVICE);
	// String telephoneId=telephonyManager.getDeviceId();
	// SharedPreferences
	// sp=getSharedPreferences(SharedPreferencesParams.TELEPHONE_INFORMATION,
	// MODE_PRIVATE);
	// SharedPreferences.Editor editor = sp.edit();
	// editor.putString(SharedPreferencesParams.TELEPHONE_ID, telephoneId);
	// editor.apply();
	// }
	//
	// private boolean isZh() {
	// Locale locale = getResources().getConfiguration().locale;
	// String language = locale.getLanguage();
	// if (language.endsWith("zh"))
	// return true;
	// else
	// return false;
	// }
	/**
	 * 按键震动一次 按键声音 一次
	 * 
	 * @param context
	 */
	/*
	 * @SuppressWarnings("deprecation") public static void startVibrator(Context
	 * context){
	 * 
	 * SharedPreferences sp =
	 * context.getSharedPreferences(SharedPreferencesParams
	 * .INSTALL_SWITCHINFORMATION, Context.MODE_PRIVATE); Boolean
	 * isVibrator=sp.getBoolean(SharedPreferencesParams.INSTALL_MSGSWITCH,
	 * true); Boolean
	 * isSou=sp.getBoolean(SharedPreferencesParams.INSTALL_SOUSWITCH, true);
	 * if(isVibrator){ if(vibrator==null) { //获得震动权限
	 * vibrator=(android.os.Vibrator
	 * )context.getSystemService(Context.VIBRATOR_SERVICE); }
	 * //自定义震动时间，第一个参数为震动延时时间，第二个为震动时间，后面还可加参数
	 * 
	 * long [] pattern = {50,100};
	 * 
	 * //-1为震动一次，0为一直震动 2为震动2次 vibrator.vibrate(pattern,-1); } if(isSou){
	 * 
	 * if(soundPool==null) { soundPool=new SoundPool(1,
	 * AudioManager.STREAM_MUSIC, 0); soundPool.load(context, R.raw.ringer,1);
	 * try { Thread.sleep(100); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * } soundPool.play(1, 1, 1, 1, 0, 1);
	 * 
	 * } }
	 * 
	 * static char list[] = new
	 * char[]{'0','8','4','C','2','A','6','E','1','9','5','D','3','B','7','F'};
	 * private static String Reverse(char ch) { char buf= ch;
	 * 
	 * if((ch >= '0')&&(ch <= '9')) buf = list[ch-'0']; if((ch >= 'A')&&(ch <=
	 * 'F')) buf = list[ch-'A'+10];
	 * 
	 * return String.valueOf(buf); }
	 */

	/**
	 * 对红外编码中，对两位的十六进制的编码，在本字节中按逆序转换， 如0x02->0x40,0x5a->0xa5,0xb5->0xad
	 * 
	 * @param src
	 * @return 转换后字节逆序的红外编码
	 */
	/*
	 * public static String Reverse_IrData(String src) { String des=""; int len
	 * = src.length(); int i; if(len>0) { String []srcArray = src.split(",");
	 * for(i=0;i<srcArray.length;i++) { if(i>0)//除了第0个，以后每个之前都要加逗号分隔符 des+=",";
	 * if(srcArray[i].length()==2 && !srcArray[i].contains("-") &&
	 * !srcArray[i].contains(">")&& !srcArray[i].contains(";")) {
	 * //这里注意16进制字符中高低位要前后换位置转换 des+= Reverse(srcArray[i].toCharArray()[1]);
	 * des+= Reverse(srcArray[i].toCharArray()[0]); } else des+=srcArray[i]; } }
	 * return des; }
	 */
	/**
	 * 
	 * isNeedAdjustTime 判断是否需要调整时间
	 * 
	 * @author wx
	 * @param @param time 设备的时间
	 * @param @param timeValue 设备时间和手机时间的最大差值
	 * @return boolean
	 * @throws null
	 */
	public static boolean isNeedAdjustTime(byte[] time, int timeValue) {
		Calendar cal = Calendar.getInstance();

		Date deviceTime = getRealTime(time);
		Date nowPhoneTime = cal.getTime();

		if (deviceTime != null) {
			long timeDifference = (nowPhoneTime.getTime() - deviceTime.getTime()) / 1000;
			if (Math.abs(timeDifference) < timeValue) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * getRealTime 返回真正的时间
	 * 
	 * @author wx
	 * @param @param time 从设备中获取的时间
	 * @param @return
	 * @return Date
	 * @throws null
	 */
	public static Date getRealTime(byte[] time) {
		Date date = null;
		String buf;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (time[0] != 0) {
			buf = String.format("%d-%d-%d %d:%d:%d", 2000 + time[0], time[1], time[2], time[3], time[4], time[5]);

			try {
				date = formatter.parse(buf);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			date = new Date(2000, 1, 1, 0, 0, 0);
		}
		return date;
	}

	/**
	 * 
	 * DateToString 将date格式化输出为String,格式为HH:mm:ss
	 * 
	 * @author wx
	 * @param date
	 * @param isHour24
	 *            是否需要24小时
	 * @return String
	 * @throws null
	 */
	public static String DateToTimeString(Date date, Boolean isHour24) {

		int hour = date.getHours();
		int minute = date.getMinutes();
		int second = date.getSeconds();

		if (isHour24 == false) {
			// String str = "";
			// SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
			//
			// str = format.format(date);
			String ampm;
			if (hour == 0) {
				ampm = "AM";
				hour = 12;
			} else if (hour >= 1 && hour < 12)
				ampm = "AM";
			else if (hour == 12) {
				ampm = "PM";
			} else {
				ampm = "PM";
				hour -= 12;
			}
			String str = String.format("%02d:%02d:%02d %s", hour, minute, second, ampm);
			return str;
		} else {
			// String str = "";
			// SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			// str = format.format(date);
			String str = String.format("%02d:%02d:%02d", hour, minute, second);
			return str;
		}
	}

	/**
	 * 把24小时制的HH：mm转换成 12小时制的hh：mm +am/pm的字符串
	 * 
	 * @param Hour24
	 * @param minute
	 * @return
	 */
	public static String setHour24_2_Hour12(int Hour24, int minute) {
		String ampm;
		if (Hour24 == 0) {
			ampm = "AM";
			Hour24 = 12;
		} else if (Hour24 >= 1 && Hour24 < 12)
			ampm = "AM";
		else if (Hour24 == 12)
			ampm = "PM";
		else {
			ampm = "PM";
			Hour24 -= 12;
		}
		return String.format("%02d:%02d %s", Hour24, minute, ampm);

	}
	
	/**
	 * 把24小时制的HH：mm转换成 12小时制的hh：mm +am/pm的字符串,返回SpannableString，格式化字符串
	 * 
	 * @param Hour24
	 * @param minute
	 * @return
	 */
	public static SpannableString setSpanHour24_2_Hour12(int Hour24, int minute) {
		String ampm;
		if (Hour24 == 0) {
			ampm = "AM";
			Hour24 = 12;
		} else if (Hour24 >= 1 && Hour24 < 12)
			ampm = "AM";
		else if (Hour24 == 12)
			ampm = "PM";
		else {
			ampm = "PM";
			Hour24 -= 12;
		}
		SpannableString msp = new SpannableString(String.format("%02d:%02d %s", Hour24, minute, ampm));
		msp.setSpan(new AbsoluteSizeSpan(28, true), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new AbsoluteSizeSpan(14, true), 5,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return msp ;

	}

	/**
	 * 
	 * getBitArray 将byte转换为一个长度为8的byte数组，数组每个值代表bit
	 * 
	 * @author wx
	 * @param @param b
	 * @param @return
	 * @return byte[]
	 * @throws null
	 */
	public static byte[] getBitArray(byte b) {
		byte[] array = new byte[8];
		/*
		 * for (int i = 7; i >= 0; i--) { array[i] = (byte) (b & 1); b = (byte)
		 * (b >> 1); }
		 */
		for (int i = 0; i <= 7; i++) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}

	public static int getVarlenDelta(byte[] data) {
		// int multiplier = 1;
		// int value = 0;
		int startPos = 4;
		int digit = data[startPos++] & 0xff;
		int detla = 0;
		while ((digit & 128) != 0) {

			digit = data[startPos++] & 0xff;
			// value += (digit & 127) * multiplier;
			// multiplier *= 128;
			detla++;
		}
		return detla;

	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		if (context == null) {
			return 0;
		}
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	public static boolean isAppOnForeground(Context context) {  
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
        List<RunningTaskInfo> tasksInfo = manager.getRunningTasks(1);  
        if (tasksInfo.size() > 0) {  
            // 应用程序位于堆栈的顶层  
            String src = context.getPackageName();  
            String des = tasksInfo.get(0).topActivity.getPackageName();  
            if (src.equalsIgnoreCase(des)) {  
                return true;  
            }  
        }  
        return false;  
    } 
}
