package com.study.mingappk.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.widgets.gallerfinal.CoreConfig;
import com.study.mingappk.common.widgets.gallerfinal.FunctionConfig;
import com.study.mingappk.common.widgets.gallerfinal.ImageLoader;
import com.study.mingappk.common.widgets.gallerfinal.PauseOnScrollListener;
import com.study.mingappk.common.widgets.gallerfinal.ThemeConfig;
import com.study.mingappk.common.widgets.gallerfinal.widget.GFImageView;

import java.io.File;


/**
 * Android自定义相册，实现了拍照、图片选择（单选/多选）、 裁剪（单/多裁剪）、旋转，GalleryFinal为你定制相册。
 * Created by Ming on 2016/3/22.
 */
public class MyGallerFinal {

    //设置主题

    //获取当前app主题的颜色
    Context mContext=APP.getInstance().getApplicationContext();
    int themeColor=   ThemeUtils.getColorById(mContext, R.color.theme_color_primary);
    int themeColor2=   ThemeUtils.getColorById(mContext, R.color.theme_color_primary_dark);

    ThemeConfig theme = new ThemeConfig.Builder()
            .setTitleBarBgColor(themeColor)//标题栏背景颜色
            .setFabNornalColor(themeColor)//设置Floating按钮Nornal状态颜色
            .setCropControlColor(themeColor)//设置裁剪控制点和裁剪框颜色
            .setCheckSelectedColor(themeColor)//选择框选中颜色
            .setFabPressedColor(themeColor2)//设置Floating按钮Pressed状态颜色
//            .setCheckNornalColor(Color.WHITE)//选择框未选颜色
            .setTitleBarTextColor(Color.WHITE)//标题栏文本字体颜色
            .setIconBack(R.mipmap.ic_toolbar_back)//设置返回按钮icon
            .setIconRotate(R.mipmap.ic_action_repeat)//设置旋转icon
            .setIconCrop(R.mipmap.ic_action_crop)//设置裁剪icon
            .setIconCamera(R.mipmap.ic_action_camera)//设置相机icon
//            .setIconFolderArrow()//设置标题栏文件夹下拉arrow图标
//            .setIconDelete()//设置多选编辑页删除按钮icon
//            .setIconCheck()//设置checkbox和文件夹已选icon
//            .setIconFab()//设置Floating按钮icon
//            .setEditPhotoBgTexture()//设置图片编辑页面图片margin外背景
//            .setIconPreview()//设置预览按钮icon
//            .setPreviewBg()//设置预览页背景
            .build();
    //配置功能
    FunctionConfig functionConfig = new FunctionConfig.Builder()
            //.setMutiSelectMaxSize(9)//配置多选数量
            .setEnableEdit(true)//开启编辑功能
            .setEnableCrop(true)//开启裁剪功能
            .setEnableRotate(false)//开启旋转功能
            .setEnableCamera(true)//开启相机功能
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
    class GlideImageLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
            Glide.with(activity)
                    .load("file://" + path)
//                    .placeholder(defaultDrawable)
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

                        @Override
                        public void setRequest(Request request) {
                            imageView.setTag(R.id.adapter_item_tag_key, request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                        }
                    });
        }

        @Override
        public void clearMemoryCache() {
        }
    }

    PauseOnScrollListener pauseOnScrollListener = new GlidePauseOnScrollListener(false, false);

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
                .setNoAnimcation(true)//关闭动画
                .setEditPhotoCacheFolder(new File(APP.FILE_PATH + "EditTemp/"))//配置编辑（裁剪和旋转）功能产生的cache文件保存目录，不做配置的话默认保存在/sdcard/GalleryFinal/edittemp/
                .setTakePhotoFolder(new File(APP.FILE_PATH, "MyCamera/"))//设置拍照保存目录，默认是/sdcard/DICM/GalleryFinal/
                .build();
    }
}
