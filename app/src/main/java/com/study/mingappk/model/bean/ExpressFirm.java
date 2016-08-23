package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/8/18.
 */
public class ExpressFirm {
    /**
     * err : 0
     * data : [{"id":"1","shipname":"中通","stats":"0"},{"id":"2","shipname":"圆通","stats":"0"},{"id":"3","shipname":"申通","stats":"0"},{"id":"6","shipname":"优速","stats":"0"},{"id":"7","shipname":"韵达","stats":"0"},{"id":"8","shipname":"百世汇通","stats":"0"},{"id":"9","shipname":"快捷","stats":"0"}]
     */

    private int err;
    /**
     * id : 1
     * shipname : 中通
     * stats : 0
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
        private String shipname;
        private String stats;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShipname() {
            return shipname;
        }

        public void setShipname(String shipname) {
            this.shipname = shipname;
        }

        public String getStats() {
            return stats;
        }

        public void setStats(String stats) {
            this.stats = stats;
        }
    }
}
