package com.study.mingappk.model.service;

import com.study.mingappk.model.bean.A1Provice;
import com.study.mingappk.model.bean.A2City;
import com.study.mingappk.model.bean.A3County;
import com.study.mingappk.model.bean.A4Town;
import com.study.mingappk.model.bean.A5Village;
import com.study.mingappk.model.bean.BBSList;
import com.study.mingappk.model.bean.BbsCommentList;
import com.study.mingappk.model.bean.FollowVillageList;
import com.study.mingappk.model.bean.FriendDetail;
import com.study.mingappk.model.bean.FriendList;
import com.study.mingappk.model.bean.Login;
import com.study.mingappk.model.bean.MessageList;
import com.study.mingappk.model.bean.Phone2Adress;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.UploadFiles;
import com.study.mingappk.model.bean.UserInfo;
import com.study.mingappk.model.bean.ZanList;

import java.lang.reflect.Array;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Observable<Login> getObservable_Login(
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
    Observable<Phone2Adress> getObservable_Phone2Adress(
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
    Call<UserInfo> getCall_UserInfo(
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
    Call<Result> postCall_UpdateHead(
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
    Call<Result> postCall_UpdateInfo(
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
    Call<FollowVillageList> getCall_FollowList(
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
    Call<Result> postCall_FollowVillage(
            @Field("auth") String auth,
            @Field("vid") String vid);

    /**
     * 该接口用户帖子的附件上传，包括图片其他压缩包等
     *
     * @param auth 验证参数
     * @param file 附件上传
     * @return insert_id和url
     */
    @Multipart
    @POST("bbs/ufiles")
    Observable<UploadFiles> postObservable_UploadImage(
            @Part("auth") String auth,
            @Part("files\"; filename=\"jpg") RequestBody file
            // @PartMap Map<String, RequestBody> params,
    );

    /**
     * @param auth    认证信息
     * @param vid     村id
     * @param title   标题，最长64个字
     * @param conts   内容，最长500个字
     * @param pimg    图片
     * @param file_id 附件的id，没有附件就不传(多个附件请用“逗号”隔开)
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("bbs/add")
    Observable<Result> postObservable_BBSPost(
            @Field("auth") String auth,
            @Field("vid") String vid,
            @Field("title") String title,
            @Field("conts") String conts,
            @Field("pimg") String pimg,
            @Field("file_id") String file_id);

    /**
     * 评论帖子
     *
     * @param auth  认证信息
     * @param pid   帖子id
     * @param conts 评论内容，最长255个字
     * @return 结果msg+insert_id
     */
    @FormUrlEncoded
    @POST("bbs/addcom")
    Observable<Result> postObservable_AddComment(
            @Field("auth") String auth,
            @Field("pid") String pid,
            @Field("conts") String conts);

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
    Observable<BBSList> getObservable_BBSList(
            @Query("auth") String auth,
            @Query("vid") String vid,
            @Query("page") int page,
            @Query("pagesize") int pagesize);

    /**
     * 获取评论列表接口
     *
     * @param auth     认证信息
     * @param pid      帖子id
     * @param page     当前页码，默认为：1页
     * @param pagesize 每页条数，默认20条
     * @return 评论列表
     */
    @GET("bbs/comlist")
    Observable<BbsCommentList> getObservable_BbsCommentList(
            @Query("auth") String auth,
            @Query("pid") String pid,
            @Query("page") int page,
            @Query("pagesize") int pagesize);

    /**
     * 获取村圈中帖子点赞的列表
     *
     * @param auth     认证信息
     * @param pid      帖子id
     * @param page     当前页码，默认为：1页
     * @param pagesize 每页条数，默认20条
     * @return 点赞时间，用户头像和名字
     */
    @GET("bbs/zanlist")
    Observable<ZanList> getObservable_ZanList(
            @Query("auth") String auth,
            @Query("pid") String pid,
            @Query("page") int page,
            @Query("pagesize") int pagesize);

    /**
     * 对文档点赞
     *
     * @param auth 认证信息
     * @param pid  文档id
     * @return 结果msg
     */
    @GET("bbs/zans")
    Call<Result> getCall_ClickLike(
            @Query("auth") String auth,
            @Query("pid") String pid);


    /**
     * 分页获取好友列表信息，同时该接口会返回，好友总数cnt，其中uid:10000:小包谷【语音助手】;uid:10001:我们村【客服】
     *
     * @param auth     认证信息
     * @param page     当前页码，默认为：1页
     * @param pagesize 每页条数，默认20条
     * @return "aname":"备注名",
     * "uid":"13123",
     * "name":"昵称",
     * "phone":"手机号",
     * "sex":"0",
     * "head":"头像"
     */
    @GET("friend/list")
    Call<FriendList> getCall_FriendList(
            @Query("auth") String auth,
            @Query("page") int page,
            @Query("pagesize") int pagesize);

    /**
     * 获取好友的档案信息
     *
     * @param auth 认证信息
     * @param uid  好友id
     * @return "userinfo":用户信息
     * "photoinfo":用户相册
     * "videoinfo":用户视频
     * "voiceinfo":语音留言（废除）
     * "scoreinfo":学习成绩
     * "healthinfo":健康状况
     * "bbs_top_pic4":帖子最新4图
     */
    @GET("arch/info_e")
    Observable<FriendDetail> getObservable_FriendDetail(
            @Query("auth") String auth,
            @Query("uid") String uid);

    /**
     * 设置好友别名或者称呼
     *
     * @param auth  认证信息
     * @param uid   好友id
     * @param aname 好友别名或者称呼
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("friend/rname")
    Observable<Result> postObservabel_RemarkName(
            @Field("auth") String auth,
            @Field("uid") String uid,
            @Field("aname") String aname);

    /**
     * 获取用户（好友）帖子列表接口
     *
     * @param auth     认证信息
     * @param uid      用户id
     * @param page     当前页码，默认为：1页
     * @param pagesize 每页条数，默认20条
     * @return 帖子列表信息
     */
    @GET("bbs/ulist")
    Observable<BBSList> getObservable_FriendBbsList(
            @Query("auth") String auth,
            @Query("uid") String uid,
            @Query("page") int page,
            @Query("pagesize") int pagesize);

    /**
     * 用户之间发送消息
     *
     * @param from   发送人id
     * @param to     接收人id
     * @param ct     消息类型，0文字，1图片，2声音，3html，4内部消息json格式，5交互消息 6应用透传消息json格式,7朋友系统消息json
     * @param app    发送消息的app
     * @param txt    消息内容
     * @param source 发送的资源，默认空数组，如果有资源则是数据流base64后的数据+’.’+资源的扩展名
     * @param ex     扩展字段， 根据不同应用定义不同的意义
     * @param mt     发送方式:1即时消息，2异步消息
     * @param xt     发送人类型 0系统，2用户与用户，1公众号与用户
     * @return 返回
     */
    @FormUrlEncoded
    @POST("http://push.traimo.com/msg/user_sent")
    Observable<Result> postObservabel_sendMessage(
            @Field("from") String from,
            @Field("to") String to,
            @Field("ct") int ct,
            @Field("app") String app,
            @Field("txt") String txt,
            @Field("source") Array source,
            @Field("ex") String ex,
            @Field("mt") int mt,
            @Field("xt") int xt);

    /**
     * 用户获取消息接口
     *
     * @param me  接收人id
     * @param app 需要什么app的消息
     * @param os  1安卓，2苹果，3winphone，4  web
     * @return MessageList
     */
    @GET("http://push.traimo.com/msg/lists")
    Observable<MessageList> getObservable_MessageList(
            @Query("me") String me,
            @Query("app") String app,
            @Query("os") int os);


}
