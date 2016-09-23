package com.study.mingappk.model.bean;

import java.util.List;

/**
 * 汽车保险订单列表
 * Created by Ming on 2016/9/23.
 */
public class InsuranceOrderList {

    /**
     * cnt : 26
     * list : [{"id":"65","vid":"510107007006","c_addr":"四川省井研县镇阳乡石龙村5组3号","c_cid":"511124199709246613","c_name":"张桂滔","c_phone":"1556","c_number":"川5","c_no":"555","money_paid":"0.01","price":"0.01","ctime":"1473839216","status":"0","pay_status":"2","ptime":"1473839332","stime":"0","htime":"0","phone":"18200390595","other_sn":"2016091421001004620248473875","order_sn":"s20160914485597574854"}]
     * url : http://121.40.105.149:9901/alipay/n_url_baoxian
     */

    private String cnt;
    private String url;
    /**
     * id : 65
     * vid : 510107007006
     * c_addr : 四川省井研县镇阳乡石龙村5组3号
     * c_cid : 511124199709246613
     * c_name : 张桂滔
     * c_phone : 1556
     * c_number : 川5
     * c_no : 555
     * money_paid : 0.01
     * price : 0.01
     * ctime : 1473839216
     * status : 0
     * pay_status : 2
     * ptime : 1473839332
     * stime : 0
     * htime : 0
     * phone : 18200390595
     * other_sn : 2016091421001004620248473875
     * order_sn : s20160914485597574854
     */

    private List<ListBean> list;

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String id;
        private String vid;
        private String c_addr;
        private String c_cid;
        private String c_name;
        private String c_phone;
        private String c_number;
        private String c_no;
        private String money_paid;
        private String price;
        private String ctime;
        private String status;
        private String pay_status;
        private String ptime;
        private String stime;
        private String htime;
        private String phone;
        private String other_sn;
        private String order_sn;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getC_addr() {
            return c_addr;
        }

        public void setC_addr(String c_addr) {
            this.c_addr = c_addr;
        }

        public String getC_cid() {
            return c_cid;
        }

        public void setC_cid(String c_cid) {
            this.c_cid = c_cid;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getC_phone() {
            return c_phone;
        }

        public void setC_phone(String c_phone) {
            this.c_phone = c_phone;
        }

        public String getC_number() {
            return c_number;
        }

        public void setC_number(String c_number) {
            this.c_number = c_number;
        }

        public String getC_no() {
            return c_no;
        }

        public void setC_no(String c_no) {
            this.c_no = c_no;
        }

        public String getMoney_paid() {
            return money_paid;
        }

        public void setMoney_paid(String money_paid) {
            this.money_paid = money_paid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getHtime() {
            return htime;
        }

        public void setHtime(String htime) {
            this.htime = htime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOther_sn() {
            return other_sn;
        }

        public void setOther_sn(String other_sn) {
            this.other_sn = other_sn;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }
    }
}
