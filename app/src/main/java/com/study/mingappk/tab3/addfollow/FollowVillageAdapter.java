package com.study.mingappk.tab3.addfollow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.model.bean.QueryVillageList;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/5/31.
 */
public class FollowVillageAdapter extends BaseRecyclerViewAdapter<QueryVillageList.DataBean, FollowVillageAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_addfollow_search_village, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemLayout,position);
            }
        });
        String village_name = mList.get(position).getProvince_name()
                + mList.get(position).getCity_name()
                + mList.get(position).getCounty_name()
                + mList.get(position).getTown_name()
                + mList.get(position).getVillage_name();
        holder.text.setText(village_name);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.text)
        TextView text;
        @Bind(R.id.item_layout)
        RelativeLayout itemLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
