package com.study.mingappk.tab2.friendlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jude.utils.JUtils;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.common.utils.pinyin.CharacterParser;
import com.study.mingappk.common.utils.pinyin.PinyinComparator;
import com.study.mingappk.common.views.SideBar;
import com.study.mingappk.common.views.stickyrecyclerheaders.StickyRecyclerHeadersDecoration;
import com.study.mingappk.model.bean.FriendList;
import com.study.mingappk.model.service.MyServiceClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class FriendListFragment extends Fragment implements FriendListAdapter.OnItemClickListener {
    AppCompatActivity mActivity;
    @Bind(R.id.contact_member)
    RecyclerView mXRecyclerView;
    @Bind(R.id.contact_dialog)
    TextView mUserDialog;
    @Bind(R.id.contact_sidebar)
    SideBar mSideBar;

    private XRecyclerView.LayoutManager mLayoutManager;
    private FriendListAdapter mAdapter;
    List<FriendList.DataBean.ListBean> mList = new ArrayList<>();
    private int cnt;//列表数据总条数
    final private static int PAGE_SIZE = 50;//
    private int page = 1;

    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
//        mActivity.setSupportActionBar(toolbar2);

        initView();
        getDataList();//获取friendList数据和cnt值
    }
    private void initView() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        mSideBar.setTextView(mUserDialog);
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mXRecyclerView.scrollToPosition(position);//?
                }
            }
        });

    }

    //配置RecyclerView
    private void configXRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器

        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
//以下为XRecyclerView功能
/*        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);//自定义下拉刷新箭头图标
        mXRecyclerView.setPullRefreshEnabled(false);//关闭刷新功能
//        View header =   LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
//        mRecyclerView.addHeaderView(header);

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }


            @Override
            public void onLoadMore() {
                int pages = (int) (cnt / PAGE_SIZE + 1);
                if (page <= pages) {
                    String auth = APP.getInstance().getAuth();
                    MyServiceClient.getService().getCall_FriendList(auth, page, PAGE_SIZE).enqueue(new Callback<FriendList>() {
                        @Override
                        public void onResponse(Call<FriendList> call, Response<FriendList> response) {
                            if (response.isSuccessful()) {
                                FriendList friendList = response.body();
                                if (friendList != null && friendList.getErr() == 0) {
                                    mList.addAll(friendList.getData().getList());
                                    mAdapter.notifyDataSetChanged();
                                    mXRecyclerView.loadMoreComplete();
                                    page++;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<FriendList> call, Throwable t) {
                            JUtils.Log("加载更多好友出错：" + t.getMessage());

                        }
                    });
                } else {
                    mXRecyclerView.loadMoreComplete();
                }
            }
        });*/
    }

    private void refreshList() {
        page = 1;
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getCall_FriendList(auth, page, PAGE_SIZE)
                .enqueue(new Callback<FriendList>() {
                    @Override
                    public void onResponse(Call<FriendList> call, Response<FriendList> response) {
                        if (response.isSuccessful()) {
                            FriendList friendList = response.body();
                            if (friendList != null && friendList.getErr() == 0) {
                                mList.clear();
                                mList.addAll(friendList.getData().getList());
                                mAdapter.notifyDataSetChanged();
//                                mXRecyclerView.refreshComplete();
                                page = 2;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FriendList> call, Throwable t) {
                        JUtils.Log("刷新好友列表出错：" + t.getMessage());
                    }
                });

    }

    private void getDataList() {
        page = 1;
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getCall_FriendList(auth, page, PAGE_SIZE).enqueue(new Callback<FriendList>() {
            @Override
            public void onResponse(Call<FriendList> call, Response<FriendList> response) {
                if (response.isSuccessful()) {
                    FriendList friendList = response.body();
                    if (friendList != null && friendList.getErr() == 0) {
                        cnt = friendList.getData().getCnt();

                        combinationLists(friendList);

                        if (mAdapter == null) {
                            mAdapter = new FriendListAdapter(mActivity, mList);
                            configXRecyclerView();//XRecyclerView配置
                            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
                            mXRecyclerView.addItemDecoration(headersDecor);
                            mXRecyclerView.addItemDecoration(new MyItemDecoration2(mActivity));//添加分割线
                            mAdapter.setOnItemClickListener(FriendListFragment.this);
                            mXRecyclerView.setAdapter(mAdapter);//设置adapter
                            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                                @Override
                                public void onChanged() {
                                    headersDecor.invalidateHeaders();
                                }
                            });
                            page = 2;
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendList> call, Throwable t) {
                JUtils.Log("初始化村圈数据出错：" + t.getMessage());

            }
        });
    }

    /**
     * 排好序，重新组合需要的数据
     *
     * @param friendList 好友列表
     */
    private void combinationLists(FriendList friendList) {
        //系统：新的朋友，小苞谷，客服
        FriendList.DataBean.ListBean tempMember = new FriendList.DataBean.ListBean();
        tempMember.setName("新的朋友");
        tempMember.getHead();
        tempMember.setSortLetters("$");
        mList.add(tempMember);
        for (int i = 0; i < 2; i++) {
            tempMember = friendList.getData().getList().get(i);
            tempMember.setSortLetters("$");
            mList.add(tempMember);
        }

        //我
        tempMember = friendList.getData().getList().get(2);
        tempMember.setSortLetters("%");
        mList.add(tempMember);

        //其他好友
        List<FriendList.DataBean.ListBean> tempList = new ArrayList<>();
        for (int i = 3; i < friendList.getData().getList().size(); i++) {
            tempMember = friendList.getData().getList().get(i);
            if(tempMember.getName().equals("")){
                //若用户名为空，显示手机号，中间四位为*
                String iphone = friendList.getData().getList().get(i).getPhone();
                String showName = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
                tempMember.setName(showName);
            }
            String pinyin = characterParser.getSelling(friendList.getData().getList().get(i).getName());//用户名转拼音
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                tempMember.setSortLetters(sortString.toUpperCase());
            } else {
                tempMember.setSortLetters("#");
            }
            tempList.add(tempMember);
        }
        Collections.sort(tempList, pinyinComparator);
        mList.addAll(tempList);
    }

    @Override
    public void onItemClick(View view, int position) {
        //点击选项操作
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        //长按选项操作
    }


}


