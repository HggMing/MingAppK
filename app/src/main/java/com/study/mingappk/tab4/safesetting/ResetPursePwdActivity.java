package com.study.mingappk.tab4.safesetting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintButton;
import com.bilibili.magicasakura.widgets.TintEditText;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.widgets.sms_autofill.SmsObserver;
import com.study.mingappk.common.widgets.sms_autofill.SmsResponseCallback;
import com.study.mingappk.common.widgets.sms_autofill.VerificationCodeSmsFilter;
import com.study.mingappk.model.bean.Result;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ResetPursePwdActivity extends BackActivity {

    @Bind(R.id.et_rcode)
    TintEditText etRcode;
    @Bind(R.id.btn_get_rcode)
    TintButton btnGetRcode;
    @Bind(R.id.et_newpwd1)
    TintEditText etPwd1;
    @Bind(R.id.et_newpwd2)
    TintEditText etPwd2;
    @Bind(R.id.btn_reset_psw)
    TintButton btnResetPsw;
    @Bind(R.id.content)
    LinearLayout content;

    private SmsObserver smsObserver;
    private String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_purse_pwd);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_reset_purse_pwd);

        auth = Hawk.get(APP.USER_AUTH);
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
                .get_TradeCode(auth)
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
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringTools.isEmpty(pwd1)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwd1.equals(pwd2)) {
                    Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd1.length() < 6) {
                    Toast.makeText(this, "密码必须在6位", Toast.LENGTH_SHORT).show();
                    return ;
                }
                resetPassword(rcode, pwd1);
                break;
        }
    }

    /**
     * 重置钱包密码
     *
     * @param code 验证码
     * @param pwd  密码
     */
    private void resetPassword(String code, String pwd) {
        MyServiceClient.getService()
                .post_ResetPursePwd(auth,code,pwd)
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
                            //重置密码成功后，返回设置界面
                            Toast.makeText(ResetPursePwdActivity.this, "找回钱包密码成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(ResetPursePwdActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
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
