package com.study.mingappk.tab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.model.bean.FollowVillageList;
import com.study.mingappk.common.utils.BaseTools;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/3/30.
 */
public class Tab3Adapter extends RecyclerView.Adapter<Tab3Adapter.ViewHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<FollowVillageList.DataEntity.ListEntity> mList;

    public Tab3Adapter(Context mContext, List<FollowVillageList.DataEntity.ListEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mLayoutInflater.inflate(R.layout.item_tab3, parent, false);
        return new ViewHolder(mView);
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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
        String imageUrl = MyServiceClient.getBaseUrl() + mList.get(position).getPic();
        Glide.with(mContext).load(imageUrl)
                .placeholder(R.mipmap.default_village)
                .into(holder.imageViewVillage);//关注村圈图
        String villageName = mList.get(position).getVillage_name();
        holder.tvVillageName.setText(villageName);//关注村圈名称
        String newMessage = mList.get(position).getBbsInfo().getDesc();
        holder.tvNews.setText(newMessage);//村圈最新消息
        String date = mList.get(position).getBbsInfo().getCtime();
        if (date != null) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//            String time = dateFormat.format(new Date(Long.valueOf(date + "000")));
            String time = BaseTools.getTimeFormatText(new Date(Long.valueOf(date + "000")));
            holder.tvTime.setText(time);//最新动态时间
        } else {
            holder.tvTime.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
