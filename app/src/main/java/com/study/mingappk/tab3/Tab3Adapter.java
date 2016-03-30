package com.study.mingappk.tab3;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.FollowVillageListResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/3/30.
 */
public class Tab3Adapter extends RecyclerView.Adapter<Tab3Adapter.Tab3Holder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<FollowVillageListResult.DataEntity.ListEntity> folllowList;

    public Tab3Adapter(Context mContext, List<FollowVillageListResult.DataEntity.ListEntity> folllowList) {
        this.mContext = mContext;
        this.folllowList = folllowList;
        mLayoutInflater = LayoutInflater.from(mContext);

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

    /**
     * 创建ViewHolder
     */
    @Override
    public Tab3Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mLayoutInflater.inflate(R.layout.item_tab3, parent, false);
        return new Tab3Holder(mView);
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final Tab3Holder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.tab3Item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.tab3Item, position);
                }
            });
            holder.tab3Item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.tab3Item, position);
                    return true;
                }
            });
        }
        //显示数据编辑
        String imageUrl= MyNetApi.getBaseUrl()+folllowList.get(position).getPic();
        Glide.with(mContext).load(imageUrl).into(holder.imageViewVillage);//关注村圈图
        holder.tvVillageName.setText(folllowList.get(position).getVillage_name());//关注村圈名称
        holder.tvNews.setText(folllowList.get(position).getBbsInfo().getDesc());//村圈最新消息
        String date =folllowList.get(position).getBbsInfo().getCtime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time = dateFormat.format(new Date(Long.valueOf(date+"000")));
        holder.tvTime.setText(time);//最新动态时间
    }

    @Override
    public int getItemCount() {
        return folllowList.size();
    }

    static class Tab3Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.tab3_item)
        RelativeLayout tab3Item;
        @Bind(R.id.imageView_village)
        ImageView imageViewVillage;
        @Bind(R.id.tv_villageName)
        TextView tvVillageName;
        @Bind(R.id.tv_news)
        TextView tvNews;
        @Bind(R.id.tv_time)
        TextView tvTime;

        Tab3Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
