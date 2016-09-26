package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/9/25.
 */
public class BBSDetail {
    /**
     * err : 0
     * msg : 获取成功
     * data : {"id":"7352","title":"","conts":"好早啊","ctime":"1474667291","uid":"13114","phone":"19910001000","uname":"代号000","vid":"510922103201","source":"-1","stats":"1","nums":"3","pic":"","zans":"1","is_manage":"2","pic_1":"","os":"-1","files":[{"id":"13697","pid":"7352","uid":"13114","url":"/Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655.jpg","surl_1":"/Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655_1.jpg","surl_2":"/Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655_2.jpg","stats":"0"},{"id":"13698","pid":"7352","uid":"13114","url":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985010049569.jpg","surl_1":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985010049569_1.jpg","surl_2":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985010049569_2.jpg","stats":"0"},{"id":"13699","pid":"7352","uid":"13114","url":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985748494854.jpg","surl_1":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985748494854_1.jpg","surl_2":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985748494854_2.jpg","stats":"0"}],"userinfo":{"uid":"13114","phone":"19910001000","uname":"代号000","head":"/Public/head/13114/VSY8MLT1GPAB1460598788.jpg"},"my_is_zan":0}
     */

    private int err;
    private String msg;
    /**
     * id : 7352
     * title :
     * conts : 好早啊
     * ctime : 1474667291
     * uid : 13114
     * phone : 19910001000
     * uname : 代号000
     * vid : 510922103201
     * source : -1
     * stats : 1
     * nums : 3
     * pic :
     * zans : 1
     * is_manage : 2
     * pic_1 :
     * os : -1
     * files : [{"id":"13697","pid":"7352","uid":"13114","url":"/Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655.jpg","surl_1":"/Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655_1.jpg","surl_2":"/Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655_2.jpg","stats":"0"},{"id":"13698","pid":"7352","uid":"13114","url":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985010049569.jpg","surl_1":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985010049569_1.jpg","surl_2":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985010049569_2.jpg","stats":"0"},{"id":"13699","pid":"7352","uid":"13114","url":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985748494854.jpg","surl_1":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985748494854_1.jpg","surl_2":"/Public/bbs/file/13114/2016-09-24/20160924s20160924985748494854_2.jpg","stats":"0"}]
     * userinfo : {"uid":"13114","phone":"19910001000","uname":"代号000","head":"/Public/head/13114/VSY8MLT1GPAB1460598788.jpg"}
     * my_is_zan : 0
     */

    private DataBean data2;

    public void setData(BBSList.DataEntity.ListEntity data) {
        this.data = data;
    }

    public BBSList.DataEntity.ListEntity getData() {
        return data;
    }

    private BBSList.DataEntity.ListEntity data;

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

    public DataBean getData2() {
        return data2;
    }

    public void setData2(DataBean data2) {
        this.data2 = data2;
    }

    public static class DataBean {
        private String id;
        private String title;
        private String conts;
        private String ctime;
        private String uid;
        private String phone;
        private String uname;
        private String vid;
        private String source;
        private String stats;
        private String nums;
        private String pic;
        private String zans;
        private String is_manage;
        private String pic_1;
        private String os;
        /**
         * uid : 13114
         * phone : 19910001000
         * uname : 代号000
         * head : /Public/head/13114/VSY8MLT1GPAB1460598788.jpg
         */

        private UserinfoBean userinfo;
        private int my_is_zan;
        /**
         * id : 13697
         * pid : 7352
         * uid : 13114
         * url : /Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655.jpg
         * surl_1 : /Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655_1.jpg
         * surl_2 : /Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655_2.jpg
         * stats : 0
         */

        private List<FilesBean> files;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getConts() {
            return conts;
        }

        public void setConts(String conts) {
            this.conts = conts;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

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

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStats() {
            return stats;
        }

        public void setStats(String stats) {
            this.stats = stats;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getZans() {
            return zans;
        }

        public void setZans(String zans) {
            this.zans = zans;
        }

        public String getIs_manage() {
            return is_manage;
        }

        public void setIs_manage(String is_manage) {
            this.is_manage = is_manage;
        }

        public String getPic_1() {
            return pic_1;
        }

        public void setPic_1(String pic_1) {
            this.pic_1 = pic_1;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public int getMy_is_zan() {
            return my_is_zan;
        }

        public void setMy_is_zan(int my_is_zan) {
            this.my_is_zan = my_is_zan;
        }

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        public static class UserinfoBean {
            private String uid;
            private String phone;
            private String uname;
            private String head;

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
        }

        public static class FilesBean {
            private String id;
            private String pid;
            private String uid;
            private String url;
            private String surl_1;
            private String surl_2;
            private String stats;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSurl_1() {
                return surl_1;
            }

            public void setSurl_1(String surl_1) {
                this.surl_1 = surl_1;
            }

            public String getSurl_2() {
                return surl_2;
            }

            public void setSurl_2(String surl_2) {
                this.surl_2 = surl_2;
            }

            public String getStats() {
                return stats;
            }

            public void setStats(String stats) {
                this.stats = stats;
            }
        }
    }
}
