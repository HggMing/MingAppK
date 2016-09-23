package com.study.mingappk.model.event;

/**
 * Created by Ming on 2016/9/22.
 */
public class UpdataShopOwnerHeadEvent {
    public String getHeadUrl() {
        return headUrl;
    }

    String headUrl;

    public UpdataShopOwnerHeadEvent(String headUrl) {
        this.headUrl = headUrl;
    }
}
