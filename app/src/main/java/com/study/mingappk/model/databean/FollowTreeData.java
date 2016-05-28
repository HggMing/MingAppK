package com.study.mingappk.model.databean;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ming on 2016/5/26.
 */
public class FollowTreeData {
    public static final int ITEM_TYPE_PARENT = 0;
    public static final int ITEM_TYPE_CHILD = 1;

    public FollowTreeData() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String uuid;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;//显示类型

    public int getTreeDepth() {
        return treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }

    private int treeDepth = 0;// 路径的深度

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    private boolean expand;// 是否展开

    public List<FollowTreeData> getChildren() {
        return children;
    }

    public void setChildren(List<FollowTreeData> children) {
        this.children = children;
    }

    private List<FollowTreeData> children;

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    private String parent_name;
    private String child_name;

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    private String country_id;

    public String getTown_id() {
        return town_id;
    }

    public void setTown_id(String town_id) {
        this.town_id = town_id;
    }

    private String town_id;

    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(String village_id) {
        this.village_id = village_id;
    }

    private String village_id;

}
