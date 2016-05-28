package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/5/26.
 */
public class RecommendVillage {

    /**
     * err : 0
     * data : [{"vid":"361130000000","name":"婺源县","type":"county"},{"vid":"511425000000","name":"青神县","type":"county"}]
     */

    private int err;
    /**
     * vid : 361130000000
     * name : 婺源县
     * type : county
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
        private String vid;
        private String name;
        private String type;

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
