package com.study.mingappk.tab4.safesetting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.model.bean.CheckPhone;
import com.study.mingappk.model.bean.ResultOther;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BackActivity;

import java.io.IOException;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SafeSettingActivity extends BackActivity {

    @Bind(R.id.tv_is_binding)
    TextView tvIsBinging;
    @Bind(R.id.arrow_binding)
    View arrowBinging;

    private int red;
    private int blue;

    private final int REQUEST_IS_REAL_NAME_BINGING = 123;//请求实名认证
    public static String IS_BINDING = "is_binding";
    private boolean isBinding;//是否实名认证
    private boolean isBindingChecked;//是否检查完实名认证状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);

        setToolbarTitle(R.string.title_activity_safe_setting);

        red = getResources().getColor(android.R.color.holo_red_light);
        blue = getResources().getColor(android.R.color.holo_blue_light);

        //是否实名认证显示
        getIsBinding();
    }

    private void getIsBinding() {
        //1)将除图片外的参数以及机构key组成一个字符串(注意顺序)
        String phone = Hawk.get(APP.LOGIN_NAME);
        String other = "compid=9&phone=" + phone;
        String str = other + "&key=69939442285489888751746749876227";
        //2)使用MD5算法加密上述字符串
        String sign = BaseTools.md5(str);
        //3)最终得到参数字符串：（注意，KEY参数不带到参数列表,sign参数加入参数列表）
        String str2 = other + "&sign=" + sign;
        //4)把上述字符串做base64加密，最终得到请求:
        String paraString = Base64.encodeToString(str2.getBytes(), Base64.NO_WRAP);
        RequestBody data = RequestBody.create(MediaType.parse("text/plain"), paraString);

        MyServiceClient.getService()
                .post_IsRealBinding(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        isBindingChecked = true;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String s = null;
                        try {
                            s = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Gson gson = new Gson();
                        CheckPhone result = gson.fromJson(new String(Base64.decode(s, Base64.DEFAULT)), CheckPhone.class);
                        if (result.getErr() == 0) {//已经实名认证
                            tvIsBinging.setText("已认证");
                            tvIsBinging.setTextColor(blue);
                            arrowBinging.setVisibility(View.INVISIBLE);
                            isBinding = true;
                        }
                        if (result.getErr() == 1002) {//还没有实名认证
                            tvIsBinging.setText("未认证");
                            tvIsBinging.setTextColor(red);
                            isBinding = false;
                        }
                    }
                });
    }

    @OnClick({R.id.click_identity_binding, R.id.click_change_psw, R.id.click_purse_psw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_identity_binding:
                //Toast.makeText(mActivity, "实名认证", Toast.LENGTH_SHORT).show();
                if (isBinding) {
                    Toast.makeText(SafeSettingActivity.this, "已完成实名认证", Toast.LENGTH_SHORT).show();
                } else if (isBindingChecked) {
                    Intent intent = new Intent(this, RealNameBindingActivity.class);
                    startActivityForResult(intent, REQUEST_IS_REAL_NAME_BINGING);
                }
                break;
            case R.id.click_change_psw:
                //Toast.makeText(SettingCommonActivity.this, "修改登录密码", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, ChangePwdActivity.class);
                startActivity(intent1);
                break;
            case R.id.click_purse_psw:
                //Toast.makeText(SettingCommonActivity.this, "新建or修改钱包密码", Toast.LENGTH_SHORT).show();
                //检测是否设置钱包密码
                String auth = Hawk.get(APP.USER_AUTH);
                MyServiceClient.getService()
                        .get_IsSetPWD(auth)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultOther>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResultOther resultOther) {
                                if (resultOther.getIs_pwd() == 1) {//已设置密码
//                            Toast.makeText(MyPurseActivity.this, "修改钱包密码", Toast.LENGTH_SHORT).show();
                                    Intent intent2 = new Intent(SafeSettingActivity.this, ChangePursePwdActivity.class);
                                    startActivity(intent2);
                                } else {
                                    //设置钱包密码
                                    Intent intent = new Intent(SafeSettingActivity.this, SetPursePwdActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IS_REAL_NAME_BINGING:
                if (resultCode == RESULT_OK) {
                    //是否实名认证显示
                    isBinding = data.getBooleanExtra(IS_BINDING, false);
                    if (isBinding) {
                        tvIsBinging.setText("已认证");
                        tvIsBinging.setTextColor(blue);
                        arrowBinging.setVisibility(View.INVISIBLE);
                    } else {
                        tvIsBinging.setText("未认证");
                        tvIsBinging.setTextColor(red);
                    }
                }
                break;
        }
    }
}
