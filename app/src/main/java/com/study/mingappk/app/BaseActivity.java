package com.study.mingappk.app;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.study.mingappk.R;

public class BaseActivity extends Activity {
    /* protected ImageButton leftButton;// 左按键
     protected ImageButton rightButton;// 右按键
     protected ImageButton rightButton1;*/
    private RelativeLayout layout_top;
    protected ProgressBar progressBar1;
    private boolean IsConfig = false;
    private int rightSelected = 0;
    /*private TextView _txt_right;
    private boolean IsSetTxtRight = false;*/

    private boolean IsHaveUserInfo = true;//该activity是否包含 用户信息


    private ViewGroup _contentLayout;

    /**
     * 设置子页面需要显示的布局
     *
     * @param contentLayout 子页面的布局
     */
    public void SetContentLayout(ViewGroup contentLayout) {
        _contentLayout = contentLayout;
    }

    /**
     * 设置：该activity是否包含用户信息
     *
     * @param boo
     */
    public void SetIsHaveUserInfo(boolean boo) {
        IsHaveUserInfo = boo;
    }


    public void onCreate(Bundle savedInstanceState) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// 屏幕不弹出输入框
        setContentView(R.layout.activity_base);
        super.onCreate(savedInstanceState);
    }


    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setCurrentActivity(this);//设置此焦点Activity的实例
        //TestinAgent.onResume(this);//此行必须放在super.onResume之后
    }


    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TestinAgent.onStop(this);//此行必须放在super.onStop之后
    }

    private void clearReferences() {
        BaseActivity currActivity = MyApplication.getInstance().getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
            MyApplication.getInstance().setCurrentActivity(null);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (MyApplication.getInstance().getisClickHomeKey()) {
            SharedPreferences sp=getSharedPreferences("setting",MODE_PRIVATE);
            Boolean isSetPwd = sp.getBoolean("isSetPwd", false);
            if (isSetPwd && !MyApplication.getInstance().getisisCloseJGG()) {
                MyApplication.getInstance().setisClickHomeKey(false);
                MyApplication.getInstance().setisisCloseJGG(false);
                Intent intent = new Intent();
                intent.setAction("com.isall.ebankclient.openjiugongge");
                sendBroadcast(intent);

                //CheckWifi.CheckWifiBySSid(this);
            }
        } else {

        }
    }

}
