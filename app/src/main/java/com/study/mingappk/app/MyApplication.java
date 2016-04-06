package com.study.mingappk.app;

import android.app.Application;

import com.jude.utils.BuildConfig;
import com.jude.utils.JUtils;

public class MyApplication extends Application {
    /**
     * 单例模式中获取唯一的Application实例
     */
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JUtils.initialize(this);
        JUtils.setDebug(BuildConfig.DEBUG, "mm");
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    private String auth;

}
