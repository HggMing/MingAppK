package com.study.mingappk.tab3.affairs;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.study.mingappk.R;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BackActivity {

    public static String TYPE = "type_for_title";
    public static String NEWS_ID = "news_id";
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        switch (getIntent().getIntExtra(TYPE, 1)) {
            case 1:
                setToolbarTitle("新闻");
                break;
            case 2:
                setToolbarTitle("政策");
                break;
            case 3:
                setToolbarTitle("服务");
                break;
            case 4:
                setToolbarTitle("资讯");
                break;
        }

        initData();
    }

    private void initData() {
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
        String id = getIntent().getStringExtra(NEWS_ID);
        webView.loadUrl(MyServiceClient.getBaseUrl() + "/news/info?id=" + id);
    }
}
