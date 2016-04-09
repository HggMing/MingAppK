package com.study.mingappk.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ming on 2016/4/1.
 */
public class BBSListResult {

    /**
     * err : 0
     * data : {"cnt":"44","list":[{"id":"270","title":"","conts":"美丽的乡村风景","ctime":"1457493158","uid":"11022","uname":"Macheal Jackson","vid":"361130103203","source":"1","stats":"1","nums":"1","pic":"/Public/bbs/11022/2016-03-09/2016030920160309545749565550.png","zans":"5","is_manage":"2","pic_1":"","files":[{"id":"259","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309485597100101.png","surl_1":"","surl_2":""},{"id":"260","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309524810152509.png","surl_1":"","surl_2":""},{"id":"261","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309551015110199.png","surl_1":"","surl_2":""},{"id":"262","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309101514955575.png","surl_1":"","surl_2":""},{"id":"263","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309505710252485.png","surl_1":"","surl_2":""},{"id":"264","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309569910151554.png","surl_1":"","surl_2":""},{"id":"265","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309995510251100.png","surl_1":"","surl_2":""},{"id":"266","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309485251975299.png","surl_1":"","surl_2":""},{"id":"267","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309529948989857.png","surl_1":"","surl_2":""}],"userinfo":{"uid":"11022","phone":"13800138009","uname":"小张Zhang","head":"/Public/head/11022/5TR0QHT05SJQ1459417673.jpg"}}]}
     */

    private int err;
    /**
     * cnt : 44
     * list : [{"id":"270","title":"","conts":"美丽的乡村风景","ctime":"1457493158","uid":"11022","uname":"Macheal Jackson","vid":"361130103203","source":"1","stats":"1","nums":"1","pic":"/Public/bbs/11022/2016-03-09/2016030920160309545749565550.png","zans":"5","is_manage":"2","pic_1":"","files":[{"id":"259","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309485597100101.png","surl_1":"","surl_2":""},{"id":"260","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309524810152509.png","surl_1":"","surl_2":""},{"id":"261","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309551015110199.png","surl_1":"","surl_2":""},{"id":"262","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309101514955575.png","surl_1":"","surl_2":""},{"id":"263","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309505710252485.png","surl_1":"","surl_2":""},{"id":"264","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309569910151554.png","surl_1":"","surl_2":""},{"id":"265","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309995510251100.png","surl_1":"","surl_2":""},{"id":"266","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309485251975299.png","surl_1":"","surl_2":""},{"id":"267","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309529948989857.png","surl_1":"","surl_2":""}],"userinfo":{"uid":"11022","phone":"13800138009","uname":"小张Zhang","head":"/Public/head/11022/5TR0QHT05SJQ1459417673.jpg"}}]
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
         * id : 270
         * title :
         * conts : 美丽的乡村风景
         * ctime : 1457493158
         * uid : 11022
         * uname : Macheal Jackson
         * vid : 361130103203
         * source : 1
         * stats : 1
         * nums : 1
         * pic : /Public/bbs/11022/2016-03-09/2016030920160309545749565550.png
         * zans : 5
         * is_manage : 2
         * pic_1 :
         * files : [{"id":"259","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309485597100101.png","surl_1":"","surl_2":""},{"id":"260","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309524810152509.png","surl_1":"","surl_2":""},{"id":"261","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309551015110199.png","surl_1":"","surl_2":""},{"id":"262","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309101514955575.png","surl_1":"","surl_2":""},{"id":"263","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309505710252485.png","surl_1":"","surl_2":""},{"id":"264","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309569910151554.png","surl_1":"","surl_2":""},{"id":"265","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309995510251100.png","surl_1":"","surl_2":""},{"id":"266","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309485251975299.png","surl_1":"","surl_2":""},{"id":"267","pid":"270","uid":"11022","url":"/Public/bbs/file/11022/2016-03-09/2016030920160309529948989857.png","surl_1":"","surl_2":""}]
         * userinfo : {"uid":"11022","phone":"13800138009","uname":"小张Zhang","head":"/Public/head/11022/5TR0QHT05SJQ1459417673.jpg"}
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

        public static class ListEntity {
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
            /**
             * uid : 11022
             * phone : 13800138009
             * uname : 小张Zhang
             * head : /Public/head/11022/5TR0QHT05SJQ1459417673.jpg
             */

            private UserinfoEntity userinfo;
            /**
             * id : 259
             * pid : 270
             * uid : 11022
             * url : /Public/bbs/file/11022/2016-03-09/2016030920160309485597100101.png
             * surl_1 :
             * surl_2 :
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

            public static class UserinfoEntity {
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
            }

            public static class FilesEntity implements Parcelable {
                private String id;
                private String pid;
                private String uid;
                private String url;
                private String surl_1;
                private String surl_2;

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
                }

                public FilesEntity() {
                }

                protected FilesEntity(Parcel in) {
                    this.id = in.readString();
                    this.pid = in.readString();
                    this.uid = in.readString();
                    this.url = in.readString();
                    this.surl_1 = in.readString();
                    this.surl_2 = in.readString();
                }

                public static final Parcelable.Creator<FilesEntity> CREATOR = new Parcelable.Creator<FilesEntity>() {
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
        }
    }
}
