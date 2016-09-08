package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/8/26.
 */
public class IpPort {
    /**
     * ip : 192.168.10.1
     * port : 2060
     * v : 20
     * t : 3
     * mac : a4:db:30:6a:7f:92
     */

    private String ip;
    private String port;
    private String v;
    private String t;
    private String mac;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
