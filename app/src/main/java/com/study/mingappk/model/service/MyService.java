package com.study.mingappk.model.service;

import com.study.mingappk.model.bean.A1Provice;
import com.study.mingappk.model.bean.A2City;
import com.study.mingappk.model.bean.A3County;
import com.study.mingappk.model.bean.A4Town;
import com.study.mingappk.model.bean.A5Village;
import com.study.mingappk.model.bean.ApplyInfo;
import com.study.mingappk.model.bean.BBSList;
import com.study.mingappk.model.bean.BbsCommentList;
import com.study.mingappk.model.bean.CheckPhone;
import com.study.mingappk.model.bean.FollowVillageList;
import com.study.mingappk.model.bean.FriendDetail;
import com.study.mingappk.model.bean.FriendList;
import com.study.mingappk.model.bean.Login;
import com.study.mingappk.model.bean.MessageList;
import com.study.mingappk.model.bean.NewsList;
import com.study.mingappk.model.bean.QueryVillageList;
import com.study.mingappk.model.bean.RecommendVillage;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.UploadFiles;
import com.study.mingappk.model.bean.UserInfo;
import com.study.mingappk.model.bean.ZanList;

import java.lang.reflect.Array;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Observable<Login> get_Login(
            @Query("logname") String logname,
            @Query("pwd") String pwd);


    /**
     * 进行实名认证，注册接口
     *
     * @param data    加密后的参数
     * @param facepic 正面免冠图片
     * @param pic1    身份证正面照片
     * @return 先base64解密，得到json
     */
    @Multipart
    @POST("http://capi.nids.com.cn/iras/reg")
    Observable<ResponseBody> post_FaceRealBinding(
            @Part("data") String data,
            @Part("facepic\"; filename=\"jpg") RequestBody facepic,
            @Part("pic1\"; filename=\"jpg") RequestBody pic1);

    /**
     * 验证是否已实名认证
     *
     * @param data 加密后的参数
     * @return 先base64解密，得到json
     */
    @Multipart
    @POST("http://capi.nids.com.cn/iras/userinfo")
    Observable<ResponseBody> post_IsRealBinding(
            @Part("data") String data);

    /**
     * 人脸登录：登录接口需要提供正面人脸照片和用户电话号码，系统在得到照片之后将和用户注册照片进行比对，返回结果。
     *
     * @param data    加密后的参数
     * @param facepic 人脸图片
     * @return 先base64解密，得到json
     */
    @Multipart
    @POST("http://capi.nids.com.cn/iras/ver")
    Observable<ResponseBody> post_FaceLogin(
            @Part("data") String data,
            @Part("facepic\"; filename=\"jpg") RequestBody facepic);

    /**
     * 人脸认证成功后登录接口：该接口在进行人脸认证成功后，直接调用该接口，进行登录。
     * 该接口中如果该用户，未绑定就会直接绑定认证的时候提交的账号，如果绑定了 ，就会用绑定的账号登录
     *
     * @param sign 人脸认证标识，在进行人脸认证成功后，认证接口返回的sign
     * @return
     */
    @FormUrlEncoded
    @POST("user/authlogin")
    Observable<Login> post_FaceLogin2(
            @Field("sign") String sign);

    /**
     * 验证手机号码是否注册接口
     *
     * @param tel 手机号
     * @return 注册参数sign
     */
    @GET("user/telcheck")
    Observable<CheckPhone> get_CheckPhone(
            @Query("tel") String tel);

    /**
     * 获取注册验证码接口
     *
     * @param sign 验证手机号时，返回的签名 sign
     * @param type 类型，1注册，2找回密码,3、重新绑定手机
     * @param tel  手机号
     * @return 结果msg
     */
    @GET("user/rcode")
    Observable<Result> get_RCode(
            @Query("sign") String sign,
            @Query("type") int type,
            @Query("tel") String tel);

    /**
     * 注册接口
     *
     * @param tel    手机号码
     * @param code   验证码
     * @param pwd    密码（6-16位）
     * @param sign   签名
     * @param rphone 推荐人手机号
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<Result> post_Register(
            @Field("tel") String tel,
            @Field("code") String code,
            @Field("pwd") String pwd,
            @Field("sign") String sign,
            @Field("rphone") String rphone);

    /**
     * 忘记密码，账号检查接口
     *
     * @param tel 手机号码
     * @return 注册参数sign
     */
    @GET("password/telcheck")
    Observable<CheckPhone> get_CheckPhonePSW(
            @Query("tel") String tel);

    /**
     * 重置密码接口:该接口主要用户，忘记密码，通过短信验证码重置密码密码的接口
     *
     * @param tel  登录账号
     * @param pwd  密码（6-16位）
     * @param code 验证码
     * @param sign 找回密码签名
     * @return 结果msg（返回有info，暂时不用）
     */
    @FormUrlEncoded
    @POST("password/findpwd")
    Observable<Result> post_ResetPassword(
            @Field("tel") String tel,
            @Field("pwd") String pwd,
            @Field("code") String code,
            @Field("sign") String sign);

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
     * 获取政务里，消息列表
     *
     * @param vid      村id
     * @param type     1：新闻，2：政策，3：服务，4：资讯
     * @param page     当前页码，默认为：1页
     * @param pagesize 每页条数，默认20条
     * @return 详细列表清单
     */
    @GET("news/list")
    Observable<NewsList> get_NewsList(
            @Query("vid") String vid,
            @Query("type") int type,
            @Query("page") int page,
            @Query("pagesize") int pagesize);

    /**
     * 查询村庄详细地址接口
     *
     * @param village_name 村名 关键字
     * @return 详细村地址名
     */
    @GET("vill/qlist")
    Observable<QueryVillageList> get_QueryVillage(
            @Query("village_name") String village_name);

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
     * 获取推荐村圈接口
     *
     * @param auth 认证信息
     * @return 推荐的村
     */
    @GET("vill/recommend")
    Observable<RecommendVillage> get_RecommendVillage(
            @Query("auth") String auth);

    /**
     * 该接口用户帖子的附件上传，包括图片其他压缩包等
     *
     * @param auth 验证参数
     * @param file 附件上传
     * @return insert_id和url
     */
    @Multipart
    @POST("bbs/ufiles")
    Observable<UploadFiles> post_UploadImage(
            @Part("auth") String auth,
            @Part("files\"; filename=\"jpg") RequestBody file
            // @PartMap Map<String, RequestBody> params,
    );

    /**
     * 发布新帖子
     *
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
    Observable<Result> post_BBSPost(
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
    Observable<Result> post_AddComment(
            @Field("auth") String auth,
            @Field("pid") String pid,
            @Field("conts") String conts);

    /**
     * 删除帖子接口
     *
     * @param auth 认证信息
     * @param id   帖子id
     * @return 结果meg
     */
    @FormUrlEncoded
    @POST("bbs/del")
    Observable<Result> post_DeleteBbs(
            @Field("auth") String auth,
            @Field("id") String id);

    /**
     * 删除帖子评论接口
     *
     * @param auth 认证信息
     * @param id   评论id
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("bbs/delcom")
    Observable<Result> post_DeleteComment(
            @Field("auth") String auth,
            @Field("id") String id);

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
    Observable<BBSList> get_BBSList(
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
    Observable<BbsCommentList> get_BbsCommentList(
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
    Observable<ZanList> get_ZanList(
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
     * 举报帖子接口
     *
     * @param auth  认证信息
     * @param bid   帖子id,必填
     * @param conts 举报原因，选填
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("bbs/report")
    Observable<Result> post_Report(
            @Field("auth") String auth,
            @Field("bid") String bid,
            @Field("conts") String conts);

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
    Observable<FriendDetail> get_FriendDetail(
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
    Observable<Result> post_RemarkName(
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
    Observable<BBSList> get_FriendBbsList(
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
    Observable<Result> post_sendMessage(
            @Field("from") String from,
            @Field("to") String to,
            @Field("ct") String ct,
            @Field("app") String app,
            @Field("txt") String txt,
            @Field("source") Array source,
            @Field("ex") String ex,
            @Field("mt") int mt,
            @Field("xt") String xt);

    /**
     * 用户获取消息接口
     *
     * @param me  接收人id
     * @param app 需要什么app的消息
     * @param os  1安卓，2苹果，3winphone，4  web
     * @return MessageList
     */
    @GET("http://push.traimo.com/msg/lists")
    Observable<MessageList> get_MessageList(
            @Query("me") String me,
            @Query("app") String app,
            @Query("os") int os);

    /**
     * 注册用户（当客户端与推送服务器建立连接后调用）
     *
     * @param me  当前的eid
     * @param os  1安卓，2苹果，3winphone，4  web
     * @param app 当前应用，yxj
     * @param cid 第三方连接id
     * @return 结果msg
     */
    @GET("http://push.traimo.com/client/register")
    Observable<Result> getObservable_RegisterChat(
            @Query("me") String me,
            @Query("os") int os,
            @Query("app") String app,
            @Query("cid") String cid);

    /**
     * 申请站长接口
     *
     * @param auth     认证信息
     * @param vid      村id
     * @param uname    姓名
     * @param contact  手机号码
     * @param conts    申请理由
     * @param sex      性别 0:男 1:女
     * @param edu      教育程度
     * @param cid_img1 身份证正面照ID :先通过上传接口将图片上传，这里的参数传返回的ID
     * @param cid_img2 身份证背面照ID: 先通过上传接口将图片上传，这里的参数传返回的ID
     * @param q_img    其他材料:注意，这里将所有其他材料上传后组合成字符串，使用”,”分割，如：878,879,880
     * @param brithday 生日:字符串格式
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("vill/applymaster")
    Observable<Result> post_ApplyMaster(
            @Field("auth") String auth,
            @Field("vid") String vid,
            @Field("uname") String uname,
            @Field("contact") String contact,
            @Field("conts") String conts,
            @Field("sex") int sex,
            @Field("edu") String edu,
            @Field("cid_img1") int cid_img1,
            @Field("cid_img2") int cid_img2,
            @Field("q_img") int q_img,
            @Field("brithday") String brithday);

    /**
     * 查询申请站长状态
     *
     * @param auth 认证信息
     * @param vid  村id
     * @return  申请人的信息
     */
    @GET("vill/applystatus")
    Observable<ApplyInfo> get_IsApply(
            @Query("auth") String auth,
            @Query("vid") String vid);

    /**
     * 是否设置了交易密码
     *
     * @param auth 认证信息
     * @return is_pwd
     */
    @GET("amount/is_set_pwd")
    Observable<Result> get_IsSetPWD(
            @Query("auth") String auth);

    /**
     * 设置交易密码
     *
     * @param auth 认证信息
     * @param pwd  交易密码
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("amount/set_pwd")
    Observable<Result> post_SetPursePWD(
            @Field("auth") String auth,
            @Field("pwd") String pwd);

    /**
     * 重置交易密码
     *
     * @param auth    认证信息
     * @param old_pwd 原始交易密码
     * @param new_pwd 新交易密码
     * @return 结果msg
     */
    @FormUrlEncoded
    @POST("amount/reset_pwd")
    Observable<Result> post_ResetPursePWD(
            @Field("auth") String auth,
            @Field("old_pwd") String old_pwd,
            @Field("new_pwd") String new_pwd);
}
