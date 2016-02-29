package com.study.mingappk.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.study.mingappk.activity.BaseActivity;
import com.study.mingappk.model.MessageInfo;
import com.study.mingappk.model.UserInfo;


import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

public class MyApplication extends Application {
    private static MyApplication instance;

    private List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 单例模式中获取唯一的Application实例
     */

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    /**
     * 添加Activity到容器中
     *
     * @param activity 传入当前activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有Activity并finish,退出软件
     */
    public void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }

        try {
            System.exit(0);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private UserInfo userInfo;//用户信息序列

    public void set_userInfo(UserInfo model) {
        this.userInfo = model;
    }

    public UserInfo get_userInfo() {
        return this.userInfo;
    }

    private String routerMac; // 路由器mac

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    private String bname; // 网点名称

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    private Vector AppInfoVec;

    public void set_AppInfoVec(Vector appVec) {
        this.AppInfoVec = appVec;
    }

    public Vector get_AppInfoVec() {
        return AppInfoVec;
    }

    private String net;// 为Web打开的页面

    public void set_Net(String net) {
        this.net = net;
    }

    public String get_Net() {
        if (net == null) {
            return "";
        }
        return net;
    }

    private String push;// 为push地址

    public void set_Push(String push) {
        this.push = push;
    }

    public String get_Push() {
        if (push == null) {
            return "";
        }
        return push;
    }

    private String pop;// 弹出广告页图片地址

    public void Set_pop(String popString) {
        this.pop = popString;
    }

    public String get_pop() {
        if (pop == null) {
            return "";
        }
        return pop;
    }

    private Boolean isResetPasswordPageClose;// 是否关闭的 重设密码页面 用来判断是否打开密码重设

    public void setIsResetPasswordPage(Boolean isbooBoolean) {
        isResetPasswordPageClose = isbooBoolean;
    }

    public Boolean getIsResetPasswordPage() {
        return isResetPasswordPageClose;
    }

    private Boolean isClickHomeKey = false;// 是否按Home 键

    public void setisClickHomeKey(Boolean isbooBoolean) {
        isClickHomeKey = isbooBoolean;
    }

    public Boolean getisClickHomeKey() {
        return isClickHomeKey;
    }

    private Boolean isCloseJGG = false;// 是否关闭九宫格页面

    public void setisisCloseJGG(Boolean isbooBoolean) {
        isCloseJGG = isbooBoolean;
    }

    public Boolean getisisCloseJGG() {
        return isCloseJGG;
    }

    private String ClientID;

    public void set_ClientID(String clientid) {
        this.ClientID = clientid;
    }

    public String get_ClientID() {
        return this.ClientID;
    }

    private boolean isInMsgActivity;

    public void set_isInMsgActivity(boolean bool) {
        this.isInMsgActivity = bool;
    }

    public boolean get_isInMsgActivity() {
        return this.isInMsgActivity;
    }

    private BaseActivity mCurrentActivity = null;


    public BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }
    /**
     * 设置Activity_Base的一个实例
     * @param mCurrentActivity
     */
    public void setCurrentActivity(BaseActivity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }


    private String Rmac;

    public void set_Rmac(String rmac) {
        this.Rmac = rmac;
    }

    public String get_Rmac() {
        return this.Rmac;
    }


    private String Bid = "1";

    public void set_Bid(String Bid) {
        this.Bid = Bid;
    }

    public String get_Bid() {
        return this.Bid;
    }


    private String ChooseBid = "1";

    public void set_ChooseBid(String Bid) {
        this.ChooseBid = Bid;
    }

    public String get_ChooseBid() {
        return this.ChooseBid;
    }


    private Vector<MessageInfo> newmsgVector;

    public void set_NewMsg(Vector<MessageInfo> newmsgVector) {
        this.newmsgVector = newmsgVector;
    }

    public Vector<MessageInfo> get_NewMsg() {
        return this.newmsgVector;
    }


    private String FunctionNet;// 为Web功能打开的页面

    public void set_FunctionNet(String net) {
        this.FunctionNet = net;
    }

    public String get_FunctionNet() {
        if (FunctionNet == null) {
            return "";
        }
        return FunctionNet;
    }


    private String ConsultPhone;// 咨询电话

    public void set_ConsultPhone(String net) {
        this.ConsultPhone = net;
    }

    public String get_ConsultPhone() {
        if (ConsultPhone == null) {
            return "";
        }
        return ConsultPhone;
    }


    private double lontitude = 0;

    public void set_Lontitude(double lontitude) {
        this.lontitude = lontitude;
    }

    public double get_Lontitude() {
        if (lontitude == 0) {
            return 0;
        }
        return lontitude;
    }


    private double latitude = 0;

    public void set_Latitude(double latitude) {
        this.latitude = latitude;
    }

    public double get_Latitude() {
        if (latitude == 0) {
            return 0;
        }
        return latitude;
    }
}
