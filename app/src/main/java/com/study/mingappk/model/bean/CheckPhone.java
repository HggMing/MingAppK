package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/5/19.
 */
public class CheckPhone {

    /**
     * err : 0
     * sign : Ack8x9qyp8t7usmMAfOZbf1+RYSUo1C76zGdzVGa5/KAwm/30SLusw==
     * msg : 该手机号未注册,可以使用
     * info : {"uid":"13114"}
     */

    private int err;
    private String sign;
    private String msg;
    /**
     * uid : 13114
     */

    private InfoBean info;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
