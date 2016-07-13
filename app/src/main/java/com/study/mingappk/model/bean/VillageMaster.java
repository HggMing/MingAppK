package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/7/13.
 */
public class VillageMaster {

    /**
     * err : 0
     * data : {"cnt":"8","list":[{"id":"105","contact":"13970362798","uname":"俞友伟","sex":"0","job":"支书","head":"/Public/vuser/7/2016-03-16/2016031620160316515152565456.png","zzmm":"","vid":"361130103203","ctime":"1458124819","stats":"0","sorts":"1"},{"id":"104","contact":"15899364158","uname":"俞学志","sex":"0","job":"主任","head":"/Public/vuser/7/2016-03-16/2016031620160316999910210148.png","zzmm":"","vid":"361130103203","ctime":"1458124764","stats":"0","sorts":"2"},{"id":"102","contact":"13707031566","uname":"俞顺利","sex":"0","job":"支委/副主任","head":"/Public/vuser/7/2016-03-16/2016031620160316535097561025.png","zzmm":"","vid":"361130103203","ctime":"1458124597","stats":"0","sorts":"3"},{"id":"103","contact":"13576368009","uname":"俞家平","sex":"0","job":"支委","head":"/Public/vuser/7/2016-03-16/2016031620160316505548974998.png","zzmm":"","vid":"361130103203","ctime":"1458124690","stats":"0","sorts":"4"},{"id":"101","contact":"13979360073","uname":"江文飞","sex":"0","job":"支委","head":"/Public/vuser/7/2016-03-16/2016031620160316525050511005.png","zzmm":"","vid":"361130103203","ctime":"1458124532","stats":"0","sorts":"5"},{"id":"100","contact":"13979353566","uname":"俞根庆","sex":"0","job":"村委","head":"/Public/vuser/7/2016-03-16/2016031620160316554855531015.png","zzmm":"","vid":"361130103203","ctime":"1458123927","stats":"0","sorts":"6"},{"id":"99","contact":"13979353566","uname":"俞少华","sex":"0","job":"村委","head":"/Public/vuser//2016-03-16/2016031620160316529999575057.png","zzmm":"","vid":"361130103203","ctime":"1458123451","stats":"0","sorts":"7"},{"id":"98","contact":"15070331869","uname":"江瑶","sex":"1","job":"支委","head":"/Public/vuser/7/2016-03-16/2016031620160316101495210255.png","zzmm":"","vid":"361130103203","ctime":"1458123358","stats":"0","sorts":"8"}]}
     */

    private int err;
    /**
     * cnt : 8
     * list : [{"id":"105","contact":"13970362798","uname":"俞友伟","sex":"0","job":"支书","head":"/Public/vuser/7/2016-03-16/2016031620160316515152565456.png","zzmm":"","vid":"361130103203","ctime":"1458124819","stats":"0","sorts":"1"},{"id":"104","contact":"15899364158","uname":"俞学志","sex":"0","job":"主任","head":"/Public/vuser/7/2016-03-16/2016031620160316999910210148.png","zzmm":"","vid":"361130103203","ctime":"1458124764","stats":"0","sorts":"2"},{"id":"102","contact":"13707031566","uname":"俞顺利","sex":"0","job":"支委/副主任","head":"/Public/vuser/7/2016-03-16/2016031620160316535097561025.png","zzmm":"","vid":"361130103203","ctime":"1458124597","stats":"0","sorts":"3"},{"id":"103","contact":"13576368009","uname":"俞家平","sex":"0","job":"支委","head":"/Public/vuser/7/2016-03-16/2016031620160316505548974998.png","zzmm":"","vid":"361130103203","ctime":"1458124690","stats":"0","sorts":"4"},{"id":"101","contact":"13979360073","uname":"江文飞","sex":"0","job":"支委","head":"/Public/vuser/7/2016-03-16/2016031620160316525050511005.png","zzmm":"","vid":"361130103203","ctime":"1458124532","stats":"0","sorts":"5"},{"id":"100","contact":"13979353566","uname":"俞根庆","sex":"0","job":"村委","head":"/Public/vuser/7/2016-03-16/2016031620160316554855531015.png","zzmm":"","vid":"361130103203","ctime":"1458123927","stats":"0","sorts":"6"},{"id":"99","contact":"13979353566","uname":"俞少华","sex":"0","job":"村委","head":"/Public/vuser//2016-03-16/2016031620160316529999575057.png","zzmm":"","vid":"361130103203","ctime":"1458123451","stats":"0","sorts":"7"},{"id":"98","contact":"15070331869","uname":"江瑶","sex":"1","job":"支委","head":"/Public/vuser/7/2016-03-16/2016031620160316101495210255.png","zzmm":"","vid":"361130103203","ctime":"1458123358","stats":"0","sorts":"8"}]
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
         * id : 105
         * contact : 13970362798
         * uname : 俞友伟
         * sex : 0
         * job : 支书
         * head : /Public/vuser/7/2016-03-16/2016031620160316515152565456.png
         * zzmm :
         * vid : 361130103203
         * ctime : 1458124819
         * stats : 0
         * sorts : 1
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
            private String contact;
            private String uname;
            private String sex;
            private String job;
            private String head;
            private String zzmm;
            private String vid;
            private String ctime;
            private String stats;
            private String sorts;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getZzmm() {
                return zzmm;
            }

            public void setZzmm(String zzmm) {
                this.zzmm = zzmm;
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

            public String getStats() {
                return stats;
            }

            public void setStats(String stats) {
                this.stats = stats;
            }

            public String getSorts() {
                return sorts;
            }

            public void setSorts(String sorts) {
                this.sorts = sorts;
            }
        }
    }
}
