package com.study.mingappk.model.bean;

import java.util.List;

/**
 * 省份列表
 * Created by Ming on 2016/3/19.
 */
public class A1Provice {

    /**
     * err : 0
     * data : [{"provice_id":"110","provice_name":"北京市"},{"provice_id":"120","provice_name":"天津市"},{"provice_id":"130","provice_name":"河北省"},{"provice_id":"140","provice_name":"山西省"},{"provice_id":"150","provice_name":"内蒙古自治区"},{"provice_id":"210","provice_name":"辽宁省"},{"provice_id":"220","provice_name":"吉林省"},{"provice_id":"230","provice_name":"黑龙江省"},{"provice_id":"310","provice_name":"上海市"},{"provice_id":"320","provice_name":"江苏省"},{"provice_id":"330","provice_name":"浙江省"},{"provice_id":"340","provice_name":"安徽省"},{"provice_id":"350","provice_name":"福建省"},{"provice_id":"360","provice_name":"江西省"},{"provice_id":"370","provice_name":"山东省"},{"provice_id":"410","provice_name":"河南省"},{"provice_id":"420","provice_name":"湖北省"},{"provice_id":"430","provice_name":"湖南省"},{"provice_id":"440","provice_name":"广东省"},{"provice_id":"450","provice_name":"广西壮族自治区"},{"provice_id":"460","provice_name":"海南省"},{"provice_id":"500","provice_name":"重庆市"},{"provice_id":"510","provice_name":"四川省"},{"provice_id":"520","provice_name":"贵州省"},{"provice_id":"530","provice_name":"云南省"},{"provice_id":"540","provice_name":"西藏自治区"},{"provice_id":"610","provice_name":"陕西省"},{"provice_id":"620","provice_name":"甘肃省"},{"provice_id":"630","provice_name":"青海省"},{"provice_id":"640","provice_name":"宁夏回族自治区"},{"provice_id":"650","provice_name":"新疆维吾尔自治区"}]
     */

    private int err;
    /**
     * provice_id : 110
     * provice_name : 北京市
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
        private String provice_id;
        private String provice_name;

        public void setProvice_id(String provice_id) {
            this.provice_id = provice_id;
        }

        public void setProvice_name(String provice_name) {
            this.provice_name = provice_name;
        }

        public String getProvice_id() {
            return provice_id;
        }

        public String getProvice_name() {
            return provice_name;
        }
    }
}
