package com.study.mingappk.model.bean;

import java.util.List;

/**
 * 村级列表
 * Created by Ming on 2016/3/19.
 */
public class A5Village {

    /**
     * err : 0
     * data : [{"village_id":"510104020007","village_name":"青石桥社区"},{"village_id":"510104020008","village_name":"滨江路社区"},{"village_id":"510104020009","village_name":"督院街社区"}]
     */

    private int err;
    /**
     * village_id : 510104020007
     * village_name : 青石桥社区
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
        private String village_id;
        private String village_name;

        public void setVillage_id(String village_id) {
            this.village_id = village_id;
        }

        public void setVillage_name(String village_name) {
            this.village_name = village_name;
        }

        public String getVillage_id() {
            return village_id;
        }

        public String getVillage_name() {
            return village_name;
        }
    }
}
