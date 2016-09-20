package com.study.mingappk.tab4.shop.shoptab1.books;

import android.os.Bundle;

import com.study.mingappk.R;
import com.study.mingappk.tab4.shop.shoptab1.express.ExpressSendFragment;
import com.study.mingappk.tmain.baseactivity.TabsActivity;

public class BooksActivity extends TabsActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarTitle(R.string.title_activity_books);

        // 初始化mTitles、mFragments等ViewPager需要的数据
        initData();
    }

    private void initData() {
        // Tab的标题
        mTitles = new String[]{"图书列表", "已借书籍"};

        //初始化填充到ViewPager中的Fragment集合
        mFragments.add(0, new BooksTab1Fragment());
        mFragments.add(1, new BooksTab2Fragment());


        mAdapter.setItem(mTitles, mFragments);
    }


}
