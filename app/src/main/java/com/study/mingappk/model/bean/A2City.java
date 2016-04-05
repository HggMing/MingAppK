package com.study.mingappk.model.bean;

import java.util.List;

/**
 * 市级列表
 * Created by Ming on 2016/3/19.
 */
public class A2City {

    /**
     * err : 0
     * data : [{"city_id":"510100000000","city_name":"成都市"},{"city_id":"510300000000","city_name":"自贡市"},{"city_id":"510400000000","city_name":"攀枝花市"},{"city_id":"510500000000","city_name":"泸州市"},{"city_id":"510600000000","city_name":"德阳市"},{"city_id":"510700000000","city_name":"绵阳市"},{"city_id":"510800000000","city_name":"广元市"},{"city_id":"510900000000","city_name":"遂宁市"},{"city_id":"511000000000","city_name":"内江市"},{"city_id":"511100000000","city_name":"乐山市"},{"city_id":"511300000000","city_name":"南充市"},{"city_id":"511400000000","city_name":"眉山市"},{"city_id":"511500000000","city_name":"宜宾市"},{"city_id":"511600000000","city_name":"广安市"},{"city_id":"511700000000","city_name":"达州市"},{"city_id":"511800000000","city_name":"雅安市"},{"city_id":"511900000000","city_name":"巴中市"},{"city_id":"512000000000","city_name":"资阳市"},{"city_id":"513200000000","city_name":"阿坝藏族羌族自治州"},{"city_id":"513300000000","city_name":"甘孜藏族自治州"},{"city_id":"513400000000","city_name":"凉山彝族自治州"}]
     */

    private int err;
    /**
     * city_id : 510100000000
     * city_name : 成都市
     */

    private List<DataEntity> data;

    public void setErr(int err) {
        this.err = err;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getErr() {
        return err;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String city_id;
        private String city_name;

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_id() {
            return city_id;
        }

        public String getCity_name() {
            return city_name;
        }
    }
}
