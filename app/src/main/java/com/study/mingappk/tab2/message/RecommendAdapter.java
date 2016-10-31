package com.study.mingappk.tab2.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.model.bean.RecommendList;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BaseRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/9/21.
 */
public class RecommendAdapter extends BaseRecyclerViewAdapter<RecommendList.DataBean.ListBean, RecommendAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab1_recommend, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context mContext = holder.itemView.getContext();
        RecommendList.DataBean.ListBean data = mList.get(position);
        //产品图片
//        ViewGroup.LayoutParams imageLP = holder.imgProduct.getLayoutParams();
//        imageLP.height =1;
//        holder.imgProduct.setLayoutParams(imageLP);

        String imageUrl = MyServiceClient.getBaseUrl() + data.getPicurl_1();
        Glide.with(mContext).load(imageUrl)
                .centerCrop()
//                .placeholder(R.mipmap.default_nine_picture)
                .into(holder.imgProduct);
        //显示产品简介
        holder.tvTitle.setText(data.getTitle());
        //显示产品价格
        holder.tvPrice.setText(data.getPrice());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_product)
        ImageView imgProduct;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
