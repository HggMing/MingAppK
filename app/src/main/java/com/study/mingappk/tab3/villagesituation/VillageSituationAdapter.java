package com.study.mingappk.tab3.villagesituation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/3/30.
 */
public class VillageSituationAdapter extends RecyclerView.Adapter<VillageSituationAdapter.ViewHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public String[] mTexts={"村况","荣誉室","村委","活动","美食"};
    public int[] mIcons={
            R.mipmap.village_situation1,
            R.mipmap.village_situation2,
            R.mipmap.village_situation3,
            R.mipmap.village_situation4,
            R.mipmap.village_situation4};

    public VillageSituationAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
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
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.icon,position);
                }
            });
        }
        holder.mTextView.setText(mTexts[position]);
        Glide.with(mContext).load(mIcons[position])
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return mTexts.length;
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
