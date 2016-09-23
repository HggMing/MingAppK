package com.study.mingappk.tab3.villagesituation.villageinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.model.bean.VillageInfo;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BaseRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/7/13.
 */
public class VillageInfoAdapter extends BaseRecyclerViewAdapter<VillageInfo.DataBean, VillageInfoAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_village_info, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Context mContext=holder.itemView.getContext();
        //图片加载
        String imageUrl = MyServiceClient.getBaseUrl() + mList.get(position).getPic1();
        Glide.with(mContext).load(imageUrl)
                .placeholder(R.mipmap.default_nine_picture)
                .into(holder.imageView1);
        //标题及内容显示
        String title=mList.get(position).getTitle();
        holder.tvTitle.setText(title);
        String content=mList.get(position).getContent();
        holder.tvNews.setText(content);
        //点击事件
        if (mOnItemClickListener != null) {
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.mItem, position);
                }
            });
            holder.mItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.mItem, position);
                    return true;
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView_1)
        ImageView imageView1;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.title_layout)
        LinearLayout titleLayout;
        @Bind(R.id.tv_news)
        TextView tvNews;
        @Bind(R.id.tab3_item)
        RelativeLayout mItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
