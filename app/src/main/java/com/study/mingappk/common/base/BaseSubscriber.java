package com.study.mingappk.common.base;

import android.content.Context;
import android.widget.Toast;

import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;

import rx.Subscriber;

/**
 * Created by Ming on 2016/10/25.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {


    @Override
    public void onStart() {
        super.onStart();
        Context mContext= APP.getInstance().getApplicationContext();
        if (!BaseTools.checkNetWorkStatus(mContext)) {
            Toast.makeText(mContext, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            // 一定好主动调用下面这一句
            //        closeLoadingProgress();
            return;
        }
        // 显示进度条
//        showLoadingProgress();
    }

    @Override
    public void onCompleted() {
        //关闭等待进度条
//        closeLoadingProgress();

    }
}
