package com.study.mingappk.model.event;

/**
 * Created by Ming on 2016/7/14.
 */
public class DeBugEvent {
    private String msg;

    public DeBugEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
