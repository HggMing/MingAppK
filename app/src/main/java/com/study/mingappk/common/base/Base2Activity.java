package com.study.mingappk.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.AppManager;
import com.study.mingappk.common.injector.component.ApplicationComponent;
import com.study.mingappk.common.utils.BaseTools;


/**
 * Created by Ming on 2016/10/19.
 */
public abstract class Base2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置滑动关闭当前，返回上一页
        swipeBack();
        initInjector();
        initUiAndListener();
        AppManager.getAppManager().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        SwipeBackHelper.onDestroy(this);
    }

    private void swipeBack() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
//                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.10f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(0xff777777)//底层阴影颜色
                .setClosePercent(0.6f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                .setDisallowInterceptTouchEvent(false)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
                .addListener(new SwipeListener() {//滑动监听

                    @Override
                    public void onScroll(float percent, int px) {//滑动的百分比与距离
                    }

                    @Override
                    public void onEdgeTouch() {//当开始滑动
                    }

                    @Override
                    public void onScrollToClose() {//当滑动关闭
                    }
                });
    }

//    protected ApplicationComponent getApplicationComponent() {
//        return ((APP) getApplication()).getApplicationComponent();
//    }


    /**
     * 设置Dagger 注入Injector
     */
    public abstract void initInjector();

    /**
     * 设置 init UI && Listener
     */
    public abstract void initUiAndListener();


    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
        //状态栏主题变色
        BaseTools.colorStatusBar(this);
    }
}
