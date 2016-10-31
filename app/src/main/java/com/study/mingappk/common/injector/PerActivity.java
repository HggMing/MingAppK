package com.study.mingappk.common.injector;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Ming on 2016/10/19.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
