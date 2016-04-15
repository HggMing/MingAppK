package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/4/13.
 */
public class FriendList {

    /**
     * err : 0
     * data : {"cnt":8,"list":[{"uid":"10000","name":"小包谷【语音助手】","head":"/Public/head/xiaobaogu.png","sex":0,"phone":""},{"uid":"10001","name":"我们村【客服】","head":"/Public/head/kefu.jpg","sex":0,"phone":""},{"uid":"12018","name":"的花朵好的话电话皇帝回电","head":"/Public/head/12018/RULB5FT1HSUC1460540488.jpg","sex":"0","phone":"18140006179"},{"aname":"","uid":"11028","name":"张锋","phone":"15206544787","sex":"0","head":"/Public/head/11028/0A6C2ETRHSFM1460538931.jpg"},{"aname":"","uid":"13123","name":"灯火辉煌","phone":"19910001001","sex":"0","head":"/Public/head/13123/UA2I3YT1UKUA1459914062.jpg"},{"aname":"","uid":"13114","name":"新手上路","phone":"19910001000","sex":"0","head":"/Public/head/13114/3RVFJTTRYLY21459821905.jpg"},{"aname":"","uid":"11017","name":"逗比请来的猴子","phone":"18200390598","sex":"0","head":"/Public/head/11017/BAN9UJT1JSB31460455097.jpg"},{"aname":"","uid":"11006","name":"张小雨","phone":"13800138005","sex":"1","head":"/Public/head/11006/UEK4TDTWZDOC1455869318.jpg"}]}
     */

    private int err;
    /**
     * cnt : 8
     * list : [{"uid":"10000","name":"小包谷【语音助手】","head":"/Public/head/xiaobaogu.png","sex":0,"phone":""},{"uid":"10001","name":"我们村【客服】","head":"/Public/head/kefu.jpg","sex":0,"phone":""},{"uid":"12018","name":"的花朵好的话电话皇帝回电","head":"/Public/head/12018/RULB5FT1HSUC1460540488.jpg","sex":"0","phone":"18140006179"},{"aname":"","uid":"11028","name":"张锋","phone":"15206544787","sex":"0","head":"/Public/head/11028/0A6C2ETRHSFM1460538931.jpg"},{"aname":"","uid":"13123","name":"灯火辉煌","phone":"19910001001","sex":"0","head":"/Public/head/13123/UA2I3YT1UKUA1459914062.jpg"},{"aname":"","uid":"13114","name":"新手上路","phone":"19910001000","sex":"0","head":"/Public/head/13114/3RVFJTTRYLY21459821905.jpg"},{"aname":"","uid":"11017","name":"逗比请来的猴子","phone":"18200390598","sex":"0","head":"/Public/head/11017/BAN9UJT1JSB31460455097.jpg"},{"aname":"","uid":"11006","name":"张小雨","phone":"13800138005","sex":"1","head":"/Public/head/11006/UEK4TDTWZDOC1455869318.jpg"}]
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
        private int cnt;
        /**
         * uid : 10000
         * name : 小包谷【语音助手】
         * head : /Public/head/xiaobaogu.png
         * sex : 0
         * phone :
         */

        private List<ListBean> list;

        public int getCnt() {
            return cnt;
        }

        public void setCnt(int cnt) {
            this.cnt = cnt;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String aname;
            private String uid;
            private String name;
            private String head;
            private int sex;
            private String phone;
            private String sortLetters;

            public String getSortLetters() {
                return sortLetters;
            }

            public void setSortLetters(String sortLetters) {
                this.sortLetters = sortLetters;
            }

            public String getAname() {
                return aname;
            }

            public void setAname(String aname) {
                this.aname = aname;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
