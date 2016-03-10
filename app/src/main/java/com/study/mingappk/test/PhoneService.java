package com.study.mingappk.test;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * 查询手机号归属地api请求服务接口
 * Created by Ming on 2016/3/10.
 */
public interface PhoneService {
    @GET ("apistore/mobilenumber/mobilenumber")
    Call<PhoneResult> getResult(@Header("apikey") String apikey, @Query("phone")String phone);
}
