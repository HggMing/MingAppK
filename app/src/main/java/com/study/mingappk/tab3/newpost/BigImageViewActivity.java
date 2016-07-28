package com.study.mingappk.tab3.newpost;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.study.mingappk.R;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.views.gallerfinal.model.PhotoInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class BigImageViewActivity extends AppCompatActivity {

    public static String IMAGE_LIST = "image_list_post";
    public static String IMAGE_INDEX = "index_post";
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.indicator)
    CircleIndicator mIndicator;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private List<PhotoInfo> mList;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_pager);
        ButterKnife.bind(this);
        BaseTools.transparentStatusBar(this);//透明状态栏

        mList = (List<PhotoInfo>) getIntent().getSerializableExtra(IMAGE_LIST);
        index = getIntent().getIntExtra(IMAGE_INDEX, 0);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mIndicator.setViewPager(mViewPager);//设置指示器
        mViewPager.setCurrentItem(index, true);
    }

    private class SamplePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            String imageUrl ="file://"+mList.get(position).getPhotoPath();
            progressBar.setVisibility(View.VISIBLE);
            //加载目标大图
            Glide.with(container.getContext())
                    .load(imageUrl)
                    .into(new GlideDrawableImageViewTarget(photoView){
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    onBackPressed();//点击图片返回
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });
            // 添加 PhotoView 到 ViewPager并返回
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }
    }
}
