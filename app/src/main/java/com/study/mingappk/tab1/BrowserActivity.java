package com.study.mingappk.tab1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.melnykov.fab.FloatingActionButton;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BaseActivity;
import com.study.mingappk.common.widgets.alipay.PayUtils;
import com.study.mingappk.common.widgets.dialog.MyDialog;
import com.study.mingappk.common.widgets.gallertools.StringUtils;
import com.study.mingappk.model.bean.OrderInfo;
import com.study.mingappk.tab1.webutils.X5WebView;
import com.study.mingappk.tab2.chat.ChatActivity;
import com.study.mingappk.tab3.product.ProductListActivity;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BrowserActivity extends BaseActivity {
    public static String KEY_URL = "key_url";
    TextView toolbarTitle;
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
//    @Bind(R.id.m_webview)
//    X5WebView mWebView;

    private X5WebView mWebView;
    private ViewGroup mViewParent;
    private static final int MAX_LENGTH = 14;
    private ProgressBar mPageLoadingProgressBar = null;
    private URL mIntentUrl;
    WebSettings webSetting;

    private boolean isShowCart;//是否显示购物车
    private String shoppingCartUrl;//购物车地址
    private int shoppingCartNum;//购物车中商品列表条数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        Intent intent = getIntent();
        if (intent != null) {
            try {
                mIntentUrl = new URL(intent.getStringExtra(KEY_URL));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            if (Build.VERSION.SDK_INT >= 11) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);
        mViewParent = (ViewGroup) findViewById(R.id.webView1);

        toolbar = (Toolbar) findViewById(R.id.toolbar_activity_base);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        assert toolbar != null;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_toolbar_back);
        }

        configFab();

        this.webViewTransportTest();

        init();

//        Observable.timer(10, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        init();
//                    }
//                });
    }

    private void configFab() {
        //设置fab
        fab.setVisibility(View.VISIBLE);
        int themeColor = ThemeUtils.getColorById(this, R.color.theme_color_primary);
        int themeColor2 = ThemeUtils.getColorById(this, R.color.theme_color_primary_dark);
        fab.setColorNormal(themeColor);//fab背景颜色
        fab.setColorPressed(themeColor2);//fab点击后背景颜色
        fab.setColorRipple(themeColor2);//fab点击后涟漪颜色
    }

    private void webViewTransportTest() {
        X5WebView.setSmallWebViewEnabled(true);
    }


    private void initProgressBar() {
        mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources().getDrawable(R.drawable.color_progressbar));
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        mWebView = new X5WebView(this);
        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        initProgressBar();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                //如果不需要其他对点击链接事件的处理返回true，否则返回false
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView webView, String s) {
                super.onLoadResource(webView, s);
                if (shoppingCartNum > 0&&isShowCart) {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
               fab.setVisibility(View.GONE);
                isShowCart=false;
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title != null && title.length() > MAX_LENGTH) {
                    title = title.subSequence(0, MAX_LENGTH) + "...";
                }
                toolbarTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPageLoadingProgressBar.setProgress(newProgress);
                if (mPageLoadingProgressBar != null && newProgress != 100) {
                    mPageLoadingProgressBar.setVisibility(View.VISIBLE);
                } else if (mPageLoadingProgressBar != null) {
                    mPageLoadingProgressBar.setVisibility(View.GONE);
                }
            }

            //处理javascript中的alert
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, final JsResult jsResult) {
                MyDialog.Builder builder = new MyDialog.Builder(BrowserActivity.this);
                builder.setTitle("提示")
                        .setCannel(false)
                        .setMessage(s1)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //点击确定按钮之后，继续执行网页中的操作
                                        jsResult.confirm();
                                        dialog.dismiss();
                                    }
                                });
                if (!isFinishing()) {
                    builder.create().show();
                }
                return true;
            }

            //处理javascript中的confirm
            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, final JsResult jsResult) {
                MyDialog.Builder builder = new MyDialog.Builder(BrowserActivity.this);
                builder.setTitle("提示")
                        .setMessage(s1)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        jsResult.confirm();
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                jsResult.cancel();
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                return true;
            }
        });

        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                new AlertDialog.Builder(BrowserActivity.this)
                        .setTitle("下载")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(BrowserActivity.this, "fake message: i'll download...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(BrowserActivity.this, "fake message: refuse download...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        Toast.makeText(BrowserActivity.this, "fake message: refuse download...", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });

        mWebView.addJavascriptInterface(new WebAppInterface(this), "village");

        webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());

        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        if (mIntentUrl != null) {
            mWebView.loadUrl(mIntentUrl.toString());
        }
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        mWebView.loadUrl(shoppingCartUrl);
    }

    class WebAppInterface {
        Context mContext;

        WebAppInterface(Context context) {
            mContext = context;
        }

        String auth = Hawk.get(APP.USER_AUTH);

        @JavascriptInterface
        public String setTitle(String title, String isHomepage) {
            //修改标题
            return "{\"err\":\"0\"}";
        }

        @JavascriptInterface
        public String setPageParam(String cartUrl, String num) {
            //购物车
            shoppingCartUrl = cartUrl;
            if (!StringUtils.isEmpty(num)) {
                shoppingCartNum = Integer.parseInt(num);
            }
            isShowCart = true;
            return "{\"err\":\"0\"}";
        }

        @JavascriptInterface
        public String getAuth() {
            return "{\"err\":\"0\",\"auth\":\"" + auth + "\"}";
        }

        @JavascriptInterface
        public String Alipay(final String order_sn) {
//            Toast.makeText(BrowserActivity.this, "order_sn:" + order_sn, Toast.LENGTH_SHORT).show();
            MyServiceClient.getService()
                    .get_OrderInfo(order_sn, auth)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<OrderInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(OrderInfo orderInfo) {
                            OrderInfo.DataBean data = orderInfo.getData();
                            PayUtils payUtils = new PayUtils(BrowserActivity.this, -1);
                            payUtils.pay(data.getOrder_title(), "村特产订单",
                                    String.valueOf(data.getMoney()), data.getOrder_sn(), data.getUrl());
                        }
                    });
            return "{\"err\":\"0\"}";
        }

        @JavascriptInterface
        public String goInVill(String village_id) {
//            Toast.makeText(BrowserActivity.this, "vid:" + village_id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, ProductListActivity.class);
            intent.putExtra(ProductListActivity.VILLAGE_ID, village_id);
            startActivity(intent);
            return "{\"err\":\"0\"}";
        }

        @JavascriptInterface
        public String talk2Shopper(String shopper_id, String shopper_name, String shopper_head_url) {
//            Toast.makeText(BrowserActivity.this, "s_id:"+shopper_id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra(ChatActivity.UID, shopper_id);
            intent.putExtra(ChatActivity.USER_NAME, shopper_name);
            startActivity(intent);
            return "{\"err\":\"0\"}";
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null)
            return;
        mWebView.loadUrl(intent.getData().toString());
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }
}
