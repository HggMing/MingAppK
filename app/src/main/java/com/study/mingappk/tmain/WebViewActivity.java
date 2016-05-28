package com.study.mingappk.tmain;

import android.os.Bundle;
import android.webkit.WebView;

import com.study.mingappk.R;
import com.study.mingappk.model.service.MyServiceClient;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends BackActivity {

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        setToolbarTitle("免责条款");
        webView.loadUrl(MyServiceClient.URL_REG);
    }
}
