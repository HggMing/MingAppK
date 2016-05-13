package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/5/3.
 */
public class FriendDetail {

    /**
     * err : 0
     * data : {"userinfo":{"uid":"11006","phone":"13800138005","logname":"13800138005","sex":"1","uname":"张小雨","birth":"","ctime":"1454031338","cid":"123456789098765678","ctype":"0","dev":"0","loc":"361130103203","lastlog":"1461638609","lastip":"125.71.76.179","lastdev":"-1","logcnt":"105","head":"/Public/head/11006/UEK4TDTWZDOC1455869318.jpg","rank":null,"province_id":"360","province_name":"江西省","city_id":"361100000000","city_name":"上饶市","county_id":"361130000000","county_name":"婺源县","town_id":"361130103000","town_name":"江湾镇","village_id":"361130103203","village_name":"汪口村","alias_name":""},"photoinfo":[{"id":"12","uid":"11006","url":"/Public/photo/11006/2016-02-19/201602192016021956985010.jpg","ctime":"1455865496","surl_1":"/Public/photo/11006/2016-02-19/201602192016021956985010_1.jpg"},{"id":"11","uid":"11006","url":"/Public/photo/11006/2016-02-19/201602192016021949545448.jpg","ctime":"1455865489","surl_1":"/Public/photo/11006/2016-02-19/201602192016021949545448_1.jpg"},{"id":"5","uid":"11006","url":"/Public/photo/11006/2016-02-18/201602182016021853549850.jpg","ctime":"1455788821","surl_1":"/Public/photo/11006/2016-02-18/201602182016021853549850_1.jpg"}],"videoinfo":{"id":"6","uid":"11006","url":"/Public/video/default.mp4","ctime":"1455788821","stats":"1"},"voiceinfo":{"id":"6","uid":"11006","url":"/Public/voice/default.amr","ctime":"1455788821"},"scoreinfo":[{"k":"语文","v":"100","s":"yuwen"},{"k":"数学","v":"90","s":"shuxue"},{"k":"英语","v":"90","s":"yingyu"},{"k":"体育","v":"90","s":"tiyu"},{"k":"班级排名","v":"3","s":"sort"},{"k":"老师评语","v":"活泼开朗，同学关系好，为人热情","s":"comment"}],"healthinfo":[{"k":"血压","v":"90/135mmolHg","s":"xueya"},{"k":"血糖","v":"3.1mmol/L","s":"xuetang"},{"k":"心率","v":"正常","s":"xinlv"},{"k":"视力","v":"正常","s":"shili"},{"k":"听力","v":"正常","s":"tingli"},{"k":"其他","v":"未见异常","s":"qita"},{"k":"建议","v":"多吃蔬菜水果","s":"comment"}],"bbs_top_pic4":[]}
     */

    private int err;
    /**
     * userinfo : {"uid":"11006","phone":"13800138005","logname":"13800138005","sex":"1","uname":"张小雨","birth":"","ctime":"1454031338","cid":"123456789098765678","ctype":"0","dev":"0","loc":"361130103203","lastlog":"1461638609","lastip":"125.71.76.179","lastdev":"-1","logcnt":"105","head":"/Public/head/11006/UEK4TDTWZDOC1455869318.jpg","rank":null,"province_id":"360","province_name":"江西省","city_id":"361100000000","city_name":"上饶市","county_id":"361130000000","county_name":"婺源县","town_id":"361130103000","town_name":"江湾镇","village_id":"361130103203","village_name":"汪口村","alias_name":""}
     * photoinfo : [{"id":"12","uid":"11006","url":"/Public/photo/11006/2016-02-19/201602192016021956985010.jpg","ctime":"1455865496","surl_1":"/Public/photo/11006/2016-02-19/201602192016021956985010_1.jpg"},{"id":"11","uid":"11006","url":"/Public/photo/11006/2016-02-19/201602192016021949545448.jpg","ctime":"1455865489","surl_1":"/Public/photo/11006/2016-02-19/201602192016021949545448_1.jpg"},{"id":"5","uid":"11006","url":"/Public/photo/11006/2016-02-18/201602182016021853549850.jpg","ctime":"1455788821","surl_1":"/Public/photo/11006/2016-02-18/201602182016021853549850_1.jpg"}]
     * videoinfo : {"id":"6","uid":"11006","url":"/Public/video/default.mp4","ctime":"1455788821","stats":"1"}
     * voiceinfo : {"id":"6","uid":"11006","url":"/Public/voice/default.amr","ctime":"1455788821"}
     * scoreinfo : [{"k":"语文","v":"100","s":"yuwen"},{"k":"数学","v":"90","s":"shuxue"},{"k":"英语","v":"90","s":"yingyu"},{"k":"体育","v":"90","s":"tiyu"},{"k":"班级排名","v":"3","s":"sort"},{"k":"老师评语","v":"活泼开朗，同学关系好，为人热情","s":"comment"}]
     * healthinfo : [{"k":"血压","v":"90/135mmolHg","s":"xueya"},{"k":"血糖","v":"3.1mmol/L","s":"xuetang"},{"k":"心率","v":"正常","s":"xinlv"},{"k":"视力","v":"正常","s":"shili"},{"k":"听力","v":"正常","s":"tingli"},{"k":"其他","v":"未见异常","s":"qita"},{"k":"建议","v":"多吃蔬菜水果","s":"comment"}]
     * bbs_top_pic4 : []
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
        /**
         * uid : 11006
         * phone : 13800138005
         * logname : 13800138005
         * sex : 1
         * uname : 张小雨
         * birth :
         * ctime : 1454031338
         * cid : 123456789098765678
         * ctype : 0
         * dev : 0
         * loc : 361130103203
         * lastlog : 1461638609
         * lastip : 125.71.76.179
         * lastdev : -1
         * logcnt : 105
         * head : /Public/head/11006/UEK4TDTWZDOC1455869318.jpg
         * rank : null
         * province_id : 360
         * province_name : 江西省
         * city_id : 361100000000
         * city_name : 上饶市
         * county_id : 361130000000
         * county_name : 婺源县
         * town_id : 361130103000
         * town_name : 江湾镇
         * village_id : 361130103203
         * village_name : 汪口村
         * alias_name :
         */

        private UserinfoBean userinfo;
        /**
         * id : 6
         * uid : 11006
         * url : /Public/video/default.mp4
         * ctime : 1455788821
         * stats : 1
         */

        private VideoinfoBean videoinfo;
        /**
         * id : 6
         * uid : 11006
         * url : /Public/voice/default.amr
         * ctime : 1455788821
         */

        private VoiceinfoBean voiceinfo;
        /**
         * id : 12
         * uid : 11006
         * url : /Public/photo/11006/2016-02-19/201602192016021956985010.jpg
         * ctime : 1455865496
         * surl_1 : /Public/photo/11006/2016-02-19/201602192016021956985010_1.jpg
         */

        private List<PhotoinfoBean> photoinfo;
        /**
         * k : 语文
         * v : 100
         * s : yuwen
         */

        private List<ScoreinfoBean> scoreinfo;
        /**
         * k : 血压
         * v : 90/135mmolHg
         * s : xueya
         */

        private List<HealthinfoBean> healthinfo;
        private List<BbsTopPic4Bean> bbs_top_pic4;

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public VideoinfoBean getVideoinfo() {
            return videoinfo;
        }

        public void setVideoinfo(VideoinfoBean videoinfo) {
            this.videoinfo = videoinfo;
        }

        public VoiceinfoBean getVoiceinfo() {
            return voiceinfo;
        }

        public void setVoiceinfo(VoiceinfoBean voiceinfo) {
            this.voiceinfo = voiceinfo;
        }

        public List<PhotoinfoBean> getPhotoinfo() {
            return photoinfo;
        }

        public void setPhotoinfo(List<PhotoinfoBean> photoinfo) {
            this.photoinfo = photoinfo;
        }

        public List<ScoreinfoBean> getScoreinfo() {
            return scoreinfo;
        }

        public void setScoreinfo(List<ScoreinfoBean> scoreinfo) {
            this.scoreinfo = scoreinfo;
        }

        public List<HealthinfoBean> getHealthinfo() {
            return healthinfo;
        }

        public void setHealthinfo(List<HealthinfoBean> healthinfo) {
            this.healthinfo = healthinfo;
        }

        public List<BbsTopPic4Bean> getBbs_top_pic4() {
            return bbs_top_pic4;
        }

        public void setBbs_top_pic4(List<BbsTopPic4Bean> bbs_top_pic4) {
            this.bbs_top_pic4 = bbs_top_pic4;
        }

        public static class UserinfoBean {
            private String uid;
            private String phone;
            private String logname;
            private String sex;
            private String uname;
            private String birth;
            private String ctime;
            private String cid;
            private String ctype;
            private String dev;
            private String loc;
            private String lastlog;
            private String lastip;
            private String lastdev;
            private String logcnt;
            private String head;
            private Object rank;
            private String province_id;
            private String province_name;
            private String city_id;
            private String city_name;
            private String county_id;
            private String county_name;
            private String town_id;
            private String town_name;
            private String village_id;
            private String village_name;
            private String alias_name;

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

            public String getLogname() {
                return logname;
            }

            public void setLogname(String logname) {
                this.logname = logname;
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

            public String getBirth() {
                return birth;
            }

            public void setBirth(String birth) {
                this.birth = birth;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getCtype() {
                return ctype;
            }

            public void setCtype(String ctype) {
                this.ctype = ctype;
            }

            public String getDev() {
                return dev;
            }

            public void setDev(String dev) {
                this.dev = dev;
            }

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getLastlog() {
                return lastlog;
            }

            public void setLastlog(String lastlog) {
                this.lastlog = lastlog;
            }

            public String getLastip() {
                return lastip;
            }

            public void setLastip(String lastip) {
                this.lastip = lastip;
            }

            public String getLastdev() {
                return lastdev;
            }

            public void setLastdev(String lastdev) {
                this.lastdev = lastdev;
            }

            public String getLogcnt() {
                return logcnt;
            }

            public void setLogcnt(String logcnt) {
                this.logcnt = logcnt;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public Object getRank() {
                return rank;
            }

            public void setRank(Object rank) {
                this.rank = rank;
            }

            public String getProvince_id() {
                return province_id;
            }

            public void setProvince_id(String province_id) {
                this.province_id = province_id;
            }

            public String getProvince_name() {
                return province_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getCounty_id() {
                return county_id;
            }

            public void setCounty_id(String county_id) {
                this.county_id = county_id;
            }

            public String getCounty_name() {
                return county_name;
            }

            public void setCounty_name(String county_name) {
                this.county_name = county_name;
            }

            public String getTown_id() {
                return town_id;
            }

            public void setTown_id(String town_id) {
                this.town_id = town_id;
            }

            public String getTown_name() {
                return town_name;
            }

            public void setTown_name(String town_name) {
                this.town_name = town_name;
            }

            public String getVillage_id() {
                return village_id;
            }

            public void setVillage_id(String village_id) {
                this.village_id = village_id;
            }

            public String getVillage_name() {
                return village_name;
            }

            public void setVillage_name(String village_name) {
                this.village_name = village_name;
            }

            public String getAlias_name() {
                return alias_name;
            }

            public void setAlias_name(String alias_name) {
                this.alias_name = alias_name;
            }
        }

        public static class VideoinfoBean {
            private String id;
            private String uid;
            private String url;
            private String ctime;
            private String stats;

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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
        }

        public static class VoiceinfoBean {
            private String id;
            private String uid;
            private String url;
            private String ctime;

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }
        }

        public static class PhotoinfoBean {
            private String id;
            private String uid;
            private String url;
            private String ctime;
            private String surl_1;

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getSurl_1() {
                return surl_1;
            }

            public void setSurl_1(String surl_1) {
                this.surl_1 = surl_1;
            }
        }

        public static class ScoreinfoBean {
            private String k;
            private String v;
            private String s;

            public String getK() {
                return k;
            }

            public void setK(String k) {
                this.k = k;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }

            public String getS() {
                return s;
            }

            public void setS(String s) {
                this.s = s;
            }
        }

        public static class HealthinfoBean {
            private String k;
            private String v;
            private String s;

            public String getK() {
                return k;
            }

            public void setK(String k) {
                this.k = k;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }

            public String getS() {
                return s;
            }

            public void setS(String s) {
                this.s = s;
            }
        }
        public static class BbsTopPic4Bean {
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
