package com.study.mingappk.model.bean;

/**
 *通用信息的基础上，带一些单独属性
 * Created by Ming on 2016/3/13.
 */
public class ResultOther {
    //接口：amount/is_set_pwd
    private int is_pwd;//0未设置钱包密码，1、已设置

    //接口：amount/balance
    private String money;
    private int is_bind;//0未绑定银行卡，1、已绑定

    //接口：amount/bind;express/add
    private String insert_id;


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(int is_bind) {
        this.is_bind = is_bind;
    }

    public int getIs_pwd() {
        return is_pwd;
    }

    public void setIs_pwd(int is_pwd) {
        this.is_pwd = is_pwd;
    }

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
