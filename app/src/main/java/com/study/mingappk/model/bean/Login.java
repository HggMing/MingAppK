package com.study.mingappk.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

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


    public Map<String, VidInfoBean> getVid_info() {
        return vid_info;
    }

    public void setVid_info(Map<String, VidInfoBean> vid_info) {
        this.vid_info = vid_info;
    }

    private Map<String,VidInfoBean> vid_info;

    public static class VidInfoBean {
        /**
         * id : 244602
         * province_id : 350
         * province_name : 福建省
         * city_id : 350800000000
         * city_name : 龙岩市
         * county_id : 350881000000
         * county_name : 漳平市
         * town_id : 350881100000
         * town_name : 新桥镇
         * village_id : 350881100203
         * village_name : 西埔村
         * bid : 0
         * pwd : null
         */

        private String id;
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
        private String bid;
        private Object pwd;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public Object getPwd() {
            return pwd;
        }

        public void setPwd(Object pwd) {
            this.pwd = pwd;
        }
    }

}
