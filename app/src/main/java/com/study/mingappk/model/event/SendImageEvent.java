package com.study.mingappk.model.event;

/**
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
