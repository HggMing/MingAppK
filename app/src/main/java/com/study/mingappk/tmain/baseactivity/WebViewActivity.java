package com.study.mingappk.tmain.baseactivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.study.mingappk.R;
import com.study.mingappk.tmain.baseactivity.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends BackActivity {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    public static String TAG = "the_title_name";
    public static String VILLAGE_ID = "the_village_id";
    public final static String TITLE_NAME1 = "免责条款";
    public final static String TITLE_NAME2 = "村况";
    public static final String URL_REG1 = "http://121.40.105.149:9901/system/clause";//免责条款显示网址
    public static final String URL_REG2 = "http://121.40.105.149:9901/vill/vill?k=1&v=";//村况显示网址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        //设置toolbar标题
        String title = getIntent().getStringExtra(TAG);
        setToolbarTitle(title);
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
        switch (title) {
            case TITLE_NAME1:
                webView.loadUrl(URL_REG1);
                break;
            case TITLE_NAME2:
                String vid = getIntent().getStringExtra(VILLAGE_ID);
                webView.loadUrl(URL_REG2 + vid);
                break;
        }
    }

}
