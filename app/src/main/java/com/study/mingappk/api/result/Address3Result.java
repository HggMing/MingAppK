package com.study.mingappk.api.result;

import java.util.List;

/**
 * 县级列表
 * Created by Ming on 2016/3/19.
 */
public class Address3Result {

    /**
     * err : 0
     * data : [{"county_id":"510104000000","county_name":"锦江区"},{"county_id":"510105000000","county_name":"青羊区"},{"county_id":"510106000000","county_name":"金牛区"},{"county_id":"510107000000","county_name":"武侯区"},{"county_id":"510108000000","county_name":"成华区"},{"county_id":"510112000000","county_name":"龙泉驿区"},{"county_id":"510113000000","county_name":"青白江区"},{"county_id":"510114000000","county_name":"新都区"},{"county_id":"510115000000","county_name":"温江区"},{"county_id":"510121000000","county_name":"金堂县"},{"county_id":"510122000000","county_name":"双流县"},{"county_id":"510124000000","county_name":"郫县"},{"county_id":"510129000000","county_name":"大邑县"},{"county_id":"510131000000","county_name":"蒲江县"},{"county_id":"510132000000","county_name":"新津县"},{"county_id":"510181000000","county_name":"都江堰市"},{"county_id":"510182000000","county_name":"彭州市"},{"county_id":"510183000000","county_name":"邛崃市"},{"county_id":"510184000000","county_name":"崇州市"}]
     */

    private int err;
    /**
     * county_id : 510104000000
     * county_name : 锦江区
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
        private String county_id;
        private String county_name;

        public void setCounty_id(String county_id) {
            this.county_id = county_id;
        }

        public void setCounty_name(String county_name) {
            this.county_name = county_name;
        }

        public String getCounty_id() {
            return county_id;
        }

        public String getCounty_name() {
            return county_name;
        }
    }
}
