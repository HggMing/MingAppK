package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/8/26.
 */
public class EbankWifiConnect {
    /**
     * status : 1
     * ret : 0
     * txt :
     * name :
     * mac : a4:db:30:6a:7f:92
     * des : 获取到MAC地址
     */

    private String status;
    private String ret;
    private String txt;
    private String name;
    private String mac;
    private String des;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
