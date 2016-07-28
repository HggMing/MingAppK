package com.study.mingappk.model.service;

import android.content.Context;

import com.jude.utils.JUtils;
import com.study.mingappk.app.APP;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 服务器连接客服端
 */
public class MyServiceClient {

    private static final String BASE_URL = "http://121.40.105.149:9901/";//API接口的主机地址
    private static final String BASE_URL2 = "http://121.40.105.149:9901";//API接口的主机地址
    private static final String BASE_URL3 = "http://push.traimo.com/source/";//聊天资源文件base_url
   public static final String DEFAULT_HEAD = "http://121.40.105.149:9901/Public/head/default.png";//服务器提供的默认头像，这里是为了方便替换为本地图片


    public static String getBaseUrl() {
        return BASE_URL2;
    }
    public static String getChatBaseUrl() {
        return BASE_URL3;
    }

    private static MyService mService;

    public static MyService getService() {
        if (mService == null) {
            createService();
        }
        return mService;
    }

    private static void createService() {
        mService = createRetrofit().create(MyService.class);
    }

    private static Retrofit createRetrofit() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();//拦截reqeust
                if (!JUtils.isNetWorkAvilable()) {//判断网络连接状况
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                            .build();
                }

                Response response = chain.proceed(request);
                if (JUtils.isNetWorkAvilable()) {
                    int maxAge = 60 * 60;// 有网络时 设置缓存超时时间1个小时
                    response.newBuilder()
                            .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .header("Cache-Control", "public, max-age=" + maxAge)//设置缓存超时时间
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)//设置缓存策略，及超时策略
                            .build();
                }
                return response;
            }
        };

        Context context = APP.getInstance().getApplicationContext();
        File cacheDirectory = new File(APP.FILE_PATH, "HttpCache");
        Cache cache = new Cache(cacheDirectory, 20 * 1024 * 1024);

        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)//设定10秒超时
                .addInterceptor(interceptor)
                .cache(cache)//设置缓存目录
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(client)
                .build();
    }
}




