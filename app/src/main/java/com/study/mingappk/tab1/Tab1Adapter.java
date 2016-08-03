package com.study.mingappk.tab1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.study.mingappk.R;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.model.database.InstantMsgModel;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Ming on 2016/7/19.
 */
public class Tab1Adapter extends BaseRecyclerViewAdapter<InstantMsgModel, Tab1Adapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab1, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        InstantMsgModel data = mList.get(position);
        Context mContext = holder.itemView.getContext();
        //显示数据编辑
        String imageUrl = data.getUicon();
        Glide.with(mContext).load(imageUrl)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .placeholder(R.mipmap.defalt_user_circle)
                .into(holder.icon);

        String uName = data.getUname();
        holder.name.setText(uName);
        if("10000".equals(data.getUid())||"10001".equals(data.getUid())){//小苞谷和客服
            holder.name.setTextColor(mContext.getResources().getColor(R.color.purple));
        }

        String msgContent = data.getContent();
        holder.content.setText(msgContent);

        String date = data.getTime();
        if (date != null) {
            String timeFormat = BaseTools.getTimeFormat01(new Date(Long.valueOf(date + "000")));
            holder.time.setText(timeFormat);//最新动态时间
        } else {
            holder.time.setText("");
        }

        int count=data.getCount();
        if(count>0){
            holder.badge.setVisibility(View.VISIBLE);
            holder.badge.setText(String.valueOf(count));
        }else {
            holder.badge.setVisibility(View.GONE);
        }

        //点击事件
        if (mOnItemClickListener != null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.item, position);
                }
            });
            holder.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.item, position);
                    return true;
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.badge)
        TextView badge;
        @Bind(R.id.item)
        RelativeLayout item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
