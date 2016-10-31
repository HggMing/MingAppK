package com.study.mingappk.tmain.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.widgets.dialog.MyDialog;
import com.study.mingappk.model.bean.CheckPhone;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.tmain.userlogin.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestPhoneNumberActivity extends BackActivity {

    public static String TYPE = "_type";//1注册2忘记密码
    public static String LOGIN_NAME = "login_name";//登录时未注册的账号
    @Bind(R.id.et_phone_test)
    EditText etPhone;
    @Bind(R.id.btn_ok)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_phone_number);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_test_phone_number);

        String login = getIntent().getStringExtra(LOGIN_NAME);
        if (!StringTools.isEmpty(login)) {
            etPhone.setText(login);
        }
    }

    @OnClick(R.id.btn_ok)
    public void onClick() {
        btnOk.setEnabled(false);
        String phone = etPhone.getEditableText().toString();
        if (StringTools.isEmpty(phone)) {
            btnOk.setEnabled(true);
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!BaseTools.checkPhone(phone)) {
            btnOk.setEnabled(true);
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        int type = getIntent().getIntExtra(TYPE, 0);
        switch (type) {
            case 1://注册
                checkPhone(phone);
                break;
            case 2://忘记密码
                checkPhonePSW(phone);
                break;
            default:
                break;
        }

    }

    /**
     * 忘记密码，检查手机号是否存在
     */
    private void checkPhonePSW(final String phone) {
        MyServiceClient.getService().get_CheckPhonePSW(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CheckPhone>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CheckPhone checkPhone) {
                        if (checkPhone.getErr() == 0) {//账号存在
                            Intent intent = new Intent(TestPhoneNumberActivity.this, ResetPasswordActivity.class);
                            intent.putExtra(ResetPasswordActivity.PHONE, phone);
                            intent.putExtra(ResetPasswordActivity.SIGN, checkPhone.getSign());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TestPhoneNumberActivity.this, checkPhone.getMsg(), Toast.LENGTH_SHORT).show();
                            btnOk.setEnabled(true);
                        }
                    }
                });
    }

    /**
     * 注册时，检查手机号是否已注册
     */
    private void checkPhone(final String phone) {
        MyServiceClient.getService().get_CheckPhone(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CheckPhone>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CheckPhone checkPhone) {
                        switch (checkPhone.getErr()) {
                            case 0://未注册
//                                Toast.makeText(TestPhoneNumberActivity.this, "进入注册页面", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TestPhoneNumberActivity.this, RegisterActivity.class);
                                intent.putExtra(RegisterActivity.PHONE, phone);
                                intent.putExtra(RegisterActivity.SIGN, checkPhone.getSign());
                                startActivity(intent);
                                finish();
                                break;
                            case 2005://已注册
                                MyDialog.Builder builder1 = new MyDialog.Builder(TestPhoneNumberActivity.this);
                                builder1.setTitle("提示")
                                        .setCannel(false)
                                        .setMessage(checkPhone.getMsg())
                                        .setPositiveButton("确定",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog,
                                                                        int which) {
                                                        Intent intent2 = new Intent();
                                                        intent2.putExtra(LoginActivity.REGISTERED_PHONE_NUMBER, phone);
                                                        setResult(RESULT_OK, intent2);
                                                        finish();
                                                        dialog.dismiss();
                                                    }
                                                });
                                if (!isFinishing()) {
                                    builder1.create().show();
                                }
                                break;
                        }

                    }
                });

    }
}
