package com.study.mingappk.tmain;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;
import com.study.mingappk.R;


public class BackActivity extends BaseActivity {
    /*//滑动返回的配置
    SlidrConfig config = new SlidrConfig.Builder()
            .primaryColor(getResources().getColor(R.color.colorPrimary))//滑动时状态栏的渐变结束的颜色
            .secondaryColor(getResources().getColor(R.color.colorPrimary))//滑动时状态栏的渐变开始的颜色
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
            .build();
    protected void setSlidr(Activity activity){
        Slidr.attach(activity,config);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_back);
        }
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
