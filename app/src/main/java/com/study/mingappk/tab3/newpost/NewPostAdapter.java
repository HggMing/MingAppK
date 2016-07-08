package com.study.mingappk.tab3.newpost;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.common.views.gallerfinal.model.PhotoInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/4/20.
 */

public class NewPostAdapter extends RecyclerView.Adapter<NewPostAdapter.ViewHolder> {

    List<PhotoInfo> mList;

    public void setItem(List<PhotoInfo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    /**
     * 点击事件接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_post_image_list, parent, false);
        return new ViewHolder(mView);
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.postPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.postPicture, position);
                }
            });
            holder.delPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.delPicture, position);
                }
            });

        }
        //显示数据编辑
        String imageUrl = "file://" + mList.get(position).getPhotoPath();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .into(holder.postPicture);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.post_picture)
        ImageView postPicture;
        @Bind(R.id.del_picture)
        ImageView delPicture;
        @Bind(R.id.frame_temp)
        FrameLayout frameTemp;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
