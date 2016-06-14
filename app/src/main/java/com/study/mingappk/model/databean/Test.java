package com.study.mingappk.model.databean;

/**
 * Created by Ming on 2016/6/13.
 */
public class Test {

    private String name;

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String idnum;

    public Test(String name, String idnum) {
        this.name = name;
        this.idnum = idnum;
    }

}
