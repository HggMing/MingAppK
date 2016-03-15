package com.study.mingappk.api.result;

/**
 *通用无附带信息的返回
 * Created by Ming on 2016/3/13.
 */
public class Result {

    /**
     * err : 0
     * msg : 反馈结果详细提示内容
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
