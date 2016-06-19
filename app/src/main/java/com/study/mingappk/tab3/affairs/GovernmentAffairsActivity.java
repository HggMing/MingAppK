package com.study.mingappk.tab3.affairs;

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
import android.view.MenuItem;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.QueryVillageList;
import com.study.mingappk.tab3.addfollow.FollowVillageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GovernmentAffairsActivity extends AppCompatActivity {

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
    @Bind(R.id.toolbar_title2)
    TextView toolbarTitle;

    public static String VILLAGE_ID = "village_id";
    public static String FRAGMENT_TYPE = "fragment_type";

    // TabLayout中的tab标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;

    private FollowVillageAdapter mAdapter = new FollowVillageAdapter();
    List<QueryVillageList.DataBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_affairs);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        toolbarTitle.setText(R.string.title_activity_government_affairs);

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

        //设置滑动关闭当前，返回上一页
        swipeBack();
    }

    private void swipeBack() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
//                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.15f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(APP.getInstance().getResources().getColor(R.color.swipe_back))//底层阴影颜色
                .setClosePercent(0.6f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                .setDisallowInterceptTouchEvent(false)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
                .addListener(new SwipeListener() {//滑动监听

                    @Override
                    public void onScroll(float percent, int px) {//滑动的百分比与距离
                    }

                    @Override
                    public void onEdgeTouch() {//当开始滑动
                    }

                    @Override
                    public void onScrollToClose() {//当滑动关闭
                    }
                });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    private void initData() {

        // Tab的标题
        mTitles = new String[]{"新闻", "政策", "服务", "资讯"};

        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        mFragments.add(0, new NewsListFragment());
        mFragments.add(1, new NewsListFragment());
        mFragments.add(2, new NewsListFragment());
        mFragments.add(3, new NewsListFragment());
        //将Activity的值（村id）传给fragment
        for (int i = 0; i < mFragments.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString(VILLAGE_ID, getIntent().getStringExtra(VILLAGE_ID));
            bundle.putInt(FRAGMENT_TYPE, i+1);
            mFragments.get(i).setArguments(bundle);
        }
    }

    private void configViews() {

        // 设置显示Toolbar
        setSupportActionBar(mToolbar);
        // 初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(4);

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