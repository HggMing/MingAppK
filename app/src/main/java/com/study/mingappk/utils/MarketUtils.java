package com.study.mingappk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarketUtils
{
	/**
     *  判断网络是否连接
     * @param context
     * @return
     */
    public static boolean checkNetWorkStatus(Context context) {
        ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }
    
    
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if (bitmap == null)

			return null;

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}
    
    /**
	 * 64位解码
	 * */

	public static String base64Decode(String str64) {
		byte[] asBytes = Base64.decode(str64, 0);
		String str = null;
		try {
			str = new String(asBytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
    
    
    /**
     * 获取手机imei号
     * @return
     */
    public static String getTelImei(Context context)
    {
    	TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    	return mTm.getDeviceId();
    }
    
    /**
	 * 获取程序的版本号
	 * @param context
	 * @return
	 */
	public static String GetClientVersion(Context context)
	{
		String clientVersion = "";
		// 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
		try 
		{
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			clientVersion = String.valueOf(packInfo.versionCode);
		} 
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return clientVersion;
	}
	
	/**
     * 判断是否为电话号码
     *
     * @return boolean 
     */
	public static boolean checkPhone(String phone)
	{
		Pattern pattern = Pattern.compile("^[1][3-9]\\d{9}$");
		Matcher matcher = pattern.matcher(phone);

		if (matcher.matches())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 获取程序的版本号
	 * @param context
	 * @return
	 */
	public static String GetClientVersionName(Context context)
	{
		String clientVersion = "";
		// 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
		try 
		{
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			clientVersion = String.valueOf(packInfo.versionName);
		} 
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return clientVersion;
	}
	
	/**
	 * 获取程序的版本号
	 * @param context
	 * @return
	 */
	public static String GetClientVersionCode(Context context)
	{
		String clientVersion = "";
		// 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
		try 
		{
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			clientVersion = String.valueOf(packInfo.versionCode);
		} 
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return clientVersion;
	}
	
	/**
	 * 获取手机mac地址
	 * 
	 * @return
	 */
	public static String GetMacAddress(Context context)
	{
		String result = "";
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		result = wifiInfo.getMacAddress();
		return result;
	}
	
	/**
	 * 
	 * @param source
	 *            要被压缩的图片
	 * @param width
	 *            压缩的宽度
	 * @param height
	 *            压缩的高度
	 * @return
	 */
	public static Bitmap transImage(Bitmap source, int width, int height,
			String picName, boolean isSave) {
		int bitmapWidth = source.getWidth();
		int bitmapHeight = source.getHeight();
		// 缩放图片的尺寸
		float scaleWidth = (float) width / bitmapWidth;
		float scaleHeight = (float) height / bitmapHeight;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 产生缩放后的Bitmap对象
		Bitmap resizeBitmap = Bitmap.createBitmap(source, 0, 0, bitmapWidth,
				bitmapHeight, matrix, false);
		// 保存图片
		if (isSave) 
		{
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/oacem/data/photo/";
			String fileName = picName;
			File out = new File(path);
			if (!out.exists()) {
				out.mkdirs();
			}
			out = new File(path, fileName);
			try
			{
				FileOutputStream outStream = new FileOutputStream(out);
				resizeBitmap.compress(CompressFormat.JPEG, 100, outStream);
				outStream.close();
			}
			catch (Exception e)
			{

			}
		}
		return resizeBitmap;
	}
	
	
	/**
	 * 
	 * @param source
	 *            要被压缩的图片
	 * @param width
	 *            压缩的宽度
	 * @param height
	 *            压缩的高度
	 * @return
	 */
	public static Bitmap transImagefordraggrid(Bitmap source, int width, int height,
			String picName, boolean isSave) {
		int bitmapWidth = source.getWidth();
		int bitmapHeight = source.getHeight();
		// 缩放图片的尺寸
		float scaleWidth = (float) width / bitmapWidth;
		float scaleHeight = (float) height / bitmapHeight;
		
		if (scaleWidth<1)
		{
			scaleWidth=1;
		}
		
		if (scaleHeight<1)
		{
			scaleHeight=1;
		}
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 产生缩放后的Bitmap对象
		Bitmap resizeBitmap = Bitmap.createBitmap(source, 0, 0, bitmapWidth,
				bitmapHeight, matrix, false);
		// 保存图片
		if (isSave) 
		{
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/oacem/data/photo/";
			String fileName = picName;
			File out = new File(path);
			if (!out.exists()) {
				out.mkdirs();
			}
			out = new File(path, fileName);
			try
			{
				FileOutputStream outStream = new FileOutputStream(out);
				resizeBitmap.compress(CompressFormat.JPEG, 100, outStream);
				outStream.close();
			}
			catch (Exception e)
			{

			}
		}
		return resizeBitmap;
	}
	
	
	 /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (dpValue * scale + 0.5f);
    } 
    
    /**
     * 以图片中心截取扇形
     *
     *
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels,int startAngle,int sweepAngle)
    {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);
        //canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    
	// 比较时间
	public static long dateDiff(String datepart, Calendar beginCal,
			Calendar endCal) {
		long retVal = 0;
		if (datepart.equals("yy"))// 年
		{
			int beginYear = beginCal.get(Calendar.YEAR);
			int endYear = endCal.get(Calendar.YEAR);
			int beginMonth = beginCal.get(Calendar.MONTH) + 1;
			int endMonth = endCal.get(Calendar.MONTH) + 1;
			int beginDay = beginCal.get(Calendar.DATE);
			int endDay = endCal.get(Calendar.DATE);
			retVal = endYear - beginYear;
			if ((endMonth - beginMonth <= 0) && (endDay - beginDay < 0)
					&& retVal > 0) {
				retVal--;
			} else if ((endMonth - beginMonth >= 0) && (endDay - beginDay > 0)
					&& retVal < 0) {
				retVal++;
			}
			return retVal;
		} else if (datepart.equals("mm"))// 月
		{
			int beginYear = beginCal.get(Calendar.YEAR);
			int endYear = endCal.get(Calendar.YEAR);
			int beginMonth = beginCal.get(Calendar.MONTH) + 1;
			int endMonth = endCal.get(Calendar.MONTH) + 1;
			int beginDay = beginCal.get(Calendar.DATE);
			int endDay = endCal.get(Calendar.DATE);
			retVal = endMonth - beginMonth;
			if ((endDay - beginDay < 0) && retVal > 0) {
				retVal--;
			} else if ((endDay - beginDay > 0) && retVal < 0) {
				retVal++;
			}
			return retVal + (endYear - beginYear) * 12;
		}

		long diff = 0;
		Date beginDate = beginCal.getTime();
		Date enDate = endCal.getTime();

		long beginLong = beginDate.getTime();
		long endLong = enDate.getTime();

		if (datepart.equals("dd"))// 日
		{
			diff = (endLong - beginLong) / (1000 * 24 * 60 * 60);
		} else if (datepart.equals("hh"))// 时
		{
			diff = (endLong - beginLong) / (1000 * 60 * 60);
		} else if (datepart.equals("mi"))// 分
		{
			diff = (endLong - beginLong) / (1000 * 60);
		} else if (datepart.equals("ss"))// 秒
		{
			diff = (endLong - beginLong) / (1000);
		}
		return diff;
	}

	public static String formatPrice(String str) {
		double f = 0f;
		try {
			f = Double.valueOf(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return String.format("%.2f", f);
	}
	public static void closeInput(Activity context) {// 关闭输入法

		try {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			boolean isOpen = imm.isActive();
			if (isOpen) {
				InputMethodManager inputMethodManager = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (null == context.getCurrentFocus().getWindowToken())
					return;
				inputMethodManager.hideSoftInputFromWindow(context
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
