package com.study.mingappk.tab4.shop;

import android.os.Bundle;

import com.study.mingappk.R;
import com.study.mingappk.tmain.baseactivity.BackActivity;

public class MyShopActivity extends BackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        setToolbarTitle(R.string.title_activity_my_shop);
    }
}
