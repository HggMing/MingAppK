package com.study.mingappk.test;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.study.mingappk.R;
import com.study.mingappk.model.bean.Phone2Adress;
import com.study.mingappk.model.provider.Phone2AdressProvider;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;


/**
 * 查询手机号归属地页面
 */
public class TestActivity extends BackActivity {
    @Bind(R.id.et_phone)
    AppCompatAutoCompleteTextView etPhone;

    @Bind(R.id.textView)
    TextView showAddView;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    private void queryPhoneWhereFrom() {
        showAddView.setText("");
        if (etPhone.getText().toString().isEmpty()) {
            Snackbar.make(etPhone, "请输入手机号", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }
        String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";
        Call<Phone2Adress> call = MyServiceClient.getService().getCall_Phone2Adress(API_KEY, etPhone.getText().toString());

        //发送请求

        call.enqueue(new Callback<Phone2Adress>() {
            @Override
            public void onResponse(Call<Phone2Adress> call, Response<Phone2Adress> response) {
                //4.处理结果

//                if (response.isSuccess()) {
                Phone2Adress phone2Adress = response.body();
                if (/*phone2Adress != null &&*/ phone2Adress.getErrNum() == 0) {
                    Phone2Adress.RetDataEntity retDataEntity = phone2Adress.getRetData();
                    showAddView.append("地址:" + retDataEntity.getCity());
                }
//                }
            }

            @Override
            public void onFailure(Call<Phone2Adress> call, Throwable t) {

            }
        });
    }


    private void queryByRxAndroid() {
        showAddView.setText("");
        if (etPhone.getText().toString().isEmpty()) {
            Snackbar.make(etPhone, "请输入手机号", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }
        Phone2AdressProvider.getCity(etPhone.getText().toString())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        showAddView.append("地址222:" + s);
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
                queryByRxAndroid();
                break;
        }
    }
}
