package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/5/4.
 */
public class MessageList {


    /**
     * l : [{"from":"12018","to":"13114","app":"yxj","ct":"2","txt":"声音","st":"1462526424","link":"20160506/2/amr/2df31b37378da81df4cf57223e3372dc.amr","ex":"0","xt":"2"},{"from":"12018","to":"13114","app":"yxj","ct":"1","txt":"图片","st":"1462526414","link":"20160506/1/jpg/97d26730c7cf44062cd85ff629050507.jpg","ex":"0","xt":"2"},{"from":"12018","to":"13114","app":"yxj","ct":"0","txt":"文字1111","st":"1462526397","link":"","ex":"0","xt":"2"}]
     * err : 0
     * r : http://push.traimo.com/source/
     */

    private int err;
    private String r;
    /**
     * from : 12018
     * to : 13114
     * app : yxj
     * ct : 2
     * txt : 声音
     * st : 1462526424
     * link : 20160506/2/amr/2df31b37378da81df4cf57223e3372dc.amr
     * ex : 0
     * xt : 2
     */

    private List<LBean> l;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public List<LBean> getL() {
        return l;
    }

    public void setL(List<LBean> l) {
        this.l = l;
    }

    public static class LBean {
        private String from;
        private String to;
        private String app;
        private String ct;
        private String txt;
        private String st;
        private String link;
        private String ex;
        private String xt;

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
}
