package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/11/14.
 */

public class ProductNewOrder {
    /**
     * err : 0
     * data : {"no":"s20161114489797545410","tol":0.01,"url":"http://product.yibanke.com/alipay/n_url"}
     */

    private int err;
    /**
     * no : s20161114489797545410
     * tol : 0.01
     * url : http://product.yibanke.com/alipay/n_url
     */

    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String no;
        private double tol;
        private String url;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public double getTol() {
            return tol;
        }

        public void setTol(double tol) {
            this.tol = tol;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
