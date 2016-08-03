package com.study.mingappk.model.event;

/**
 * 主页viewpaper滑动时，隐藏好友列表右边字母导航条
 * Created by Ming on 2016/7/29.
 */
public class ShowSideBarEvent {
    private boolean isShow;

    public ShowSideBarEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }
}
