package com.study.mingappk.model.provider;

import com.study.mingappk.model.bean.Phone2Adress;
import com.study.mingappk.model.service.MyServiceClient;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ming on 2016/4/5.
 */
public class Phone2AdressProvider {

    /**
     * 获取手机号归属地城市
     * @param phone 手机号
     * @return
     */
    public static Observable<String> getCity(String phone) {
        String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";
        return MyServiceClient.getService().getObservable_Phone2Adress(API_KEY, phone)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<Phone2Adress, Boolean>() {
                    @Override
                    public Boolean call(Phone2Adress phone2Adress) {
                        return (phone2Adress != null && phone2Adress.getErrNum() == 0);
                    }
                })
                .map(new Func1<Phone2Adress, String>() {
                    @Override
                    public String call(Phone2Adress phone2Adress) {
                        return phone2Adress.getRetData().getCity();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
