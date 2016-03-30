package com.study.mingappk.api;

import com.study.mingappk.api.result.Result;
import com.study.mingappk.api.result.LoginResult;
import com.study.mingappk.api.result.PhoneResult;
import com.study.mingappk.api.result.UserInfoResult;
import com.study.mingappk.common.app.MyApplication;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络接口
 */
public class MyNetApi {
    Retrofit retrofit;

    public static String getBaseUrl() {
        return BASE_URL2;
    }

    private static final String BASE_URL = "http://121.40.105.149:9901/";//API接口的主机地址
    private static final String BASE_URL2 = "http://121.40.105.149:9901";//API接口的主机地址


    public MyNetApiService getService() {
        return myNetApiService;
    }

    private MyNetApiService myNetApiService;

    public MyNetApi() {
        //1.创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl(BASE_URL)//主机地址
                .build();
        //2.创建服务
        myNetApiService = retrofit.create(MyNetApiService.class);
    }
}
