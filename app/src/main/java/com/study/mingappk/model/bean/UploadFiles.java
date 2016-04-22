package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/4/21.
 */
public class UploadFiles {

    /**
     * err : 0
     * msg : 发布成功
     * insert_id : 7
     * url :
     */

    private int err;
    private String msg;
    private String insert_id;
    private String url;

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

    public String getInsert_id() {
        return insert_id;
    }

    public void setInsert_id(String insert_id) {
        this.insert_id = insert_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
