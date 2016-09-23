package com.study.mingappk.model.bean;

import java.util.List;

/**
 * 产品推荐列表
 * Created by Ming on 2016/9/21.
 */
public class RecommendList {

    /**
     * err : 0
     * data : {"cnt":"6","list":[{"id":"213","vid":"510131101206","title":"四川蒲江红心猕猴桃 （90\u2014100g），24个，重约5斤","picurl_1":"/Public/product/13172/2016-08-04/20160804s20160804485310150565_1.jpg","price":"120.00","linkurl":"/product/info?id=213"}]}
     */

    private int err;
    /**
     * cnt : 6
     * list : [{"id":"213","vid":"510131101206","title":"四川蒲江红心猕猴桃 （90\u2014100g），24个，重约5斤","picurl_1":"/Public/product/13172/2016-08-04/20160804s20160804485310150565_1.jpg","price":"120.00","linkurl":"/product/info?id=213"}]
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
         * id : 213
         * vid : 510131101206
         * title : 四川蒲江红心猕猴桃 （90—100g），24个，重约5斤
         * picurl_1 : /Public/product/13172/2016-08-04/20160804s20160804485310150565_1.jpg
         * price : 120.00
         * linkurl : /product/info?id=213
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
            private String title;
            private String picurl_1;
            private String price;
            private String linkurl;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPicurl_1() {
                return picurl_1;
            }

            public void setPicurl_1(String picurl_1) {
                this.picurl_1 = picurl_1;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getLinkurl() {
                return linkurl;
            }

            public void setLinkurl(String linkurl) {
                this.linkurl = linkurl;
            }
        }
    }
}
