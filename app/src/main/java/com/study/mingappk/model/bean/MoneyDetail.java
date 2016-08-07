package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/8/4.
 */
public class MoneyDetail {

    /**
     * err : 0
     * data : {"cnt":"4","list":[{"id":"38","phone":"19910001000","aid":"18","types":"1","money":"0.06","op_user_phone":"","op_user_name":"","ctime":"1470126368","memo":"手机充值奖励"},{"id":"36","phone":"19910001000","aid":"18","types":"4","money":"0.01","op_user_phone":"","op_user_name":"","ctime":"1469592186","memo":""},{"id":"14","phone":"19910001000","aid":"18","types":"4","money":"0.01","op_user_phone":"","op_user_name":"","ctime":"1465958700","memo":""},{"id":"7","phone":"19910001000","aid":"18","types":"1","money":"1.00","op_user_phone":"admin","op_user_name":"admin","ctime":"1462873229","memo":"任务奖励"}]}
     */

    private int err;
    /**
     * cnt : 4
     * list : [{"id":"38","phone":"19910001000","aid":"18","types":"1","money":"0.06","op_user_phone":"","op_user_name":"","ctime":"1470126368","memo":"手机充值奖励"},{"id":"36","phone":"19910001000","aid":"18","types":"4","money":"0.01","op_user_phone":"","op_user_name":"","ctime":"1469592186","memo":""},{"id":"14","phone":"19910001000","aid":"18","types":"4","money":"0.01","op_user_phone":"","op_user_name":"","ctime":"1465958700","memo":""},{"id":"7","phone":"19910001000","aid":"18","types":"1","money":"1.00","op_user_phone":"admin","op_user_name":"admin","ctime":"1462873229","memo":"任务奖励"}]
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
         * id : 38
         * phone : 19910001000
         * aid : 18
         * types : 1
         * money : 0.06
         * op_user_phone :
         * op_user_name :
         * ctime : 1470126368
         * memo : 手机充值奖励
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

        public static class ListBean {
            private String id;
            private String phone;
            private String aid;
            private String types;
            private String money;
            private String op_user_phone;
            private String op_user_name;
            private String ctime;
            private String memo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getTypes() {
                return types;
            }

            public void setTypes(String types) {
                this.types = types;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getOp_user_phone() {
                return op_user_phone;
            }

            public void setOp_user_phone(String op_user_phone) {
                this.op_user_phone = op_user_phone;
            }

            public String getOp_user_name() {
                return op_user_name;
            }

            public void setOp_user_name(String op_user_name) {
                this.op_user_name = op_user_name;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }
        }
    }
}
