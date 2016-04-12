package com.study.mingappk.tmain;

import android.os.Bundle;
import android.view.MenuItem;

import com.study.mingappk.R;


public class BackActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
