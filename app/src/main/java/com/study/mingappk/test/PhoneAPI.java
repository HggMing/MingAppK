package com.study.mingappk.test;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Ming on 2016/3/11.
 */
public class PhoneAPI {
    private static final String BASE_URL = "http://apis.baidu.com/";//API接口的主机地址
    private static final String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";
    final PhoneService phoneService;

    /**
     * 查询手机号归属地api请求服务接口
     * Created by Ming on 2016/3/10.
     */
    public interface PhoneService {
        @GET("apistore/mobilenumber/mobilenumber")
        Call<PhoneResult> getResult(@Header("apikey") String apikey, @Query("phone") String phone);
    }

    public PhoneAPI() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl(BASE_URL)//主机地址
                .build();
        //2.创建服务
        phoneService = retrofit.create(PhoneService.class);
    }

    public Call<PhoneResult> getCall( String phone) {
        return phoneService.getResult(API_KEY, phone);
    }

}
