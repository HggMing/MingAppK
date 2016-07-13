package com.study.mingappk.tab3.villagesituation.villagemaster;

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
import com.study.mingappk.model.bean.VillageMaster;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/7/13.
 */
public class VillageMasterAdapter extends BaseRecyclerViewAdapter<VillageMaster.DataBean.ListBean, VillageMasterAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_village_master, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Context mContext = holder.itemView.getContext();
        VillageMaster.DataBean.ListBean data = mList.get(position);
        //图片加载
        String imageUrl = MyServiceClient.getBaseUrl() + data.getHead();
        Glide.with(mContext).load(imageUrl)
//                .placeholder(R.mipmap.default_nine_picture)
                .into(holder.imageView1);
        //内容显示
        String name = data.getUname();
        holder.tvName.setText(name);
        if ("0".equals(data.getSex())) {
            holder.tvSex.setText("性别： 男");
        } else {
            holder.tvSex.setText("性别： 女");
        }
        String job="职务： "+data.getJob();
        holder.tvJob.setText(job);
        String ps="政治面貌： "+data.getZzmm();
        holder.tvPs.setText(ps);
        String phone="联系方式： "+data.getContact();
        holder.tvPhone.setText(phone);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView_1)
        ImageView imageView1;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_sex)
        TextView tvSex;
        @Bind(R.id.tv_job)
        TextView tvJob;
        @Bind(R.id.tv_ps)
        TextView tvPs;
        @Bind(R.id.tv_phone)
        TextView tvPhone;
        @Bind(R.id.m_item)
        RelativeLayout mItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
