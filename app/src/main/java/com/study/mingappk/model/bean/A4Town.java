package com.study.mingappk.model.bean;

import java.util.List;

/**
 * 镇级列表
 * Created by Ming on 2016/3/19.
 */
public class A4Town {

    /**
     * err : 0
     * data : [{"town_id":"510104020000","town_name":"督院街街道办事处"},{"town_id":"510104021000","town_name":"盐市口街道办事处"},{"town_id":"510104022000","town_name":"春熙路街道办事处"},{"town_id":"510104023000","town_name":"书院街街道办事处"},{"town_id":"510104024000","town_name":"合江亭街道办事处"},{"town_id":"510104025000","town_name":"水井坊街道办事处"},{"town_id":"510104026000","town_name":"牛市口街道办事处"},{"town_id":"510104027000","town_name":"龙舟路街道办事处"},{"town_id":"510104028000","town_name":"双桂路街道办事处"},{"town_id":"510104029000","town_name":"莲新街道办事处"},{"town_id":"510104030000","town_name":"沙河街道办事处"},{"town_id":"510104031000","town_name":"东光街道办事处"},{"town_id":"510104032000","town_name":"狮子山街道办事处"},{"town_id":"510104035000","town_name":"成龙路街道办事处"},{"town_id":"510104036000","town_name":"柳江街道办事处"},{"town_id":"510104037000","town_name":"三圣街道办事处"}]
     */

    private int err;
    /**
     * town_id : 510104020000
     * town_name : 督院街街道办事处
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
        private String town_id;
        private String town_name;

        public void setTown_id(String town_id) {
            this.town_id = town_id;
        }

        public void setTown_name(String town_name) {
            this.town_name = town_name;
        }

        public String getTown_id() {
            return town_id;
        }

        public String getTown_name() {
            return town_name;
        }
    }
}
