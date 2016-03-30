package com.study.mingappk.api.result;

import java.util.List;

/**
 * Created by Ming on 2016/3/30.
 */
public class FollowVillageListResult {


    /**
     * err : 0
     * data : {"cnt":"20","list":[{"village_id":"361130103203","village_name":"汪口村","pic":"/Public/bbs/images/11051/2016-03-15/2016031520160315579854505798.png","bbsInfo":{"id":"978","title":"","ctime":"1458749692","desc":"汪口村是全国模范。"}},{"village_id":"510922103201","village_name":"上方村","pic":"/Public/system/vill/dvill.png","bbsInfo":{"id":"977","title":"","ctime":"1458705459","desc":"回到梦开始的地方。故人如何了！"}}]}
     */

    private int err;
    /**
     * cnt : 20
     * list : [{"village_id":"361130103203","village_name":"汪口村","pic":"/Public/bbs/images/11051/2016-03-15/2016031520160315579854505798.png","bbsInfo":{"id":"978","title":"","ctime":"1458749692","desc":"汪口村是全国模范。"}},{"village_id":"510922103201","village_name":"上方村","pic":"/Public/system/vill/dvill.png","bbsInfo":{"id":"977","title":"","ctime":"1458705459","desc":"回到梦开始的地方。故人如何了！"}}]
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
         * village_id : 361130103203
         * village_name : 汪口村
         * pic : /Public/bbs/images/11051/2016-03-15/2016031520160315579854505798.png
         * bbsInfo : {"id":"978","title":"","ctime":"1458749692","desc":"汪口村是全国模范。"}
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
            private String village_id;
            private String village_name;
            private String pic;
            /**
             * id : 978
             * title :
             * ctime : 1458749692
             * desc : 汪口村是全国模范。
             */

            private BbsInfoEntity bbsInfo;

            public String getVillage_id() {
                return village_id;
            }

            public void setVillage_id(String village_id) {
                this.village_id = village_id;
            }

            public String getVillage_name() {
                return village_name;
            }

            public void setVillage_name(String village_name) {
                this.village_name = village_name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public BbsInfoEntity getBbsInfo() {
                return bbsInfo;
            }

            public void setBbsInfo(BbsInfoEntity bbsInfo) {
                this.bbsInfo = bbsInfo;
            }

            public static class BbsInfoEntity {
                private String id;
                private String title;
                private String ctime;
                private String desc;

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

                public String getCtime() {
                    return ctime;
                }

                public void setCtime(String ctime) {
                    this.ctime = ctime;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }
        }
    }
}
