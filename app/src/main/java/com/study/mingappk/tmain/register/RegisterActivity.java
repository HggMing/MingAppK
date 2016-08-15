package com.study.mingappk.tmain.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.study.mingappk.model.bean.Login;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyService;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.tmain.MainActivity;
import com.study.mingappk.tmain.WebViewActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BackActivity {

    public static String PHONE = "phone";//注册的手机号
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
    @Bind(R.id.et_phone_recommend)
    EditText etPhoneRecommend;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.read)
    TextView read;

    private String regPhone;//注册的手机号
    private String sign;//验证手机号时，返回的签名

    private SmsObserver smsObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_register);

        regPhone = getIntent().getStringExtra(PHONE);
        sign = getIntent().getStringExtra(SIGN);
        read.setText(Html.fromHtml("<u>免责条款</u>"));//使用html实现下划线样式
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
                .get_RCode(sign, 1, regPhone)
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
                            showPhone.setText("验证码已发送到手机" + regPhone);
                            btnGetRcode.setEnabled(false);//设置不能点击
                            new MyCountDownTimer(100000, 1000).start();//100秒倒计时
                        }
                    }
                });
    }

    @OnClick({R.id.btn_get_rcode, R.id.btn_register, R.id.read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_rcode:
                getRCode();
                break;
            case R.id.read:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TAG, WebViewActivity.TITLE_NAME1);
                startActivity(intent);
                break;
            case R.id.btn_register:
                String rcode = etRcode.getEditableText().toString();
                String pwd1 = etPwd1.getEditableText().toString();
                String pwd2 = etPwd2.getEditableText().toString();
                String rphone = etPhoneRecommend.getEditableText().toString();

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
                if (!checkBox.isChecked()) {
                    Toast.makeText(this, "请认真阅读免责条款", Toast.LENGTH_LONG).show();
                    return;
                }
                register(rcode, pwd1, rphone);
                break;
        }
    }

    /**
     * 注册
     */
    private void register(String code, final String pwd, String rphone) {

        MyServiceClient.getService()
                .post_Register(regPhone, code, pwd, sign, rphone)
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
                            login(pwd);//注册成功后直接登录
                        } else {
                            Toast.makeText(RegisterActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void login(final String pwd) {
        MyServiceClient.getService().get_Login(regPhone, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Login>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.i("mm", "失败了" + throwable.getMessage());
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
                                    // .put(APP.LOGIN_NAME, regPhone)
                                    // .put(APP.LOGIN_PASSWORD, pwd)
                                    .commit();
                            Hawk.remove(APP.IS_FIRST_RUN);//用于显示添加村圈的引导
                            Intent intent = new Intent();
                            intent.setClass(RegisterActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
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
