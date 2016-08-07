package com.study.mingappk.model.bean;

import java.util.List;

/**
 * Created by Ming on 2016/8/5.
 */
public class CardList {
    /**
     * err : 0
     * data : [{"id":"49","phone":"19910001000","bank_name":"工商","bank_no":"64646134648888","bank_true_name":"米其林","ctime":"1470326775","memo":""},{"id":"33","phone":"19910001000","bank_name":"建行","bank_no":"123456789","bank_true_name":"明","ctime":"1465721655","memo":""}]
     */

    private int err;
    /**
     * id : 49
     * phone : 19910001000
     * bank_name : 工商
     * bank_no : 64646134648888
     * bank_true_name : 米其林
     * ctime : 1470326775
     * memo :
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

    public static class DataBean {
        private String id;
        private String phone;
        private String bank_name;
        private String bank_no;
        private String bank_true_name;
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

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }

        public String getBank_true_name() {
            return bank_true_name;
        }

        public void setBank_true_name(String bank_true_name) {
            this.bank_true_name = bank_true_name;
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
