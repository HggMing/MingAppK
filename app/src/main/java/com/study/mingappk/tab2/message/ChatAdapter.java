package com.study.mingappk.tab2.message;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.database.ChatMsgModel;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Ming on 2016/5/5.
 */
public class ChatAdapter extends BaseRecyclerViewAdapter<ChatMsgModel, RecyclerView.ViewHolder> {

    public void addData(List<ChatMsgModel> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (mList == null) {
            mList = new ArrayList<>();
        }
//        for (ChatMsgModel bean : data) {
//            addData(bean, false, false);
//        }
        mList.addAll(data);
        Collections.reverse(mList);
        this.notifyDataSetChanged();
    }

    public void addData(ChatMsgModel bean, boolean isNotifyDataSetChanged, boolean isFromHead) {
        if (bean == null) {
            return;
        }
        if (mList == null) {
            mList = new ArrayList<>();
        }

//        if (bean.getMsgType() <= 0) {
//            String content = bean.getContent();
//            if (content != null) {
//                if (content.indexOf("[img]") >= 0) {
//                    bean.setImage(content.replace("[img]", ""));
//                    bean.setMsgType(ImMsgBean.CHAT_MSGTYPE_IMG);
//                } else {
//                    bean.setMsgType(ImMsgBean.CHAT_MSGTYPE_TEXT);
//                }
//            }
//        }
            Collections.reverse(mList);
        if (isFromHead) {
            mList.add(0, bean);
        } else {
            mList.add(bean);
        }

        if (isNotifyDataSetChanged) {
            Collections.reverse(mList);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == ChatMsgModel.ITEM_TYPE_LEFT) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab2_message_list_left, parent, false);
            return new LeftViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab2_message_list_right, parent, false);
            return new RightViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LeftViewHolder) {
            //接收消息布局
            //消息时间
            String date = mList.get(position).getSt();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = dateFormat.format(new Date(Long.valueOf(date + "000")));
            ((LeftViewHolder) holder).time.setText(time);
            //头像
            String headUrl = "";
            Glide.with(holder.itemView.getContext())
                    .load(headUrl)
                    .bitmapTransform(new CropCircleTransformation(holder.itemView.getContext()))
                    .error(R.mipmap.defalt_user_circle)
                    .into(((LeftViewHolder) holder).icon);
            //消息显示
            switch (mList.get(position).getCt()) {
                case "0"://文字消息
                    String content = mList.get(position).getTxt();
                    ((LeftViewHolder) holder).content.setText(content);
                    break;
                case "1"://图片消息
                    String imageUrl = mList.get(position).getLink();
                    break;
                case "2"://声音消息
                    break;
                case "3"://html
                    break;
                case "4"://内部消息json格式
                    break;
                case "5"://交互消息
                    break;
                case "6"://应用透传消息json格式
                    break;
                case "7"://朋友系统消息json
                    break;
            }
        } else if (holder instanceof RightViewHolder) {
            //发送消息布局
            //发送消息时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date curDate = new Date(Long.valueOf(mList.get(position).getSt()));//获取发送消息时间
            String time = dateFormat.format(curDate);
            ((RightViewHolder) holder).time.setText(time);
            //头像
            String headUrl = Hawk.get(APP.ME_HEAD);
            Glide.with(holder.itemView.getContext())
                    .load(headUrl)
                    .bitmapTransform(new CropCircleTransformation(holder.itemView.getContext()))
                    .error(R.mipmap.defalt_user_circle)
                    .into(((RightViewHolder) holder).icon);
            //消息显示
            switch (mList.get(position).getCt()) {
                case "0"://文字消息
                    ((RightViewHolder) holder).content.setVisibility(View.VISIBLE);
                    ((RightViewHolder) holder).msgImage.setVisibility(View.GONE);
                    ((RightViewHolder) holder).voicePlay.setVisibility(View.GONE);
                    ((RightViewHolder) holder).sending.setVisibility(mList.get(position).getIsShowPro() == 1 ? View.VISIBLE : View.GONE);//进度圈
                    ((RightViewHolder) holder).resend.setVisibility(mList.get(position).getIsShowPro() == 2 ? View.VISIBLE : View.GONE);//感叹号
                    String content = mList.get(position).getTxt();
                    ((RightViewHolder) holder).content.setText(content);
                    break;
                case "1"://图片消息
                    ((RightViewHolder) holder).content.setVisibility(View.GONE);
                    ((RightViewHolder) holder).msgImage.setVisibility(View.VISIBLE);
                    ((RightViewHolder) holder).voicePlay.setVisibility(View.GONE);
                    break;
                case "2"://声音消息
                    ((RightViewHolder) holder).content.setVisibility(View.GONE);
                    ((RightViewHolder) holder).msgImage.setVisibility(View.GONE);
                    ((RightViewHolder) holder).voicePlay.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class LeftViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.voice_play)
        ImageView voicePlay;
        @Bind(R.id.voiceLayout)
        LinearLayout voiceLayout;
        @Bind(R.id.linearLayout)
        LinearLayout linearLayout;
        @Bind(R.id.resend)
        TextView resend;
        @Bind(R.id.sending)
        ProgressBar sending;
        @Bind(R.id.message_list_list_item_left)
        RelativeLayout messageListListItemLeft;

        LeftViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class RightViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.voice_play)
        ImageView voicePlay;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.msg_image)
        ImageView msgImage;
        @Bind(R.id.linearLayout)
        LinearLayout linearLayout;
        @Bind(R.id.resend)
        TextView resend;
        @Bind(R.id.sending)
        ProgressBar sending;
        @Bind(R.id.message_list_list_item_right)
        RelativeLayout messageListListItemRight;

        RightViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
