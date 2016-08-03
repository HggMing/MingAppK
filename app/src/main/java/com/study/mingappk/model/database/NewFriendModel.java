package com.study.mingappk.model.database;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Default;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 新的朋友页面，申请好友列表
 * Created by Ming on 2016/7/19.
 */
@Table("new_friend_list")
public class NewFriendModel extends BaseModel {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("_user_id")
    private String uid;
    @Column("_user_uicon")
    private String uicon;
    @Column("_user_name")
    private String uname;
    @Column("_user_sex")
    private String usex;
    @Column("_user_phone")
    private String uphone;
    @Column("_msg_count")
    private int count;//新消息条数
    @Column("_type")
    @Default("0")
    private int type;//显示状态：0、显示选择按钮1、同意，已添加2、不同意，已拒绝

    public NewFriendModel(String uid, String uicon, String uname, String usex, String uphone, int count) {
        this.uid = uid;
        this.count = count;
        this.uicon = uicon;
        this.uname = uname;
        this.usex = usex;
        this.uphone = uphone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUicon() {
        return uicon;
    }

    public void setUicon(String uicon) {
        this.uicon = uicon;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
