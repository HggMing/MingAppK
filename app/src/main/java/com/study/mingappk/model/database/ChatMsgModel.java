package com.study.mingappk.model.database;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.lang.reflect.Array;

/**
 * 聊天信息
 * Created by Ming on 2016/5/6.
 */
@Table("chat_message")
public class ChatMsgModel extends BaseModel {
    public static final int ITEM_TYPE_LEFT = 1;
    public static final int ITEM_TYPE_RIGHT = 0;

    // 设置为主键,自增
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    // 取名为“_id”,如果此处不重新命名,就采用属性名称
    @Column("_id")
    private int id;

    //发送消息
    private Array source;//发送的资源，默认空数组，如果有资源则是数据流base64后的数据+’.’+资源的扩展名
    private int mt;//发送方式:1即时消息，2异步消息

    public void setIsShowPro(int isShowPro) {
        this.isShowPro = isShowPro;
    }

    public int getIsShowPro() {
        return isShowPro;
    }

    private int isShowPro;//是否显示错误提示：2显示 other 不显示
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

    //当ct=100，来自分享的消息
    private String share_title;//分享内容的标题
    private String share_detail;//分享内容详情
    private String share_image;//分享内容的图片地址
    private String share_link;//分享内容的链接，例：http://121.40.105.149:9901/bbs/bbsinfo?id=4174

    public void setShareMsg(String share_title,String share_detail,String share_image,String share_link){
        this.share_title=share_title;
        this.share_detail=share_detail;
        this.share_image=share_image;
        this.share_link=share_link;
    }


    public ChatMsgModel() {
        this.isShowPro = 0;
        this.mt = 1;//即时消息
        this.xt = "2";//用户之间
        this.app = "yxj";//app固定
    }

    public ChatMsgModel(int type, String from, String to, String st, String ct, String txt, String link) {
        this();
        this.type = type;
        this.from = from;
        this.to = to;
        this.st = st;
        this.ct = ct;
        this.txt = txt;
        this.link = link;
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

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_detail() {
        return share_detail;
    }

    public void setShare_detail(String share_detail) {
        this.share_detail = share_detail;
    }

    public String getShare_image() {
        return share_image;
    }

    public void setShare_image(String share_image) {
        this.share_image = share_image;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }


}
