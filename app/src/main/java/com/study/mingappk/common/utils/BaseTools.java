package com.study.mingappk.common.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目中的常用方法
 * Created by Ming on 2016/3/8.
 */
public class BaseTools {

    /**
     * 全屏，隐藏状态栏
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
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
    /**
     * 判断是否为电话号码
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


}
