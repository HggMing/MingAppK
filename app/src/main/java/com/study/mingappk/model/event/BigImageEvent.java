package com.study.mingappk.model.event;

import com.study.mingappk.model.bean.BBSListResult;

import java.util.List;

/**
 * Created by Ming on 2016/4/8.
 */
public class BigImageEvent {
    public List<BBSListResult.DataEntity.ListEntity.FilesEntity> getList() {
        return list;
    }

    private List<BBSListResult.DataEntity.ListEntity.FilesEntity> list;

    public int getIndex() {
        return index;
    }

    private int index;

    public BigImageEvent(int index, List<BBSListResult.DataEntity.ListEntity.FilesEntity> list) {
        this.index = index;
        this.list = list;
    }
}
