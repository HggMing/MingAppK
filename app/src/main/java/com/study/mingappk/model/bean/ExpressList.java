package com.study.mingappk.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ming on 2016/8/18.
 */
public class ExpressList {
    /**
     * err : 0
     * data : {"cnt":"2","list":[{"id":"413","vid":"510107007006","number":"9191484854544848","status":"2","ship":"圆通","ship_fee":"0","jijianren":"","jphone":"","qujianren":"不然","qphone":"19910001002","ctime":"1471495295","jtime":"0","qtime":"0","cardid":"","addr":"","qucardid":"","uname":""},{"id":"412","vid":"510107007006","number":"499494949","status":"2","ship":"圆通","ship_fee":"0","jijianren":"","jphone":"","qujianren":"不对不对吧","qphone":"","ctime":"1471495254","jtime":"0","qtime":"0","cardid":"","addr":"","qucardid":"","uname":""}]}
     */

    private int err;
    /**
     * cnt : 2
     * list : [{"id":"413","vid":"510107007006","number":"9191484854544848","status":"2","ship":"圆通","ship_fee":"0","jijianren":"","jphone":"","qujianren":"不然","qphone":"19910001002","ctime":"1471495295","jtime":"0","qtime":"0","cardid":"","addr":"","qucardid":"","uname":""},{"id":"412","vid":"510107007006","number":"499494949","status":"2","ship":"圆通","ship_fee":"0","jijianren":"","jphone":"","qujianren":"不对不对吧","qphone":"","ctime":"1471495254","jtime":"0","qtime":"0","cardid":"","addr":"","qucardid":"","uname":""}]
     */

    private DataBean data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String cnt;
        /**
         * id : 413
         * vid : 510107007006
         * number : 9191484854544848
         * status : 2
         * ship : 圆通
         * ship_fee : 0
         * jijianren :
         * jphone :
         * qujianren : 不然
         * qphone : 19910001002
         * ctime : 1471495295
         * jtime : 0
         * qtime : 0
         * cardid :
         * addr :
         * qucardid :
         * uname :
         */

        private List<ListBean> list;

        public String getCnt() {
            return cnt;
        }

        public void setCnt(String cnt) {
            this.cnt = cnt;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable {
            private String id;
            private String vid;
            private String number;
            private String status;
            private String ship;
            private String ship_fee;
            private String jijianren;
            private String jphone;
            private String qujianren;
            private String qphone;
            private String ctime;
            private String jtime;
            private String qtime;
            private String cardid;
            private String addr;
            private String qucardid;
            private String uname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getShip() {
                return ship;
            }

            public void setShip(String ship) {
                this.ship = ship;
            }

            public String getShip_fee() {
                return ship_fee;
            }

            public void setShip_fee(String ship_fee) {
                this.ship_fee = ship_fee;
            }

            public String getJijianren() {
                return jijianren;
            }

            public void setJijianren(String jijianren) {
                this.jijianren = jijianren;
            }

            public String getJphone() {
                return jphone;
            }

            public void setJphone(String jphone) {
                this.jphone = jphone;
            }

            public String getQujianren() {
                return qujianren;
            }

            public void setQujianren(String qujianren) {
                this.qujianren = qujianren;
            }

            public String getQphone() {
                return qphone;
            }

            public void setQphone(String qphone) {
                this.qphone = qphone;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getJtime() {
                return jtime;
            }

            public void setJtime(String jtime) {
                this.jtime = jtime;
            }

            public String getQtime() {
                return qtime;
            }

            public void setQtime(String qtime) {
                this.qtime = qtime;
            }

            public String getCardid() {
                return cardid;
            }

            public void setCardid(String cardid) {
                this.cardid = cardid;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getQucardid() {
                return qucardid;
            }

            public void setQucardid(String qucardid) {
                this.qucardid = qucardid;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.vid);
                dest.writeString(this.number);
                dest.writeString(this.status);
                dest.writeString(this.ship);
                dest.writeString(this.ship_fee);
                dest.writeString(this.jijianren);
                dest.writeString(this.jphone);
                dest.writeString(this.qujianren);
                dest.writeString(this.qphone);
                dest.writeString(this.ctime);
                dest.writeString(this.jtime);
                dest.writeString(this.qtime);
                dest.writeString(this.cardid);
                dest.writeString(this.addr);
                dest.writeString(this.qucardid);
                dest.writeString(this.uname);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.id = in.readString();
                this.vid = in.readString();
                this.number = in.readString();
                this.status = in.readString();
                this.ship = in.readString();
                this.ship_fee = in.readString();
                this.jijianren = in.readString();
                this.jphone = in.readString();
                this.qujianren = in.readString();
                this.qphone = in.readString();
                this.ctime = in.readString();
                this.jtime = in.readString();
                this.qtime = in.readString();
                this.cardid = in.readString();
                this.addr = in.readString();
                this.qucardid = in.readString();
                this.uname = in.readString();
            }

            public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }
    }
}
