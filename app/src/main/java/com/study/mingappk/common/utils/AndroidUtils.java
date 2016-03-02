package com.study.mingappk.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import org.json.JSONObject;

public class AndroidUtils {
	public static int getStringWidth(float fontSize, String str) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		return (int) paint.measureText(str);
	}


	/**
	 * 得到json字段返回的内容（字符串）
	 * @param obj  json对象
	 * @param name  字段
	 * @param defaultVal  为空默认
	 * @return
	 */
	public static String getJsonString(JSONObject obj, String name,
			String defaultVal) {
		try {
			return obj.getString(name);
		} catch (Exception ee) {
			return defaultVal;
		}
	}

	/**
	 * 得到json字段返回的内容（int�?
	 * 
	 * @param obj json对象
	 * @param name 字段
	 * @param defaultVal 为空默认
	 *            �?
	 * @return �?
	 */
	public static int getJsonInt(JSONObject obj, String name, int defaultVal) {
		try {
			return obj.getInt(name);
		} catch (Exception ee) {
			return defaultVal;
		}
	}

	/**
	 * 得到json字段返回的内容（double�?
	 * 
	 * @param obj json对象
	 * @param name 字段
	 * @param defaultVal 为空默认
	 *            �?
	 * @return �?
	 */
	public static double getJsonDouble(JSONObject obj, String name,
			double defaultVal) {
		try {
			return obj.getDouble(name);
		} catch (Exception ee) {
			return defaultVal;
		}
	}

	public static float getJsonFloat(JSONObject obj, String name,
			float defaultVal) {

		try {
			return (float) obj.getDouble(name);
		} catch (Exception ee) {
			return defaultVal;
		}

	}

	/**
	 * url编码
	 * 
	 * @param data url内容
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String urlEncode(String data) {
		return java.net.URLEncoder.encode(data);
	}

	/**
	 * 获取AppKey
	 * 
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (Exception e) {
		}
		return apiKey;
	}

	
	/**
     * 获取手机ip
     * @param context 上下文对象
     * @return 不为null：返回获取到的手机ip  null:获取手机ip失败
     */
	public static String getIPAddress(Context context){
		WifiManager wifiMan = (WifiManager) (context.getSystemService(Context.WIFI_SERVICE));
		WifiInfo info = wifiMan.getConnectionInfo();
		if(info != null){
			
			int ipAddress = info.getIpAddress();
			String ip = "";// 本机在WIFI状态下路由分配给的IP地址
			  
			// 获得IP地址的方法一：  
			if (ipAddress != 0) {  
				ip = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."   
			        + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff)); 
				return ip;
			}  
		}
		
		
		
        return null;

	}
}
