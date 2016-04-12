package com.study.mingappk.tab3.villagesituation;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.study.mingappk.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/3/30.
 */
public class VillageSituationAdapter extends RecyclerView.Adapter<VillageSituationAdapter.ViewHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public List<String> mTexts;
    public List<Integer> mIcons;

    public VillageSituationAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        mTexts =new ArrayList<>();
        mTexts.add("村况");
        mTexts.add("荣誉室");
        mTexts.add("村官");
        mTexts.add("活动");
        mIcons=new ArrayList<>();
        mIcons.add(R.mipmap.village_situation1);
        mIcons.add(R.mipmap.village_situation2);
        mIcons.add(R.mipmap.village_situation3);
        mIcons.add(R.mipmap.village_situation4);


    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mLayoutInflater.inflate(R.layout.item_tab3_village_situation, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.mClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.mClick,position);
                }
            });
        }
        holder.mTextView.setText(mTexts.get(position));
        Glide.with(mContext).load(mIcons.get(position))
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return mTexts.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon_village_situation)
        ImageView icon;
        @Bind(R.id.text_village_situation)
        TextView mTextView;
        @Bind(R.id.click_situation)
        FrameLayout mClick;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
