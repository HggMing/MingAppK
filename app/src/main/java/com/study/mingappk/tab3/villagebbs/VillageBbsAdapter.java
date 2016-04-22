package com.study.mingappk.tab3.villagebbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.views.nineimage.NineGridImageView;
import com.study.mingappk.common.views.nineimage.NineGridImageViewAdapter;
import com.study.mingappk.model.bean.BBSList;
import com.study.mingappk.model.bean.BbsCommentList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.ZanList;
import com.study.mingappk.model.service.MyServiceClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ming on 2016/3/30.
 */
public class VillageBbsAdapter extends RecyclerView.Adapter<VillageBbsAdapter.ViewHolder> {

    private List<BBSList.DataEntity.ListEntity> mList;

    public void setItem(List<BBSList.DataEntity.ListEntity> mList) {
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
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_bbs_list2, parent, false);
        return new ViewHolder(mView);
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.bbsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.bbsItem, position);
                }
            });
            holder.bbsLikeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(mContext, "点击点赞操作", Toast.LENGTH_SHORT).show();
                    String auth = APP.getInstance().getAuth();
                    String pid = mList.get(position).getId();
                    MyServiceClient.getService().getCall_ClickLike(auth, pid).enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()) {
                                Result result = response.body();
                                if (result != null) {
                                    Toast.makeText(holder.itemView.getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
                                    if (result.getErr() == 0) {
                                        Animation animPraise = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale);
                                        holder.bbsLikeIcon.setVisibility(View.INVISIBLE);
                                        holder.bbsLiked.setVisibility(View.VISIBLE);
                                        holder.bbsLiked.startAnimation(animPraise);
                                        //点赞数+1
                                        String likeNumber = String.valueOf(Integer.parseInt(mList.get(position).getZans()) + 1);
                                        holder.bbsLike.setText(likeNumber);
                                        //点赞人头像刷新
                                        String pid=mList.get(position).getId();
                                        getLikeList(pid,holder);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(holder.itemView.getContext(), "点赞出错：" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            holder.bbsComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.bbsComment, position);
                }
            });
        }
        //显示数据编辑
        //帖子正文区域***************************************************************************************************************************
        //发帖人头像
        String headUrl = MyServiceClient.getBaseUrl() + mList.get(position).getUserinfo().getHead();
        Glide.with(holder.itemView.getContext()).load(headUrl)
                .bitmapTransform(new CropCircleTransformation(holder.itemView.getContext()))
                .priority(Priority.HIGH)
                .error(R.mipmap.defalt_user_circle)
                // .placeholder(R.mipmap.defalt_user_circle)
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
        //已点赞图标显示
        int isLiked=mList.get(position).getMy_is_zan();
        if(isLiked==1){
            holder.bbsLikeIcon.setVisibility(View.INVISIBLE);
            holder.bbsLiked.setVisibility(View.VISIBLE);
        }else{
            holder.bbsLikeIcon.setVisibility(View.VISIBLE);
            holder.bbsLiked.setVisibility(View.INVISIBLE);
        }
        //点赞总数
        String likeNumber = mList.get(position).getZans();
        holder.bbsLike.setText(likeNumber);
        //留言总数
        String msgNumber = mList.get(position).getNums();
        holder.bbsComment.setText(msgNumber);
        //帖子图片显示
        NineGridImageViewAdapter<BBSList.DataEntity.ListEntity.FilesEntity> nineGridViewAdapter = new NineGridImageViewAdapter<BBSList.DataEntity.ListEntity.FilesEntity>() {

            @Override
            protected void onDisplayImage(Context context, ImageView imageView, BBSList.DataEntity.ListEntity.FilesEntity filesEntity) {
                String imageUrl = MyServiceClient.getBaseUrl() + filesEntity.getSurl_2();
                Glide.with(context).load(imageUrl)
                        .asBitmap()
                        .into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);

            }

            @Override
            protected void onItemImageClick(Context context, int index, List<BBSList.DataEntity.ListEntity.FilesEntity> list) {
                super.onItemImageClick(context, index, list);
                // Toast.makeText(context, "点击第" + index+"个图片", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(holder.itemView.getContext(), BigImageViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(BigImageViewActivity.IMAGE_LIST, (ArrayList<? extends Parcelable>) list);
                bundle.putInt(BigImageViewActivity.IMAGE_INDEX, index);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        };
        holder.nineGridImageView.setAdapter(nineGridViewAdapter);
        List<BBSList.DataEntity.ListEntity.FilesEntity> photoList = mList.get(position).getFiles();
        holder.nineGridImageView.setImagesData(photoList);
        //评论、点赞区域***************************************************************************************************************************

        if((Integer.parseInt(likeNumber) == 0) && (Integer.parseInt(msgNumber)  == 0)){//点赞数和评论均为0
            holder.commentLikeArea.setVisibility(View.GONE);
        }

        //点赞人员显示区
        String pid=mList.get(position).getId();
        View.OnClickListener mOnClickUser = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"点击点赞人头像",Toast.LENGTH_SHORT).show();
            }
        };
        holder.likeUsersArea = new LikeUsersArea(holder.itemView, holder.itemView.getContext(), mOnClickUser);
        getLikeList(pid,holder);
        //评论区显示前5条
        View.OnClickListener onClickComment = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"点击评论",Toast.LENGTH_SHORT).show();
            }
        };
        holder.commentArea=new CommentArea(holder.itemView,onClickComment);
        getCommentList(pid,holder);

    }

    private void getLikeList(String pid,final ViewHolder holder) {
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getObservable_ZanList(auth,pid,1,99)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZanList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZanList zanList) {
                        holder.likeUsersArea.displayLikeUser(zanList);
                    }
                });
    }

    private void getCommentList(String pid,final ViewHolder holder) {
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getObservable_BbsCommentList(auth,pid,1,99)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BbsCommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BbsCommentList bbsCommentList) {
                        holder.commentArea.displayContentData(bbsCommentList.getData());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bbs_head)
        ImageView bbsHead;
        @Bind(R.id.bbs_uname)
        TextView bbsUname;
        @Bind(R.id.bbs_contents)
        TextView bbsContents;
        @Bind(R.id.nine_grid_image)
        NineGridImageView nineGridImageView;
        @Bind(R.id.bbs_ctime)
        TextView bbsCtime;
        @Bind(R.id.bbs_like_layout)
        LinearLayout bbsLikeLayout;
        @Bind(R.id.bbs_like)
        TextView bbsLike;
        @Bind(R.id.bbs_like_icon)
        ImageView bbsLikeIcon;
        @Bind(R.id.bbs_liked)
        ImageView bbsLiked;
        @Bind(R.id.bbs_comment)
        TextView bbsComment;
        @Bind(R.id.likeUsersLayout)
        LinearLayout likeUsersLayout;
        @Bind(R.id.likesAllLayout)
        LinearLayout likesAllLayout;
        @Bind(R.id.temp1)
        ImageView temp1;
        @Bind(R.id.commentMoreCount)
        TextView commentMoreCount;
        @Bind(R.id.commentMore)
        RelativeLayout commentMore;
//        @Bind(R.id.commentArea)
//        LinearLayout commentArea;
        @Bind(R.id.commentLikeArea)
        RelativeLayout commentLikeArea;
        @Bind(R.id.bbs_item)
        LinearLayout bbsItem;

        LikeUsersArea likeUsersArea;
        CommentArea commentArea;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}