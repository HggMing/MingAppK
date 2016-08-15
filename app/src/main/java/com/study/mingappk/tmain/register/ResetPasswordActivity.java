package com.study.mingappk.tmain.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.views.sms_autofill.SmsObserver;
import com.study.mingappk.common.views.sms_autofill.SmsResponseCallback;
import com.study.mingappk.common.views.sms_autofill.VerificationCodeSmsFilter;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.tmain.userlogin.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ResetPasswordActivity extends BackActivity {

    public static String PHONE = "phone";//重置密码的手机号
    public static String SIGN = "sign";//验证手机号时，返回的签名
    @Bind(R.id.show_phone)
    TextView showPhone;
    @Bind(R.id.et_rcode)
    EditText etRcode;
    @Bind(R.id.btn_get_rcode)
    Button btnGetRcode;
    @Bind(R.id.et_pwd1)
    EditText etPwd1;
    @Bind(R.id.et_pwd2)
    EditText etPwd2;
    @Bind(R.id.btn_reset_psw)
    Button btnResetPsw;
    @Bind(R.id.content)
    LinearLayout content;

    private String resetPhone;//重置密码的手机号
    private String sign;//验证手机号时，返回的签名

    private SmsObserver smsObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_reset_password);

        resetPhone = getIntent().getStringExtra(PHONE);
        sign = getIntent().getStringExtra(SIGN);
        getRCode();//获取验证码
        autoFillRCode();//自动填写验证码
    }

    private void autoFillRCode() {
        //初始化
        smsObserver = new SmsObserver(this, new SmsResponseCallback() {
            @Override
            public void onCallbackSmsContent(String smsContent) {
                //这里接收短信
                etRcode.setText(smsContent);
            }
        }, new VerificationCodeSmsFilter("10690498241618"));
        //注册短信变化监听器
        smsObserver.registerSMSObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在不需要再使用短信接收功能的时候,注销短信监听器
        smsObserver.unregisterSMSObserver();
    }


    private void getRCode() {
        MyServiceClient.getService()
                .get_RCode(sign, 2, resetPhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (result.getErr() == 0) {
                            showPhone.setText("验证码已发送到手机" + resetPhone);
                            btnGetRcode.setEnabled(false);//设置不能点击
                            new MyCountDownTimer(100000, 1000).start();//100秒倒计时,每隔1秒刷新显示
                        }
                    }
                });
    }

    @OnClick({R.id.btn_get_rcode, R.id.btn_reset_psw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_rcode:
                getRCode();
                break;
            case R.id.btn_reset_psw:
                String rcode = etRcode.getEditableText().toString();
                String pwd1 = etPwd1.getEditableText().toString();
                String pwd2 = etPwd2.getEditableText().toString();

                if (StringTools.isEmpty(rcode)) {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (StringTools.isEmpty(pwd1)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!pwd1.equals(pwd2)) {
                    Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pwd1.length() < 6 || pwd1.length() > 16) {
                    Toast.makeText(this, "密码必须在6-16位", Toast.LENGTH_LONG).show();
                    return;
                }
                resetPassword(rcode, pwd1);
                break;
        }
    }

    /**
     * 重置密码
     *
     * @param code 验证码
     * @param pwd  密码
     */
    private void resetPassword(String code, String pwd) {
        MyServiceClient.getService()
                .post_ResetPassword(resetPhone, pwd, code, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result result) {
                        Toast.makeText(ResetPasswordActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();

                        if (result.getErr() == 0) {
                            //重置密码成功后，返回登录界面
                            Hawk.chain()
                                    .put(APP.LOGIN_NAME, resetPhone)
                                    .put(APP.LOGIN_PASSWORD, "")
                                    .commit();
                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    //倒计时的实现
    class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数 ,例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法,例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnGetRcode.setText("重新获取" + millisUntilFinished / 1000);//设置倒计时显示
        }

        @Override
        public void onFinish() {
            btnGetRcode.setText("获取验证码");
            btnGetRcode.setEnabled(true);//设置能点击
        }
    }
}
