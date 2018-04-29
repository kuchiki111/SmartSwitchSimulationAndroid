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
	// �Ƿ�Ϊ����״̬
	private static boolean isTest = false;
	private static android.os.Vibrator vibrator;
	private static SoundPool soundPool;
	

	/**
	 * �Ƿ���ͨ����
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
	 * ��ȡ�ֻ�ID
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
	 * �ֻ������ʽ��֤
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
	 * ��ȡ��ǰ�� versionName
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
	 * �Ƿ������İ汾
	 * 
	 * @param context
	 * @return zh ���� en Ӣ��
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
		PackageManager nPackageManager = context.getPackageManager();// �õ���������
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
	// * ��ȡ�豸ID
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
	 * ������һ�� �������� һ��
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
	 * if(isVibrator){ if(vibrator==null) { //�����Ȩ��
	 * vibrator=(android.os.Vibrator
	 * )context.getSystemService(Context.VIBRATOR_SERVICE); }
	 * //�Զ�����ʱ�䣬��һ������Ϊ����ʱʱ�䣬�ڶ���Ϊ��ʱ�䣬���滹�ɼӲ���
	 * 
	 * long [] pattern = {50,100};
	 * 
	 * //-1Ϊ��һ�Σ�0Ϊһֱ�� 2Ϊ��2�� vibrator.vibrate(pattern,-1); } if(isSou){
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
	 * �Ժ�������У�����λ��ʮ�����Ƶı��룬�ڱ��ֽ��а�����ת���� ��0x02->0x40,0x5a->0xa5,0xb5->0xad
	 * 
	 * @param src
	 * @return ת�����ֽ�����ĺ������
	 */
	/*
	 * public static String Reverse_IrData(String src) { String des=""; int len
	 * = src.length(); int i; if(len>0) { String []srcArray = src.split(",");
	 * for(i=0;i<srcArray.length;i++) { if(i>0)//���˵�0�����Ժ�ÿ��֮ǰ��Ҫ�Ӷ��ŷָ��� des+=",";
	 * if(srcArray[i].length()==2 && !srcArray[i].contains("-") &&
	 * !srcArray[i].contains(">")&& !srcArray[i].contains(";")) {
	 * //����ע��16�����ַ��иߵ�λҪǰ��λ��ת�� des+= Reverse(srcArray[i].toCharArray()[1]);
	 * des+= Reverse(srcArray[i].toCharArray()[0]); } else des+=srcArray[i]; } }
	 * return des; }
	 */
	/**
	 * 
	 * isNeedAdjustTime �ж��Ƿ���Ҫ����ʱ��
	 * 
	 * @author wx
	 * @param @param time �豸��ʱ��
	 * @param @param timeValue �豸ʱ����ֻ�ʱ�������ֵ
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
	 * getRealTime ����������ʱ��
	 * 
	 * @author wx
	 * @param @param time ���豸�л�ȡ��ʱ��
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
	 * DateToString ��date��ʽ�����ΪString,��ʽΪHH:mm:ss
	 * 
	 * @author wx
	 * @param date
	 * @param isHour24
	 *            �Ƿ���Ҫ24Сʱ
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
	 * ��24Сʱ�Ƶ�HH��mmת���� 12Сʱ�Ƶ�hh��mm +am/pm���ַ���
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
	 * ��24Сʱ�Ƶ�HH��mmת���� 12Сʱ�Ƶ�hh��mm +am/pm���ַ���,����SpannableString����ʽ���ַ���
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
	 * getBitArray ��byteת��Ϊһ������Ϊ8��byte���飬����ÿ��ֵ����bit
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
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
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
            // Ӧ�ó���λ�ڶ�ջ�Ķ���  
            String src = context.getPackageName();  
            String des = tasksInfo.get(0).topActivity.getPackageName();  
            if (src.equalsIgnoreCase(des)) {  
                return true;  
            }  
        }  
        return false;  
    } 
}
