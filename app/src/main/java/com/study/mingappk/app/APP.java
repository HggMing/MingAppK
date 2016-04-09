package com.study.mingappk.app;

import android.app.Application;

import com.jude.utils.JUtils;
import com.squareup.otto.Bus;

public class APP extends Application {
    /**
     * 单例模式中获取唯一的Application实例
     */
    private static APP instance;

    public static APP getInstance() {
        return instance;
    }
    public static final Bus bus = new Bus();
    public static Bus getBus(){
        return bus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JUtils.initialize(this);
        JUtils.setDebug(true, "mm");
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    private String auth;

}
