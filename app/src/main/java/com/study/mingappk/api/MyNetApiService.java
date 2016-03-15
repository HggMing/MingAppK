package com.study.mingappk.api;

import com.study.mingappk.api.result.LoginResult;
import com.study.mingappk.api.result.PhoneResult;
import com.study.mingappk.api.result.AdviceResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * 网络相关接口
 */
public interface MyNetApiService {
    /**
     * 登录接口
     *
     * @param logname 用户名
     * @param pwd     密码
     * @return 用户信息
     */
    @GET("user/login")
    Call<LoginResult> LOGIN_RESULT_CALL(@Query("logname") String logname, @Query("pwd") String pwd);

    /**
     * 查询号码归属地接口
     *
     * @param apikey api键
     * @param phone  手机号
     * @return 归属地信息
     */
    @GET("http://apis.baidu.com/apistore/mobilenumber/mobilenumber")
    Call<PhoneResult> PHONE_RESULT_CALL(@Header("apikey") String apikey, @Query("phone") String phone);

    /**
     * 意见反馈接口
     *
     * @param auth    认证信息
     * @param content 反馈内容
     * @param contact 联系方式
     * @return 是否成功
     */
    @GET("feedback/add")
    Call<AdviceResult> ADVICE_RESULT_CALL(@Query("auth") String auth, @Query("content") String content, @Query("contact") String contact);
}
