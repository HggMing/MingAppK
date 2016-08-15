package com.study.mingappk.tmain.userlogin;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.Login;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.MainActivity;
import com.study.mingappk.tmain.register.TestPhoneNumberActivity;
import com.study.mingappk.tmain.userlogin.facelogin.FaceLoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends Activity {
    public static String IS_AUTO_LOGIN = "是否自动登录";
    final private String TAG = "mm:LoginActivity";
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
    private boolean isAutoLogin;
    private boolean isRememberPwd;// 是否记住密码

    private String point;//弹出提示框内容
    private static final int REGISTERED_PHONE = 123;//请求返回已注册手机号
    public static final String REGISTERED_PHONE_NUMBER = "registered_phone_number";//返回已注册手机号

    public void onCreate(Bundle savedInstanceState) {
        BaseTools.setFullScreen(this);//隐藏状态栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        ButterKnife.bind(this);

        isRememberPwd = Hawk.get(APP.IS_REMEMBER_PASSWORD, true);//初始默认记住密码
        loginname = Hawk.get(APP.LOGIN_NAME, "");
        loginpwd = Hawk.get(APP.LOGIN_PASSWORD, "");

        setIcon();

        isAutoLogin = getIntent().getBooleanExtra(IS_AUTO_LOGIN, false);
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
        MyServiceClient.getService().get_Login(loginname, loginpwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Login>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.i(TAG, "失败了" + throwable.getMessage());
                        point = "亲，网络不给力啊,请检查网络";
                        loginFailure(point);
                    }

                    @Override
                    public void onNext(Login login) {
                        if (login.getErr() == 0) {
                            //储存店长管理村地址
                            if (login.getShopowner().getIs_shopowner() == 1) {
                                String manager_vid = login.getShopowner().getManager_vid();
                                if (manager_vid != null && manager_vid.length() >= 12) {
                                    String key_vid = manager_vid.substring(0, 12);//取出第一个店长vid
                                    Login.VidInfoBean vidInfoBean = login.getVid_info().get(key_vid);
                                    String vName = vidInfoBean.getProvince_name() +
                                            vidInfoBean.getCity_name() +
                                            vidInfoBean.getCounty_name() +
                                            vidInfoBean.getTown_name() +
                                            vidInfoBean.getVillage_name();//店长村详细地址
                                    Hawk.put(APP.MANAGER_ADDRESS,vName);
                                }
                            }
                            Hawk.chain()
                                    .put(APP.USER_AUTH, login.getAuth())//保存认证信息
                                    .put(APP.ME_UID, login.getInfo().getUid())
                                    .put(APP.IS_SHOP_OWNER, login.getShopowner().getIs_shopowner())
                                    .commit();
                            loginSuccess();
                            return;
                        }
                        if (login.getErr() == 2003) {
                            point = login.getMsg();
                            loginFailureToReg(point);
                            return;
                        }
                        point = login.getMsg();
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
        MyDialog.Builder builder1 = new MyDialog.Builder(LoginActivity.this);
        builder1.setTitle("提示")
                .setCannel(false)
                .setMessage(s)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                btn_login.setClickable(true);
                                btn_login.setText("登录");
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this, TestPhoneNumberActivity.class);
                                intent.putExtra(TestPhoneNumberActivity.LOGIN_NAME, loginname);
                                intent.putExtra(TestPhoneNumberActivity.TYPE, 1);
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
        MyDialog.Builder builder = new MyDialog.Builder(LoginActivity.this);
        builder.setTitle("提示")
                .setCannel(false)
                .setMessage(s)
                .setPositiveButton("确定",
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
            Hawk.chain()
                    .put(APP.LOGIN_NAME, loginname)
                    .put(APP.LOGIN_PASSWORD, loginpwd)
                    .commit();
        } else {
            Hawk.chain()
                    .put(APP.LOGIN_NAME, "")
                    .put(APP.LOGIN_PASSWORD, "")
                    .commit();
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
                Intent intent3 = new Intent();
                intent3.setClass(this, FaceLoginActivity.class);
                startActivity(intent3);
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                break;
            case R.id.img_jzmm:
                onClickJzmm();
                break;
            case R.id.tv_reg:
                Intent intent = new Intent();
                intent.setClass(this, TestPhoneNumberActivity.class);
                intent.putExtra(TestPhoneNumberActivity.LOGIN_NAME, "");
                intent.putExtra(TestPhoneNumberActivity.TYPE, 1);
                startActivityForResult(intent, REGISTERED_PHONE);
                break;
            case R.id.tv_forgetpwd:
                Intent intent2 = new Intent();
                intent2.setClass(this, TestPhoneNumberActivity.class);
                intent2.putExtra(TestPhoneNumberActivity.LOGIN_NAME, "");
                intent2.putExtra(TestPhoneNumberActivity.TYPE, 2);
                startActivity(intent2);
                break;
        }
    }

    private void onClickJzmm() {
        if (isRememberPwd) {
            isRememberPwd = false;
            Hawk.put(APP.IS_REMEMBER_PASSWORD, false);
            img_jzmm.setBackgroundResource(R.mipmap.agree_no);
        } else {
            isRememberPwd = true;
            Hawk.put(APP.IS_REMEMBER_PASSWORD, true);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REGISTERED_PHONE:
                if (resultCode == Activity.RESULT_OK) {
                    String result = data.getStringExtra(REGISTERED_PHONE_NUMBER);
                    et_name.setText(result);
                    et_pwd.setText("");
                    Hawk.chain()
                            .put(APP.LOGIN_NAME, result)
                            .put(APP.LOGIN_PASSWORD, "")
                            .commit();
                }
                break;
            default:
                break;
        }
    }

}
