package com.study.mingappk.tab3.villagebbs.likeusers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.ZanList;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.tab2.frienddetail.FriendDetailActivity;
import com.study.mingappk.common.base.BaseRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Ming on 2016/6/2.
 */
public class LikeUserListAdapter extends BaseRecyclerViewAdapter<ZanList.DataBean.ListBean, LikeUserListAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab2, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Context mContext = holder.itemView.getContext();
        //点击进入详情查看
        holder.friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = mList.get(position).getUid();
                Intent intent = new Intent(mContext, FriendDetailActivity.class);
                intent.putExtra(FriendDetailActivity.FRIEND_UID, uid);
                mContext.startActivity(intent);
            }
        });
        //显示数据编辑
        //显示进入箭头
        holder.arrow.setVisibility(View.VISIBLE);
        //好友名字的显示
        String showName = mList.get(position).getName();
        if (StringTools.isEmpty(showName)) {
            showName = "User" + mList.get(position).getUid();
        }
        holder.userName.setText(showName);
        //好友头像的显示
        String imageUrl = MyServiceClient.getBaseUrl() + mList.get(position).getUser_head();
        if (MyServiceClient.DEFAULT_HEAD.equals(imageUrl)) {//未设置头像时，更换服务器提供的默认头像为本地
            Glide.with(mContext)
                    .load(R.mipmap.defalt_user_circle)
                    .into(holder.userHead);
        } else {
            Glide.with(mContext).load(imageUrl)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .error(R.mipmap.defalt_user_circle)
                    .into(holder.userHead);
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_head)
        ImageView userHead;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.arrow)
        View arrow;
        @Bind(R.id.friends)
        RelativeLayout friends;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
