package com.study.mingappk.common.app;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import com.study.mingappk.api.result.LoginResult;
import com.study.mingappk.api.result.UserInfoResult;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication instance;
    /**
     * 单例模式中获取唯一的Application实例
     */

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    private String auth;

    public static String getBaseUrl() {
        return BASE_URL2;
    }

    private static final String BASE_URL2 = "http://121.40.105.149:9901";//API接口的主机地址

    public UserInfoResult.DataEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoResult.DataEntity userInfo) {
        this.userInfo = userInfo;
    }

    private UserInfoResult.DataEntity userInfo;//用户信息



}
