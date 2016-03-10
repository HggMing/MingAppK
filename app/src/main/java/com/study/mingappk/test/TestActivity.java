package com.study.mingappk.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.mingappk.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.study.mingappk.test.PhoneResult;


/**
 * 查询手机号归属地页面
 */
public class TestActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://apis.baidu.com/";//API接口的主机地址
    private static final String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_phone)
    AppCompatAutoCompleteTextView etPhone;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.textView)
    TextView showAddView;
    @Bind(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    private void queryPhoneWhereFrom() {
        showAddView.setText("");
        if(etPhone.getText().toString().isEmpty()){
            Snackbar.make(etPhone, "请输入手机号", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl(BASE_URL)//主机地址
                .build();

        //2.创建访问API的请求
        final PhoneService phoneService = retrofit.create(PhoneService.class);
        Call<PhoneResult> call = phoneService.getResult(API_KEY, etPhone.getText().toString());

        //3.发送请求

        call.enqueue(new Callback<PhoneResult>() {
            @Override
            public void onResponse(Call<PhoneResult> call, Response<PhoneResult> response) {
                //4.处理结果

                if (response.isSuccess()) {
                    PhoneResult phoneResult = response.body();
                    if (phoneResult != null && phoneResult.getErrNum() == 0) {
                        PhoneResult.RetDataEntity retDataEntity = phoneResult.getRetData();
                        showAddView.append("地址:" + retDataEntity.getCity());
                    }
                }
            }

            @Override
            public void onFailure(Call<PhoneResult> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.button, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                queryPhoneWhereFrom();
                break;
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }

}
