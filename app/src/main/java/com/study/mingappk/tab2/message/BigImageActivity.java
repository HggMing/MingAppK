package com.study.mingappk.tab2.message;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.views.bigimageview.MViewPager;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.BBSList;
import com.study.mingappk.model.service.MyServiceClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class BigImageActivity extends AppCompatActivity {

    public static String IMAGE_LIST = "image_list_chat";
    public static String IMAGE_INDEX = "index_chat";
    @Bind(R.id.view_pager)
    MViewPager mViewPager;
    @Bind(R.id.indicator)
    CircleIndicator mIndicator;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private ArrayList<String> mList;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_pager);
        ButterKnife.bind(this);
        BaseTools.transparentStatusBar(this);//透明状态栏

//        mList = getIntent().getParcelableArrayListExtra(IMAGE_LIST);
        mList = getIntent().getStringArrayListExtra(IMAGE_LIST);
        index = getIntent().getIntExtra(IMAGE_INDEX, 0);
        mViewPager.setAdapter(new SamplePagerAdapter());
//        mIndicator.setViewPager(mViewPager);//设置指示器
        mViewPager.setCurrentItem(index, true);
    }

    private class SamplePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
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
        public Object instantiateItem(final ViewGroup container, final int position) {
            PhotoView photoView = new PhotoView(container.getContext());
           final String imageUrl = mList.get(position);

            progressBar.setVisibility(View.VISIBLE);
            //加载目标大图
            Glide.with(container.getContext())
                    .load(imageUrl)
                    .into(new GlideDrawableImageViewTarget(photoView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
            //点击图片返回
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    onBackPressed();//点击图片返回
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });

            //长按下载图片
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //长按下载图片
                    MyDialog.Builder builder = new MyDialog.Builder(container.getContext());
                    builder.setTitle("提示")
                            .setMessage("下载图片" + "?")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //执行获得权限后相关代码
                                            saveImage(imageUrl);
                                            dialog.dismiss();
                                        }
                                    })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    if (!isFinishing()) {
                        builder.create().show();
                    }
                    return false;
                }
            });
            // 添加 PhotoView 到 ViewPager并返回
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }
    }


    public void saveImage(String imageUrl) {

        String[] names = new String[0];
        if (imageUrl != null) {
            names = imageUrl.split("/");
        }
        final String imageName = names[names.length - 1];
        Glide
                .with(BigImageActivity.this)
                .load(imageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        saveToSdCard(BigImageActivity.this, resource, imageName);
                    }
                });

    }

    public String saveToSdCard(Context context, Bitmap bitmap, String filename) {
        String stored = null;
        File folder = new File(APP.FILE_PATH + "Download/Pictures/");
        boolean a = folder.mkdirs();
        File file = new File(folder, filename);
        if (file.exists())
            return null;
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            stored = "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "图片已保存到" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), filename, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        return stored;
    }
}