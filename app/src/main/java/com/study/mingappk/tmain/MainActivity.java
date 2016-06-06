package com.study.mingappk.tmain;

import android.Manifest;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.receiver.MyMsgBroadcastReceiver;
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

    @Bind(R.id.toolbar_title_main)
    TextView toolbarTitle;
    private FragmentManager fragmentManager;
    private boolean isExit;//是否退出
    private int idToolbar = 1;//toolbar 功能按钮页
    private boolean isFirstRun;//是否初次运行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);

        initView();
    }

    private void initView() {
        //个推,初始化SDK
//        PushManager.getInstance().initialize(this.getApplicationContext());
        //接收消息BroadcastReciver
        /*MyMsgBroadcastReceiver msReciver = new MyMsgBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyMsgBroadcastReceiver.MYMSG_ACTION);
        this.registerReceiver(msReciver, intentFilter);*/

        fragments.add(new Tab1Fragment());
        fragments.add(new FriendListFragment());
        fragments.add(new VillageListFragment());
        fragments.add(new SettingFragment());

        fragmentManager = this.getSupportFragmentManager();

        viewPager.setSlipping(true);//设置ViewPager是否可以滑动
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setAdapter(new MyPagerAdapter());

        isFirstRun = Hawk.get(APP.IS_FIRST_RUN, true);
        if (isFirstRun) {
            viewPager.setCurrentItem(2, true);
            toolbarTitle.setText(getResources().getText(R.string.tab3_main));
            tab3Guide.setVisibility(View.VISIBLE);
            Hawk.put(APP.IS_FIRST_RUN, false);
        } else {
            toolbarTitle.setText(getResources().getText(R.string.tab1_main));
        }
    }

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
                    toolbarTitle.setText(R.string.tab1_main);
                    idToolbar = 1;
                    mTab1.setImageDrawable(getResources().getDrawable(R.mipmap.tab1_btn1));
                    tTab1.setTextColor(getResources().getColor(R.color.tab_bnt1));   //选中时的字体颜色
                    setTab2ToB();
                    setTab3ToB();
                    setTab4ToB();
                    break;
                case 1:
                    toolbarTitle.setText(R.string.tab2_main);
                    idToolbar = 2;
                    mTab2.setImageDrawable(getResources().getDrawable(R.mipmap.tab2_btn1));
                    tTab2.setTextColor(getResources().getColor(R.color.tab_bnt1));
                    setTab1ToB();
                    setTab3ToB();
                    setTab4ToB();
                    break;
                case 2:
                    toolbarTitle.setText(R.string.tab3_main);
                    idToolbar = 3;
                    mTab3.setImageDrawable(getResources().getDrawable(R.mipmap.tab3_btn1));
                    tTab3.setTextColor(getResources().getColor(R.color.tab_bnt1));
                    setTab1ToB();
                    setTab2ToB();
                    setTab4ToB();
                    break;
                case 3:
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

    //**************** Android M Permission (Android 6.0权限控制代码封装)*****************************************************
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable;

    public interface PermissionCallback {
        void hasPermission();

        void noPermission();
    }

    /**
     * Android M运行时权限请求封装
     *
     * @param permissionDes 权限描述
     * @param runnable      请求权限回调
     * @param permissions   请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions) {
        if (permissions.length == 0) return;
//        this.permissionrequestCode = requestCode;
        this.permissionRunnable = runnable;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)) {
            if (permissionRunnable != null) {
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        } else {
            //permission has not been granted.
            requestPermission(permissionDes, permissionRequestCode, permissions);
        }

    }

    private boolean checkPermissionGranted(String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(String permissionDes, final int requestCode, final String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.

//            Snackbar.make(getWindow().getDecorView(), requestName,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.common_ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ActivityCompat.requestPermissions(BaseAppCompatActivity.this,
//                                    permissions,
//                                    requestCode);
//                        }
//                    })
//                    .show();
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, permissions, requestCode);
                        }
                    }).show();

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(MainActivity.this, permissions, requestCode);
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (permissionRunnable != null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            } else {
                Toast.makeText(MainActivity.this, "正常体验软件，请在系统设置中，为本APP授权：存储空间！", Toast.LENGTH_SHORT).show();
                if (permissionRunnable != null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    //********************** END Android M Permission ****************************************

}
