package com.study.mingappk.common.widgets.rxdownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import rx.subjects.Subject;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/11/10
 * Time: 11:28
 * FIXME
 */
class DownloadReceiver extends BroadcastReceiver {
    public static final String RX_BROADCAST_DOWNLOAD_NEXT = "com.study.mingappk.common.widgets.rxdownload.broadcast.intent.action.next";
    public static final String RX_BROADCAST_DOWNLOAD_COMPLETE = "com.study.mingappk.common.widgets.rxdownload.broadcast.intent.complete";
    public static final String RX_BROADCAST_DOWNLOAD_ERROR = "com.study.mingappk.common.widgets.rxdownload.broadcast.intent.error";

    public static final String RX_BROADCAST_KEY_EXCEPTION = "com.study.mingappk.common.widgets.rxdownload.broadcast.key.exception";
    public static final String RX_BROADCAST_KEY_STATUS = "com.study.mingappk.common.widgets.rxdownload.broadcast.key.status";
    public static final String RX_BROADCAST_KEY_URL = "com.study.mingappk.common.widgets.rxdownload.broadcast.key.url";

    private String mKeyUrl;
    private Subject<DownloadStatus, DownloadStatus> mSubject;

    DownloadReceiver(String keyUrl, Subject<DownloadStatus, DownloadStatus> subject) {
        mSubject = subject;
        mKeyUrl = keyUrl;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String key = intent.getStringExtra(RX_BROADCAST_KEY_URL);
        switch (action) {
            case RX_BROADCAST_DOWNLOAD_NEXT:
                if (key.compareTo(mKeyUrl) == 0) {
                    mSubject.onNext((DownloadStatus) intent.getParcelableExtra(RX_BROADCAST_KEY_STATUS));
                }
                break;
            case RX_BROADCAST_DOWNLOAD_COMPLETE:
                if (key.compareTo(mKeyUrl) == 0) {
                    mSubject.onCompleted();
                }
                break;
            case RX_BROADCAST_DOWNLOAD_ERROR:
                if (key.compareTo(mKeyUrl) == 0) {
                    Throwable e = (Throwable) intent.getSerializableExtra(RX_BROADCAST_KEY_EXCEPTION);
                    mSubject.onError(e);
                }
                break;
        }
    }

    IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RX_BROADCAST_DOWNLOAD_NEXT);
        intentFilter.addAction(RX_BROADCAST_DOWNLOAD_COMPLETE);
        intentFilter.addAction(RX_BROADCAST_DOWNLOAD_ERROR);
        return intentFilter;
    }
}