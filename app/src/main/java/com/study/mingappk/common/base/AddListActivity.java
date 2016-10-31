package com.study.mingappk.common.base;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.melnykov.fab.FloatingActionButton;
import com.study.mingappk.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddListActivity extends BackActivity {

    @Bind(R.id.m_x_recyclerview)
    public RecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    public TextView contentEmpty;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        ButterKnife.bind(this);

        config();
    }

    private void config() {
        //设置fab
        fab.attachToRecyclerView(mXRecyclerView);//fab随recyclerView的滚动，隐藏和出现
        int themeColor = ThemeUtils.getColorById(this, R.color.theme_color_primary);
        int themeColor2 = ThemeUtils.getColorById(this, R.color.theme_color_primary_dark);
        fab.setColorNormal(themeColor);//fab背景颜色
        fab.setColorPressed(themeColor2);//fab点击后背景颜色
        fab.setColorRipple(themeColor2);//fab点击后涟漪颜色
        //设置recyclerview布局
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    @OnClick(R.id.fab)
    public void onClick() {
    }
}
