package com.study.mingappk.common.utils;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.study.mingappk.app.APP;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ming on 2016/8/10.
 */
public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        String cachePath = APP.FILE_PATH +"GlideCache/";
        File f = new File(cachePath);
        if (!f.exists()) {
            f.mkdirs();
            try {
                new File(cachePath, ".nomedia").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int cacheSize250MegaBytes = 250*1024*1024;
        //设置glide缓存目录和大小
        builder.setDiskCache(new DiskLruCacheFactory(cachePath, cacheSize250MegaBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
