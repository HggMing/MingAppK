package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/11/2.
 */

public class ProductList {
    /**
     * err : 0
     * data : {"cnt":"2","list":[{"id":"270","vid":"510107007006","picurl":"/Public/product/7/2016-11-01/20161101s20161101555655991014.jpg","title":"测试11","content":"1111111111111","ctime":"1478067845","picurl_1":"/Public/product/7/2016-11-01/20161101s20161101555655991014_1.jpg","num":"11255","sale_num":"10","score":"5","price":"1.01","stats":"1","jifen":"1","sorts":"1","is_tuijian":"0","is_shangjia":"1","p_number":"001","cid":"19","miaoshu":"1111111","smart_price":"1.00","ship_fee":"1.00","guige":"1","linkurl":"/product/info?id=270","txt":"1111111111111"},{"id":"264","vid":"510107007006","picurl":"/Public/product/7/2016-10-31/20161031s20161031102995555535.jpg","title":"红心猕猴桃（测试）","content":"<img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103849_90365.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103850_71867.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103850_21760.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103851_78222.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103851_12210.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103851_78723.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103851_89126.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103852_96551.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103852_84624.jpg\" alt=\"\" />","ctime":"1478066850","picurl_1":"/Public/product/7/2016-10-31/20161031s20161031102995555535_1.jpg","num":"11055","sale_num":"10","score":"5","price":"0.01","stats":"1","jifen":"0","sorts":"2","is_tuijian":"0","is_shangjia":"1","p_number":"001","cid":"19","miaoshu":"蒲江红心猕猴桃","smart_price":"10.00","ship_fee":"0.01","guige":"100g","linkurl":"/product/info?id=264","txt":""}]}
     */

    private int err;
    /**
     * cnt : 2
     * list : [{"id":"270","vid":"510107007006","picurl":"/Public/product/7/2016-11-01/20161101s20161101555655991014.jpg","title":"测试11","content":"1111111111111","ctime":"1478067845","picurl_1":"/Public/product/7/2016-11-01/20161101s20161101555655991014_1.jpg","num":"11255","sale_num":"10","score":"5","price":"1.01","stats":"1","jifen":"1","sorts":"1","is_tuijian":"0","is_shangjia":"1","p_number":"001","cid":"19","miaoshu":"1111111","smart_price":"1.00","ship_fee":"1.00","guige":"1","linkurl":"/product/info?id=270","txt":"1111111111111"},{"id":"264","vid":"510107007006","picurl":"/Public/product/7/2016-10-31/20161031s20161031102995555535.jpg","title":"红心猕猴桃（测试）","content":"<img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103849_90365.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103850_71867.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103850_21760.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103851_78222.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103851_12210.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103851_78723.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103851_89126.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103852_96551.jpg\" alt=\"\" /><img src=\"/plug/kindeditor/php/../attached/image/20161031/20161031103852_84624.jpg\" alt=\"\" />","ctime":"1478066850","picurl_1":"/Public/product/7/2016-10-31/20161031s20161031102995555535_1.jpg","num":"11055","sale_num":"10","score":"5","price":"0.01","stats":"1","jifen":"0","sorts":"2","is_tuijian":"0","is_shangjia":"1","p_number":"001","cid":"19","miaoshu":"蒲江红心猕猴桃","smart_price":"10.00","ship_fee":"0.01","guige":"100g","linkurl":"/product/info?id=264","txt":""}]
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
        /**
         * id : 270
         * vid : 510107007006
         * picurl : /Public/product/7/2016-11-01/20161101s20161101555655991014.jpg
         * title : 测试11
         * content : 1111111111111
         * ctime : 1478067845
         * picurl_1 : /Public/product/7/2016-11-01/20161101s20161101555655991014_1.jpg
         * num : 11255
         * sale_num : 10
         * score : 5
         * price : 1.01
         * stats : 1
         * jifen : 1
         * sorts : 1
         * is_tuijian : 0
         * is_shangjia : 1
         * p_number : 001
         * cid : 19
         * miaoshu : 1111111
         * smart_price : 1.00
         * ship_fee : 1.00
         * guige : 1
         * linkurl : /product/info?id=270
         * txt : 1111111111111
         */

        private List<ListBean> list;

        public String getCnt() {
            return cnt;
        }

        public void setCnt(String cnt) {
            this.cnt = cnt;
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
            private String picurl;
            private String title;
            private String content;
            private String ctime;
            private String picurl_1;
            private String num;
            private String sale_num;
            private String score;
            private String price;
            private String stats;
            private String jifen;
            private String sorts;
            private String is_tuijian;
            private String is_shangjia;
            private String p_number;
            private String cid;
            private String miaoshu;
            private String smart_price;
            private String ship_fee;
            private String guige;
            private String linkurl;
            private String txt;

            //自定义参数
            private int buyNum=0;
            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
            }

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

            public String getPicurl() {
                return picurl;
            }

            public void setPicurl(String picurl) {
                this.picurl = picurl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getPicurl_1() {
                return picurl_1;
            }

            public void setPicurl_1(String picurl_1) {
                this.picurl_1 = picurl_1;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getSale_num() {
                return sale_num;
            }

            public void setSale_num(String sale_num) {
                this.sale_num = sale_num;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStats() {
                return stats;
            }

            public void setStats(String stats) {
                this.stats = stats;
            }

            public String getJifen() {
                return jifen;
            }

            public void setJifen(String jifen) {
                this.jifen = jifen;
            }

            public String getSorts() {
                return sorts;
            }

            public void setSorts(String sorts) {
                this.sorts = sorts;
            }

            public String getIs_tuijian() {
                return is_tuijian;
            }

            public void setIs_tuijian(String is_tuijian) {
                this.is_tuijian = is_tuijian;
            }

            public String getIs_shangjia() {
                return is_shangjia;
            }

            public void setIs_shangjia(String is_shangjia) {
                this.is_shangjia = is_shangjia;
            }

            public String getP_number() {
                return p_number;
            }

            public void setP_number(String p_number) {
                this.p_number = p_number;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getMiaoshu() {
                return miaoshu;
            }

            public void setMiaoshu(String miaoshu) {
                this.miaoshu = miaoshu;
            }

            public String getSmart_price() {
                return smart_price;
            }

            public void setSmart_price(String smart_price) {
                this.smart_price = smart_price;
            }

            public String getShip_fee() {
                return ship_fee;
            }

            public void setShip_fee(String ship_fee) {
                this.ship_fee = ship_fee;
            }

            public String getGuige() {
                return guige;
            }

            public void setGuige(String guige) {
                this.guige = guige;
            }

            public String getLinkurl() {
                return linkurl;
            }

            public void setLinkurl(String linkurl) {
                this.linkurl = linkurl;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }
    }
}
