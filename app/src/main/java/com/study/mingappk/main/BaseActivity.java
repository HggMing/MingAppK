package com.study.mingappk.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.study.mingappk.R;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    /**
     * 一键退出:
     */
    //新建一个 ActivityCollector 类 作为 Activity 管理器
    public static class ActivityCollector {

        public static List<Activity> activities = new ArrayList<Activity>();

        public static void addActivity(Activity activity) {
            activities.add(activity);
        }

        public static void removeActivity(Activity activity) {
            activities.remove(activity);
        }

        /**
         * 在需要一键退出的地方调用 ActivityCollector.finishAll()
         */
        public static void finishAll() {
            for (Activity activity : activities) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    //重写 onCreate()、onDestroy() 方法，代码如下
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);//添加activity，便于一键退出
        initContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_base);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private LinearLayout parentLayout;//把父类activity和子类activity的view都add到这里

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        //   viewGroup.removeAllViews();
        parentLayout = new LinearLayout(this);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLayout, true);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLayout, true);
    }

    @Override
    public void setContentView(View view) {
        parentLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentLayout.addView(view, params);
    }


}
