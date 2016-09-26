package com.study.mingappk.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.study.mingappk.R;

public class APP extends Application implements ThemeUtils.switchColor {
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
    public static final String FRIEND_LIST_UID = "friend_list_uid";//好友uid，用于判定是否为好友。
    public static String APPLY_INFO = "apply_info_";//储存申请店长人的信息
    public static String APPLY_INFO_VID = "apply_vid_";//储存申请店长,管理的村

    public static final String SELECTED_CARD = "selected_card";//存储用户的默认提现银行卡，注销登录时须清空。
    public static final String MANAGER_ADDRESS = "village_manager_address";//存储店长用户的村店地址，注销登录时须清空。
    public static final String MANAGER_VID="manager_village_id";//存储店长用户的村店地址，注销登录时须清空。



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
        //
        ThemeUtils.setSwitchColor(this);
        //用于存储
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)//普通加密模式
                .setStorage(HawkBuilder.newSharedPrefStorage(this))//储存方式，sp或sqlite，这里设置sp
                .setLogLevel(LogLevel.FULL)//设置日志
                .build();
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme != null) {
            colorId = getThemeColorId(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);
    }

    public String getTheme(Context context) {
        String[] themeColors=getResources().getStringArray(R.array.theme_colors);
        if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_02) {
            return themeColors[1];
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_03) {
            return themeColors[2];
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_04) {
            return themeColors[3];
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_05) {
            return themeColors[4];
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_06) {
            return themeColors[5];
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_07) {
            return themeColors[6];
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_08) {
            return themeColors[7];
        }
        return themeColors[0];
    }

    private
    @ColorRes
    int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.theme_color_primary_trans:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return colorId;
    }

    @Override
    public int replaceColor(Context context, @ColorInt int color) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return color;
        }
        String theme = getTheme(context);
        int colorId = -1;

        if (theme != null) {
            colorId = getThemeColor(context, color, theme);
        }
        return colorId != -1 ? getResources().getColor(colorId) : color;
    }

    private
    @ColorRes
    int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xff009688:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case 0x00796B:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case 0x99049184:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return -1;
    }
}
