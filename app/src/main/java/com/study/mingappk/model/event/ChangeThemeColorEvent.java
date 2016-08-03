package com.study.mingappk.model.event;

import com.study.mingappk.model.bean.BBSList;

import java.util.List;

/**
 * 更改主题颜色
 * Created by Ming on 2016/4/8.
 */
public class ChangeThemeColorEvent {


    private int type;//type=1则是在通用中切换主题

    public ChangeThemeColorEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
