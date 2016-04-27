package com.study.mingappk.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.BaseTools;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Test3Activity extends AppCompatActivity {

    @Bind(R.id.toolbar_test3)
    Toolbar toolbarTest3;
    @Bind(R.id.collapsing_toolbar_layout_test3)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        ButterKnife.bind(this);
        BaseTools.transparentStatusBar(this);//透明状态栏

        //setSupportActionBar(toolbarTest3);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        //mCollapsingToolbarLayout.setTitle("设置标题文字");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        // mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
    }
}
