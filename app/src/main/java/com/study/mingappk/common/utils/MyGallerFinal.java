package com.study.mingappk.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.study.mingappk.R;

import java.io.File;

import cn.finalteam.galleryfinal.BuildConfig;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Android自定义相册，实现了拍照、图片选择（单选/多选）、 裁剪（单/多裁剪）、旋转，GalleryFinal为你定制相册。
 * Created by Ming on 2016/3/22.
 */
public class MyGallerFinal {

    //设置主题
    ThemeConfig theme = new ThemeConfig.Builder()
            .setTitleBarBgColor(Color.rgb(0x6D, 0xAC, 0xEA))
            .setTitleBarTextColor(Color.WHITE)
            .setFabNornalColor(Color.rgb(0x6D, 0xAC, 0xEA))
            .setFabPressedColor(Color.BLUE)
            .setCheckNornalColor(Color.WHITE)
            .setCheckSelectedColor(Color.BLACK)
            .setIconBack(R.mipmap.app_back)
            .setIconRotate(R.mipmap.ic_action_repeat)
            .setIconCrop(R.mipmap.ic_action_crop)
            .setIconCamera(R.mipmap.ic_action_camera)
            .build();
    //配置功能
    FunctionConfig functionConfig = new FunctionConfig.Builder()
            //.setMutiSelectMaxSize(2)//配置多选数量
            .setEnableEdit(true)//开启编辑功能
            .setEnableCrop(true)//开启裁剪功能
            .setEnableRotate(false)//开启旋转功能
            .setEnableCamera(false)//开启相机功能
            // .setCropWidth(800)//裁剪宽度
            // .setCropHeight(800)//裁剪高度
            //.setCropSquare(true)//裁剪正方形
//          .setSelected(List)//添加已选列表,只是在列表中默认呗选中不会过滤图片
//          .setFilter(List list)//添加图片过滤，也就是不在GalleryFinal中显示
            .setRotateReplaceSource(false)//配置选择图片时是否替换原始图片，默认不替换
            .setCropReplaceSource(false)//配置裁剪图片时是否替换原始图片，默认不替换
            .setForceCrop(true)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
            .setForceCropEdit(false)//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
            .setEnablePreview(false)//是否开启预览功能
            .build();

    //配置imageloader
    ImageLoader imageloader = new GlideImageLoader();

    //选择图片加载器
    class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
            Glide.with(activity)
                    .load("file://" + path)
                    .placeholder(defaultDrawable)
                    .error(defaultDrawable)
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
                    .skipMemoryCache(true)
                    //.centerCrop()
                    .into(new ImageViewTarget<GlideDrawable>(imageView) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            imageView.setImageDrawable(resource);
                        }

                      /*  @Override
                        public void setRequest(Request request) {
                             imageView.setTag(R.id.adapter_item_tag_key,request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                        }*/
                    });
        }

        @Override
        public void clearMemoryCache() {
        }
    }

    PauseOnScrollListener pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);

    class GlidePauseOnScrollListener extends PauseOnScrollListener {

        public GlidePauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
            super(pauseOnScroll, pauseOnFling);
        }

        @Override
        public void resume() {
            Glide.with(getActivity()).resumeRequests();
        }

        @Override
        public void pause() {
            Glide.with(getActivity()).pauseRequests();
        }
    }

    public CoreConfig getCoreConfig(Context context) {
        return new CoreConfig.Builder(context, imageloader, theme)
                .setFunctionConfig(functionConfig)//配置全局GalleryFinal功能
                .setPauseOnScrollListener(pauseOnScrollListener)//设置imageloader滑动加载图片优化OnScrollListener,根据选择的ImageLoader来选择PauseOnScrollListener
                .setNoAnimcation(false)//关闭动画
                .setEditPhotoCacheFolder(new File(Environment.getExternalStorageDirectory() + "/MingAppk/edittemp/"))//配置编辑（裁剪和旋转）功能产生的cache文件保存目录，不做配置的话默认保存在/sdcard/GalleryFinal/edittemp/
                .setTakePhotoFolder(new File(Environment.getExternalStorageDirectory(), "/MingAppk/" + "Photo/"))//设置拍照保存目录，默认是/sdcard/DICM/GalleryFinal/
                .build();
    }
}
