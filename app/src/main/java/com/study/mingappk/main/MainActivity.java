package com.study.mingappk.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.tab1.Tab1Fragment;
import com.study.mingappk.tab2.Tab2Fragment;
import com.study.mingappk.tab3.Tab3Fragment;
import com.study.mingappk.tab4.Tab4Fragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public MainViewPager viewPager;
    private ImageView mTab1, mTab2, mTab3, mTab4;
    private TextView tTab1, tTab2, tTab3, tTab4;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentManager fragmentManager;
    private int currIndex = 0;// 当前页卡编号

    /**
     * 点击监听
     */
    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);//选中index页
        }
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab1_btn1));
                    tTab1.setTextColor(getResources().getColor(R.color.tab_bnt1));   //选中时的字体颜色

                    if (currIndex == 1) {
                        mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab2_btn0));
                        tTab2.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    } else if (currIndex == 2) {
                        mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab3_btn0));
                        tTab3.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    } else if (currIndex == 3) {
                        mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab4_btn0));
                        tTab4.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    }
                    break;
                case 1:
                    mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab2_btn1));
                    tTab2.setTextColor(getResources().getColor(R.color.tab_bnt1));

                    if (currIndex == 0) {
                        mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab1_btn0));
                        tTab1.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    } else if (currIndex == 2) {
                        mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab3_btn0));
                        tTab3.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    } else if (currIndex == 3) {
                        mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab4_btn0));
                        tTab4.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    }
                    break;
                case 2:
                    mTab3.setImageDrawable(getResources().getDrawable(
                            R.drawable.tab3_btn1));
                    tTab3.setTextColor(getResources().getColor(R.color.tab_bnt1));
                    if (currIndex == 0) {
                        mTab1.setImageDrawable(getResources().getDrawable(
                                R.drawable.tab1_btn0));
                        tTab1.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    } else if (currIndex == 1) {
                        mTab2.setImageDrawable(getResources().getDrawable(
                                R.drawable.tab2_btn0));
                        tTab2.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    } else if (currIndex == 3) {
                        mTab4.setImageDrawable(getResources().getDrawable(
                                R.drawable.tab4_btn0));
                        tTab4.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    }
                    break;
                case 3:
                    mTab4.setImageDrawable(getResources().getDrawable(
                            R.drawable.tab4_btn1));
                    tTab4.setTextColor(getResources().getColor(R.color.tab_bnt1));
                    if (currIndex == 0) {
                        mTab1.setImageDrawable(getResources().getDrawable(
                                R.drawable.tab1_btn0));
                        tTab1.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    } else if (currIndex == 1) {
                        mTab2.setImageDrawable(getResources().getDrawable(
                                R.drawable.tab2_btn0));
                        tTab2.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    } else if (currIndex == 2) {
                        mTab3.setImageDrawable(getResources().getDrawable(
                                R.drawable.tab3_btn0));
                        tTab3.setTextColor(getResources().getColor(R.color.tab_bnt0));
                    }
                    break;
            }
            currIndex = arg0;
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
         * 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
         * @return
         */
        @Override
        public int getCount() {
            return fragments.size();
        }

        /**
         * 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
         * @param arg0
         * @param arg1
         * @return
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(fragments.get(position).getView());
        }

        /**
         * 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
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

        initViews();//初始化控件

        fragments.add(new Tab1Fragment());
        fragments.add(new Tab2Fragment());
        fragments.add(new Tab3Fragment());
        fragments.add(new Tab4Fragment());

        fragmentManager = this.getSupportFragmentManager();

        viewPager = (MainViewPager) findViewById(R.id.viewPager_main);
        viewPager.setSlipping(true);//设置ViewPager是否可以滑动
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.removeOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setAdapter(new MyPagerAdapter());


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    /**
     * 初始化控件
     */
    public void initViews() {
        final RelativeLayout tab1Layout = (RelativeLayout) findViewById(R.id.tab1Layout);
        mTab1 = (ImageView) findViewById(R.id.img_tab1_main);
        mTab2 = (ImageView) findViewById(R.id.img_tab2_main);
        mTab3 = (ImageView) findViewById(R.id.img_tab3_main);
        mTab4 = (ImageView) findViewById(R.id.img_tab4_main);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));
        mTab4.setOnClickListener(new MyOnClickListener(3));

        tTab1 = (TextView) findViewById(R.id.text_tab1_main);   //获取textView控件
        tTab2 = (TextView) findViewById(R.id.text_tab2_main);
        tTab3 = (TextView) findViewById(R.id.text_tab3_main);
        tTab4 = (TextView) findViewById(R.id.text_tab4_main);
        tTab1.setOnClickListener(new MyOnClickListener(0));
        tTab2.setOnClickListener(new MyOnClickListener(1));
        tTab3.setOnClickListener(new MyOnClickListener(2));
        tTab4.setOnClickListener(new MyOnClickListener(3));

    }
}
