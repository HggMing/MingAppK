package com.study.mingappk.model.bean;

/**
 * Created by Ming on 2016/6/23.
 */
public class ApplyInfo {

    /**
     * err : 0
     * msg : 没有申请记录
     * data : {"id":"167","uid":"13155","uname":"繁华","phone":"19910001002","vid":"511425110222","head":"","conts":"","ctime":"1466649684","stats":"0","memos":"","contact":"","sex":"1","edu":"本科","cid_img1":"4686","cid_img2":"4687","q_img":"","brithday":"1990-10-29"}
     */

    private int err;
    private String msg;
    /**
     * id : 167
     * uid : 13155
     * uname : 繁华
     * phone : 19910001002
     * vid : 511425110222
     * head :
     * conts :
     * ctime : 1466649684
     * stats : 0   //0审核中，1通过，2未通过
     * memos :
     * contact :
     * sex : 1
     * edu : 本科
     * cid_img1 : 4686
     * cid_img2 : 4687
     * q_img :
     * brithday : 1990-10-29
     */

    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String uid;
        private String uname;
        private String phone;
        private String vid;
        private String head;
        private String conts;
        private String ctime;
        private String stats;
        private String memos;
        private String contact;
        private String sex;
        private String edu;
        private String cid_img1;
        private String cid_img2;
        private String q_img;
        private String brithday;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getConts() {
            return conts;
        }

        public void setConts(String conts) {
            this.conts = conts;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getStats() {
            return stats;
        }

        public void setStats(String stats) {
            this.stats = stats;
        }

        public String getMemos() {
            return memos;
        }

        public void setMemos(String memos) {
            this.memos = memos;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getEdu() {
            return edu;
        }

        public void setEdu(String edu) {
            this.edu = edu;
        }

        public String getCid_img1() {
            return cid_img1;
        }

        public void setCid_img1(String cid_img1) {
            this.cid_img1 = cid_img1;
        }

        public String getCid_img2() {
            return cid_img2;
        }

        public void setCid_img2(String cid_img2) {
            this.cid_img2 = cid_img2;
        }

        public String getQ_img() {
            return q_img;
        }

        public void setQ_img(String q_img) {
            this.q_img = q_img;
        }

        public String getBrithday() {
            return brithday;
        }

        public void setBrithday(String brithday) {
            this.brithday = brithday;
        }
    }
}
