package com.study.mingappk.api;

import com.study.mingappk.api.result.AdviceResult;
import com.study.mingappk.api.result.LoginResult;
import com.study.mingappk.api.result.PhoneResult;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

/**
 * 网络接口
 */
public class MyNetApi {
    Retrofit retrofit;
    private static final String BASE_URL = "http://121.40.105.149:9901/";//API接口的主机地址
    MyNetApiService myNetApiService;

    public MyNetApi() {
        //1.创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl(BASE_URL)//主机地址
                .build();
        //2.创建服务
        myNetApiService = retrofit.create(MyNetApiService.class);
    }


    public Call<PhoneResult> getCall(String phone) {
        String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";
        return myNetApiService.PHONE_RESULT_CALL(API_KEY, phone);
    }

    public Call<LoginResult> getCall(String logname, String pwd) {
        return myNetApiService.LOGIN_RESULT_CALL(logname, pwd);
    }

    public Call<AdviceResult> getCall(String auth, String content, String contact) {
        return myNetApiService.ADVICE_RESULT_CALL(auth, content, contact);
    }

}
