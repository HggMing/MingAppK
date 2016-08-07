package com.study.mingappk.model.database;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 好友列表
 * Created by Ming on 2016/7/15.
 */
@Table("friend_list")
public class FriendsModel extends BaseModel {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("_user_id")
    private String uid;
    @Column("_user_name")
    private String uname;
    @Column("_user_uicon")
    private String uicon;
    @Column("_msg_count")
    private int count;//新消息条数

    public FriendsModel() {
    }

    public FriendsModel(String uid, String uname, String uicon) {
        this.uid = uid;
        this.uicon = uicon;
        this.uname = uname;
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
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
