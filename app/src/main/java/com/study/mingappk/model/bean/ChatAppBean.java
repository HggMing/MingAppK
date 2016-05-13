package com.study.mingappk.model.bean;

public class ChatAppBean {

    private int id;
    private int icon;
    private String funcName;

    public int getIcon() {
        return icon;
    }

    public String getFuncName() {
        return funcName;
    }

    public int getId() {
        return id;
    }

    public ChatAppBean(int icon, String funcName){
        this.icon = icon;
        this.funcName = funcName;
    }
}
