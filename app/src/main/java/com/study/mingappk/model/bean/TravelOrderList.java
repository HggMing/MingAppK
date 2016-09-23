package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/9/23.
 */
public class TravelOrderList {
    /**
     * cnt : 3
     * list : [{"travel_order_id":"3","travel_product_main_id":"1","travel_product_detail_id":"4","vid":"510107007006","if_adult":"0","name":"1212","birthday":"2016-09-01","credentials_type":"0","credentials_no":"31313","sex":"0","phone":"1313","pay_status":"2","ptime":"2016-09-01 00:00:00","stime":"0","other_sn":"2016090121001004630284038632","order_sn":"s124","price":"199.00","create_time":"2016-08-31 00:00:00","money_paid":"0.00","memos":"合并支付：s123,s124","status":"0","travel_product_main":{"travel_product_main_id":"1","small_image":"/Public/travel/image/product1.jpg","introduction_url":"/travel/product/product1_introduction","status":"0","type":"5","provinceid":"510","cityid":"0","countyid":"0","townid":"0","vid":"0","create_time":"2016-08-26 00:00:00","title":"成都包机直飞【芽庄】5晚6日半自由行","introduction":"成都包机直飞【芽庄】5晚6日半自由行"},"travel_product_detail":{"travel_product_detail_id":"4","travel_product_main_id":"1","start_date":"2016-09-01","status":"0","adult_price":"199.00","create_time":"2016-08-29 00:00:00","child_price":"100.00","enrol_end_date":"2016-08-30","price_type":null,"add_bed":null}}]
     * url : http://121.40.105.149:9901/alipay/n_url_travel
     */

    private String cnt;
    private String url;
    /**
     * travel_order_id : 3
     * travel_product_main_id : 1
     * travel_product_detail_id : 4
     * vid : 510107007006
     * if_adult : 0
     * name : 1212
     * birthday : 2016-09-01
     * credentials_type : 0
     * credentials_no : 31313
     * sex : 0
     * phone : 1313
     * pay_status : 2
     * ptime : 2016-09-01 00:00:00
     * stime : 0
     * other_sn : 2016090121001004630284038632
     * order_sn : s124
     * price : 199.00
     * create_time : 2016-08-31 00:00:00
     * money_paid : 0.00
     * memos : 合并支付：s123,s124
     * status : 0
     * travel_product_main : {"travel_product_main_id":"1","small_image":"/Public/travel/image/product1.jpg","introduction_url":"/travel/product/product1_introduction","status":"0","type":"5","provinceid":"510","cityid":"0","countyid":"0","townid":"0","vid":"0","create_time":"2016-08-26 00:00:00","title":"成都包机直飞【芽庄】5晚6日半自由行","introduction":"成都包机直飞【芽庄】5晚6日半自由行"}
     * travel_product_detail : {"travel_product_detail_id":"4","travel_product_main_id":"1","start_date":"2016-09-01","status":"0","adult_price":"199.00","create_time":"2016-08-29 00:00:00","child_price":"100.00","enrol_end_date":"2016-08-30","price_type":null,"add_bed":null}
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
        private String travel_order_id;
        private String travel_product_main_id;
        private String travel_product_detail_id;
        private String vid;
        private String if_adult;
        private String name;
        private String birthday;
        private String credentials_type;
        private String credentials_no;
        private String sex;
        private String phone;
        private String pay_status;
        private String ptime;
        private String stime;
        private String other_sn;
        private String order_sn;
        private String price;
        private String create_time;
        private String money_paid;
        private String memos;
        private String status;
        /**
         * travel_product_main_id : 1
         * small_image : /Public/travel/image/product1.jpg
         * introduction_url : /travel/product/product1_introduction
         * status : 0
         * type : 5
         * provinceid : 510
         * cityid : 0
         * countyid : 0
         * townid : 0
         * vid : 0
         * create_time : 2016-08-26 00:00:00
         * title : 成都包机直飞【芽庄】5晚6日半自由行
         * introduction : 成都包机直飞【芽庄】5晚6日半自由行
         */

        private TravelProductMainBean travel_product_main;
        /**
         * travel_product_detail_id : 4
         * travel_product_main_id : 1
         * start_date : 2016-09-01
         * status : 0
         * adult_price : 199.00
         * create_time : 2016-08-29 00:00:00
         * child_price : 100.00
         * enrol_end_date : 2016-08-30
         * price_type : null
         * add_bed : null
         */

        private TravelProductDetailBean travel_product_detail;

        public String getTravel_order_id() {
            return travel_order_id;
        }

        public void setTravel_order_id(String travel_order_id) {
            this.travel_order_id = travel_order_id;
        }

        public String getTravel_product_main_id() {
            return travel_product_main_id;
        }

        public void setTravel_product_main_id(String travel_product_main_id) {
            this.travel_product_main_id = travel_product_main_id;
        }

        public String getTravel_product_detail_id() {
            return travel_product_detail_id;
        }

        public void setTravel_product_detail_id(String travel_product_detail_id) {
            this.travel_product_detail_id = travel_product_detail_id;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getIf_adult() {
            return if_adult;
        }

        public void setIf_adult(String if_adult) {
            this.if_adult = if_adult;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCredentials_type() {
            return credentials_type;
        }

        public void setCredentials_type(String credentials_type) {
            this.credentials_type = credentials_type;
        }

        public String getCredentials_no() {
            return credentials_no;
        }

        public void setCredentials_no(String credentials_no) {
            this.credentials_no = credentials_no;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getMoney_paid() {
            return money_paid;
        }

        public void setMoney_paid(String money_paid) {
            this.money_paid = money_paid;
        }

        public String getMemos() {
            return memos;
        }

        public void setMemos(String memos) {
            this.memos = memos;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public TravelProductMainBean getTravel_product_main() {
            return travel_product_main;
        }

        public void setTravel_product_main(TravelProductMainBean travel_product_main) {
            this.travel_product_main = travel_product_main;
        }

        public TravelProductDetailBean getTravel_product_detail() {
            return travel_product_detail;
        }

        public void setTravel_product_detail(TravelProductDetailBean travel_product_detail) {
            this.travel_product_detail = travel_product_detail;
        }

        public static class TravelProductMainBean {
            private String travel_product_main_id;
            private String small_image;
            private String introduction_url;
            private String status;
            private String type;
            private String provinceid;
            private String cityid;
            private String countyid;
            private String townid;
            private String vid;
            private String create_time;
            private String title;
            private String introduction;

            public String getTravel_product_main_id() {
                return travel_product_main_id;
            }

            public void setTravel_product_main_id(String travel_product_main_id) {
                this.travel_product_main_id = travel_product_main_id;
            }

            public String getSmall_image() {
                return small_image;
            }

            public void setSmall_image(String small_image) {
                this.small_image = small_image;
            }

            public String getIntroduction_url() {
                return introduction_url;
            }

            public void setIntroduction_url(String introduction_url) {
                this.introduction_url = introduction_url;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getProvinceid() {
                return provinceid;
            }

            public void setProvinceid(String provinceid) {
                this.provinceid = provinceid;
            }

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public String getCountyid() {
                return countyid;
            }

            public void setCountyid(String countyid) {
                this.countyid = countyid;
            }

            public String getTownid() {
                return townid;
            }

            public void setTownid(String townid) {
                this.townid = townid;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }

        public static class TravelProductDetailBean {
            private String travel_product_detail_id;
            private String travel_product_main_id;
            private String start_date;
            private String status;
            private String adult_price;
            private String create_time;
            private String child_price;
            private String enrol_end_date;
            private Object price_type;
            private Object add_bed;

            public String getTravel_product_detail_id() {
                return travel_product_detail_id;
            }

            public void setTravel_product_detail_id(String travel_product_detail_id) {
                this.travel_product_detail_id = travel_product_detail_id;
            }

            public String getTravel_product_main_id() {
                return travel_product_main_id;
            }

            public void setTravel_product_main_id(String travel_product_main_id) {
                this.travel_product_main_id = travel_product_main_id;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAdult_price() {
                return adult_price;
            }

            public void setAdult_price(String adult_price) {
                this.adult_price = adult_price;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getChild_price() {
                return child_price;
            }

            public void setChild_price(String child_price) {
                this.child_price = child_price;
            }

            public String getEnrol_end_date() {
                return enrol_end_date;
            }

            public void setEnrol_end_date(String enrol_end_date) {
                this.enrol_end_date = enrol_end_date;
            }

            public Object getPrice_type() {
                return price_type;
            }

            public void setPrice_type(Object price_type) {
                this.price_type = price_type;
            }

            public Object getAdd_bed() {
                return add_bed;
            }

            public void setAdd_bed(Object add_bed) {
                this.add_bed = add_bed;
            }
        }
    }
}
