package com.study.mingappk.userlogin;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.common.app.MyApplication;
import com.study.mingappk.common.app.info.UserInfo;
import com.study.mingappk.common.dialog.Dialog_Model;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.MarketUtils;
import com.study.mingappk.main.MainActivity;
import com.study.mingappk.userlogin.request.Request_PushReg;
import com.study.mingappk.userlogin.request.Request_UserLogin;
import com.study.mingappk.userlogin.request.ResultPacket;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.igexin.sdk.PushManager;

public class LoginActivity extends Activity {
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.img_jzmm)
    ImageView img_jzmm;
    @Bind(R.id.tv_reg)
    TextView tv_reg;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.btn_facelogin)
    Button btn_facelogin;
    @Bind(R.id.tv_forgetpwd)
    TextView tv_forgetpwd;

    private MyApplication application;
    private String loginname;
    private String loginpwd;

    private boolean status_jzmm;// 是否记住密码
    private boolean isAutoLogin = false;// 是否自动登录

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private ProgressDialog progressDialog = null;
    LayoutParams laParams;
    private int viewwidth;


    private int[] imgLoaction = new int[2];

    public void onCreate(Bundle savedInstanceState) {

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        BaseTools.setFullScreen(this);//隐藏状态栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        ButterKnife.bind(this);

        //PushManager.getInstance().initialize(this.getApplicationContext());//推送，初始化

        sp = getSharedPreferences("setting", MODE_PRIVATE);
        status_jzmm = sp.getBoolean("status_jzmm", true);//初始默认记住密码
        loginname = sp.getString("loginname", "");
        loginpwd = sp.getString("loginpwd", "");

        isAutoLogin = getIntent().getBooleanExtra("isAutoLogin", false);

        application = MyApplication.getInstance();
        application.addActivity(this);

        setView();

        laParams = (LayoutParams) btn_login.getLayoutParams();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格
        progressDialog.setMessage("正在登录,请稍候");//设置Message提示内容
        progressDialog.setIndeterminate(false);//indeterminate设置是否是不明确的状态
        progressDialog.setCancelable(false);//cancelable 设置是否进度条是可以取消的

    }


    private void setView() {
        et_name.setText(loginname);

        if (status_jzmm) {
            img_jzmm.setBackgroundResource(R.drawable.agree);
            et_pwd.setText(loginpwd);
        } else {
            img_jzmm.setBackgroundResource(R.drawable.agree_no);
        }

    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100://认证通过
                    if (progressDialog != null) {
                        progressDialog.dismiss(); // 关闭进度�?
                        progressDialog.hide();
                    }

                    SetCanEdit(true);

                    UserInfo userInfo = (UserInfo) msg.obj;
                    application.set_userInfo(userInfo);
                    editor = sp.edit();
                    editor.putString("mobile", loginname);

                    PushReg();

                    if (status_jzmm) {
                        editor.putString("loginname", loginname);
                        editor.putString("loginpwd", loginpwd);
                        editor.commit();
                    } else {
                        editor.putString("loginname", "");
                        editor.putString("loginpwd", "");
                        editor.commit();
                    }

                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    super.handleMessage(msg);
                    break;

                case 444:
                    onClick(btn_login);
                    break;


                case 998://手机未注册
                    if (progressDialog != null) {
                        progressDialog.dismiss(); // 关闭进度�?
                        progressDialog.hide();
                    }
                    SetCanEdit(true);

            /*Animation scaleAnimation2 = new ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation2.setDuration(10);
            scaleAnimation2.setFillAfter(true);

            TranslateAnimation animationLeft = new TranslateAnimation(0,
                    1, 0, 0);
            animationLeft.setDuration(10);
            animationLeft.setRepeatCount(0);
            animationLeft.setFillAfter(true);

            TranslateAnimation animationRight = new TranslateAnimation(0,
                    -1, 0, 0);
            animationRight.setDuration(10);
            animationRight.setRepeatCount(0);
            animationRight.setFillAfter(true);*/


                    btn_login.setText("登录");
                    //btn_login.startAnimation(scaleAnimation2);

                    Dialog_Model.Builder builder1 = new Dialog_Model.Builder(
                            LoginActivity.this);
                    builder1.setTitle("提示");
                    builder1.setCannel(false);
                    builder1.setMessage(msg.obj.toString());
                    builder1.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    btn_login.setClickable(true);
                                    btn_login.setText("登录");

                           /* Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, Activity_CheckPhone.class);
                            intent.putExtra("loginname", loginname);
                            startActivity(intent);*/
                                    dialog.dismiss();
                                }

                            });
                    if (!isFinishing()) {
                        builder1.create().show();
                    }
                    super.handleMessage(msg);
                    break;

                case 999://除了手机未注册外的其他认证错误
                    try {

                        if (progressDialog != null) {
                            progressDialog.dismiss(); // 关闭进度�?
                            progressDialog.hide();
                        }
                        SetCanEdit(true);

                /*Animation scaleAnimation3 = new ScaleAnimation(0f, 1f, 0f,
                        1f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation3.setDuration(10);
                scaleAnimation3.setFillAfter(true);*/
                        btn_login.setText("登录");

               /* TranslateAnimation animationLeft1 = new TranslateAnimation(
                        0, 1, 0, 0);
                animationLeft1.setDuration(10);
                animationLeft1.setRepeatCount(0);
                animationLeft1.setFillAfter(true);

                TranslateAnimation animationRight1 = new TranslateAnimation(
                        0, -1, 0, 0);
                animationRight1.setDuration(10);
                animationRight1.setRepeatCount(0);
                animationRight1.setFillAfter(true);
                btn_login.startAnimation(scaleAnimation3);*/

                        Dialog_Model.Builder builder = new Dialog_Model.Builder(
                                LoginActivity.this);
                        builder.setTitle("提示");
                        builder.setCannel(false);
                        builder.setMessage(msg.obj.toString());
                        builder.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        btn_login.setClickable(true);
                                        btn_login.setText("登录");
                                        dialog.dismiss();
                                    }
                                });
                        if (!isFinishing()) {
                            builder.create().show();
                        }

                    } catch (Exception e) {
                    }
                    super.handleMessage(msg);
                    break;
            }
        }

    };


    @Override
    protected void onResume() {
        super.onResume();

        int[] location = new int[2];
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                location[0], location[1]);
        if (isAutoLogin) {
            Message message = new Message();
            message.what = 444;
            handler.sendMessageDelayed(message, 500);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MyApplication.getInstance().exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void loginThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResultPacket resultPacket = new ResultPacket();

                Request_UserLogin request = new Request_UserLogin(LoginActivity.this, loginname, loginpwd);
                resultPacket = request.DealProcess();
                if (!resultPacket.getIsError()) {
                    Thread.currentThread().interrupt();
                    Message message = new Message();
                    message.what = 100;
                    message.obj = request.userInfo;
                    handler.sendMessage(message);
                    return;
                } else {
                    Thread.currentThread().interrupt();
                    if (resultPacket.getResultCode().equals("99")) {
                        Message message = new Message();
                        message.what = 999;
                        message.obj = resultPacket.getDescription();
                        handler.sendMessage(message);
                        return;
                    } else if (resultPacket.getResultCode()
                            .equals("98")) {
                        Message message = new Message();
                        message.what = 998;
                        message.obj = resultPacket.getDescription();
                        handler.sendMessage(message);
                        return;
                    }
                }
            }
        }).start();
    }


    private void PushReg() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                ResultPacket resultPacket = new ResultPacket();
                if (MyApplication.getInstance().get_userInfo() != null && MyApplication.getInstance().get_ClientID() != null && MyApplication.getInstance().get_ClientID().length() > 0) {
                    Request_PushReg request = new Request_PushReg(
                            LoginActivity.this, String.valueOf(MyApplication.getInstance().get_userInfo().uid), "yxj",
                            MyApplication.getInstance().get_ClientID());
                    resultPacket = request.DealProcess();
                }
            }

        }).start();
    }

    private void SetCanEdit(boolean bool) {
        et_name.setEnabled(bool);
        et_name.setClickable(bool);
        et_pwd.setEnabled(bool);
        et_pwd.setClickable(bool);
    }

    @OnClick({R.id.img_jzmm, R.id.tv_reg, R.id.btn_login, R.id.btn_facelogin, R.id.tv_forgetpwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                btn_login.setClickable(false);
                btn_login.setText("登录中...");

                loginname = et_name.getEditableText().toString();

                if (loginname.equals("")) {
                    btn_login.setClickable(true);
                    btn_login.setText("登录");
                    Toast.makeText(this, "登录名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!MarketUtils.checkPhone(loginname)) {
                    btn_login.setClickable(true);
                    btn_login.setText("登录");
                    Toast.makeText(this, "登录名不是标准的手机号码格式", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginpwd = et_pwd.getEditableText().toString();

                if (loginpwd.equals("")) {
                    btn_login.setClickable(true);
                    btn_login.setText("登录");
                    Toast.makeText(this, "登录密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginThread();

                break;
            case R.id.btn_facelogin:
//                Intent intent2 = new Intent();
//                intent2.setClass(Activity_Login.this, Activity_FaceLogin.class);
//                startActivity(intent2);
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                break;
            case R.id.img_jzmm:
                if (status_jzmm) {
                    status_jzmm = false;
                    editor = sp.edit();
                    editor.putBoolean("status_jzmm", status_jzmm);
                    editor.commit();
                    img_jzmm.setBackgroundResource(R.drawable.agree_no);
                } else {
                    status_jzmm = true;
                    editor = sp.edit();
                    editor.putBoolean("status_jzmm", status_jzmm);
                    editor.commit();
                    img_jzmm.setBackgroundResource(R.drawable.agree);
                }
                break;
            case R.id.tv_reg:
//                intent = new Intent();
//                intent.setClass(this, Activity_CheckPhone.class);
//                intent.putExtra("type", 1);
//                startActivity(intent);
                break;
            case R.id.tv_forgetpwd:
//                intent = new Intent();
//                intent.setClass(this, Activity_CheckPhone.class);
//                intent.putExtra("type", 2);
//                startActivity(intent);
                break;
        }
    }

}
