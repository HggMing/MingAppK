package com.study.mingappk.tab3.villagebbs.bbsdetail;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.views.dialog.Dialog_Model;
import com.study.mingappk.model.bean.BbsCommentList;
import com.study.mingappk.model.bean.FollowVillageList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        if (mOnItemClickListener != null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.item, position);
                }
            });
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
        Glide.with(holder.itemView.getContext()).load(headUrl)
                .bitmapTransform(new CropCircleTransformation(holder.itemView.getContext()))
                .error(R.mipmap.defalt_user_circle)
                // .placeholder(R.mipmap.defalt_user_circle)
                .into(holder.icon);
        //评论人姓名
        String uname = mList.get(position).getUname();
        if (uname.isEmpty()) {
            //若用户名为空，显示手机号，中间四位为*
            String iphone = mList.get(position).getUser_tel();
            uname = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
        }
        holder.name.setText(uname);
        //评论时间
        String date = mList.get(position).getCtime();
        if (date != null) {
            String showTime = BaseTools.getTimeFormatText(new Date(Long.valueOf(date + "000")));
            holder.time.setText(showTime);
        } else {
            holder.time.setText("");
        }
        //评论内容
        String commentContent = mList.get(position).getConts();
        holder.content.setText(commentContent);
        //删除评论（仅发布评论者可删除）
        String uid = mList.get(position).getUid();
        if (Hawk.get(APP.ME_UID).equals(uid)) {
            holder.bbsCommentDel.setVisibility(View.VISIBLE);
        }else{
            holder.bbsCommentDel.setVisibility(View.GONE);
        }
        holder.bbsCommentDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.bbsCommentDel,position);
            }
        });

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
