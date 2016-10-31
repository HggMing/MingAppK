package com.study.mingappk.tab2.friendlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.bilibili.magicasakura.widgets.TintToolbar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BaseActivity;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.UserInfoByPhone;
import com.study.mingappk.tab2.frienddetail.FriendDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FriendListActivity extends BaseActivity {

    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.text_search)
    TintTextView textSearch;
    @Bind(R.id.search_page)
    LinearLayout searchPage;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    TintToolbar toolbar;

    private String searchText;
    private String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbarTitle.setText(R.string.title_activity_friend_list);

        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_toolbar_back);
        }
        auth = Hawk.get(APP.USER_AUTH);

        //设置搜索好友
        configSearch();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
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
//                viewPager.setVisibility(View.GONE);
                searchPage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
//                Toast.makeText(MainActivity.this, "搜索关闭", Toast.LENGTH_SHORT).show();
//                viewPager.setVisibility(View.VISIBLE);
                searchPage.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 查询用户（输入手机号），以便添加为好友
     *
     * @param searchText
     */
    private void searchFriend(String searchText) {
//        Toast.makeText(FriendListActivity.this, "搜索："+searchText, Toast.LENGTH_SHORT).show();
        MyServiceClient.getService()
                .get_UserInfoByPhone(auth, searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfoByPhone>() {
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
                            Intent intent = new Intent(FriendListActivity.this, FriendDetailActivity.class);
                            intent.putExtra(FriendDetailActivity.FRIEND_UID, uid);
                            startActivity(intent);
                            searchView.closeSearch();
                        } else {//没有查找到
                            Toast.makeText(FriendListActivity.this, userInfoByPhone.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    //点击搜索好友
    @OnClick(R.id.click_search)
    public void onClick() {
        if (!StringTools.isEmpty(searchText)) {
            searchFriend(searchText);
            //关闭输入法
            BaseTools.closeInputMethod(this);
        }
    }
}
