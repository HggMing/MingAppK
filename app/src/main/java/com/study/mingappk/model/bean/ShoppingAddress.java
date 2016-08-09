package com.study.mingappk.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ming on 2016/8/7.
 */
public class ShoppingAddress {
    /**
     * err : 0
     * data : [{"id":"87","uid":"13114","uname":"老大","tel":"19910001000","addr":"成都金牛区","is_def":"1","zipcode":"610000"}]
     */

    private int err;
    /**
     * id : 87
     * uid : 13114
     * uname : 老大
     * tel : 19910001000
     * addr : 成都金牛区
     * is_def : 1
     * zipcode : 610000
     */

    private List<DataBean> data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private String id;
        private String uid;
        private String uname;
        private String tel;
        private String addr;
        private String is_def;
        private String zipcode;

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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getIs_def() {
            return is_def;
        }

        public void setIs_def(String is_def) {
            this.is_def = is_def;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.uid);
            dest.writeString(this.uname);
            dest.writeString(this.tel);
            dest.writeString(this.addr);
            dest.writeString(this.is_def);
            dest.writeString(this.zipcode);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.uid = in.readString();
            this.uname = in.readString();
            this.tel = in.readString();
            this.addr = in.readString();
            this.is_def = in.readString();
            this.zipcode = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
