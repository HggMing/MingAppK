package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/8/23.
 */
public class MyVillUsers {
    /**
     * err : 0
     * msg : 获取成功
     * data : {"cnt":"5","list":[{"uid":"13167","phone":"18380416513","sex":"0","uname":"走在希望的田野上","head":"/Public/head/13167/PLYKCHTTADR91463558629.jpg","rank":"0","loc":"510107007006","cid":""},{"uid":"13123","phone":"19910001001","sex":"0","uname":"代号001","head":"/Public/head/13123/X0BR4YT1VFTV1461310782.jpg","rank":"0","loc":"510107007006","cid":""},{"uid":"13111","phone":"18200390595","sex":"0","uname":"猴子请来的逗比","head":"/Public/head/13111/0YJ8FET5FDCH1471251412.jpg","rank":null,"loc":"510107007006","cid":""},{"uid":"11025","phone":"18108101188","sex":"0","uname":"周子涵","head":"/Public/head/11025/43NOIHTRKSJ81460365941.jpg","rank":null,"loc":"510107007006","cid":"511123198808233358"},{"uid":"12018","phone":"18140006179","sex":"0","uname":"天马行空","head":"/Public/head/12018/1WXUOSTTSG661463466759.jpg","rank":null,"loc":"510107007006","cid":"147852369874123654"}]}
     */

    private int err;
    private String msg;
    /**
     * cnt : 5
     * list : [{"uid":"13167","phone":"18380416513","sex":"0","uname":"走在希望的田野上","head":"/Public/head/13167/PLYKCHTTADR91463558629.jpg","rank":"0","loc":"510107007006","cid":""},{"uid":"13123","phone":"19910001001","sex":"0","uname":"代号001","head":"/Public/head/13123/X0BR4YT1VFTV1461310782.jpg","rank":"0","loc":"510107007006","cid":""},{"uid":"13111","phone":"18200390595","sex":"0","uname":"猴子请来的逗比","head":"/Public/head/13111/0YJ8FET5FDCH1471251412.jpg","rank":null,"loc":"510107007006","cid":""},{"uid":"11025","phone":"18108101188","sex":"0","uname":"周子涵","head":"/Public/head/11025/43NOIHTRKSJ81460365941.jpg","rank":null,"loc":"510107007006","cid":"511123198808233358"},{"uid":"12018","phone":"18140006179","sex":"0","uname":"天马行空","head":"/Public/head/12018/1WXUOSTTSG661463466759.jpg","rank":null,"loc":"510107007006","cid":"147852369874123654"}]
     */

    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
         * uid : 13167
         * phone : 18380416513
         * sex : 0
         * uname : 走在希望的田野上
         * head : /Public/head/13167/PLYKCHTTADR91463558629.jpg
         * rank : 0
         * loc : 510107007006
         * cid :
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
            private String uid;
            private String phone;
            private String sex;
            private String uname;
            private String head;
            private String rank;
            private String loc;
            private String cid;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }
        }
    }
}
