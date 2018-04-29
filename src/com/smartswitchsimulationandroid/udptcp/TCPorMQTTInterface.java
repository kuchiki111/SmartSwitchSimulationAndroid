//package com.smartswitchsimulationandroid.udptcp;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.chinesetimer.command.AppGetDeviceRSSIAck;
//import com.chinesetimer.command.AppGetDeviceStateMqttAck;
//import com.chinesetimer.command.AppSetDeviceSNTPSyncAck;
//import com.chinesetimer.command.AppSetDeviceTimeAck;
//import com.chinesetimer.command.AppBindDeviceAck;
//import com.chinesetimer.command.AppConnectDeviceHeartbeatAck;
//import com.chinesetimer.command.AppControlSocketAckBase;
//import com.chinesetimer.command.AppControlTimeSynchronizeAck;
//import com.chinesetimer.command.AppLoginDeviceAck;
//import com.chinesetimer.command.AppSetDeviceBinAck;
//import com.chinesetimer.command.AppSetDeviceSNTPAck;
//import com.chinesetimer.command.AppSetDeviceCountDownAck;
//import com.chinesetimer.command.AppSetDeviceCycleTimingAck;
//import com.chinesetimer.command.AppSetDeviceModeAck;
//import com.chinesetimer.command.AppGetDeviceParamsAck;
//import com.chinesetimer.command.AppGetDeviceStateAck;
//import com.chinesetimer.constant.MainActivityHandlerParams;
//import com.chinesetimer.params.AppVariant;
//import com.chinesetimer.params.ToolParams;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//public abstract class TCPorMQTTInterface {
//
//	abstract public Boolean init(String serverIP, int serverPort, Handler handler);
//
//	abstract public void insertItem(byte[] item, boolean withRelatedDevices);
//
//	public abstract void insertItemWithResend(byte[] item, boolean withRelatedDevices);
//
//	public void stop() {
//		isStopped = true;
//	};
//
//	abstract public Boolean init(Context context, String[] publishTopic, String[] myTopics, Handler handler);
//
//	public abstract void changePublishTopic(String[] topics);
//
//	// 重发队列的数组
//	protected List<ResendItem> resendList = new ArrayList<ResendItem>();
//	protected Context mContext;
//	public Handler mHandler;
//
//	public class ResendItem {
//		public byte[] item;
//		/** 重发次数 */
//		public int resendCount;
//		public String topics[];// 表示发送到那个Topic中，mqtt中使用，tcp中无效
//		/** 计时次数，记为4,2,0各发送一次，每过500毫秒累减一次，初始值6，以避免已加入重发队列，马上发送一次的问题 */
//		public int timeCount;
//
//		public ResendItem(byte[] item) {
//			this.item = item;
//			this.resendCount = 0;
//			timeCount = 6;
//			this.topics = null;
//		}
//
//		public ResendItem(byte[] item, String[] topic) {
//			this.item = item;
//			this.resendCount = 0;
//			timeCount = 6;
//			this.topics = topics;
//		}
//	}
//
//	protected boolean isStopped = false;
//
//	/**
//	 * 重发线程类
//	 * 
//	 * @author hxc
//	 *
//	 */
//	protected class ResendThread extends Thread {
//
//		public ResendThread() {
//			// TODO Auto-generated constructor stub
//		}
//
//		@Override
//		public void run() {
//
//			while (!isStopped) {
//				try {
//					Thread.sleep(500);// 每过500ms轮询一次
//					// Log.i("TCP","重发检测");
//					for (int i = 0; i < resendList.size(); i++) {
//						ResendItem resend_item = resendList.get(i);
//						resend_item.timeCount--;
//						if (resend_item.timeCount == 4 || resend_item.timeCount == 2 || resend_item.timeCount == 0) {
//							Log.i("TCPResend", "重发" + resend_item.resendCount);
//							insertItem(resend_item.item, false);
//							Thread.sleep(10);// 两次发包之间间隔10ms
//
//							if (++resend_item.resendCount >= 3) {// 重发次数满3次，从重发队列删除
//								resendList.remove(i);
//								i--;
//							}
//						}
//					}
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	/**
//	 * tcp和mqtt统一的接收数据处理方法
//	 * 
//	 * @param data
//	 * @param mHandler
//	 */
//	public void handler(byte[] data, Handler mHandler, String DID) {
//		if (data.length >= 8) {
//			// Log.d("MQTT", Hex.encodeHexStr(data));
//			int delta = ToolParams.getVarlenDelta(data);
//			int CMD = ((data[6 + delta] << 8) & 0xff00) | (data[7 + delta] & 0xff);
//			// Log.i("MQTT","CMD="+String.format("%x", CMD));
//			if (mHandler != null) {
//				switch (CMD) {
//				// 注意本条是在mqtt中发送本来udp使用的获取所有设备状态的广播包协议（硬件实际也支持mqtt协议发送）
//				// 会转移到ActivitieMain中进行处理
//				case UDPTCPCommand.APP_GET_DEVICE_STATE_UDP_ACK: {
//					AppGetDeviceStateMqttAck ack = new AppGetDeviceStateMqttAck(data, DID);
//
//					Message msg = new Message();
//					msg.obj = ack;
//					msg.what = MainActivityHandlerParams.AppGetDeviceStateUDP;
//					mHandler.sendMessage(msg);
//				}
//					break;
//				case UDPTCPCommand.APP_BIND_DEVICE_ACK: {
//					AppBindDeviceAck appBindDeviceAck = new AppBindDeviceAck(data);
//					if (appBindDeviceAck.result) {
//						Message msg = new Message();
//						msg.obj = appBindDeviceAck;
//						msg.what = MainActivityHandlerParams.BindingDevice;
//						mHandler.sendMessage(msg);
//					} else {
//						mHandler.sendEmptyMessage(MainActivityHandlerParams.BindingDeviceFailed);
//					}
//					break;
//				}
//				case UDPTCPCommand.APP_LOGIN_DEVICE_ACK: {
//					AppLoginDeviceAck appLoginDeviceAck = new AppLoginDeviceAck(data);
//					if (appLoginDeviceAck.result) {
//						Message msg = new Message();
//						msg.obj = appLoginDeviceAck;
//						msg.what = MainActivityHandlerParams.LoginDevice;
//						mHandler.sendMessage(msg);
//					} else {
//						mHandler.sendEmptyMessage(MainActivityHandlerParams.LoginDeviceFailed);
//					}
//					break;
//				}
//				case UDPTCPCommand.APP_ADJUST_DEVICE_TIME_ACK: {
//					AppSetDeviceTimeAck appAdjustDeviceTimeAck = new AppSetDeviceTimeAck(data);
//					if (appAdjustDeviceTimeAck.result) {
//						Message msg = new Message();
//						msg.obj = appAdjustDeviceTimeAck;
//						msg.what = MainActivityHandlerParams.SetDeviceTime;
//						mHandler.sendMessage(msg);
//					} else {
//						mHandler.sendEmptyMessage(MainActivityHandlerParams.SetDeviceTimeFailed);
//					}
//					break;
//				}
//				case UDPTCPCommand.APP_SETTING_DEVICE_SNTP_ACK: {
//					AppSetDeviceSNTPAck appSettingDeviceSNTPAck = new AppSetDeviceSNTPAck(data);
//					if (appSettingDeviceSNTPAck.result) {
//						Message msg = new Message();
//						msg.obj = appSettingDeviceSNTPAck;
//						msg.what = MainActivityHandlerParams.SettingDeviceSNTP;
//						mHandler.sendMessage(msg);
//					} else {
//						mHandler.sendEmptyMessage(MainActivityHandlerParams.SettingDeviceSNTPFailed);
//					}
//					break;
//				}
//				case UDPTCPCommand.APP_SETTING_DEVICE_SNTP_SYNC_ACK: {
//					AppSetDeviceSNTPSyncAck ack = new AppSetDeviceSNTPSyncAck(data);
//					if (ack.result) {
//						Message msg = new Message();
//						msg.obj = ack;
//						msg.what = MainActivityHandlerParams.SettingDeviceSNTPSync;
//						mHandler.sendMessage(msg);
//					} else {
//						mHandler.sendEmptyMessage(MainActivityHandlerParams.SettingDeviceSNTPSyncFailed);
//					}
//					break;
//				}
//				case UDPTCPCommand.APP_CONNECT_DEVICE_HEARTBEAT_ACK: {
//					AppConnectDeviceHeartbeatAck appConnectDeviceHeartbeatAck = new AppConnectDeviceHeartbeatAck(data);
//					Message msg = new Message();
//					msg.obj = appConnectDeviceHeartbeatAck;
//					msg.what = MainActivityHandlerParams.ConnectDeviceHeartbeat;
//					mHandler.sendMessage(msg);
//					break;
//				}
//				// case UDPTCPCommand.APP_SET_TIME_ZONE_ACK:
//				// {
//				// AppSetTimeZoneAck appSetTimeZoneAck = new
//				// AppSetTimeZoneAck(data);
//				// if(appSetTimeZoneAck.result){
//				// Message msg = new Message();
//				// msg.obj = appSetTimeZoneAck;
//				// msg.what = MainActivityHandlerParams.SetDeviceTimeZone;
//				// mHandler.sendMessage(msg);
//				// }else{
//				// mHandler.sendEmptyMessage(MainActivityHandlerParams.SetDeviceTimeZoneFailed);
//				// }
//				// break;
//				// }
//				case UDPTCPCommand.APP_SET_BIN_ACK: {
//					AppSetDeviceBinAck appSetDeviceBinAck = new AppSetDeviceBinAck(data);
//					if (appSetDeviceBinAck.OperationState != 1) {
//						Message msg = new Message();
//						msg.obj = appSetDeviceBinAck;
//						msg.what = MainActivityHandlerParams.SetDeviceBin;
//						mHandler.sendMessage(msg);
//					} else {
//						mHandler.sendEmptyMessage(MainActivityHandlerParams.SetDeviceBinFailed);
//					}
//					break;
//				}
//				case UDPTCPCommand.APP_GET_DEVICE_RSSI_ACK: {
//					AppGetDeviceRSSIAck ack = new AppGetDeviceRSSIAck(data);
//					Message msg = new Message();
//					msg.obj = ack;
//					msg.what = MainActivityHandlerParams.GetDeviceRSSI;
//					mHandler.sendMessage(msg);
//
//				}
//					break;
//				case UDPTCPCommand.APP_CONTROL_SOCKET_ACK: {
//					AppControlSocketAckBase appControlSocketAck = new AppControlSocketAckBase(data);
//					Message msg = new Message();
//					switch (appControlSocketAck.actionType) {
//					case AppVariant.QueryDeviceStatusAck:
//						AppGetDeviceStateAck deviceStateAck = new AppGetDeviceStateAck(data, DID);
//						if (deviceStateAck.operationState == 0) {
//							msg.obj = deviceStateAck;
//							msg.what = MainActivityHandlerParams.GetDeviceState;
//							mHandler.sendMessage(msg);
//						} else {
//							mHandler.sendEmptyMessage(MainActivityHandlerParams.GetDeviceStateFailed);
//						}
//						break;
//					case AppVariant.QueryDeviceParamsAck:
//						AppGetDeviceParamsAck getDeviceParamsAck = new AppGetDeviceParamsAck(data);
//						if (getDeviceParamsAck.operationState == 0) {
//							msg.obj = getDeviceParamsAck;
//							msg.what = MainActivityHandlerParams.GetDeviceParams;
//							mHandler.sendMessage(msg);
//						} else {
//							mHandler.sendEmptyMessage(MainActivityHandlerParams.GetDeviceParamsFailed);
//						}
//						break;
//					case AppVariant.DeviceModeChangeAck:
//						AppSetDeviceModeAck deviceModeChangeAck = new AppSetDeviceModeAck(data, DID);
//						if (deviceModeChangeAck.operationState == 0) {
//							msg.obj = deviceModeChangeAck;
//							msg.what = MainActivityHandlerParams.DeviceModeChange;
//							mHandler.sendMessage(msg);
//						} else {
//							mHandler.sendEmptyMessage(MainActivityHandlerParams.DeviceModeChangeFailed);
//						}
//						break;
//					case AppVariant.DeviceCycleTimingAck:
//						AppSetDeviceCycleTimingAck deviceCycleTimingAck = new AppSetDeviceCycleTimingAck(data);
//						if (deviceCycleTimingAck.operationState == 0) {
//							msg.obj = deviceCycleTimingAck;
//							msg.what = MainActivityHandlerParams.DeviceCycleTiming;
//							mHandler.sendMessage(msg);
//						} else {
//							mHandler.sendEmptyMessage(MainActivityHandlerParams.DeviceCycleTimingFailed);
//						}
//						break;
//					case AppVariant.DeviceCountDownAck:
//						AppSetDeviceCountDownAck deviceCountDownAck = new AppSetDeviceCountDownAck(data);
//						if (deviceCountDownAck.operationState == 0) {
//							msg.obj = deviceCountDownAck;
//							msg.what = MainActivityHandlerParams.DeviceCountDownTiming;
//							mHandler.sendMessage(msg);
//						} else {
//							mHandler.sendEmptyMessage(MainActivityHandlerParams.DeviceCountDownTimingFailed);
//						}
//						break;
//
//					default:
//						break;
//					}
//
//					break;
//				}
//
//				default:
//					break;
//				}
//			}
//		}
//	}
//}
