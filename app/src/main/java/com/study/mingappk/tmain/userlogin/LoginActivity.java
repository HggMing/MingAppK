package com.study.mingappk.tmain.userlogin;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.LoginResult;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.common.dialog.Dialog_Model;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.tmain.MainActivity;
import com.study.mingappk.test.TestActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends Activity {
    final private static String TAG="mm:LoginActivity";
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

    private String loginname;
    private String loginpwd;

    private boolean isRememberPwd;// 是否记住密码

    private SharedPreferences.Editor spEditor;
    private String point;//弹出提示框内容

    public void onCreate(Bundle savedInstanceState) {
        BaseTools.setFullScreen(this);//隐藏状态栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        ButterKnife.bind(this);

        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        spEditor = sp.edit();
        isRememberPwd = sp.getBoolean("isRememberPwd", true);//初始默认记住密码
        loginname = sp.getString("loginname", "");
        loginpwd = sp.getString("loginpwd", "");
        setIcon();

        boolean isAutoLogin = getIntent().getBooleanExtra("isAutoLogin", false);
        if (isAutoLogin) {
            onClick(btn_login);
        }
    }

    /**
     * 初始化记住密码图标状态
     */
    private void setIcon() {
        et_name.setText(loginname);

        if (isRememberPwd) {
            img_jzmm.setBackgroundResource(R.mipmap.agree);
            et_pwd.setText(loginpwd);
        } else {
            img_jzmm.setBackgroundResource(R.mipmap.agree_no);
        }

    }

    private void loginByRx() {

        if (!BaseTools.checkNetWorkStatus(this)) {
            point = "很抱歉，您的网络已经中断，请检查是否连接。";
            loginFailure(point);
            return;
        }
        new MyServiceClient().getService().getCall_Login(loginname,loginpwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.i(TAG,"失败了"+throwable.getMessage());
                        point = "亲，网络不给力啊,请检查网络";
                        loginFailure(point);
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                            if (loginResult.getErr() == 0) {
                                APP.getInstance().setAuth(loginResult.getAuth());//保存认证信息
                                loginSuccess();
                                return;
                            }
                            if (loginResult.getErr() == 2003) {
                                point = loginResult.getMsg();
                                loginFailureToReg(point);
                                return;
                            }
                            point = loginResult.getMsg();
                            loginFailure(point);
                    }
                });
    }

    /**
     * 输入手机号未注册
     *
     * @param s 提示内容
     */
    private void loginFailureToReg(String s) {
        SetCanEdit(true);
        btn_login.setText("登录");
        Dialog_Model.Builder builder1 = new Dialog_Model.Builder(
                LoginActivity.this);
        builder1.setTitle("提示");
        builder1.setCannel(false);
        builder1.setMessage(s);
        builder1.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        btn_login.setClickable(true);
                        btn_login.setText("登录");
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, TestActivity.class);
                        intent.putExtra("loginname", loginname);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
        if (!isFinishing()) {
            builder1.create().show();
        }
    }

    /**
     * 认证不成功，弹出提示
     *
     * @param s 提示内容
     */
    private void loginFailure(String s) {
        SetCanEdit(true);
        btn_login.setText("登录");
        Dialog_Model.Builder builder = new Dialog_Model.Builder(
                LoginActivity.this);
        builder.setTitle("提示");
        builder.setCannel(false);
        builder.setMessage(s);
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
    }

    /**
     * 登录成功
     */
    private void loginSuccess() {
        SetCanEdit(true);
        if (isRememberPwd) {
            spEditor.putString("loginname", loginname);
            spEditor.putString("loginpwd", loginpwd);
            spEditor.commit();
        } else {
            spEditor.putString("loginname", "");
            spEditor.putString("loginpwd", "");
            spEditor.commit();
        }

        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        LoginActivity.this.finish();
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
                onClickLogin();
                break;
            case R.id.btn_facelogin:
//                Intent intent2 = new Intent();
//                intent2.setClass(Activity_Login.this, Activity_FaceLogin.class);
//                startActivity(intent2);
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                break;
            case R.id.img_jzmm:
                onClickJzmm();
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

    private void onClickJzmm() {
        if (isRememberPwd) {
            isRememberPwd = false;
            spEditor.putBoolean("isRememberPwd", false);
            spEditor.commit();
            img_jzmm.setBackgroundResource(R.mipmap.agree_no);
        } else {
            isRememberPwd = true;
            spEditor.putBoolean("isRememberPwd", true);
            spEditor.commit();
            img_jzmm.setBackgroundResource(R.mipmap.agree);
        }
    }

    private void onClickLogin() {
        btn_login.setClickable(false);
        btn_login.setText("登录中...");

        loginname = et_name.getEditableText().toString();
        if (loginname.equals("")) {
            btn_login.setClickable(true);
            btn_login.setText("登录");
            Toast.makeText(this, "登录名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!BaseTools.checkPhone(loginname)) {
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
        loginByRx();
    }

}
