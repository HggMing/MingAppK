package com.study.mingappk.tab3.addfollow;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.study.mingappk.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FollowVillageActivity extends AppCompatActivity {

    @Bind(R.id.id_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.id_tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.id_appbarlayout)
    AppBarLayout mAppbarLayout;
    @Bind(R.id.id_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.id_coordinatorlayout)
    CoordinatorLayout mCoordinatorLayout;

    // TabLayout中的tab标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_village);
        ButterKnife.bind(this);

        // 初始化mTitles、mFragments等ViewPager需要的数据
        initData();

        // 对各种控件进行设置、适配、填充数据
        configViews();
    }

    private void initData() {

        // Tab的标题
        mTitles = new String[]{"推荐", "自选"};

        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        mFragments.add(0, new FollowR1Fragment());
        mFragments.add(1, new FollowR2Fragment());
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
}
