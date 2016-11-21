package com.study.mingappk.common.widgets.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.common.widgets.rxdownload.DownloadStatus;
import com.study.mingappk.common.widgets.rxdownload.RxDownload;

import java.io.File;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Dialog_UpdateApp extends Dialog {
    public Dialog_UpdateApp(Context context) {
        super(context);
    }

    public Dialog_UpdateApp(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title = "提示";
        private String message;
        private boolean isCannel = true;  //点击对话框外，关闭对话框

        private String ok;
        private String cancel;
        private OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        private ProgressBar mProgress;
        private TextView mPercent;
        private TextView mSize;

        private Subscription subscription;
        private String updateUrl;
        private static final String DOWNLOAD_SAVE_PATH =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        private String mSaveName = "slove.apk";


        public Builder setCannel(boolean isCannel) {
            this.isCannel = isCannel;
            return this;
        }

        public Builder setUrl(String updateUrl) {
            this.updateUrl = updateUrl;
            return this;
        }


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public String getMessage() {
            return this.message;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(OnClickListener listener) {
            this.ok = "确定";
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.ok = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.ok = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(OnClickListener listener) {
            this.cancel = "取消";
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.cancel = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.cancel = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setProgress(boolean isChunked, int totalSize, int downloadSize) {
            mProgress.setIndeterminate(isChunked);
            mProgress.setMax(totalSize);
            mProgress.setProgress(downloadSize);
            return this;
        }

        public Builder setPercent(String percent) {
            mPercent.setText(percent);
            return this;
        }

        public Builder setMSize(String formatStatusString) {
            mSize.setText(formatStatusString);
            return this;
        }

        @SuppressWarnings("deprecation")
        public Dialog_UpdateApp create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Dialog_UpdateApp dialog = new Dialog_UpdateApp(context, R.style.MyDialog);
            dialog.setCancelable(isCannel);

            View layout = inflater.inflate(R.layout.dialog_update, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            WindowManager windowManager = ((Activity) context).getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth() - 80); // 设置宽度

            ((TextView) layout.findViewById(R.id.title)).setText(title);

            View is_show = (View) layout.findViewById(R.id.is_show);

            mProgress = (ProgressBar) layout.findViewById(R.id.progress);
            mPercent = (TextView) layout.findViewById(R.id.percent);
            mSize = (TextView) layout.findViewById(R.id.size);
            Button btn_cancel = (Button) layout.findViewById(R.id.negativeButton);
            btn_cancel.setText(cancel);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                }
            });
            if (null == cancel) {
                is_show.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
            }

            Button btn_ok = (Button) layout.findViewById(R.id.positiveButton);
            btn_ok.setText(ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });
            if (null == ok) {
                is_show.setVisibility(View.GONE);
                btn_ok.setVisibility(View.GONE);
            }
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            }
            showUpdateAuto();
            dialog.setContentView(layout);
            return dialog;
        }

        private void showUpdateAuto() {
//            updateUrl = "http://a.gdown.baidu.com/data/wisegame/f4314d752861cf51/WeChat_900.apk";
            if (updateUrl != null) {
                subscription = RxDownload.getInstance()
                        .download(updateUrl, mSaveName, DOWNLOAD_SAVE_PATH)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<DownloadStatus>() {
                            @Override
                            public void onCompleted() {
                                unsubscrbe();
                                installApp();
                            }

                            @Override
                            public void onError(Throwable e) {
                                unsubscrbe();
                            }

                            @Override
                            public void onNext(final DownloadStatus status) {
                                setProgress(status.isChunked, (int) status.getTotalSize(), (int) status.getDownloadSize());
                                setPercent(status.getPercent());
                                setMSize(status.getFormatStatusString());
                            }
                        });
            }
        }

        /**
         * 下载后安装App
         */
        private void installApp() {
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider",
                        new File(DOWNLOAD_SAVE_PATH + File.separator + mSaveName));
            } else {
                uri = Uri.fromFile(new File(DOWNLOAD_SAVE_PATH + File.separator + mSaveName));
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }

        /**
         * 取消订阅
         */
        public void unsubscrbe() {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
