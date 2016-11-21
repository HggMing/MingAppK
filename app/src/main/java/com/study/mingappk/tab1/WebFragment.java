package com.study.mingappk.tab1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintProgressBar;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页
 */
public class WebFragment extends Fragment {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressBar)
    TintProgressBar progressBar;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    private String auth;
    AppCompatActivity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        auth = Hawk.get(APP.USER_AUTH);

        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
        //添加进度条
        webView.setWebChromeClient(new MyWebChromeClient());
        //为WebView设置WebViewClient处理某些操作
        webView.setWebViewClient(new MywWebViewClient());
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        File cachePath = new File(APP.FILE_PATH, "WebCache");
        webSetting.setAppCachePath(cachePath.getPath());
        File databasesPath = new File(APP.FILE_PATH, "DbCache");
        webSetting.setDatabasePath(databasesPath.getPath());

        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void closetouch() {
//                Toast.makeText(mActivity, "关闭滑动", Toast.LENGTH_SHORT).show();
                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                });
            }

            @JavascriptInterface
            public void opentouch() {
//                Toast.makeText(mActivity, "打开滑动", Toast.LENGTH_SHORT).show();
                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
            }
        }, "mingapk");

//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                            v.getParent().requestDisallowInterceptTouchEvent(true);
//                            break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        v.getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//                return false;
//            }
//        });
        // 打开链接。   device_type 普通浏览器：0  app:1  微信：2
        webView.loadUrl(MyServiceClient.getBaseUrl() + "/mobile/index?auth=" + auth + "&device_type=1");
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){"
                + "var objs = $('.lunboBox'); " //轮播图片的div容器，
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "    objs[i].ontouchstart=function()  " + "    {  "
                + "        window.mingapk.opentouch();  "
                + "   return true; }  "
                + "}"

                + "var objs = $('.lunboBox'); " //轮播图片的div容器，
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "    objs[i].ontouchend=function()  " + "    {  "
                + "        window.mingapk.closetouch();  "
                + "   return true; }  "
                + "}"

//                + "var objs = $('body'); "  //点击任何地方 ， 系统开始可以接受滚动
//                + "for(var i=0;i<objs.length;i++)  " + "{"
//                + "    objs[i].ontouchstart=function()  " + "    {  "
//                + "        window.mingapk.opentouch();  "
//                + "   return false; }  "
//                + "}"

                + "})()");
    }


    @OnClick(R.id.content_empty)
    public void onClick() {
        webView.reload();//刷新网页
        contentEmpty.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (progressBar != null) {
                progressBar.setProgress(i);
                if (i == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                    AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
                    animation.setDuration(500);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    progressBar.startAnimation(animation);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    class MywWebViewClient extends WebViewClient {
        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
            Intent intent = new Intent(mActivity, BrowserActivity.class);
            intent.putExtra(BrowserActivity.KEY_URL, url);
            startActivity(intent);
            //如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebViewClient.a a) {
            super.onReceivedError(webView, webResourceRequest, a);
            contentEmpty.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }
    }


}
