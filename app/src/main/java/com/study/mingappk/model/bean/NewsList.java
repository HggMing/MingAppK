package com.study.mingappk.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ming on 2016/6/18.
 */
public class NewsList {
    /**
     * err : 0
     * data : {"cnt":"2","list":[{"id":"1","ntype":"1","maintitle":"标题1","subtitle":"标题2","conts":" 照片的摄影 ","inscribe":"落款","mark":"备注","proviceid":"0","cityid":"0","countyid":"0","townid":"0","vid":"361130103203","postype":"0","ctime":"1466220962"},{"id":"2","ntype":"1","maintitle":"测试标题","subtitle":"测试标题2","conts":"6月18日0时20分","inscribe":"测试落款","mark":"测试备注","proviceid":"0","cityid":"0","countyid":"0","townid":"0","vid":"361130103203","postype":"0","ctime":"1466220862"}]}
     */

    private int err;
    /**
     * cnt : 2
     * list : [{"id":"1","ntype":"1","maintitle":"标题1","subtitle":"标题2","conts":" 照片的摄影 ","inscribe":"落款","mark":"备注","proviceid":"0","cityid":"0","countyid":"0","townid":"0","vid":"361130103203","postype":"0","ctime":"1466220962"},{"id":"2","ntype":"1","maintitle":"测试标题","subtitle":"测试标题2","conts":"6月18日0时20分","inscribe":"测试落款","mark":"测试备注","proviceid":"0","cityid":"0","countyid":"0","townid":"0","vid":"361130103203","postype":"0","ctime":"1466220862"}]
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
         * id : 1
         * ntype : 1
         * maintitle : 标题1
         * subtitle : 标题2
         * conts :  照片的摄影
         * inscribe : 落款
         * mark : 备注
         * proviceid : 0
         * cityid : 0
         * countyid : 0
         * townid : 0
         * vid : 361130103203
         * postype : 0
         * ctime : 1466220962
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
            private String ntype;
            private String maintitle;
            private String subtitle;
            private String conts;
            private String inscribe;
            private String mark;
            private String proviceid;
            private String cityid;
            private String countyid;
            private String townid;
            private String vid;
            private String postype;
            private String ctime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNtype() {
                return ntype;
            }

            public void setNtype(String ntype) {
                this.ntype = ntype;
            }

            public String getMaintitle() {
                return maintitle;
            }

            public void setMaintitle(String maintitle) {
                this.maintitle = maintitle;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getConts() {
                return conts;
            }

            public void setConts(String conts) {
                this.conts = conts;
            }

            public String getInscribe() {
                return inscribe;
            }

            public void setInscribe(String inscribe) {
                this.inscribe = inscribe;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public String getProviceid() {
                return proviceid;
            }

            public void setProviceid(String proviceid) {
                this.proviceid = proviceid;
            }

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public String getCountyid() {
                return countyid;
            }

            public void setCountyid(String countyid) {
                this.countyid = countyid;
            }

            public String getTownid() {
                return townid;
            }

            public void setTownid(String townid) {
                this.townid = townid;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getPostype() {
                return postype;
            }

            public void setPostype(String postype) {
                this.postype = postype;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.ntype);
                dest.writeString(this.maintitle);
                dest.writeString(this.subtitle);
                dest.writeString(this.conts);
                dest.writeString(this.inscribe);
                dest.writeString(this.mark);
                dest.writeString(this.proviceid);
                dest.writeString(this.cityid);
                dest.writeString(this.countyid);
                dest.writeString(this.townid);
                dest.writeString(this.vid);
                dest.writeString(this.postype);
                dest.writeString(this.ctime);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.id = in.readString();
                this.ntype = in.readString();
                this.maintitle = in.readString();
                this.subtitle = in.readString();
                this.conts = in.readString();
                this.inscribe = in.readString();
                this.mark = in.readString();
                this.proviceid = in.readString();
                this.cityid = in.readString();
                this.countyid = in.readString();
                this.townid = in.readString();
                this.vid = in.readString();
                this.postype = in.readString();
                this.ctime = in.readString();
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
