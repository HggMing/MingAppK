package com.study.mingappk.tab4.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintTextView;
import com.bilibili.magicasakura.widgets.TintView;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.event.ChangeThemeColorEvent;
import com.study.mingappk.model.event.UpdataShopOwnerHeadEvent;
import com.study.mingappk.tab4.shop.shoptab1.ShopTab1Fragment;
import com.study.mingappk.tab4.shop.shoptab2.ShopTab2Fragment;
import com.study.mingappk.tab4.shop.shoptab3.ShopTab3Fragment;
import com.study.mingappk.tab4.shop.shoptab4.ShopTab4Fragment;
import com.study.mingappk.tmain.MainViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyShopFragment extends Fragment {
    @Bind(R.id.icon_head)
    ImageView iconHead;
    @Bind(R.id.shop_address)
    TextView shopAddress;
    @Bind(R.id.line_12)
    View line12;
    @Bind(R.id.text_tab1)
    TintTextView tTab1;
    @Bind(R.id.text_tab2)
    TintTextView tTab2;
    @Bind(R.id.text_tab3)
    TintTextView tTab3;
    @Bind(R.id.text_tab4)
    TintTextView tTab4;
    @Bind(R.id.viewPager)
    MainViewPager viewPager;
    @Bind(R.id.view_tab1)
    TintView vTab1;
    @Bind(R.id.tab1Layout)
    RelativeLayout tab1Layout;
    @Bind(R.id.view_tab2)
    TintView vTab2;
    @Bind(R.id.tab2Layout)
    RelativeLayout tab2Layout;
    @Bind(R.id.view_tab3)
    TintView vTab3;
    @Bind(R.id.tab3Layout)
    RelativeLayout tab3Layout;
    @Bind(R.id.view_tab4)
    TintView vTab4;
    @Bind(R.id.tab4Layout)
    RelativeLayout tab4Layout;

    private AppCompatActivity mActivity;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_shop, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        //头像
        String headUrl = Hawk.get(APP.ME_HEAD);
        Glide.with(this)
                .load(headUrl)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .error(R.mipmap.defalt_user_circle)
                .into(iconHead);
        //地址
        String address = Hawk.get(APP.MANAGER_ADDRESS, "无管理村，请联系维护人员处理！");
        shopAddress.setText(address);

        //fragment设置
        fragments.add(new ShopTab1Fragment());
        fragments.add(new ShopTab2Fragment());
        fragments.add(new ShopTab3Fragment());
        fragments.add(new ShopTab4Fragment());

        fragmentManager = mActivity.getSupportFragmentManager();

        viewPager.setSlipping(true);//设置ViewPager是否可以滑动
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setAdapter(new MyPagerAdapter());
    }

    //主页为singletop模式，更换主题后手动刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeThemeColor(ChangeThemeColorEvent event) {
        //更改主题后，我的店上方Tab页，文字颜色改变
        int themeColor = ThemeUtils.getColorById(mActivity, R.color.theme_color_primary);
        int arg0 = viewPager.getCurrentItem();
        switch (arg0){
            case 0:
                tTab1.setTextColor(themeColor);   //选中时的字体颜色
                vTab1.setBackgroundColor(themeColor);
                break;
            case 1:
                tTab2.setTextColor(themeColor);
                vTab2.setBackgroundColor(themeColor);
                break;
            case 2:
                tTab3.setTextColor(themeColor);
                vTab3.setBackgroundColor(themeColor);
                break;
            case 3:
                tTab4.setTextColor(themeColor);
                vTab4.setBackgroundColor(themeColor);
                break;
        }
    }

    //更新店长头像
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updataHead(UpdataShopOwnerHeadEvent event) {
        //如果是店长，更新首页，店长头像。解决无APP.ME_HEAD时，头像显示问题
        String headUrl =event.getHeadUrl();
        Glide.with(this)
                .load(headUrl)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .error(R.mipmap.defalt_user_circle)
                .into(iconHead);
    }

    /**
     * 页卡切换监听,点击改变图标外观
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        int white = ContextCompat.getColor(mActivity, R.color.white);

        @Override
        public void onPageSelected(int arg0) {
            int themeColor = ThemeUtils.getColorById(mActivity, R.color.theme_color_primary);
            switch (arg0) {
                case 0:
                    tTab1.setTextColor(themeColor);   //选中时的字体颜色
                    vTab1.setBackgroundColor(themeColor);
                    tab1Layout.setBackgroundResource(R.mipmap.divide_15_top);
                    setTab2ToB();
                    setTab3ToB();
                    setTab4ToB();
                    break;
                case 1:
                    tTab2.setTextColor(themeColor);
                    vTab2.setBackgroundColor(themeColor);
                    tab2Layout.setBackgroundResource(R.mipmap.divide_15_top);
                    setTab1ToB();
                    setTab3ToB();
                    setTab4ToB();
                    break;
                case 2:
                    tTab3.setTextColor(themeColor);
                    vTab3.setBackgroundColor(themeColor);
                    tab3Layout.setBackgroundResource(R.mipmap.divide_15_top);
                    setTab1ToB();
                    setTab2ToB();
                    setTab4ToB();
                    break;
                case 3:
                    tTab4.setTextColor(themeColor);
                    vTab4.setBackgroundColor(themeColor);
                    tab4Layout.setBackgroundResource(R.mipmap.divide_15_top);
                    setTab1ToB();
                    setTab2ToB();
                    setTab3ToB();
                    break;
            }
        }

        private void setTab1ToB() {
            tTab1.setTextColor(ContextCompat.getColor(mActivity, R.color.tab_bnt0));
            vTab1.setBackgroundColor(white);
            tab1Layout.setBackgroundColor(white);
        }

        private void setTab2ToB() {
            tTab2.setTextColor(ContextCompat.getColor(mActivity, R.color.tab_bnt0));
            vTab2.setBackgroundColor(white);
            tab2Layout.setBackgroundColor(white);
        }

        private void setTab3ToB() {
            tTab3.setTextColor(ContextCompat.getColor(mActivity, R.color.tab_bnt0));
            vTab3.setBackgroundColor(white);
            tab3Layout.setBackgroundColor(white);
        }

        private void setTab4ToB() {
            tTab4.setTextColor(ContextCompat.getColor(mActivity, R.color.tab_bnt0));
            vTab4.setBackgroundColor(white);
            tab4Layout.setBackgroundColor(white);
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
        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(fragments.get(position).getView());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = fragments.get(position);
            if (!fragment.isAdded()) { // 如果fragment还没有added
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(fragment, fragment.getClass().getSimpleName());
                ft.commit();
                fragmentManager.executePendingTransactions();
            }

            if (fragment.getView().getParent() == null) {
                container.addView(fragment.getView()); // 为viewpager增加布局
            }
            return fragment.getView();
        }

    }

    @OnClick({R.id.tab1Layout, R.id.tab2Layout, R.id.tab3Layout, R.id.tab4Layout})
    public void onClick(View view) {
        switch (view.getId()) {
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
}
