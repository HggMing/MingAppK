package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/4/17.
 */
public class BbsCommentList {

    /**
     * err : 0
     * data : {"cnt":"4","list":[{"id":"482","uid":"13114","uname":"代号000","ctime":"1460599064","stats":"1","pid":"1141","conts":"提卡提卡","user_head":"/Public/head/13114/VSY8MLT1GPAB1460598788.jpg","user_tel":"19910001000"},{"id":"481","uid":"13114","uname":"代号000","ctime":"1460599049","stats":"1","pid":"1141","conts":"黑凤梨","user_head":"/Public/head/13114/VSY8MLT1GPAB1460598788.jpg","user_tel":"19910001000"},{"id":"369","uid":"13146","uname":"","ctime":"1460428450","stats":"1","pid":"1141","conts":"拍点照片发呗","user_head":"/Public/head/default.png","user_tel":"13800138038"},{"id":"295","uid":"12018","uname":"大王叫我来巡山","ctime":"1460359616","stats":"1","pid":"1141","conts":"过来点个赞。。","user_head":"/Public/head/12018/RULB5FT1HSUC1460540488.jpg","user_tel":"18140006179"}]}
     */

    private int err;
    /**
     * cnt : 4
     * list : [{"id":"482","uid":"13114","uname":"代号000","ctime":"1460599064","stats":"1","pid":"1141","conts":"提卡提卡","user_head":"/Public/head/13114/VSY8MLT1GPAB1460598788.jpg","user_tel":"19910001000"},{"id":"481","uid":"13114","uname":"代号000","ctime":"1460599049","stats":"1","pid":"1141","conts":"黑凤梨","user_head":"/Public/head/13114/VSY8MLT1GPAB1460598788.jpg","user_tel":"19910001000"},{"id":"369","uid":"13146","uname":"","ctime":"1460428450","stats":"1","pid":"1141","conts":"拍点照片发呗","user_head":"/Public/head/default.png","user_tel":"13800138038"},{"id":"295","uid":"12018","uname":"大王叫我来巡山","ctime":"1460359616","stats":"1","pid":"1141","conts":"过来点个赞。。","user_head":"/Public/head/12018/RULB5FT1HSUC1460540488.jpg","user_tel":"18140006179"}]
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
         * id : 482
         * uid : 13114
         * uname : 代号000
         * ctime : 1460599064
         * stats : 1
         * pid : 1141
         * conts : 提卡提卡
         * user_head : /Public/head/13114/VSY8MLT1GPAB1460598788.jpg
         * user_tel : 19910001000
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
            private String uname;
            private String ctime;
            private String stats;
            private String pid;
            private String conts;
            private String user_head;
            private String user_tel;

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

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
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

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getConts() {
                return conts;
            }

            public void setConts(String conts) {
                this.conts = conts;
            }

            public String getUser_head() {
                return user_head;
            }

            public void setUser_head(String user_head) {
                this.user_head = user_head;
            }

            public String getUser_tel() {
                return user_tel;
            }

            public void setUser_tel(String user_tel) {
                this.user_tel = user_tel;
            }
        }
    }
}
