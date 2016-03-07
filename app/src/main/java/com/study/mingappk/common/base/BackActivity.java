package com.study.mingappk.common.base;

import android.os.Bundle;
import android.view.MenuItem;


public class BackActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置toolbar后,开启返回图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设备返回图标样式
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.more);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                //case R.id.action_settings:
                // return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

}
