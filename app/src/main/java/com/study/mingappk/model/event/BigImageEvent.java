package com.study.mingappk.model.event;

import com.study.mingappk.model.bean.BBSList;

import java.util.List;

/**
 * Created by Ming on 2016/4/8.
 */
@Deprecated
public class BigImageEvent {

    public List<BBSList.DataEntity.ListEntity.FilesEntity> getList() {
        return list;
    }

    private List<BBSList.DataEntity.ListEntity.FilesEntity> list;

    public int getIndex() {
        return index;
    }

    private int index;

    public BigImageEvent(int index, List<BBSList.DataEntity.ListEntity.FilesEntity> list) {
        this.index = index;
        this.list = list;
    }
}
