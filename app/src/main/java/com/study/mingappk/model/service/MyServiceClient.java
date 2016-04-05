package com.study.mingappk.model.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 服务器连接客服端
 */
public class MyServiceClient {

    private static final String BASE_URL = "http://121.40.105.149:9901/";//API接口的主机地址
    private static final String BASE_URL2 = "http://121.40.105.149:9901";//API接口的主机地址

    public static String getBaseUrl() {
        return BASE_URL2;
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
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(client)
                .build();
    }
}




