package com.study.mingappk.common.injector.component;

import android.content.Context;


import com.study.mingappk.common.injector.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ming on 2016/10/19.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();


}
