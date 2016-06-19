package com.study.mingappk.tmain;

import android.os.Bundle;
import android.view.MenuItem;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;


public class BackActivity extends BaseActivity {
    /*//滑动返回的配置
    SlidrConfig config = new SlidrConfig.Builder()
            .primaryColor(APP.getInstance().getResources().getColor(R.color.colorPrimary))//滑动时状态栏的渐变结束的颜色
            .secondaryColor(APP.getInstance().getResources().getColor(R.color.colorPrimary))//滑动时状态栏的渐变开始的颜色
            .position(SlidrPosition.LEFT)//从左边滑动
            .sensitivity(1f)
            .scrimColor(Color.BLACK)//滑动时Activity之间的颜色
            .scrimStartAlpha(0.8f)//滑动开始时两个Activity之间的透明度
            .scrimEndAlpha(0f)//滑动结束时两个Activity之间的透明度
            .velocityThreshold(2400)//超过这个滑动速度，忽略位移限定值就切换Activity
            .distanceThreshold(0.25f)//滑动位移占屏幕的百分比，超过这个间距就切换Activity
            .edge(true)
            .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
//                            .listener(new SlidrListener(){})
            .build();*/
    /*protected void setSlidr(Activity activity){
        Slidr.attach(activity,config);
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_back);
        }
//        Slidr.attach(this,config);
        //设置滑动关闭当前，返回上一页
        swipeBack();
    }

    private void swipeBack() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
//                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.15f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(APP.getInstance().getResources().getColor(R.color.swipe_back))//底层阴影颜色
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
