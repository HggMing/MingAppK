package com.study.mingappk.api.result;

/**
 * Created by Ming on 2016/3/13.
 */
public class AdviceResult {

    /**
     * err : 0
     * msg : 反馈成功
     */

    private int err;
    private String msg;

    public void setErr(int err) {
        this.err = err;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }
}
