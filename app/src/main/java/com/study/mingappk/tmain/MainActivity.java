package com.study.mingappk.tmain;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.tab1.Tab1Fragment;
import com.study.mingappk.tab2.friendlist.FriendListFragment;
import com.study.mingappk.tab3.villagelist.VillageListFragment;
import com.study.mingappk.tab4.SettingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.viewPager_main)
    MainViewPager viewPager;
    @Bind(R.id.img_tab1_main)
    ImageView mTab1;
    @Bind(R.id.text_tab1_main)
    TextView tTab1;
    @Bind(R.id.img_tab2_main)
    ImageView mTab2;
    @Bind(R.id.text_tab2_main)
    TextView tTab2;
    @Bind(R.id.img_tab3_main)
    ImageView mTab3;
    @Bind(R.id.text_tab3_main)
    TextView tTab3;
    @Bind(R.id.img_tab4_main)
    ImageView mTab4;
    @Bind(R.id.text_tab4_main)
    TextView tTab4;
    public List<Fragment> fragments = new ArrayList<>();
    @Bind(R.id.toolbar_main)
    Toolbar mToolBar;
    @Bind(R.id.tab3_guide)
    ImageView tab3Guide;

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    private FragmentManager fragmentManager;
    private boolean isExit;//是否退出
    int idToolbar = 1;//toolbar 功能按钮页

    @OnClick({R.id.tab3_guide, R.id.tab1Layout, R.id.tab2Layout, R.id.tab3Layout, R.id.tab4Layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab3_guide:
                tab3Guide.setVisibility(View.GONE);
                break;
            case R.id.tab1Layout:
                viewPager.setCurrentItem(0);//选中index页
                break;
            case R.id.tab2Layout:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab3Layout:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tab4Layout:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    /**
     * 页卡切换监听,点击改变图标外观
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
//                    mToolBar.setTitle("动态");
                    toolbarTitle.setText(R.string.tab1_main);
                    idToolbar = 1;
                    mTab1.setImageDrawable(getResources().getDrawable(R.mipmap.tab1_btn1));
                    tTab1.setTextColor(getResources().getColor(R.color.tab_bnt1));   //选中时的字体颜色
                    setTab2ToB();
                    setTab3ToB();
                    setTab4ToB();
                    break;
                case 1:
//                    mToolBar.setTitle("老乡");
                    toolbarTitle.setText(R.string.tab2_main);
                    idToolbar = 2;
                    mTab2.setImageDrawable(getResources().getDrawable(R.mipmap.tab2_btn1));
                    tTab2.setTextColor(getResources().getColor(R.color.tab_bnt1));
                    setTab1ToB();
                    setTab3ToB();
                    setTab4ToB();
                    break;
                case 2:
//                    mToolBar.setTitle("我的村");
                    toolbarTitle.setText(R.string.tab3_main);
                    idToolbar = 3;
                    boolean isFirstRun = Hawk.get(APP.IS_FIRST_RUN, true);
                    if (isFirstRun) {
                        tab3Guide.setVisibility(View.VISIBLE);
                        Hawk.put(APP.IS_FIRST_RUN, false);
                    }
                    mTab3.setImageDrawable(getResources().getDrawable(R.mipmap.tab3_btn1));
                    tTab3.setTextColor(getResources().getColor(R.color.tab_bnt1));
                    setTab1ToB();
                    setTab2ToB();
                    setTab4ToB();
                    break;
                case 3:
//                    mToolBar.setTitle("设置");
                    toolbarTitle.setText(R.string.tab4_main);
                    idToolbar = 4;
                    mTab4.setImageDrawable(getResources().getDrawable(R.mipmap.tab4_btn1));
                    tTab4.setTextColor(getResources().getColor(R.color.tab_bnt1));
                    setTab1ToB();
                    setTab2ToB();
                    setTab3ToB();
                    break;
            }
            invalidateOptionsMenu();
        }


        private void setTab1ToB() {
            mTab1.setImageDrawable(getResources().getDrawable(R.mipmap.tab1_btn0));
            tTab1.setTextColor(getResources().getColor(R.color.tab_bnt0));
        }

        private void setTab2ToB() {
            mTab2.setImageDrawable(getResources().getDrawable(R.mipmap.tab2_btn0));
            tTab2.setTextColor(getResources().getColor(R.color.tab_bnt0));
        }

        private void setTab3ToB() {
            mTab3.setImageDrawable(getResources().getDrawable(R.mipmap.tab3_btn0));
            tTab3.setTextColor(getResources().getColor(R.color.tab_bnt0));
        }

        private void setTab4ToB() {
            mTab4.setImageDrawable(getResources().getDrawable(R.mipmap.tab4_btn0));
            tTab4.setTextColor(getResources().getColor(R.color.tab_bnt0));
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /**
     * 填充ViewPager的数据适配器
     */
    private class MyPagerAdapter extends PagerAdapter {
        /**
         * 获取要滑动的控件的数量
         *
         * @return 页数
         */
        @Override
        public int getCount() {
            return fragments.size();
        }

        /**
         * 来判断显示的是否是同一页，这里我们将两个参数相比较返回即可
         *
         * @param arg0
         * @param arg1
         * @return
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * PagerAdapter只缓存四页Tab图，如果滑动的Fragment超出了缓存的范围，就会调用这个方法，将其销毁
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(fragments.get(position).getView());
        }

        /**
         * 当要页面可以进行缓存的时候，会调用这个方法进行显示Tab的初始化，我们将要显示的Fragment加入到ViewGroup中，然后作为返回值返回即可
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = fragments.get(position);
            if (!fragment.isAdded()) { // 如果fragment还没有added
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(fragment, fragment.getClass().getSimpleName());
                ft.commit();
                /**
                 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
                 * 会在进程的主线程中,用异步的方式来执行。
                 * 如果想要立即执行这个等待中的操作,就要调用这个方法(只能在主线程中调用)。
                 * 要注意的是,所有的回调和相关的行为都会在这个调用中被执行完成,因此要仔细确认这个方法的调用位置。
                 */
                fragmentManager.executePendingTransactions();
            }

            if (fragment.getView().getParent() == null) {
                container.addView(fragment.getView()); // 为viewpager增加布局
            }
            return fragment.getView();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        toolbarTitle.setText(getResources().getText(R.string.tab1_main));

        fragments.add(new Tab1Fragment());
        fragments.add(new FriendListFragment());
        fragments.add(new VillageListFragment());
        fragments.add(new SettingFragment());

        fragmentManager = this.getSupportFragmentManager();

        viewPager.setSlipping(true);//设置ViewPager是否可以滑动
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setAdapter(new MyPagerAdapter());
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    /* @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

       if (id == R.id.action_follow) {
           Intent intent = new Intent(this, FollowVillageActivity.class);
           startActivityForResult(intent,0);
           return true;
       }
       return super.onOptionsItemSelected(item);
   }*/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (idToolbar) {
            case 2:
                menu.findItem(R.id.action_search).setVisible(true);
                menu.findItem(R.id.action_follow).setVisible(false);
                break;
            case 3:
                menu.findItem(R.id.action_follow).setVisible(true);
                menu.findItem(R.id.action_search).setVisible(false);
                break;
            default:
                menu.findItem(R.id.action_follow).setVisible(false);
                menu.findItem(R.id.action_search).setVisible(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            super.onBackPressed();
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isExit = false;
        }
    };
}
