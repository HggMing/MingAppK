package com.study.mingappk.model.bean;

import java.util.List;

/**
 * 村实惠订单列表
 * Created by Ming on 2016/9/23.
 */
public class SalesOrderList {

    /**
     * cnt : 23
     * list : [{"id":"1065","order_sn":"s20160922571025150521","uid":"10","uname":"逗比","phone":"18200390595","buy_uname":"李焓","address":"四川省 成都市 高新区天仁路387号大鼎世纪广场3栋4层","tel":"18628155109","order_status":"0","pay_status":"2","shipping_id":"0","shipping_name":"","pay_type_id":"2","shipping_fee":"0.00","money_paid":"29.70","order_amount":"29.70","products":"伍田牛油火锅 成都倾城之味 醇麻柔和 香浓味厚 精选调味 3袋","ctime":"1474531961","pay_time":"1474531962","deliver_time":"0","take_time":"0","end_time":"0","other_sn":"","vid":"510107007006","available_jifen":"10","jifen":"0","tk_time":"0","f_price":"4.00","b_price":"30","business_id":"2","is_js":"0","express_sn":"","product_list":[{"id":"2007","order_s":"s20160922571025150521","goods_id":"121","goods_name":"伍田牛油火锅 成都倾城之味 醇麻柔和 香浓味厚 精选调味 3袋","goods_number":"1","b_price":"30.00","market_price":"59.00","goods_price":"29.70","ctime":"1474531961","jifen":"10","picurl":"/Public/product/7/2016-07-27/20160727s20160727575010199579_1.jpg","f_price":"4.00","shipping_fee":"0.00","business_id":"2"}]}]
     * url : http://product.yibanke.com/alipay/n_url_2
     */

    private String cnt;
    private String url;
    /**
     * id : 1065
     * order_sn : s20160922571025150521
     * uid : 10
     * uname : 逗比
     * phone : 18200390595
     * buy_uname : 李焓
     * address : 四川省 成都市 高新区天仁路387号大鼎世纪广场3栋4层
     * tel : 18628155109
     * order_status : 0
     * pay_status : 2
     * shipping_id : 0
     * shipping_name :
     * pay_type_id : 2
     * shipping_fee : 0.00
     * money_paid : 29.70
     * order_amount : 29.70
     * products : 伍田牛油火锅 成都倾城之味 醇麻柔和 香浓味厚 精选调味 3袋
     * ctime : 1474531961
     * pay_time : 1474531962
     * deliver_time : 0
     * take_time : 0
     * end_time : 0
     * other_sn :
     * vid : 510107007006
     * available_jifen : 10
     * jifen : 0
     * tk_time : 0
     * f_price : 4.00
     * b_price : 30
     * business_id : 2
     * is_js : 0
     * express_sn :
     * product_list : [{"id":"2007","order_s":"s20160922571025150521","goods_id":"121","goods_name":"伍田牛油火锅 成都倾城之味 醇麻柔和 香浓味厚 精选调味 3袋","goods_number":"1","b_price":"30.00","market_price":"59.00","goods_price":"29.70","ctime":"1474531961","jifen":"10","picurl":"/Public/product/7/2016-07-27/20160727s20160727575010199579_1.jpg","f_price":"4.00","shipping_fee":"0.00","business_id":"2"}]
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
        private String order_sn;
        private String uid;
        private String uname;
        private String phone;
        private String buy_uname;
        private String address;
        private String tel;
        private String order_status;
        private String pay_status;
        private String shipping_id;
        private String shipping_name;
        private String pay_type_id;
        private String shipping_fee;
        private String money_paid;
        private String order_amount;
        private String products;
        private String ctime;
        private String pay_time;
        private String deliver_time;
        private String take_time;
        private String end_time;
        private String other_sn;
        private String vid;
        private String available_jifen;
        private String jifen;
        private String tk_time;
        private String f_price;
        private String b_price;
        private String business_id;
        private String is_js;
        private String express_sn;
        /**
         * id : 2007
         * order_s : s20160922571025150521
         * goods_id : 121
         * goods_name : 伍田牛油火锅 成都倾城之味 醇麻柔和 香浓味厚 精选调味 3袋
         * goods_number : 1
         * b_price : 30.00
         * market_price : 59.00
         * goods_price : 29.70
         * ctime : 1474531961
         * jifen : 10
         * picurl : /Public/product/7/2016-07-27/20160727s20160727575010199579_1.jpg
         * f_price : 4.00
         * shipping_fee : 0.00
         * business_id : 2
         */

        private List<ProductListBean> product_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBuy_uname() {
            return buy_uname;
        }

        public void setBuy_uname(String buy_uname) {
            this.buy_uname = buy_uname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getShipping_id() {
            return shipping_id;
        }

        public void setShipping_id(String shipping_id) {
            this.shipping_id = shipping_id;
        }

        public String getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        public String getPay_type_id() {
            return pay_type_id;
        }

        public void setPay_type_id(String pay_type_id) {
            this.pay_type_id = pay_type_id;
        }

        public String getShipping_fee() {
            return shipping_fee;
        }

        public void setShipping_fee(String shipping_fee) {
            this.shipping_fee = shipping_fee;
        }

        public String getMoney_paid() {
            return money_paid;
        }

        public void setMoney_paid(String money_paid) {
            this.money_paid = money_paid;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getProducts() {
            return products;
        }

        public void setProducts(String products) {
            this.products = products;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getDeliver_time() {
            return deliver_time;
        }

        public void setDeliver_time(String deliver_time) {
            this.deliver_time = deliver_time;
        }

        public String getTake_time() {
            return take_time;
        }

        public void setTake_time(String take_time) {
            this.take_time = take_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getOther_sn() {
            return other_sn;
        }

        public void setOther_sn(String other_sn) {
            this.other_sn = other_sn;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getAvailable_jifen() {
            return available_jifen;
        }

        public void setAvailable_jifen(String available_jifen) {
            this.available_jifen = available_jifen;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getTk_time() {
            return tk_time;
        }

        public void setTk_time(String tk_time) {
            this.tk_time = tk_time;
        }

        public String getF_price() {
            return f_price;
        }

        public void setF_price(String f_price) {
            this.f_price = f_price;
        }

        public String getB_price() {
            return b_price;
        }

        public void setB_price(String b_price) {
            this.b_price = b_price;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getIs_js() {
            return is_js;
        }

        public void setIs_js(String is_js) {
            this.is_js = is_js;
        }

        public String getExpress_sn() {
            return express_sn;
        }

        public void setExpress_sn(String express_sn) {
            this.express_sn = express_sn;
        }

        public List<ProductListBean> getProduct_list() {
            return product_list;
        }

        public void setProduct_list(List<ProductListBean> product_list) {
            this.product_list = product_list;
        }

        public static class ProductListBean {
            private String id;
            private String order_s;
            private String goods_id;
            private String goods_name;
            private String goods_number;
            private String b_price;
            private String market_price;
            private String goods_price;
            private String ctime;
            private String jifen;
            private String picurl;
            private String f_price;
            private String shipping_fee;
            private String business_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder_s() {
                return order_s;
            }

            public void setOrder_s(String order_s) {
                this.order_s = order_s;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(String goods_number) {
                this.goods_number = goods_number;
            }

            public String getB_price() {
                return b_price;
            }

            public void setB_price(String b_price) {
                this.b_price = b_price;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getJifen() {
                return jifen;
            }

            public void setJifen(String jifen) {
                this.jifen = jifen;
            }

            public String getPicurl() {
                return picurl;
            }

            public void setPicurl(String picurl) {
                this.picurl = picurl;
            }

            public String getF_price() {
                return f_price;
            }

            public void setF_price(String f_price) {
                this.f_price = f_price;
            }

            public String getShipping_fee() {
                return shipping_fee;
            }

            public void setShipping_fee(String shipping_fee) {
                this.shipping_fee = shipping_fee;
            }

            public String getBusiness_id() {
                return business_id;
            }

            public void setBusiness_id(String business_id) {
                this.business_id = business_id;
            }
        }
    }
}
