package com.study.mingappk.model.bean;

/**
 * 来自我的村的分享
 * Created by Ming on 2016/9/25.
 */
public class ShareMsg {
    /**
     * title : 代号000
     * detail : 好早啊
     * image : http://121.40.105.149:9901//Public/bbs/file/13114/2016-09-24/20160924s20160924984855975655_1.jpg
     * link : http://121.40.105.149:9901/bbs/bbsinfo?id=7352
     */

    private String title;
    private String detail;
    private String image;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
