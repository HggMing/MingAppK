package com.study.mingappk.tab3.villagebbs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.utils.JUtils;
import com.study.mingappk.R;
import com.study.mingappk.model.bean.ZanList;
import com.study.mingappk.model.service.MyServiceClient;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;

public class LikeUsersArea {
    private Context mContext;
    private View likeUsersAllLayout;
    public LinearLayout likeUsersLayout;

    View.OnClickListener mOnClickUser;

    public LikeUsersArea(View convertView, Context context, View.OnClickListener mOnClickUser) {
        this.mContext = context;
        this.mOnClickUser = mOnClickUser;
        likeUsersAllLayout = convertView.findViewById(R.id.likesAllLayout);
        likeUsersLayout = (LinearLayout) convertView.findViewById(R.id.likeUsersLayout);
        likeUsersLayout.getViewTreeObserver().addOnPreDrawListener(new MyPreDraw(likeUsersAllLayout, likeUsersLayout));
    }


    private class MyPreDraw implements ViewTreeObserver.OnPreDrawListener {

        private LinearLayout layout;
        private View allLayout;

        public MyPreDraw(View allLayout, LinearLayout linearLayout) {
            layout = linearLayout;
            this.allLayout = allLayout;
        }

        @Override
        public boolean onPreDraw() {
            int width = layout.getWidth();

            if (width <= 0) {
                return true;
            }

            if (layout.getChildCount() > 0) {
                layout.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }

            width -= (layout.getPaddingLeft() + layout.getPaddingRight());

            int imageWidth = JUtils.dip2px(30);
            int imageMargin = JUtils.dip2px(12);

            int shenxia = width % (imageWidth + imageMargin);
            int count = width / (imageWidth + imageMargin);
            imageMargin += shenxia / count;
            imageMargin /= 2;

            final int MAX_DISPLAY_USERS = 10;
            if (count > MAX_DISPLAY_USERS) {
                count = MAX_DISPLAY_USERS;
            }

            for (int i = 0; i < count; ++i) {
                ImageView view = new ImageView(mContext);
                layout.addView(view);

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.width = imageWidth;
                lp.height = imageWidth;
                lp.leftMargin = imageMargin;
                lp.rightMargin = imageMargin;
                view.setLayoutParams(lp);
                view.setOnClickListener(mOnClickUser);
            }

            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            layout.addView(textView);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) textView.getLayoutParams();
            lp.width = imageWidth;
            lp.height = imageWidth;
            lp.leftMargin = imageMargin;
            lp.rightMargin = imageMargin;
            textView.setBackgroundResource(R.mipmap.ic_bg_good_count);
            textView.setTextColor(0xffffffff);
            textView.setVisibility(View.GONE);
            textView.setOnClickListener(onClickLikeUsrs);

//            displayLikeUser();
            return true;
        }
    }

    public void displayLikeUser(ZanList zList) {
        if ("0".equals(zList.getData().getCnt())) {
            likeUsersAllLayout.setVisibility(View.GONE);
        } else {
            likeUsersAllLayout.setVisibility(View.VISIBLE);
        }

        if (likeUsersLayout.getChildCount() == 0) {
            likeUsersLayout.setTag(zList);
            return;
        }

        int readUserCount = Integer.parseInt(zList.getData().getCnt());//点赞总数
        List<ZanList.DataBean.ListBean> displayUsers = new ArrayList<>();
        displayUsers.addAll(zList.getData().getList());

        int imageCount = likeUsersLayout.getChildCount() - 1;//区域显示头像数

//        Log.d("mm", "ddd disgood " + imageCount + "," + displayUsers.size() + "," + readUserCount);

        //  likeUsersLayout.getChildAt(imageCount).setTag(maopaoData.id);

        if (displayUsers.size() < imageCount) {
            if (readUserCount <= imageCount) {
                int i = 0;
                for (; i < displayUsers.size(); ++i) {
                    updateImageDisplay(displayUsers, i);
                }

                for (; i < imageCount; ++i) {
                    likeUsersLayout.getChildAt(i).setVisibility(View.GONE);
                }

                likeUsersLayout.getChildAt(i).setVisibility(View.GONE);

            } else {
                int i = 0;
                for (; i < displayUsers.size(); ++i) {
                    updateImageDisplay(displayUsers, i);
                }

                for (; i < imageCount; ++i) {
                    likeUsersLayout.getChildAt(i).setVisibility(View.GONE);
                }

                TextView textV = (TextView) likeUsersLayout.getChildAt(imageCount);
                textV.setVisibility(View.VISIBLE);
                textV.setText(readUserCount + "");
            }

        } else {
            --imageCount;
            for (int i = 0; i < imageCount; ++i) {
                updateImageDisplay(displayUsers, i);
            }

            likeUsersLayout.getChildAt(imageCount).setVisibility(View.GONE);
            TextView textView = (TextView) likeUsersLayout.getChildAt(imageCount + 1);
            textView.setVisibility(View.VISIBLE);
            textView.setText(readUserCount + "");
        }

        imageCount = likeUsersLayout.getChildCount() - 1;
        for (int i = 0; i < imageCount; ++i) {
            View v = likeUsersLayout.getChildAt(i);
            if (v.getVisibility() == View.VISIBLE) {
                //    v.setTag(displayUsers.get(i).global_key);
            } else {
                break;
            }
        }
    }


    private void updateImageDisplay(List<ZanList.DataBean.ListBean> likeUsers, int i) {
        ImageView image = (ImageView) likeUsersLayout.getChildAt(i);
        image.setVisibility(View.VISIBLE);

        String head_like = MyServiceClient.getBaseUrl() + likeUsers.get(i).getUser_head();
        Glide.with(mContext)
                .load(head_like)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(image);
    }

    View.OnClickListener onClickLikeUsrs = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(mContext,"点击进入显示所有点赞人员页面",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), LikeUsersListActivity_class);
//            intent.putExtra("id", (int) v.getTag());
//            startActivity(intent);
        }
    };

}