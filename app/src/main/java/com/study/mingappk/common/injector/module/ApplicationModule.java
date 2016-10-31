package com.study.mingappk.common.injector.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ming on 2016/10/19.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return mContext;
    }
}
