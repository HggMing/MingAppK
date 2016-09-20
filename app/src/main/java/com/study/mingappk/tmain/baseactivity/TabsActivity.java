package com.study.mingappk.tmain.baseactivity;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.study.mingappk.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabsActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.m_toolbar)
    TintToolbar mToolbar;
    @Bind(R.id.m_tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.m_viewpager)
    ViewPager mViewpager;

    // TabLayout中的tab标题
    public String[] mTitles;
    // 填充到ViewPager中的Fragment
    public List<Fragment> mFragments = new ArrayList<>();
    // ViewPager的数据适配器
    public MyViewPagerAdapter mAdapter;

    public void setToolbarTitle(@StringRes int resid) {
        toolbarTitle.setText(resid);
    }

    public void setToolbarTitle(String s) {
        toolbarTitle.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_back);
        }

        // 对各种控件进行设置、适配、填充数据
        configViews();
    }

    private void configViews() {
        // 设置显示Toolbar
        setSupportActionBar(mToolbar);
        // 初始化ViewPager的适配器，并设置给它
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);
        // 设置ViewPager最大缓存的页面个数
        mViewpager.setOffscreenPageLimit(mFragments.size());

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewpager);
    }

    public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] mTitles;
        private List<Fragment> mFragments;

        public MyViewPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> mFragments) {
            super(fm);
            this.mTitles = mTitles;
            this.mFragments = mFragments;
        }

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setItem(String[] mTitles, List<Fragment> mFragments) {
            this.mTitles = mTitles;
            this.mFragments = mFragments;
            this.notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return this.mFragments == null ? 0 : this.mFragments.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
