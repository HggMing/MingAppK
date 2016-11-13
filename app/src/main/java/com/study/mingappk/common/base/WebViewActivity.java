package com.study.mingappk.common.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;

import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.study.mingappk.app.APP.BASE_URL;

public class WebViewActivity extends BackActivity {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    public static String KEY_TITLE = "the_title_name";
    public static String KEY_URL = "the_url";

    public final static String TITLE_NAME1 = "免责条款";
    public static final String URL_REG1 = BASE_URL + "system/clause";//免责条款显示网址

    public final static String TITLE_NAME2 = "村况";
    public static final String URL_REG2 = BASE_URL + "vill/vill?k=1&v=";//村况显示网址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        //设置toolbar标题
        String mTitle = getIntent().getStringExtra(KEY_TITLE);
        setToolbarTitle(mTitle);
        //添加进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
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
        });
        //使webVIew里面图片自适应
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

        } else {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应页面大小 ，只4.4以下有效
        }
        /*//设置加载进来的页面自适应手机屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);*/
        //加载页面
        String mUrl = getIntent().getStringExtra(KEY_URL);
        webView.loadUrl(mUrl);
    }
}
