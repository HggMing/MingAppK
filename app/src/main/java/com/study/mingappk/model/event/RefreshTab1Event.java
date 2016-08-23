package com.study.mingappk.model.event;

/**
 * 刷新tab1上的新消息计数
 * Created by Ming on 2016/8/19.
 */
public class RefreshTab1Event {
    public int getCount() {
        return count;
    }

    int count;

    public RefreshTab1Event(int count) {
        this.count = count;
    }
}
