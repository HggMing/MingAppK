package com.study.mingappk.tab4.shop.shoptab1.express;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.study.mingappk.R;
import com.study.mingappk.tab3.affairs.NewsListFragment;
import com.study.mingappk.tmain.baseactivity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExpressSendActivity extends BaseActivity {

    @Bind(R.id.toolbar_title2)
    TextView toolbarTitle;
    @Bind(R.id.m_toolbar)
    TintToolbar mToolbar;
    @Bind(R.id.m_tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.m_viewpager)
    ViewPager mViewPager;

    // TabLayout中的tab标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;

    public static String EXPRESS_STATUS = "express_status";//0为待寄快递；1为已寄快递
    public static final int REQUEST_CODE = 1231;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_send);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        toolbarTitle.setText(R.string.title_activity_express_send);

        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_back);
        }
        // 初始化mTitles、mFragments等ViewPager需要的数据
        initData();
        // 对各种控件进行设置、适配、填充数据
        configViews();
    }

    private void initData() {

        // Tab的标题
        mTitles = new String[]{"待寄快递", "已寄快递"};

        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        mFragments.add(0, new ExpressSendFragment());
        mFragments.add(1,new ExpressSendFragment());

        //将标记值传给fragment，0为待寄快递；1为已寄快递
        for (int i = 0; i < mFragments.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt(EXPRESS_STATUS, i );
            mFragments.get(i).setArguments(bundle);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                for (int i = 0; i < mFragments.size(); i++) {
                    ((ExpressSendFragment) mFragments.get(i)).initData();
                }
            }
        }
    }

    private void configViews() {

        // 设置显示Toolbar
        setSupportActionBar(mToolbar);
        // 初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(2);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] mTitles;
        private List<Fragment> mFragments;

        public MyViewPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> mFragments) {
            super(fm);
            this.mTitles = mTitles;
            this.mFragments = mFragments;
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
            return mFragments.size();
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
