package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/5/30.
 */
public class QueryVillageList {
    /**
     * err : 0
     * data : [{"id":"13500","province_id":"130","province_name":"河北省","city_id":"130100000000","city_name":"石家庄市","county_id":"130125000000","county_name":"行唐县","town_id":"130125206000","town_name":"上方乡","village_id":"130125206202","village_name":"上方村","bid":"0"},{"id":"157838","province_id":"320","province_name":"江苏省","city_id":"320100000000","city_name":"南京市","county_id":"320117000000","county_name":"溧水区","town_id":"320117104000","town_name":"石湫镇","village_id":"320117104207","village_name":"上方村","bid":"0"},{"id":"183751","province_id":"330","province_name":"浙江省","city_id":"330200000000","city_name":"宁波市","county_id":"330226000000","county_name":"宁海县","town_id":"330226104000","town_name":"一市镇","village_id":"330226104201","village_name":"山上方村","bid":"0"},{"id":"196342","province_id":"330","province_name":"浙江省","city_id":"330700000000","city_name":"金华市","county_id":"330702000000","county_name":"婺城区","town_id":"330702105000","town_name":"蒋堂镇","village_id":"330702105214","village_name":"上方村","bid":"0"},{"id":"199235","province_id":"330","province_name":"浙江省","city_id":"330700000000","city_name":"金华市","county_id":"330781000000","county_name":"兰溪市","town_id":"330781209000","town_name":"柏社乡","village_id":"330781209220","village_name":"上方村","bid":"0"},{"id":"199495","province_id":"330","province_name":"浙江省","city_id":"330700000000","city_name":"金华市","county_id":"330782000000","county_name":"义乌市","town_id":"330782005000","town_name":"后宅街道","village_id":"330782005222","village_name":"上方村","bid":"0"},{"id":"201370","province_id":"330","province_name":"浙江省","city_id":"330800000000","city_name":"衢州市","county_id":"330802000000","county_name":"柯城区","town_id":"330802210000","town_name":"九华乡","village_id":"330802210235","village_name":"上方村","bid":"0"},{"id":"201486","province_id":"330","province_name":"浙江省","city_id":"330800000000","city_name":"衢州市","county_id":"330803000000","county_name":"衢江区","town_id":"330803100000","town_name":"上方镇","village_id":"330803100246","village_name":"上方村","bid":"0"},{"id":"205026","province_id":"330","province_name":"浙江省","city_id":"331000000000","city_name":"台州市","county_id":"331022000000","county_name":"三门县","town_id":"331022102000","town_name":"珠岙镇","village_id":"331022102213","village_name":"上方村","bid":"0"},{"id":"207405","province_id":"330","province_name":"浙江省","city_id":"331000000000","city_name":"台州市","county_id":"331081000000","county_name":"温岭市","town_id":"331081104000","town_name":"新河镇","village_id":"331081104273","village_name":"上方村","bid":"0"},{"id":"210154","province_id":"330","province_name":"浙江省","city_id":"331100000000","city_name":"丽水市","county_id":"331124000000","county_name":"松阳县","town_id":"331124101000","town_name":"古市镇","village_id":"331124101200","village_name":"上方村","bid":"0"},{"id":"238443","province_id":"350","province_name":"福建省","city_id":"350500000000","city_name":"泉州市","county_id":"350582000000","county_name":"晋江市","town_id":"350582109000","town_name":"内坑镇","village_id":"350582109216","village_name":"上方村","bid":"0"},{"id":"256139","province_id":"360","province_name":"江西省","city_id":"360700000000","city_name":"赣州市","county_id":"360731000000","county_name":"于都县","town_id":"360731211000","town_name":"仙下乡","village_id":"360731211202","village_name":"上方村","bid":"0"},{"id":"434246","province_id":"430","province_name":"湖南省","city_id":"430300000000","city_name":"湘潭市","county_id":"430321000000","county_name":"湘潭县","town_id":"430321108000","town_name":"青山桥镇","village_id":"430321108204","village_name":"上方村","bid":"0"},{"id":"549340","province_id":"510","province_name":"四川省","city_id":"510900000000","city_name":"遂宁市","county_id":"510922000000","county_name":"射洪县","town_id":"510922103000","town_name":"金华镇","village_id":"510922103201","village_name":"上方村","bid":"0"}]
     */

    private int err;
    /**
     * id : 13500
     * province_id : 130
     * province_name : 河北省
     * city_id : 130100000000
     * city_name : 石家庄市
     * county_id : 130125000000
     * county_name : 行唐县
     * town_id : 130125206000
     * town_name : 上方乡
     * village_id : 130125206202
     * village_name : 上方村
     * bid : 0
     */

    private List<DataBean> data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
    }
}
