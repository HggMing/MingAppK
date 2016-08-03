package com.study.mingappk.model.event;

/**
 * 发送图片消息事件
 * Created by Ming on 2016/7/27.
 */
public class SendImageEvent {
    String imagePath;

    public SendImageEvent(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

}
