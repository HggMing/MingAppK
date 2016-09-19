package com.study.mingappk.tmain;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.TintTextView;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.jude.utils.JUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.AddFriendRequest;
import com.study.mingappk.model.bean.EbankWifiConnect;
import com.study.mingappk.model.bean.IpPort;
import com.study.mingappk.model.bean.MessageList;
import com.study.mingappk.model.bean.UserInfoByPhone;
import com.study.mingappk.model.database.ChatMsgModel;
import com.study.mingappk.model.database.FriendsModel;
import com.study.mingappk.model.database.InstantMsgModel;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.database.NewFriendModel;
import com.study.mingappk.model.event.ChangeThemeColorEvent;
import com.study.mingappk.model.event.InstantMsgEvent;
import com.study.mingappk.model.event.NewFriendEvent;
import com.study.mingappk.model.event.RefreshTab1Event;
import com.study.mingappk.model.event.ShopApplyPassEvent;
import com.study.mingappk.model.event.ShowSideBarEvent;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab1.Tab1Fragment;
import com.study.mingappk.tab2.frienddetail.FriendDetailActivity;
import com.study.mingappk.tab2.friendlist.FriendListFragment;
import com.study.mingappk.tab3.villagelist.VillageListFragment;
import com.study.mingappk.tab4.SettingFragment;
import com.study.mingappk.tab4.shop.MyShopFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.viewPager_main)
    MainViewPager viewPager;
    @Bind(R.id.img_tab1_main_1)
    ImageView mTab11;
    @Bind(R.id.img_tab1_main_0)
    ImageView mTab10;
    @Bind(R.id.text_tab1_main)
    TextView tTab1;
    @Bind(R.id.img_tab2_main_1)
    ImageView mTab21;
    @Bind(R.id.img_tab2_main_0)
    ImageView mTab20;
    @Bind(R.id.text_tab2_main)
    TextView tTab2;
    @Bind(R.id.img_tab3_main_1)
    ImageView mTab31;
    @Bind(R.id.img_tab3_main_0)
    ImageView mTab30;
    @Bind(R.id.text_tab3_main)
    TextView tTab3;
    @Bind(R.id.img_tab4_main_1)
    ImageView mTab41;
    @Bind(R.id.img_tab4_main_0)
    ImageView mTab40;
    @Bind(R.id.text_tab4_main)
    TextView tTab4;
    @Bind(R.id.toolbar_main)
    Toolbar mToolBar;
    @Bind(R.id.tab3_guide)
    ImageView tab3Guide;
    @Bind(R.id.toolbar_title_main)
    TextView toolbarTitle;
    @Bind(R.id.badge)
    TextView badge;
    @Bind(R.id.badge2)
    TextView badge2;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.text_search)
    TintTextView textSearch;
    @Bind(R.id.click_search)
    LinearLayout clickSearch;
    @Bind(R.id.search_page)
    LinearLayout searchPage;
    @Bind(R.id.img_tab5_main_1)
    TintImageView mTab51;
    @Bind(R.id.img_tab5_main_0)
    ImageView mTab50;
    @Bind(R.id.text_tab5_main)
    TintTextView tTab5;
    @Bind(R.id.tab5Layout)
    RelativeLayout tab5Layout;

    public List<Fragment> fragments = new ArrayList<>();
    private MyOnPageChangeListener myOnPageChangeListener;

    private FragmentManager fragmentManager;
    private boolean isExit;//是否退出
    private int idToolbar = 1;//toolbar 功能按钮页
    private boolean isFirstRun;//是否初次运行

    private String searchText;
    private String auth;
    private int isShopOwner;//是否是店长,1是0不是

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        EventBus.getDefault().register(this);

        initView();
        //设置搜索好友
        configSearch();
        //登录后，向后台获取消息
        getMessageList(this);
        //WiFi连接到ebank网络的认证
        autoConnect();
    }

    private void autoConnect() {
        MyServiceClient.getService()
                .get_IpPort()
                .flatMap(new Func1<IpPort, Observable<EbankWifiConnect>>() {
                    @Override
                    public Observable<EbankWifiConnect> call(IpPort ipPort) {
                        return MyServiceClient.getService()
                                .get_EbankWifiConnect(ipPort.getIp(), ipPort.getPort(), ipPort.getMac(), auth);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EbankWifiConnect>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, "没有连接ebank", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(EbankWifiConnect ebankWifiConnect) {
                        if ("1".equals(ebankWifiConnect.getStatus()))
                            Toast.makeText(MainActivity.this, "恭喜你上网认证通过,获得两小时上网时间!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void configSearch() {
        //是否使用语音搜索
        searchView.setVoiceSearch(false);
        //自定义光标
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(FollowVillageActivity.this, "搜索提交", Toast.LENGTH_SHORT).show();
                searchFriend(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(MainActivity.this, "搜索文字改变", Toast.LENGTH_SHORT).show();
                textSearch.setText(newText);
                searchText = newText;
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
//                Toast.makeText(MainActivity.this, "搜索打开", Toast.LENGTH_SHORT).show();
                viewPager.setVisibility(View.GONE);
                searchPage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
//                Toast.makeText(MainActivity.this, "搜索关闭", Toast.LENGTH_SHORT).show();
                viewPager.setVisibility(View.VISIBLE);
                searchPage.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 获取消息，并本地保存，发出通知
     *
     * @param context
     */
    private void getMessageList(final Context context) {
        //请求消息
        final String me_uid = Hawk.get(APP.ME_UID, "");
        MyServiceClient.getService()
                .get_MessageList(me_uid, "yxj", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageList messageList) {
                        //接收到新消息！！
                        List<MessageList.LBean> lBeanList = messageList.getL();
                        //列表反向
                        Collections.reverse(lBeanList);
                        //消息通知
                        for (MessageList.LBean lBean : lBeanList) {
                            if ("21".equals(lBean.getCt())) {//系统消息
                                if ("1".equals(lBean.getFrom())) {//添加好友请求消息
                                    String jsonString = lBean.getTxt();
                                    Gson gson = new Gson();
                                    AddFriendRequest addFriendRequest = gson.fromJson(jsonString, AddFriendRequest.class);

                                    //好友请求保存到数据库
                                    AddFriendRequest.UinfoBean uinfo = addFriendRequest.getUinfo();
                                    String uicon = MyServiceClient.getBaseUrl() + uinfo.getHead();
                                    NewFriendModel newFriend = new NewFriendModel(uinfo.getUid(), uicon, uinfo.getUname(), uinfo.getSex(), uinfo.getPhone(), 1);
                                    MyDB.insert(newFriend);
                                    EventBus.getDefault().post(new NewFriendEvent());
                                }
                            } else {

                                ChatMsgModel chatMsg = new ChatMsgModel();
                                chatMsg.setType(ChatMsgModel.ITEM_TYPE_LEFT);//接收消息
                                if ("1".equals(lBean.getFrom())) {
                                    chatMsg.setFrom("10001");//系统消息由"我们村客服"发来
                                } else {
                                    chatMsg.setFrom(lBean.getFrom());//消息来源用户id
                                }
                                chatMsg.setTo(me_uid);
                                chatMsg.setSt(lBean.getSt());//消息时间
                                chatMsg.setCt(lBean.getCt());//消息类型
                                switch (lBean.getCt()) {
                                    case "1":
                                        chatMsg.setTxt("[图片]");
                                        break;
                                    case "2":
                                        chatMsg.setTxt("[语音]");
                                        break;
                                    default:
                                        chatMsg.setTxt(lBean.getTxt());//类型：文字
                                        break;
                                }
                                chatMsg.setLink(lBean.getLink());//类型：图片or语音
                                MyDB.insert(chatMsg);//保存到数据库

                                String uid = chatMsg.getFrom();
                                FriendsModel friend = MyDB.createDb(context).queryById(uid, FriendsModel.class);

                                //新消息条数，读取及更新
                                int count = friend.getCount() + 1;
                                friend.setCount(count);
                                MyDB.update(friend);
                                // 登录后发送通知
                         /*   int requestCode = Integer.parseInt(uid);//唯一标识通知
                            //点击通知后操作
                            Intent intent2 = new Intent(context, ChatActivity.class);
                            intent2.putExtra(ChatActivity.UID, uid);
                            intent2.putExtra(ChatActivity.USER_NAME, friend.getUname());
                            PendingIntent pIntent = PendingIntent.getActivity(context, requestCode, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

                            //构建通知
                            int largeIcon = R.mipmap.ic_launcher;
                            int smallIcon = R.mipmap.tab1_btn1;
                            String title = friend.getUname();
                            String ticker = title + ": " + chatMsg.getTxt();
                            String content = "[" + count + "条]" + ": " + chatMsg.getTxt();
                            //实例化工具类，并且调用接口
                            NotifyUtil notify3 = new NotifyUtil(context, requestCode);
                            notify3.notify_msg(pIntent, smallIcon, largeIcon, ticker, title, content, true, true, false);*/

                                //保存动态并刷新
                                InstantMsgModel msgModel = new InstantMsgModel(uid, friend.getUicon(), friend.getUname(), chatMsg.getSt(), chatMsg.getTxt(), count);
                                MyDB.insert(msgModel);
                                EventBus.getDefault().post(new InstantMsgEvent());
                            }
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //状态栏主题变色
        BaseTools.colorStatusBar(this);
    }

    //主页为singletop模式，更换主题后手动刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeThemeColor(ChangeThemeColorEvent event) {
        //更改主题后，改变tab4文字颜色
        int themeColor = ThemeUtils.getColorById(this, R.color.theme_color_primary);
        tTab4.setTextColor(themeColor);
    }

    //申请店长通过后，界面调整
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ApplyPassed(ShopApplyPassEvent event) {
        isShopOwner = 1;
        tab5Layout.setVisibility(View.VISIBLE);

        fragments.add(2, new MyShopFragment());
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(4, true);

        viewPager.removeOnPageChangeListener(myOnPageChangeListener);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initView() {
        //数据库初始化
        MyDB.createDb(this);
        //个推,初始化SDK
        PushManager.getInstance().initialize(this.getApplicationContext());

        isShopOwner = Hawk.get(APP.IS_SHOP_OWNER);


        fragments.add(new Tab1Fragment());
        fragments.add(new FriendListFragment());
        if (isShopOwner == 1) {
            fragments.add(new MyShopFragment());
            tab5Layout.setVisibility(View.VISIBLE);
        } else {
            tab5Layout.setVisibility(View.GONE);
        }
        fragments.add(new VillageListFragment());
        fragments.add(new SettingFragment());

        fragmentManager = this.getSupportFragmentManager();

        viewPager.setSlipping(true);//设置ViewPager是否可以滑动
        viewPager.setOffscreenPageLimit(fragments.size());

        myOnPageChangeListener = new MyOnPageChangeListener();
        viewPager.addOnPageChangeListener(myOnPageChangeListener);
        viewPager.setAdapter(new MyPagerAdapter());

        //初次运行软件，指导添加村
        isFirstRun = Hawk.get(APP.IS_FIRST_RUN, true);
        if (isShopOwner == 0) {
            if (isFirstRun) {
                viewPager.setCurrentItem(2, true);
                toolbarTitle.setText(getResources().getText(R.string.tab3_main));
                tab3Guide.setVisibility(View.VISIBLE);
                Hawk.put(APP.IS_FIRST_RUN, false);
            } else {
                toolbarTitle.setText(getResources().getText(R.string.tab1_main));
            }
        } else {
            toolbarTitle.setText(getResources().getText(R.string.tab1_main));
        }

        auth = Hawk.get(APP.USER_AUTH);
    }

    /**
     * 接收到消息，更新tab1处消息徽章计数
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showCount(RefreshTab1Event event) {
        int count = event.getCount();
        if (count > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(count));
        } else {
            badge.setVisibility(View.GONE);
        }
    }

    /**
     * 接收到新的朋友请求消息，更新tab2处消息徽章计数
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showCount2(NewFriendEvent event) {
        List<NewFriendModel> nFriends = MyDB.getQueryAll(NewFriendModel.class);
        int count = 0;
        for (NewFriendModel nFriend : nFriends) {
            count += nFriend.getCount();
        }
        if (count > 0) {
            badge2.setVisibility(View.VISIBLE);
            badge2.setText(String.valueOf(count));
        } else {
            badge2.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tab3_guide, R.id.tab1Layout, R.id.tab2Layout, R.id.tab3Layout, R.id.tab4Layout, R.id.tab5Layout, R.id.click_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab3_guide://点击新用户引导
                tab3Guide.setVisibility(View.GONE);
                break;
            case R.id.click_search://点击搜索好友
                if (!StringTools.isEmpty(searchText)) {
                    searchFriend(searchText);
                    //关闭输入法
                    JUtils.closeInputMethod(this);
                }
                break;
            case R.id.tab1Layout:
                viewPager.setCurrentItem(0);//选中index页
                break;
            case R.id.tab2Layout:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab3Layout:
                if (isShopOwner == 0) {
                    viewPager.setCurrentItem(2);
                } else {
                    viewPager.setCurrentItem(3);
                }
                break;
            case R.id.tab4Layout:
                if (isShopOwner == 0) {
                    viewPager.setCurrentItem(3);
                } else {
                    viewPager.setCurrentItem(4);
                }
                break;
            case R.id.tab5Layout:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    /**
     * 查询用户（输入手机号），以便添加为好友
     *
     * @param searchText
     */
    private void searchFriend(String searchText) {
//        Toast.makeText(MainActivity.this, "搜索："+searchText, Toast.LENGTH_SHORT).show();
        MyServiceClient.getService()
                .get_UserInfoByPhone(auth, searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoByPhone>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfoByPhone userInfoByPhone) {
                        if (userInfoByPhone.getErr() == 0) {
                            String uid = userInfoByPhone.getData().getUid();
                            Intent intent = new Intent(MainActivity.this, FriendDetailActivity.class);
                            intent.putExtra(FriendDetailActivity.FRIEND_UID, uid);
                            startActivity(intent);
                            searchView.closeSearch();
                        } else {//没有查找到
                            Toast.makeText(MainActivity.this, userInfoByPhone.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 页卡切换监听,点击改变图标外观
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            int themeColor = ThemeUtils.getColorById(MainActivity.this, R.color.theme_color_primary);
            ColorStateList colorStateList = ThemeUtils.getThemeColorStateList(MainActivity.this, R.color.theme_color_primary);
            switch (arg0) {
                case 0://动态
                    toolbarTitle.setText(R.string.tab1_main);
                    idToolbar = 1;

                    tTab1.setTextColor(themeColor);   //选中时的字体颜色
                    mTab11.setVisibility(View.VISIBLE);
                    mTab10.setVisibility(View.GONE);
                    setTab2ToB();
                    setTab3ToB();
                    setTab4ToB();
                    setTab5ToB();
                    break;
                case 1://老乡
                    toolbarTitle.setText(R.string.tab2_main);
                    idToolbar = 2;

                    tTab2.setTextColor(themeColor);
                    mTab21.setVisibility(View.VISIBLE);
                    mTab20.setVisibility(View.GONE);
                    setTab1ToB();
                    setTab3ToB();
                    setTab4ToB();
                    setTab5ToB();
                    break;
                case 2://店长：我的店；普通：我的村
                    if (isShopOwner == 0) {//普通：我的村
                        toolbarTitle.setText(R.string.tab3_main);
                        idToolbar = 3;

                        tTab3.setTextColor(themeColor);
                        mTab31.setVisibility(View.VISIBLE);
                        mTab30.setVisibility(View.GONE);
                        setTab1ToB();
                        setTab2ToB();
                        setTab4ToB();
                        setTab5ToB();
                    } else {//店长：我的店
                        toolbarTitle.setText(R.string.tab5_main);
                        idToolbar = 5;

                        tTab5.setTextColor(themeColor);
                        mTab51.setVisibility(View.VISIBLE);
                        mTab50.setVisibility(View.GONE);
                        setTab1ToB();
                        setTab2ToB();
                        setTab3ToB();
                        setTab4ToB();
                    }
                    break;
                case 3:
                    if (isShopOwner == 0) {//普通：设置
                        toolbarTitle.setText(R.string.tab4_main);
                        idToolbar = 4;

                        tTab4.setTextColor(themeColor);
                        mTab41.setVisibility(View.VISIBLE);
                        mTab40.setVisibility(View.GONE);
                        setTab1ToB();
                        setTab2ToB();
                        setTab3ToB();
                        setTab5ToB();
                    } else {//店长：我的村
                        toolbarTitle.setText(R.string.tab3_main);
                        idToolbar = 3;

                        tTab3.setTextColor(themeColor);
                        mTab31.setVisibility(View.VISIBLE);
                        mTab30.setVisibility(View.GONE);
                        setTab1ToB();
                        setTab2ToB();
                        setTab4ToB();
                        setTab5ToB();
                    }
                    break;
                case 4://仅店长：设置
                    toolbarTitle.setText(R.string.tab4_main);
                    idToolbar = 4;

                    tTab4.setTextColor(themeColor);
                    mTab41.setVisibility(View.VISIBLE);
                    mTab40.setVisibility(View.GONE);
                    setTab1ToB();
                    setTab2ToB();
                    setTab3ToB();
                    setTab5ToB();
                    break;
            }
            invalidateOptionsMenu();
        }


        private void setTab1ToB() {
            tTab1.setTextColor(getResources().getColor(R.color.tab_bnt0));
            mTab11.setVisibility(View.GONE);
            mTab10.setVisibility(View.VISIBLE);
        }

        private void setTab2ToB() {
            tTab2.setTextColor(getResources().getColor(R.color.tab_bnt0));
            mTab21.setVisibility(View.GONE);
            mTab20.setVisibility(View.VISIBLE);
        }

        private void setTab3ToB() {
            tTab3.setTextColor(getResources().getColor(R.color.tab_bnt0));
            mTab31.setVisibility(View.GONE);
            mTab30.setVisibility(View.VISIBLE);
        }

        private void setTab4ToB() {
            tTab4.setTextColor(getResources().getColor(R.color.tab_bnt0));
            mTab41.setVisibility(View.GONE);
            mTab40.setVisibility(View.VISIBLE);
        }

        private void setTab5ToB() {
            tTab5.setTextColor(getResources().getColor(R.color.tab_bnt0));
            mTab51.setVisibility(View.GONE);
            mTab50.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 0://什么都没做
                    break;
                case 1://正在滑动
                    EventBus.getDefault().post(new ShowSideBarEvent(false));
                    break;
                case 2://滑动完毕了
                    EventBus.getDefault().post(new ShowSideBarEvent(true));
                    break;
            }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

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
            case 2://添加好友
                menu.findItem(R.id.action_search).setVisible(true);
                menu.findItem(R.id.action_follow).setVisible(false);
                menu.findItem(R.id.action_theme).setVisible(false);
                break;
            case 3://关注村圈
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_follow).setVisible(true);
                menu.findItem(R.id.action_theme).setVisible(false);
                break;
            case 4://主题切换
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_follow).setVisible(false);
                menu.findItem(R.id.action_theme).setVisible(true);
                break;
            default:
                menu.findItem(R.id.action_search).setVisible(false);
                menu.findItem(R.id.action_follow).setVisible(false);
                menu.findItem(R.id.action_theme).setVisible(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else if (!isExit) {
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
