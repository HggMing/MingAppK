package com.study.mingappk.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.userlogin.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int MAX_WAITING_TIME = 2000; // Wait for 3 seconds
    protected boolean mShouldGoTo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseTools.setFullScreen(this);//隐藏状态栏

        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(MAX_WAITING_TIME);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                if (mShouldGoTo) {
                    redirectTo();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });


    }

    private void redirectTo() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
