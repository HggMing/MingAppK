package com.study.mingappk.common.base;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.mingappk.R;

public class BackActivity extends BaseActivity {

    TextView toolbarTitle;
    Toolbar toolbar;

    public void setToolbarTitle(@StringRes int resid) {
        toolbarTitle.setText(resid);
    }

    public void setToolbarTitle(String s) {
        toolbarTitle.setText(s);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_base);

        toolbar = (Toolbar) findViewById(R.id.toolbar_activity_base);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        assert toolbar != null;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_toolbar_back);
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

    private LinearLayout parentLayout;//把父类activity和子类activity的view都add到这里

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        parentLayout = new LinearLayout(this);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        if (viewGroup != null) {
            viewGroup.addView(parentLayout);
        }
        LayoutInflater.from(this).inflate(layoutResID, parentLayout, true);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLayout, true);
    }

    @Override
    public void setContentView(View view) {
        parentLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentLayout.addView(view, params);
    }
}
