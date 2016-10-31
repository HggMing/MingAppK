package com.study.mingappk.tab3.affairs;

import android.os.Bundle;

import com.study.mingappk.R;
import com.study.mingappk.common.base.TabsActivity;

import butterknife.ButterKnife;

/**
 * 政务
 */
public class GovernmentAffairsActivity extends TabsActivity {

    public static String VILLAGE_ID = "village_id";
    public static String FRAGMENT_TYPE = "fragment_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_government_affairs);

        // 初始化mTitles、mFragments等ViewPager需要的数据
        initData();
    }

    private void initData() {
        // Tab的标题
        mTitles = new String[]{"新闻", "政策", "服务", "资讯"};

        //初始化填充到ViewPager中的Fragment集合
        mFragments.add(0, new NewsListFragment());
        mFragments.add(1, new NewsListFragment());
        mFragments.add(2, new NewsListFragment());
        mFragments.add(3, new NewsListFragment());
        //将Activity的值（村id）传给fragment
        for (int i = 0; i < mFragments.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString(VILLAGE_ID, getIntent().getStringExtra(VILLAGE_ID));
            bundle.putInt(FRAGMENT_TYPE, i + 1);
            mFragments.get(i).setArguments(bundle);
        }

        mAdapter.setItem(mTitles,mFragments);
    }
}
