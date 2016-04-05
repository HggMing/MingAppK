package com.study.mingappk.model.service;

import com.study.mingappk.model.bean.A1Provice;
import com.study.mingappk.model.bean.A2City;
import com.study.mingappk.model.bean.A3County;
import com.study.mingappk.model.bean.A4Town;
import com.study.mingappk.model.bean.A5Village;
import com.study.mingappk.model.bean.BBSListResult;
import com.study.mingappk.model.bean.FollowVillageListResult;
import com.study.mingappk.model.bean.LoginResult;
import com.study.mingappk.model.bean.Phone2Adress;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.UserInfoResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络相关服务器接口
 */
public interface MyService {
    /**
     * 登录接口
     *
     * @param logname 用户名
     * @param pwd     密码
     * @return 用户信息
     */
    @GET("user/login")
    Observable<LoginResult> getCall_Login(
            @Query("logname") String logname,
            @Query("pwd") String pwd);

    /**
     * 查询号码归属地接口
     *
     * @param apikey api键
     * @param phone  手机号
     * @return 归属地信息
     */
    @GET("http://apis.baidu.com/apistore/mobilenumber/mobilenumber")
    Call<Phone2Adress> getCall_Phone2Adress(
            @Header("apikey") String apikey,
            @Query("phone") String phone);

    @GET("http://apis.baidu.com/apistore/mobilenumber/mobilenumber")
    Observable<Phone2Adress> getPhone2Adress2(
            @Header("apikey") String apikey,
            @Query("phone") String phone);

    /**
     * 意见反馈接口
     *
     * @param auth    认证信息
     * @param content 反馈内容
     * @param contact 联系方式
     * @return 是否成功
     */
    @GET("feedback/add")
    Call<Result> getCall_Advice(
            @Query("auth") String auth,
            @Query("content") String content,
            @Query("contact") String contact);

    /**
     * 获取用户信息接口
     *
     * @param auth 认证信息
     * @return 用户信息
     */
    @GET("user/ginfo")
    Call<UserInfoResult> getCall_UserInfo(
            @Query("auth") String auth);

    /**
     * 修改密码接口
     *
     * @param auth   认证信息
     * @param oldPwd 原始密码
     * @param pwd    新密码 新密码（6-16位）
     * @return 结果msg
     */
    @GET("password/upwd")
    Call<Result> getCall_ChangePwd(
            @Query("auth") String auth,
            @Query("Old_pwd") String oldPwd,
            @Query("pwd") String pwd);

    /**
     * 用户已经登录系统，修改头像
     *
     * @param auth 认证信息
     * @param head 头像：内容采用base64后的字符串，再加上扩展名
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("user/uhead")
    Call<Result> getCall_UpdateHead(
            @Field("auth") String auth,
            @Field("head") String head);


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
    @FormUrlEncoded
    @POST("user/uinfo")
    Call<Result> getCall_UpdateInfo(
            @Field("auth") String auth,
            @Field("uname") String uName,
            @Field("sex") String sex,
            @Field("cid") String cid,
            @Field("vid") String vid);

    /**
     * 五级详细地址列表接口
     *
     * @param auth 认证信息
     * @return 各级地址信息
     */
    @GET("vill/gprovicelist")
    Call<A1Provice> getCall_Add1(
            @Query("auth") String auth);

    @GET("vill/gcitylist")
    Call<A2City> getCall_Add2(
            @Query("auth") String auth,
            @Query("province_id") String province_id);

    @GET("vill/gcountylist")
    Call<A3County> getCall_Add3(
            @Query("auth") String auth,
            @Query("city_id") String city_id);

    @GET("vill/gtownlist")
    Call<A4Town> getCall_Add4(
            @Query("auth") String auth,
            @Query("county_id") String county_id);

    @GET("vill/gvilllist")
    Call<A5Village> getCall_Add5(
            @Query("auth") String auth,
            @Query("town_id") String town_id);

    /**
     * 分页获取村圈列表信息，同时该接口会返回，关注总数cnt
     *
     * @param auth     认证信息
     * @param page     当前页码，默认为：1页
     * @param pagesize 每页条数，默认20条
     * @return 村圈列表信息
     */
    @GET("vill/followlist")
    Call<FollowVillageListResult> getCall_FollowList(
            @Query("auth") String auth,
            @Query("page") int page,
            @Query("pagesize") int pagesize);

    /**
     * 取消关注村圈接口
     *
     * @param auth 认证信息
     * @param vid  村id
     * @return 结果msg
     */
    @GET("vill/del")
    Call<Result> getCall_DelFollowList(
            @Query("auth") String auth,
            @Query("vid") String vid);

    /**
     * 关注村圈接口
     *
     * @param auth 认证信息
     * @param vid  村id
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("vill/follow")
    Call<Result> getCall_FollowVillage(
            @Field("auth") String auth,
            @Field("vid") String vid);

    /**
     * 获取帖子列表接口
     *
     * @param auth     认证信息
     * @param vid      村id
     * @param page     当前页码，默认为：1页
     * @param pagesize 每页条数，默认20条
     * @return 帖子列表信息
     */
    @GET("bbs/list")
    Call<BBSListResult> getCall_BBSList(
            @Query("auth") String auth,
            @Query("vid") String vid,
            @Query("page") int page,
            @Query("pagesize") int pagesize);


}
