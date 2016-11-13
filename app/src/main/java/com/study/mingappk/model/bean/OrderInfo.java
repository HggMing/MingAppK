package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/11/1.
 */

public class OrderInfo {
    /**
     * err : 0
     * msg : 订单不存在
     * data : {"money":0.01,"order_sn":"s20161024985197981025","order_title":"回乡月饼","url":"http://121.40.105.149:9901/alipay/n_url","url_1":"http://121.40.105.149:9901/mobile"}
     */

    private int err;
    private String msg;
    /**
     * money : 0.01
     * order_sn : s20161024985197981025
     * order_title : 回乡月饼
     * url : http://121.40.105.149:9901/alipay/n_url
     * url_1 : http://121.40.105.149:9901/mobile
     */

    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private double money;
        private String order_sn;
        private String order_title;
        private String url;
        private String url_1;

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getOrder_title() {
            return order_title;
        }

        public void setOrder_title(String order_title) {
            this.order_title = order_title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl_1() {
            return url_1;
        }

        public void setUrl_1(String url_1) {
            this.url_1 = url_1;
        }
    }
}
