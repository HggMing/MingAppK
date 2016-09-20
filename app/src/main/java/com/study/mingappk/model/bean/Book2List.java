package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/9/19.
 */
public class Book2List {

    /**
     * err : 0
     * data : {"cnt":"3","list":[{"id":"26","vid":"510107007006","ctime":"1474167615","phone":"18140006179","name":"姓名","endtime":"0","status":"0","mark":null,"bookname":"55Sf5ZG955qE5ZOy5a2m","booknum":"151561"},{"id":"27","vid":"510107007006","ctime":"1474167617","phone":"18140006179","name":"姓名","endtime":"0","status":"0","mark":null,"bookname":"5ZCJ5LuW5oyH5Y2X","booknum":"7964484854"},{"id":"25","vid":"510107007006","ctime":"1474166819","phone":"13800138000","name":"寮犱笁","endtime":"1474166825","status":"1","mark":null,"bookname":"5oiQ5Yqf55qE5ZOy5a2m","booknum":"41818"}]}
     */

    private int err;
    /**
     * cnt : 3
     * list : [{"id":"26","vid":"510107007006","ctime":"1474167615","phone":"18140006179","name":"姓名","endtime":"0","status":"0","mark":null,"bookname":"55Sf5ZG955qE5ZOy5a2m","booknum":"151561"},{"id":"27","vid":"510107007006","ctime":"1474167617","phone":"18140006179","name":"姓名","endtime":"0","status":"0","mark":null,"bookname":"5ZCJ5LuW5oyH5Y2X","booknum":"7964484854"},{"id":"25","vid":"510107007006","ctime":"1474166819","phone":"13800138000","name":"寮犱笁","endtime":"1474166825","status":"1","mark":null,"bookname":"5oiQ5Yqf55qE5ZOy5a2m","booknum":"41818"}]
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
         * id : 26
         * vid : 510107007006
         * ctime : 1474167615
         * phone : 18140006179
         * name : 姓名
         * endtime : 0
         * status : 0
         * mark : null
         * bookname : 55Sf5ZG955qE5ZOy5a2m
         * booknum : 151561
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
            private String ctime;
            private String phone;
            private String name;
            private String endtime;
            private String status;
            private Object mark;
            private String bookname;
            private String booknum;

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

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getMark() {
                return mark;
            }

            public void setMark(Object mark) {
                this.mark = mark;
            }

            public String getBookname() {
                return bookname;
            }

            public void setBookname(String bookname) {
                this.bookname = bookname;
            }

            public String getBooknum() {
                return booknum;
            }

            public void setBooknum(String booknum) {
                this.booknum = booknum;
            }
        }
    }
}
