package com.study.mingappk.tab3.village;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.study.mingappk.R;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.model.bean.BBSListResult;
import com.study.mingappk.model.service.MyServiceClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ming on 2016/3/30.
 */
public class Tab3BBSListAdapter extends RecyclerView.Adapter<Tab3BBSListAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BBSListResult.DataEntity.ListEntity> mList;

    public Tab3BBSListAdapter(Context mContext, List<BBSListResult.DataEntity.ListEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);

    }


    /**
     * 点击事件接口
     *
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
        View mView = mLayoutInflater.inflate(R.layout.item_tab3_bbs_list, parent, false);
        return new ViewHolder(mView);
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.idCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.idCardview, position);
                }
            });
            holder.clickLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.clickLike, position);
                }
            });
            holder.clickMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.clickMsg, position);
                }
            });
        }
        //显示数据编辑
        //发帖人头像
        String headUrl = MyServiceClient.getBaseUrl() + mList.get(position).getUserinfo().getHead();
        Glide.with(mContext).load(headUrl)
//                .placeholder(R.mipmap.defuser)
                .into(holder.bbsHead);
        //发帖人昵称
        String userName = mList.get(position).getUserinfo().getUname();
        holder.bbsUname.setText(userName);
        //发帖时间
        String date = mList.get(position).getCtime();
        if (date != null) {
            String time = BaseTools.getTimeFormatText(new Date(Long.valueOf(date + "000")));
            holder.bbsCtime.setText(time);
        } else {
            holder.bbsCtime.setText("");
        }
        //发帖消息正文
        String msgContent = mList.get(position).getConts();
        holder.bbsContents.setText(msgContent);
        //点赞总数
        String likeNumber = mList.get(position).getZans();
        holder.bbsLikeNumber.setText(likeNumber);
        //留言总数
        String msgNumber = mList.get(position).getNums();
        holder.bbsMsgNumber.setText(msgNumber);
        //帖子图片显示

        NineGridImageViewAdapter<BBSListResult.DataEntity.ListEntity.FilesEntity> nineGridViewAdapter = new NineGridImageViewAdapter<BBSListResult.DataEntity.ListEntity.FilesEntity>() {

            @Override
            protected void onDisplayImage(Context context, ImageView imageView, BBSListResult.DataEntity.ListEntity.FilesEntity filesEntity) {
                String imageUrl = MyServiceClient.getBaseUrl() + filesEntity.getSurl_1();
                Glide.with(mContext).load(imageUrl)
                        .into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);

            }

            @Override
            protected void onItemImageClick(Context context, int index, List<BBSListResult.DataEntity.ListEntity.FilesEntity> list) {
                super.onItemImageClick(context, index, list);
                // Toast.makeText(context, "点击第" + index+"个图片", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, PhotoViewPagerActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList(PhotoViewPagerActivity.IMAGE_LIST, (ArrayList<? extends Parcelable>) list);
                bundle.putInt(PhotoViewPagerActivity.IMAGE_INDEX,index);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        };
        holder.nineGridImageView.setAdapter(nineGridViewAdapter);
        List<BBSListResult.DataEntity.ListEntity.FilesEntity> photoList = mList.get(position).getFiles();
        holder.nineGridImageView.setImagesData(photoList);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bbs_head)
        CircleImageView bbsHead;
        @Bind(R.id.bbs_uname)
        TextView bbsUname;
        @Bind(R.id.bbs_ctime)
        TextView bbsCtime;
        @Bind(R.id.bbs_contents)
        TextView bbsContents;
        @Bind(R.id.bbs_msg_number)
        TextView bbsMsgNumber;
        @Bind(R.id.click_msg)
        ImageView clickMsg;
        @Bind(R.id.bbs_like_number)
        TextView bbsLikeNumber;
        @Bind(R.id.click_like)
        ImageView clickLike;
        //        @Bind(R.id.id_cardview)
//        CardView idCardview;
        @Bind(R.id.nine_grid_image)
        NineGridImageView nineGridImageView;
        @Bind(R.id.id_layout)
        RelativeLayout idCardview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}