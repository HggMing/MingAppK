package com.study.mingappk.app.api;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyService;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.model.bean.EbankWifiConnect;
import com.study.mingappk.model.bean.IpPort;

import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ming on 2016/10/25.
 */

public class OtherApi {
    private static String auth = Hawk.get(APP.USER_AUTH);
    private static MyService myService = MyServiceClient.getService();

    /**
     * 认证公共Wifi（e-bank or e-site）
     * @return
     */
    public static Observable<EbankWifiConnect> ebankWifiConnect() {
        return myService.get_IpPort()
                .flatMap(new Func1<IpPort, Observable<EbankWifiConnect>>() {
                    @Override
                    public Observable<EbankWifiConnect> call(IpPort ipPort) {
                        return myService.get_EbankWifiConnect(ipPort.getIp(), ipPort.getPort(), ipPort.getMac(), auth);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
