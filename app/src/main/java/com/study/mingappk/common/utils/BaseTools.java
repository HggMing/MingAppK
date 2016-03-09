package com.study.mingappk.common.utils;

import android.app.Activity;
import android.content.Context;
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

}
