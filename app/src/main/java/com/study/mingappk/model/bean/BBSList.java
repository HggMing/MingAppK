package com.study.mingappk.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ming on 2016/4/1.
 */
public class BBSList {

    /**
     * err : 0
     * data : {"cnt":"12","list":[{"id":"1937","title":"","conts":"发动图","ctime":"1461224000","uid":"12018","uname":"忘记你我做不到","vid":"510922103201","source":"0","stats":"1","nums":"0","pic":"","zans":"0","is_manage":"2","pic_1":"","files":[{"id":"2287","pid":"1937","uid":"12018","url":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102.gif","surl_1":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102_1.gif","surl_2":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102_2.gif","stats":"0"}],"my_is_zan":0,"userinfo":{"uid":"12018","phone":"18140006179","uname":"忘记你我做不到","head":"/Public/head/12018/2JKGLST1ZQUR1460997660.jpg"}}]}
     */

    private int err;
    /**
     * cnt : 12
     * list : [{"id":"1937","title":"","conts":"发动图","ctime":"1461224000","uid":"12018","uname":"忘记你我做不到","vid":"510922103201","source":"0","stats":"1","nums":"0","pic":"","zans":"0","is_manage":"2","pic_1":"","files":[{"id":"2287","pid":"1937","uid":"12018","url":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102.gif","surl_1":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102_1.gif","surl_2":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102_2.gif","stats":"0"}],"my_is_zan":0,"userinfo":{"uid":"12018","phone":"18140006179","uname":"忘记你我做不到","head":"/Public/head/12018/2JKGLST1ZQUR1460997660.jpg"}}]
     */

    private DataEntity data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String cnt;
        /**
         * id : 1937
         * title :
         * conts : 发动图
         * ctime : 1461224000
         * uid : 12018
         * uname : 忘记你我做不到
         * vid : 510922103201
         * source : 0
         * stats : 1
         * nums : 0
         * pic :
         * zans : 0
         * is_manage : 2
         * pic_1 :
         * files : [{"id":"2287","pid":"1937","uid":"12018","url":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102.gif","surl_1":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102_1.gif","surl_2":"/Public/bbs/file/12018/2016-04-21/2016042120160421100505555102_2.gif","stats":"0"}]
         * my_is_zan : 0
         * userinfo : {"uid":"12018","phone":"18140006179","uname":"忘记你我做不到","head":"/Public/head/12018/2JKGLST1ZQUR1460997660.jpg"}
         */

        private List<ListEntity> list;

        public String getCnt() {
            return cnt;
        }

        public void setCnt(String cnt) {
            this.cnt = cnt;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public static class ListEntity implements Parcelable {
            private String id;
            private String title;
            private String conts;
            private String ctime;
            private String uid;
            private String uname;
            private String vid;
            private String source;
            private String stats;
            private String nums;
            private String pic;
            private String zans;
            private String is_manage;
            private String pic_1;
            private int my_is_zan;
            /**
             * uid : 12018
             * phone : 18140006179
             * uname : 忘记你我做不到
             * head : /Public/head/12018/2JKGLST1ZQUR1460997660.jpg
             */

            private UserinfoEntity userinfo;
            /**
             * id : 2287
             * pid : 1937
             * uid : 12018
             * url : /Public/bbs/file/12018/2016-04-21/2016042120160421100505555102.gif
             * surl_1 : /Public/bbs/file/12018/2016-04-21/2016042120160421100505555102_1.gif
             * surl_2 : /Public/bbs/file/12018/2016-04-21/2016042120160421100505555102_2.gif
             * stats : 0
             */

            private List<FilesEntity> files;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getStats() {
                return stats;
            }

            public void setStats(String stats) {
                this.stats = stats;
            }

            public String getNums() {
                return nums;
            }

            public void setNums(String nums) {
                this.nums = nums;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getZans() {
                return zans;
            }

            public void setZans(String zans) {
                this.zans = zans;
            }

            public String getIs_manage() {
                return is_manage;
            }

            public void setIs_manage(String is_manage) {
                this.is_manage = is_manage;
            }

            public String getPic_1() {
                return pic_1;
            }

            public void setPic_1(String pic_1) {
                this.pic_1 = pic_1;
            }

            public int getMy_is_zan() {
                return my_is_zan;
            }

            public void setMy_is_zan(int my_is_zan) {
                this.my_is_zan = my_is_zan;
            }

            public UserinfoEntity getUserinfo() {
                return userinfo;
            }

            public void setUserinfo(UserinfoEntity userinfo) {
                this.userinfo = userinfo;
            }

            public List<FilesEntity> getFiles() {
                return files;
            }

            public void setFiles(List<FilesEntity> files) {
                this.files = files;
            }

            public static class UserinfoEntity implements Parcelable {
                private String uid;
                private String phone;
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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.uid);
                    dest.writeString(this.phone);
                    dest.writeString(this.uname);
                    dest.writeString(this.head);
                }

                public UserinfoEntity() {
                }

                protected UserinfoEntity(Parcel in) {
                    this.uid = in.readString();
                    this.phone = in.readString();
                    this.uname = in.readString();
                    this.head = in.readString();
                }

                public static final Parcelable.Creator<UserinfoEntity> CREATOR = new Parcelable.Creator<UserinfoEntity>() {
                    @Override
                    public UserinfoEntity createFromParcel(Parcel source) {
                        return new UserinfoEntity(source);
                    }

                    @Override
                    public UserinfoEntity[] newArray(int size) {
                        return new UserinfoEntity[size];
                    }
                };
            }

            public static class FilesEntity implements Parcelable {
                private String id;
                private String pid;
                private String uid;
                private String url;
                private String surl_1;
                private String surl_2;
                private String stats;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getSurl_1() {
                    return surl_1;
                }

                public void setSurl_1(String surl_1) {
                    this.surl_1 = surl_1;
                }

                public String getSurl_2() {
                    return surl_2;
                }

                public void setSurl_2(String surl_2) {
                    this.surl_2 = surl_2;
                }

                public String getStats() {
                    return stats;
                }

                public void setStats(String stats) {
                    this.stats = stats;
                }

                public FilesEntity() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.id);
                    dest.writeString(this.pid);
                    dest.writeString(this.uid);
                    dest.writeString(this.url);
                    dest.writeString(this.surl_1);
                    dest.writeString(this.surl_2);
                    dest.writeString(this.stats);
                }

                protected FilesEntity(Parcel in) {
                    this.id = in.readString();
                    this.pid = in.readString();
                    this.uid = in.readString();
                    this.url = in.readString();
                    this.surl_1 = in.readString();
                    this.surl_2 = in.readString();
                    this.stats = in.readString();
                }

                public static final Creator<FilesEntity> CREATOR = new Creator<FilesEntity>() {
                    @Override
                    public FilesEntity createFromParcel(Parcel source) {
                        return new FilesEntity(source);
                    }

                    @Override
                    public FilesEntity[] newArray(int size) {
                        return new FilesEntity[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.title);
                dest.writeString(this.conts);
                dest.writeString(this.ctime);
                dest.writeString(this.uid);
                dest.writeString(this.uname);
                dest.writeString(this.vid);
                dest.writeString(this.source);
                dest.writeString(this.stats);
                dest.writeString(this.nums);
                dest.writeString(this.pic);
                dest.writeString(this.zans);
                dest.writeString(this.is_manage);
                dest.writeString(this.pic_1);
                dest.writeInt(this.my_is_zan);
                dest.writeParcelable(this.userinfo, flags);
                dest.writeTypedList(files);
            }

            public ListEntity() {
            }

            protected ListEntity(Parcel in) {
                this.id = in.readString();
                this.title = in.readString();
                this.conts = in.readString();
                this.ctime = in.readString();
                this.uid = in.readString();
                this.uname = in.readString();
                this.vid = in.readString();
                this.source = in.readString();
                this.stats = in.readString();
                this.nums = in.readString();
                this.pic = in.readString();
                this.zans = in.readString();
                this.is_manage = in.readString();
                this.pic_1 = in.readString();
                this.my_is_zan = in.readInt();
                this.userinfo = in.readParcelable(UserinfoEntity.class.getClassLoader());
                this.files = in.createTypedArrayList(FilesEntity.CREATOR);
            }

            public static final Parcelable.Creator<ListEntity> CREATOR = new Parcelable.Creator<ListEntity>() {
                @Override
                public ListEntity createFromParcel(Parcel source) {
                    return new ListEntity(source);
                }

                @Override
                public ListEntity[] newArray(int size) {
                    return new ListEntity[size];
                }
            };
        }
    }
}
