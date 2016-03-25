package com.study.mingappk.api;

import com.study.mingappk.api.result.Address1Result;
import com.study.mingappk.api.result.Address2Result;
import com.study.mingappk.api.result.Address3Result;
import com.study.mingappk.api.result.Address4Result;
import com.study.mingappk.api.result.Address5Result;
import com.study.mingappk.api.result.LoginResult;
import com.study.mingappk.api.result.PhoneResult;
import com.study.mingappk.api.result.Result;
import com.study.mingappk.api.result.UserInfoResult;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
    Call<LoginResult> getCall_Login(@Query("logname") String logname, @Query("pwd") String pwd);

    /**
     * 查询号码归属地接口
     *
     * @param apikey api键
     * @param phone  手机号
     * @return 归属地信息
     */
    @GET("http://apis.baidu.com/apistore/mobilenumber/mobilenumber")
    Call<PhoneResult> getCall_Phone2Adress(@Header("apikey") String apikey, @Query("phone") String phone);

    /**
     * 意见反馈接口
     *
     * @param auth    认证信息
     * @param content 反馈内容
     * @param contact 联系方式
     * @return 是否成功
     */
    @GET("feedback/add")
    Call<Result> getCall_Advice(@Query("auth") String auth, @Query("content") String content, @Query("contact") String contact);

    /**
     * 获取用户信息接口
     *
     * @param auth 认证信息
     * @return 用户信息
     */
    @GET("user/ginfo")
    Call<UserInfoResult> getCall_UserInfo(@Query("auth") String auth);

    /**
     * 修改密码接口
     *
     * @param auth   认证信息
     * @param oldPwd 原始密码
     * @param pwd    新密码 新密码（6-16位）
     * @return 结果msg
     */
    @GET("password/upwd")
    Call<Result> getCall_ChangePwd(@Query("auth") String auth, @Query("Old_pwd") String oldPwd, @Query("pwd") String pwd);

    /**
     * 用户已经登录系统，修改头像
     *
     * @param auth 认证信息
     * @param head 头像：内容采用base64后的字符串，再加上扩展名
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("user/uhead")
    Call<Result> getCall_UpdateHead(@Field("auth") String auth, @Field("head") String head);


    /**
     * 修改用户信息接口
     *
     * @param auth  认证信息
     * @param uName 姓名
     * @param sex   性别
     * @param cid   身份证号码
     * @param vid   村圈id→我的地址
     * @return 结果msg
     */
    @GET("user/uinfo")
    Call<Result> getCall_UpdateInfo(@Query("auth") String auth, @Query("uname") String uName, @Query("sex") String sex, @Query("cid") String cid, @Query("vid") String vid);

    /**
     * 五级详细地址列表接口
     *
     * @param auth 认证信息
     * @return 各级地址信息
     */
    @GET("vill/gprovicelist")
    Call<Address1Result> getCall_Add1(@Query("auth") String auth);

    @GET("vill/gcitylist")
    Call<Address2Result> getCall_Add2(@Query("auth") String auth, @Query("province_id") String province_id);

    @GET("vill/gcountylist")
    Call<Address3Result> getCall_Add3(@Query("auth") String auth, @Query("city_id") String city_id);

    @GET("vill/gtownlist")
    Call<Address4Result> getCall_Add4(@Query("auth") String auth, @Query("county_id") String county_id);

    @GET("vill/gvilllist")
    Call<Address5Result> getCall_Add5(@Query("auth") String auth, @Query("town_id") String town_id);


}
