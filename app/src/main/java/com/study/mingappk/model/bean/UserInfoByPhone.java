package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/8/1.
 */
public class UserInfoByPhone {

    /**
     * err : 0
     * msg : 查找的用户
     * data : {"uid":"13114","uname":"代号000","sex":"0","head":"/Public/head/13114/VSY8MLT1GPAB1460598788.jpg","province_id":"360","province_name":"江西省","city_id":"361100000000","city_name":"上饶市","county_id":"361130000000","county_name":"婺源县","town_id":"361130103000","town_name":"江湾镇","village_id":"361130103203","village_name":"汪口村"}
     */

    private int err;
    private String msg;
    /**
     * uid : 13114
     * uname : 代号000
     * sex : 0
     * head : /Public/head/13114/VSY8MLT1GPAB1460598788.jpg
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
        private String uid;
        private String uname;
        private String sex;
        private String head;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
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
    }
}
