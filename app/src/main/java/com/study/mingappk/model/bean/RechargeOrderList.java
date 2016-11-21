package com.study.mingappk.model.bean;

import java.util.List;

/**
 * 话费充值订单列表
 * Created by Ming on 2016/9/22.
 */
public class RechargeOrderList {

    /**
     * err : 0
     * data : {"cnt":"14","list":[{"id":"123214564","phone":"18410261029","amount":"10M","status":"1","msg":"0000","vid":"510107007006","ctime":"1473834743","paytime":"1473834763","rtype":"1","pay_status":"1","money_paid":"3","uid":"0","phoneinfo":"移动","code":"QGYA10","money":"3","callback":"{\"OrderCode\":\"201609140002205305\",\"Account\":\"18410261029\",\"Status\":\"ERROR\",\"Remark\":\"\\u5145\\u503c\\u5931\\u8d25\",\"Price\":\"2.7\",\"Balance\":\"178.46\"}","add_money":"0","other_sn":"2016091421001004530291753887","tk_time":"0","is_show":"0","billid":""}],"url":"http://product.yibanke.com/alipay/phone_url"}
     */

    private int err;
    /**
     * cnt : 14
     * list : [{"id":"123214564","phone":"18410261029","amount":"10M","status":"1","msg":"0000","vid":"510107007006","ctime":"1473834743","paytime":"1473834763","rtype":"1","pay_status":"1","money_paid":"3","uid":"0","phoneinfo":"移动","code":"QGYA10","money":"3","callback":"{\"OrderCode\":\"201609140002205305\",\"Account\":\"18410261029\",\"Status\":\"ERROR\",\"Remark\":\"\\u5145\\u503c\\u5931\\u8d25\",\"Price\":\"2.7\",\"Balance\":\"178.46\"}","add_money":"0","other_sn":"2016091421001004530291753887","tk_time":"0","is_show":"0","billid":""}]
     * url : http://product.yibanke.com/alipay/phone_url
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
        private String cnt;
        private String url;
        /**
         * id : 123214564
         * phone : 18410261029
         * amount : 10M
         * status : 1
         * msg : 0000
         * vid : 510107007006
         * ctime : 1473834743
         * paytime : 1473834763
         * rtype : 1
         * pay_status : 1
         * money_paid : 3
         * uid : 0
         * phoneinfo : 移动
         * code : QGYA10
         * money : 3
         * callback : {"OrderCode":"201609140002205305","Account":"18410261029","Status":"ERROR","Remark":"\u5145\u503c\u5931\u8d25","Price":"2.7","Balance":"178.46"}
         * add_money : 0
         * other_sn : 2016091421001004530291753887
         * tk_time : 0
         * is_show : 0
         * billid :
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
            private String phone;
            private String amount;
            private String status;
            private String msg;
            private String vid;
            private String ctime;
            private String paytime;
            private String rtype;
            private String pay_status;
            private String money_paid;
            private String uid;
            private String phoneinfo;
            private String code;
            private String money;
            private String callback;
            private String add_money;
            private String other_sn;
            private String tk_time;
            private String is_show;
            private String billid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getPaytime() {
                return paytime;
            }

            public void setPaytime(String paytime) {
                this.paytime = paytime;
            }

            public String getRtype() {
                return rtype;
            }

            public void setRtype(String rtype) {
                this.rtype = rtype;
            }

            public String getPay_status() {
                return pay_status;
            }

            public void setPay_status(String pay_status) {
                this.pay_status = pay_status;
            }

            public String getMoney_paid() {
                return money_paid;
            }

            public void setMoney_paid(String money_paid) {
                this.money_paid = money_paid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getPhoneinfo() {
                return phoneinfo;
            }

            public void setPhoneinfo(String phoneinfo) {
                this.phoneinfo = phoneinfo;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getCallback() {
                return callback;
            }

            public void setCallback(String callback) {
                this.callback = callback;
            }

            public String getAdd_money() {
                return add_money;
            }

            public void setAdd_money(String add_money) {
                this.add_money = add_money;
            }

            public String getOther_sn() {
                return other_sn;
            }

            public void setOther_sn(String other_sn) {
                this.other_sn = other_sn;
            }

            public String getTk_time() {
                return tk_time;
            }

            public void setTk_time(String tk_time) {
                this.tk_time = tk_time;
            }

            public String getIs_show() {
                return is_show;
            }

            public void setIs_show(String is_show) {
                this.is_show = is_show;
            }

            public String getBillid() {
                return billid;
            }

            public void setBillid(String billid) {
                this.billid = billid;
            }
        }
    }
}
