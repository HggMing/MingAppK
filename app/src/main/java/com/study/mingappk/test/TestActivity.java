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
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.PhoneResult;
import com.study.mingappk.main.BackActivity;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * 查询手机号归属地页面
 */
public class TestActivity extends BackActivity {
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
    }

    private void queryPhoneWhereFrom() {
        showAddView.setText("");
        if (etPhone.getText().toString().isEmpty()) {
            Snackbar.make(etPhone, "请输入手机号", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }
        String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";
        Call<PhoneResult> call = new MyNetApi().getService().getCall_Phone2Adress(API_KEY,etPhone.getText().toString());

        //发送请求

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

    rx.Observable<Response<PhoneResult>> responseObservable = rx.Observable.create(new Observable.OnSubscribe<Response<PhoneResult>>() {
        @Override
        public void call(Subscriber<? super Response<PhoneResult>> subscriber) {
            String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";
            Call<PhoneResult> call = new MyNetApi().getService().getCall_Phone2Adress(API_KEY,etPhone.getText().toString());
            try {
                subscriber.onNext(call.execute());
            } catch (IOException e) {
                e.printStackTrace();
            }
            subscriber.onCompleted();
        }
    });


    private void queryByRxAndroid() {
        showAddView.setText("");
        if (etPhone.getText().toString().isEmpty()) {
            Snackbar.make(etPhone, "请输入手机号", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }
        responseObservable
                .map(new Func1<Response<PhoneResult>, PhoneResult>() {

                    @Override
                    public PhoneResult call(Response<PhoneResult> phoneResultResponse) {return phoneResultResponse.body();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhoneResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(PhoneResult phoneResult) {
                        if (phoneResult != null && phoneResult.getErrNum() == 0) {
                            PhoneResult.RetDataEntity retDataEntity = phoneResult.getRetData();
                            showAddView.append("地址:" + retDataEntity.getCity());
                        }
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
