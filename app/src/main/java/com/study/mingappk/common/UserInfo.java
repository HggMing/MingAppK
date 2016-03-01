package com.study.mingappk.common;


import java.io.Serializable;

/*任何类型只要实现了Serializable接口，就可以被保存到文件中，或者作为数据流通过网络发送到别的地方。也可以用管道来传输到系统的其他程序中。*/
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 4474518889858658131L;
    public int uid;
    public String phone;//登录帐号，为手机号
    public String logname;
    public int sex;//1男 2女
    public String uname;//昵称
    public String birth;//生日

    public String ctime;
    public String cid;//卡号
    public String loc;
    public String lastlog;
    public String lastip;
    public String lastdev;

    //	public String star;//是否验证手机，是否验证人脸，是否权威认证（专用终端），是否指纹认证，是否声纹认证，是否证件认证
    public String headpic;//头像照片
    public String auth;
//	public int bdyhk;//0未绑定 1绑定
//	public String bid;//网店
//	public String navti;//民族
//	public String addr;//地址
//	public String face;//照片
//	public String st;//办证时间
//	public String et;//身份证有效时间
//  public String is_public;//是否是公众号 0个人号  1公众号
}
