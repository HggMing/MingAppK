package com.study.mingappk.tmain;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/5/5.
 */


public abstract class BaseRecyclerViewAdapter<M,T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {


    protected List<M> mList;

    public BaseRecyclerViewAdapter() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
    }

    /**
     * 点击事件接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public int getItemCount() {
        return this.mList == null ? 0 : this.mList.size();
    }

    public M getItem(int position) {
        return this.mList.get(position);
    }

    public List<M> getDatas() {
        return this.mList;
    }

    public void addNewDatas(List<M> datas) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        if (datas != null) {
            this.mList.addAll(0, datas);
            this.notifyItemRangeInserted(0, datas.size());
        }

    }

    public void addMoreDatas(List<M> datas) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        if (datas != null) {
            this.mList.addAll(this.mList.size(), datas);
            this.notifyItemRangeInserted(this.mList.size(), datas.size());
        }

    }

    public void setItem(List<M> datas) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        if (datas != null) {
            this.mList = datas;
        } else {
            this.mList.clear();
        }

        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.mList.remove(position);
        this.notifyItemRemoved(position);
    }

    public void removeItem(M model) {
        this.removeItem(this.mList.indexOf(model));
    }

    public void addItem(int position, M model) {
        this.mList.add(position, model);
        this.notifyItemInserted(position);
    }

    public void addFirstItem(M model) {
        this.addItem(0, model);
    }

    public void addLastItem(M model) {
        this.addItem(this.mList.size(), model);
    }

    public void setItem(int location, M newModel) {
        this.mList.set(location, newModel);
        this.notifyItemChanged(location);
    }

    public void setItem(M oldModel, M newModel) {
        this.setItem(this.mList.indexOf(oldModel), newModel);
    }

    public void moveItem(int fromPosition, int toPosition) {
        this.mList.add(toPosition, this.mList.remove(fromPosition));
        this.notifyItemMoved(fromPosition, toPosition);
    }
}
