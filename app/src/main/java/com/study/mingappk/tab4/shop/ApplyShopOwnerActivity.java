package com.study.mingappk.tab4.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.mingappk.R;
import com.study.mingappk.tmain.BackActivity;

public class ApplyShopOwnerActivity extends BackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_shop_owner);
        setToolbarTitle(R.string.title_activity_apply_shop_owner);
    }
}
