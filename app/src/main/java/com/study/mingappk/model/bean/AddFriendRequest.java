package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/8/1.
 */
public class AddFriendRequest {

    /**
     * msg : 姓名:哎呀不错,手机号：19910001004 请求加您为好友，是否同意？
     * url_1 : http://121.40.105.149:9901/friend/agree?uid=13177
     * title_1 : 同意
     * url_2 : http://121.40.105.149:9901/friend/unagree?uid=13177
     * title_2 : 不同意
     * uinfo : {"uid":"13177","phone":"19910001004","sex":"1","uname":"哎呀不错","head":"/Public/head/13177/UBHTHUTUOX961467981351.jpg"}
     */

    private String msg;
    private String url_1;
    private String title_1;
    private String url_2;
    private String title_2;
    /**
     * uid : 13177
     * phone : 19910001004
     * sex : 1
     * uname : 哎呀不错
     * head : /Public/head/13177/UBHTHUTUOX961467981351.jpg
     */

    private UinfoBean uinfo;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl_1() {
        return url_1;
    }

    public void setUrl_1(String url_1) {
        this.url_1 = url_1;
    }

    public String getTitle_1() {
        return title_1;
    }

    public void setTitle_1(String title_1) {
        this.title_1 = title_1;
    }

    public String getUrl_2() {
        return url_2;
    }

    public void setUrl_2(String url_2) {
        this.url_2 = url_2;
    }

    public String getTitle_2() {
        return title_2;
    }

    public void setTitle_2(String title_2) {
        this.title_2 = title_2;
    }

    public UinfoBean getUinfo() {
        return uinfo;
    }

    public void setUinfo(UinfoBean uinfo) {
        this.uinfo = uinfo;
    }

    public static class UinfoBean {
        private String uid;
        private String phone;
        private String sex;
        private String uname;
        private String head;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }
    }
}
