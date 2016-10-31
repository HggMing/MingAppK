package com.study.mingappk.common.widgets.nineimage;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

/**
 *自定义九宫格Adapter
 */
public abstract class NineGridImageViewAdapter<T> {
    protected abstract void onDisplayImage(Context context, ImageView imageView, T t);

    protected void onItemImageClick(Context context, int index, List<T> list) {
    }

    protected ImageView generateImageView(Context context) {
        GridImageView imageView = new GridImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}