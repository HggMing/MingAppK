package com.study.mingappk.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.userlogin.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int MAX_WAITING_TIME = 2000; //动画持续时长
    protected boolean mShouldGoTo = true;
    private SharedPreferences sp;
    private String loginname;
    private String loginpwd;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private static final int GO_MAIN = 1000;
    private static final int GO_LOGIN = 1001;
    private static final int GO_LOGIN_AUTO = 1002;
    private static final long SPLASH_DELAY_MILLIS = 2000;//延迟时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseTools.setFullScreen(this);//隐藏状态栏

        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        //设置动画
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(MAX_WAITING_TIME);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                if (mShouldGoTo) {
                    init();
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

    private void init() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        sp = getSharedPreferences("setting", 0);
        loginname = sp.getString("loginname", "");
        loginpwd = sp.getString("loginpwd", "");

        if (!loginname.equals("") && !loginpwd.equals("")) {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN_AUTO, 0);
        } else {
            // 使用Handler的postDelayed方法数秒后执行跳转.
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, SPLASH_DELAY_MILLIS);
        }
    }

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    goHome();
                    break;
                case GO_LOGIN:
                    goLogin();
                    break;
                case GO_LOGIN_AUTO:
                    goLoginAuto();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    private void goLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    /**
     * 自动登录
     */
    private void goLoginAuto() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.putExtra("isAutoLogin", true);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

}
