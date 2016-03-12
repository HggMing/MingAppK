package com.study.mingappk.userlogin;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 登录请求API
 * Created by Ming on 2016/3/11.
 */
public class LoginApi {
    private static final String BASE_URL = "http://121.40.105.149:9901/";//API接口的主机地址
    final LoginService loginService;

    /**
     * 登录请求服务接口
     */
    public interface LoginService {
        @GET("user/login")
        Call<LoginResult> getLoginResultCall(@Query("logname") String logname, @Query("pwd") String pwd);
    }

    public LoginApi() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl(BASE_URL)//主机地址
                .build();
        //2.创建服务
        loginService = retrofit.create(LoginService.class);
    }

    public Call<LoginResult> getCall(String logname, String pwd) {
        return loginService.getLoginResultCall(logname, pwd);
    }
}
