package com.study.mingappk.model.database;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.app.APP;

import java.lang.reflect.Array;

/**
 * Created by Ming on 2016/5/6.
 */
@Table("chat_message")
public class ChatMsgModel extends BaseModel {
    public static final int ITEM_TYPE_LEFT = 1;
    public static final int ITEM_TYPE_RIGHT = 0;
    //发送消息
    private Array source;//发送的资源，默认空数组，如果有资源则是数据流base64后的数据+’.’+资源的扩展名
    private int mt;//发送方式:1即时消息，2异步消息

    public void setIsShowPro(int isShowPro) {
        this.isShowPro = isShowPro;
    }

    public int getIsShowPro() {
        return isShowPro;
    }

    private int isShowPro;//A是否显示进度条：1显示 other不显示  B是否显示错误提示：2显示 other 不显示，即成功发送为0
    //接收消息
    private String st;//消息发送发出时间***
    private String link;//资源url地址***
    //通用
    @Column("_from")
    private String from;//发送人id***
    @Column("_to")
    private String to;//接收人id
    @Column("_app")
    private String app;//发送消息的app
    private String ct;//消息类型，0文字，1图片，2声音，3html，4内部消息json格式，5交互消息 6应用透传消息json格式,7朋友系统消息json***
    @Column("_txt")
    private String txt;//消息内容
    private String ex;//扩展字段， 根据不同应用定义不同的意义
    private String xt;//发送人类型 0系统，2用户与用户，1公众号与用户
    //增加属性
    @Column("_type")
    private int type;//0:发送消息1：接收消息

    public ChatMsgModel() {
        this.isShowPro=1;//显示发送消息进度条
        this.mt=1;//即时消息
        this.xt="2";//用户之间
        this.app="yxj";//app固定
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public Array getSource() {
        return source;
    }

    public void setSource(Array source) {
        this.source = source;
    }

    public int getMt() {
        return mt;
    }

    public void setMt(int mt) {
        this.mt = mt;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getXt() {
        return xt;
    }

    public void setXt(String xt) {
        this.xt = xt;
    }

}
