package com.study.mingappk.app;

import android.app.Application;
import android.os.Environment;

import com.jude.utils.JUtils;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.squareup.otto.Bus;

public class APP extends Application {
    //存储目录路径
    public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/MingAppk/";
    //SharedPreferences相关参数
    public static final String LOGIN_NAME = "login_name";//登录名
    public static final String LOGIN_PASSWORD = "login_password";//登录密码
    public static final String IS_REMEMBER_PASSWORD = "is_remember_password";//是否记住密码
    public static final String IS_FIRST_RUN = "is_first_run";//是否首次运行
    public static final String IS_UPDATA_MY_INFO = "is_updata_myInfo";//是否更新信息


    public static final String ME_UID = "me_id";//登录用户的uid
    public static final String ME_HEAD = "me_head";//登录用户的头像
    public static final String USER_AUTH = "user_auth";//用户认证信息
    public static final String IS_SHOP_OWNER = "is_shop_owner";//是否为店长，1是0不是
    public static final String IS_REAL_NAME = "is_real_name_binging";//是否实名认证。
    public static final String FRIEND_LIST_UID="friend_list_uid";//好友uid，用于判定是否为好友。


    /**
     * 单例模式中获取唯一的Application实例
     */
    private static APP instance;

    public static APP getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JUtils.initialize(this);
        JUtils.setDebug(true, "mm");
        //用于存储
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)//普通加密模式
                .setStorage(HawkBuilder.newSharedPrefStorage(this))//储存方式，sp或sqlite，这里设置sp
                .setLogLevel(LogLevel.FULL)//设置日志
                .build();
    }
}
