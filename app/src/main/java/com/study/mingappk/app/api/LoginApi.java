package com.study.mingappk.app.api;

import com.study.mingappk.app.api.service.MyService;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.model.bean.Login;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ming on 2016/10/25.
 */

public class LoginApi {
    private static MyService myService = MyServiceClient.getService();

    /**
     * 登录
     *
     * @param loginName 用户名
     * @param pwd       密码
     * @return k
     */
    public static Observable<Login> login(String loginName, String pwd) {
        return myService.get_Login(loginName, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 实名认证接口
     *
     * @param data      用户数据加密字段
     * @param faceImage 正面人脸照片
     * @param idImage   身份证正面照片
     * @return k
     */
    public static Observable<ResponseBody> faceRealBinding(String data, File faceImage, File idImage) {
        RequestBody rb_data = RequestBody.create(MediaType.parse("text/plain"), data);
        RequestBody rb_faceImage = RequestBody.create(MediaType.parse("image/*"), faceImage);
        RequestBody rb_idImage = RequestBody.create(MediaType.parse("image/*"), idImage);
        return MyServiceClient.getService()
                .post_FaceRealBinding(rb_data, rb_faceImage, rb_idImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
