package com.study.mingappk.tab4.applyshoper;

import android.os.Bundle;

import com.study.mingappk.R;
import com.study.mingappk.common.base.BackActivity;

public class MyShopActivity extends BackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        setToolbarTitle(R.string.title_activity_my_shop);
    }
}
