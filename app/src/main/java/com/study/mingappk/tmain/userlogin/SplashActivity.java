package com.study.mingappk.tmain.userlogin;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.tmain.MainActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int MAX_WAITING_TIME = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseTools.setFullScreen(this);//隐藏状态栏

        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        //设置动画
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);//透明度从0.3到不透明变化
        aa.setDuration(MAX_WAITING_TIME);//动画持续时长
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                init();
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

        String loginname = Hawk.get(APP.LOGIN_NAME, "");
        String loginpwd = Hawk.get(APP.LOGIN_PASSWORD, "");

        if (!loginname.equals("") && !loginpwd.equals("")) {
            goLoginAuto();
        } else {
            goLogin();
        }
    }


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
        intent.putExtra(LoginActivity.IS_AUTO_LOGIN, true);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }



}
