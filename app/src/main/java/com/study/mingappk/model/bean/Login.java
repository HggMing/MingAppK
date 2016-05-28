package com.study.mingappk.model.bean;

/**
 * 登录信息 返回
 * Created by Ming on 2016/3/11.
 */
public class Login {

    /**
     * err : 0
     * msg : 验证通过
     * info : {"uid":"12018","phone":"18140006179","logname":"18140006179","sex":"0","uname":"一拳","birth":"0","ctime":"1456132445","cid":"","loc":"","lastlog":"1457651689","lastip":"171.213.53.193","lastdev":"0"}
     * auth : tVU2yacQlgHvuSCohxm0zSbWpKyj1rK4We3N8KNvoGsqKmYH0YzOOg==
     */

    private int err;
    private String msg;
    /**
     * uid : 12018
     * phone : 18140006179
     * logname : 18140006179
     * sex : 0
     * uname : 一拳
     * birth : 0
     * ctime : 1456132445
     * cid :
     * loc :
     * lastlog : 1457651689
     * lastip : 171.213.53.193
     * lastdev : 0
     */

    private InfoEntity info;
    private String auth;

    /**
     * 510922103201 : {"id":"549340","province_id":"510","province_name":"四川省","city_id":"510900000000","city_name":"遂宁市","county_id":"510922000000","county_name":"射洪县","town_id":"510922103000","town_name":"金华镇","village_id":"510922103201","village_name":"上方村","bid":"0"}
     */


    public ShopownerBean getShopowner() {
        return shopowner;
    }

    public void setShopowner(ShopownerBean shopowner) {
        this.shopowner = shopowner;
    }

    /**
     * is_shopowner : 1
     * manager_vid : 510922103201
     */

    private ShopownerBean shopowner;

    /**
     * 510922103201 : {"id":"549340","province_id":"510","province_name":"四川省","city_id":"510900000000","city_name":"遂宁市","county_id":"510922000000","county_name":"射洪县","town_id":"510922103000","town_name":"金华镇","village_id":"510922103201","village_name":"上方村","bid":"0"}
     */


    public void setErr(int err) {
        this.err = err;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public String getAuth() {
        return auth;
    }

    public static class InfoEntity {
        private String uid;
        private String phone;
        private String logname;
        private String sex;
        private String uname;
        private String birth;
        private String ctime;
        private String cid;
        private String loc;
        private String lastlog;
        private String lastip;
        private String lastdev;

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setLogname(String logname) {
            this.logname = logname;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public void setLastlog(String lastlog) {
            this.lastlog = lastlog;
        }

        public void setLastip(String lastip) {
            this.lastip = lastip;
        }

        public void setLastdev(String lastdev) {
            this.lastdev = lastdev;
        }

        public String getUid() {
            return uid;
        }

        public String getPhone() {
            return phone;
        }

        public String getLogname() {
            return logname;
        }

        public String getSex() {
            return sex;
        }

        public String getUname() {
            return uname;
        }

        public String getBirth() {
            return birth;
        }

        public String getCtime() {
            return ctime;
        }

        public String getCid() {
            return cid;
        }

        public String getLoc() {
            return loc;
        }

        public String getLastlog() {
            return lastlog;
        }

        public String getLastip() {
            return lastip;
        }

        public String getLastdev() {
            return lastdev;
        }
    }

    public static class ShopownerBean {
        private int is_shopowner;
        private String manager_vid;

        public int getIs_shopowner() {
            return is_shopowner;
        }

        public void setIs_shopowner(int is_shopowner) {
            this.is_shopowner = is_shopowner;
        }

        public String getManager_vid() {
            return manager_vid;
        }

        public void setManager_vid(String manager_vid) {
            this.manager_vid = manager_vid;
        }
    }
}
