package com.study.mingappk.model.database;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 动态页面，消息列表
 * Created by Ming on 2016/7/19.
 */
@Table("instant_message_list")
public class InstantMsgModel extends BaseModel {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("_user_id")
    private String uid;
    @Column("_user_uicon")
    private String uicon;//头像
    @Column("_user_name")
    private String uname;//昵称
    @Column("_msg_time")
    private String time;//时间
    @Column("_msg_content")
    private String content;//内容
    @Column("_msg_count")
    private int count;//新消息条数

    public InstantMsgModel() {
    }

    public InstantMsgModel(String uid, String uicon, String uname, String time, String content, int count) {
        this.uid = uid;
        this.uicon = uicon;
        this.uname = uname;
        this.time = time;
        this.content = content;
        this.count = count;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
