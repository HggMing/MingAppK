package com.study.mingappk.tab3.villagebbs.bbsdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.BbsCommentList;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab2.frienddetail.FriendDetailActivity;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Ming on 2016/3/30.
 */
public class BbsDetailAdapter extends RecyclerView.Adapter<BbsDetailAdapter.ViewHolder> {

    private List<BbsCommentList.DataBean.ListBean> mList;

    public void setItem(List<BbsCommentList.DataBean.ListBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
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
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_bbs_detail, parent, false);
        return new ViewHolder(mView);
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Context mContext = holder.itemView.getContext();
        if (mOnItemClickListener != null) {
//            holder.item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onItemClick(holder.item, position);
//                }
//            });
            holder.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.item, position);
                    return true;
                }
            });
        }
        //显示数据编辑
        //评论人头像
        String headUrl = MyServiceClient.getBaseUrl() + mList.get(position).getUser_head();
        if (MyServiceClient.DEFAULT_HEAD.equals(headUrl)) {//未设置头像时，更换服务器提供的默认头像为本地
            Glide.with(mContext)
                    .load(R.mipmap.defalt_user_circle)
                    .into(holder.icon);
        } else {
            Glide.with(mContext).load(headUrl)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .error(R.mipmap.defalt_user_circle)
                    // .placeholder(R.mipmap.defalt_user_circle)
                    .into(holder.icon);
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FriendDetailActivity.class);
                String uid = mList.get(position).getUid();
                intent.putExtra(FriendDetailActivity.FRIEND_UID, uid);
                mContext.startActivity(intent);
            }
        });
        //评论人姓名
        String uname = mList.get(position).getUname();
        if (StringTools.isEmpty(uname)) {
            //若用户名为空，显示手机号，中间四位为*
            String iphone = mList.get(position).getUser_tel();
            uname = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
        }
        holder.name.setText(uname);
        //评论时间
        String date = mList.get(position).getCtime();
        String showTime = BaseTools.getTimeFormat(date);
        holder.time.setText(showTime);

        //评论内容
        String commentContent = mList.get(position).getConts();
        holder.content.setText(commentContent);
        //删除评论（仅发布评论者可删除）
        String uid = mList.get(position).getUid();
        if (Hawk.get(APP.ME_UID).equals(uid)) {
            holder.bbsCommentDel.setVisibility(View.VISIBLE);
            holder.bbsCommentDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.bbsCommentDel, position);
                }
            });
        } else {
            holder.bbsCommentDel.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.layoutContent)
        LinearLayout layoutContent;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.commentlayout)
        RelativeLayout item;
        @Bind(R.id.bbs_comment_del)
        ImageView bbsCommentDel;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
