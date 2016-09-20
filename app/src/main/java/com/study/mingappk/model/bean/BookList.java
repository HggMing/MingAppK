package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/9/19.
 */
public class BookList {
    /**
     * err : 0
     * data : {"cnt":"4","list":[{"id":"306","vid":"510107007006","pic":"/Public/books/13111/2016-09-01/20160901s20160901539956102101.files","pic_s":"/Public/books/13111/2016-09-01/20160901s20160901539956102101_1.files","bname":"成功的哲学","tolcount":"1","currcount":"1","ctime":"1472696917","bnum":"41818","status":"0"},{"id":"309","vid":"510107007006","pic":"/Public/books/13111/2016-09-14/20160914s20160914559898101985.files","pic_s":"/Public/books/13111/2016-09-14/20160914s20160914559898101985_1.files","bname":"吉他指南","tolcount":"1","currcount":"1","ctime":"1473831367","bnum":"7964484854","status":"1"}]}
     */

    private int err;
    /**
     * cnt : 4
     * list : [{"id":"306","vid":"510107007006","pic":"/Public/books/13111/2016-09-01/20160901s20160901539956102101.files","pic_s":"/Public/books/13111/2016-09-01/20160901s20160901539956102101_1.files","bname":"成功的哲学","tolcount":"1","currcount":"1","ctime":"1472696917","bnum":"41818","status":"0"},{"id":"309","vid":"510107007006","pic":"/Public/books/13111/2016-09-14/20160914s20160914559898101985.files","pic_s":"/Public/books/13111/2016-09-14/20160914s20160914559898101985_1.files","bname":"吉他指南","tolcount":"1","currcount":"1","ctime":"1473831367","bnum":"7964484854","status":"1"}]
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
         * id : 306
         * vid : 510107007006
         * pic : /Public/books/13111/2016-09-01/20160901s20160901539956102101.files
         * pic_s : /Public/books/13111/2016-09-01/20160901s20160901539956102101_1.files
         * bname : 成功的哲学
         * tolcount : 1
         * currcount : 1
         * ctime : 1472696917
         * bnum : 41818
         * status : 0
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
            private String pic;
            private String pic_s;
            private String bname;
            private String tolcount;
            private String currcount;
            private String ctime;
            private String bnum;
            private String status;

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

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPic_s() {
                return pic_s;
            }

            public void setPic_s(String pic_s) {
                this.pic_s = pic_s;
            }

            public String getBname() {
                return bname;
            }

            public void setBname(String bname) {
                this.bname = bname;
            }

            public String getTolcount() {
                return tolcount;
            }

            public void setTolcount(String tolcount) {
                this.tolcount = tolcount;
            }

            public String getCurrcount() {
                return currcount;
            }

            public void setCurrcount(String currcount) {
                this.currcount = currcount;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getBnum() {
                return bnum;
            }

            public void setBnum(String bnum) {
                this.bnum = bnum;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
