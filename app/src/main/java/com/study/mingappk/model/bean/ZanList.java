package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/4/12.
 */
public class ZanList {


    /**
     * err : 0
     * data : {"cnt":"1","list":[{"id":"3","uid":"2","tid":"1","ctime":"1452245225","user_head":"/Public/head/2/UTRC3STNOLKQ1452221198.txt","name":"niuniu"}]}
     */

    private int err;
    /**
     * cnt : 1
     * list : [{"id":"3","uid":"2","tid":"1","ctime":"1452245225","user_head":"/Public/head/2/UTRC3STNOLKQ1452221198.txt","name":"niuniu"}]
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
         * id : 3
         * uid : 2
         * tid : 1
         * ctime : 1452245225
         * user_head : /Public/head/2/UTRC3STNOLKQ1452221198.txt
         * name : niuniu
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
            private String uid;
            private String tid;
            private String ctime;
            private String user_head;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getUser_head() {
                return user_head;
            }

            public void setUser_head(String user_head) {
                this.user_head = user_head;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
