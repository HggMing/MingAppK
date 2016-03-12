package com.study.mingappk.common.app;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class MyApplication extends Application {
    private static MyApplication instance;

    private List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 单例模式中获取唯一的Application实例
     */

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    /**
     * 添加Activity到容器中
     *
     * @param activity 传入当前activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有Activity并finish,退出软件
     */
    public void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }

        try {
            System.exit(0);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
