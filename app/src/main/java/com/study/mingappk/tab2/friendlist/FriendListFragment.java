package com.study.mingappk.tab2.friendlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.utils.JUtils;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.views.SideBar;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.common.views.pinyin.CharacterParser;
import com.study.mingappk.common.views.pinyin.PinyinComparator;
import com.study.mingappk.common.views.stickyrecyclerheaders.StickyRecyclerHeadersDecoration;
import com.study.mingappk.model.bean.FriendList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.database.ChatMsgModel;
import com.study.mingappk.model.database.FriendsModel;
import com.study.mingappk.model.database.InstantMsgModel;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.event.ChangeThemeColorEvent;
import com.study.mingappk.model.event.InstantMsgEvent;
import com.study.mingappk.model.event.NewFriendEvent;
import com.study.mingappk.model.event.RefreshFriendList;
import com.study.mingappk.model.event.ShowSideBarEvent;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab2.frienddetail.FriendDetailActivity;
import com.study.mingappk.tab2.message.ChatActivity;
import com.study.mingappk.tab2.newfriend.NewFriendActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 老乡
 */
public class FriendListFragment extends Fragment implements FriendListAdapter.OnItemClickListener {
    AppCompatActivity mActivity;
    @Bind(R.id.contact_member)
    RecyclerView mXRecyclerView;
    @Bind(R.id.contact_dialog)
    TextView mUserDialog;
    @Bind(R.id.contact_sidebar)
    SideBar mSideBar;

    private FriendListAdapter mAdapter;
    List<FriendList.DataBean.ListBean> mList = new ArrayList<>();
    private int cnt;//列表数据总条数
    final private static int PAGE_SIZE = 50;//
    private int page = 1;
    private String auth;
    private List<String> friendUids = new ArrayList<>();

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
        auth = Hawk.get(APP.USER_AUTH);

        initView();
        getDataList();//获取friendList数据和cnt值
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showSideBar(ShowSideBarEvent event) {
        //通用中，更换主题后，刷新字母快速选择背景颜色
        if (event.isShow()) {
            mSideBar.setVisibility(View.VISIBLE);
            mSideBar.setBackgroundDrawable(new ColorDrawable(0x00000000));
        } else {
            mSideBar.setVisibility(View.GONE);
            mUserDialog.setVisibility(View.GONE);
        }
    }

    /**
     * 接到新的朋友消息后，更新好友列表
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showNewFriendCount(NewFriendEvent event) {
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 同意好友申请后，更新好友列表
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshFriendList(RefreshFriendList event) {
        mList.clear();
        friendUids.clear();
        getDataList();
    }

    private void initView() {
        setHasOptionsMenu(true);
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器

        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    private void getDataList() {
        page = 1;
        MyServiceClient.getService().getCall_FriendList(auth, page, PAGE_SIZE).enqueue(new Callback<FriendList>() {
            @Override
            public void onResponse(Call<FriendList> call, Response<FriendList> response) {
                if (response.isSuccessful()) {
                    FriendList friendList = response.body();
                    if (friendList != null && friendList.getErr() == 0) {
                        cnt = friendList.getData().getCnt();

                        combinationLists(friendList);

                        //储存好友信息
                        for (int i = 0; i < mList.size(); i++) {
                            String uid = mList.get(i).getUid();
                            if (uid != null) {
                                friendUids.add(uid);
                            }
                        }
                        Hawk.put(APP.FRIEND_LIST_UID, friendUids);

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
        tempMember.setUid("-9999");
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
        if (StringTools.isEmpty(tempMember.getName())) {
            //若用户名为空，显示手机号，中间四位为*
            String iphone = tempMember.getPhone();
            String showName = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
            tempMember.setName(showName);
        }
        mList.add(tempMember);

        //其他好友
        List<FriendList.DataBean.ListBean> tempList = new ArrayList<>();
        for (int i = 3; i < friendList.getData().getList().size(); i++) {
            tempMember = friendList.getData().getList().get(i);
            if (StringTools.isEmpty(tempMember.getName())) {
                //若用户名为空，显示手机号，中间四位为*
                String iphone = tempMember.getPhone();
                String showName;
                if (iphone != null) {
                    showName = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
                } else {
                    showName = "测试用户";
                }
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

        //存储好友列表到本地数据库
        for (FriendList.DataBean.ListBean user : mList) {
            String save_uid = user.getUid();
            String save_uicon = MyServiceClient.getBaseUrl() + user.getHead();
            String save_uname = user.getName();
            FriendsModel friendsModel = new FriendsModel(save_uid, save_uname, save_uicon);
            MyDB.insert(friendsModel);
        }

        //生成欢迎语动态
        FriendList.DataBean.ListBean user2 = mList.get(2);//我们村客服
        InstantMsgModel user2_msg = MyDB.createDb(mActivity).queryById(user2.getUid(), InstantMsgModel.class);
        if (user2_msg == null) {//动态不存在才添加
            String user2_icon = MyServiceClient.getBaseUrl() + user2.getHead();
            String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);//13位时间戳，截取前10位，主要统一
            String content = "你好！" + mList.get(3).getName() + ",欢迎来到我们村！";
            InstantMsgModel msgModel = new InstantMsgModel(user2.getUid(), user2_icon, user2.getName(), time, content, 1);
            MyDB.insert(msgModel);
            EventBus.getDefault().post(new InstantMsgEvent());

            //欢迎语消息保存到数据库
            ChatMsgModel chatMsg = new ChatMsgModel();
            chatMsg.setType(ChatMsgModel.ITEM_TYPE_LEFT);//接收消息
            chatMsg.setFrom(user2.getUid());//消息来源用户id
            String me_uid = Hawk.get(APP.ME_UID, "");
            chatMsg.setTo(me_uid);
            chatMsg.setSt(time);//消息时间
            chatMsg.setCt("0");//消息类型
            chatMsg.setTxt(content);//类型：文字
            MyDB.insert(chatMsg);//保存到数据库
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //点击选项操作
        switch (position) {
            case 0://新的朋友
                Intent intent0 = new Intent(mActivity, NewFriendActivity.class);
                startActivity(intent0);
                break;
            case 1://小苞谷
                Toast.makeText(mActivity, "功能维护中...", Toast.LENGTH_SHORT).show();
                break;
            case 2://客服
                Intent intent2 = new Intent(mActivity, ChatActivity.class);
                intent2.putExtra(ChatActivity.UID, mList.get(position).getUid());
                intent2.putExtra(ChatActivity.USER_NAME, mList.get(position).getName());
                startActivity(intent2);
                break;
            default://其他
                Intent intent = new Intent(mActivity, FriendDetailActivity.class);
                String uid = mList.get(position).getUid();
                intent.putExtra(FriendDetailActivity.FRIEND_UID, uid);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        //长按删除好友
        if (position > 3) {
            MyDialog.Builder builder = new MyDialog.Builder(mActivity);
            builder.setTitle("提示")
                    .setMessage("确定删除老乡“" + mList.get(position).getName() + "”？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            //数据库中删除与此好友的动态
                            InstantMsgModel iMsg = new InstantMsgModel();
                            iMsg.setUid(mList.get(position).getUid());
                            MyDB.delete(iMsg);
                            //刷新动态
                            EventBus.getDefault().post(new InstantMsgEvent());
                            //好友列表中删除与此好友的信息
                            FriendsModel friend = new FriendsModel();
                            friend.setUid(mList.get(position).getUid());
                            MyDB.delete(friend);
                            //删除好友
                            MyServiceClient.getService()
                                    .post_DelFriend(auth, mList.get(position).getUid())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<Result>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(Result result) {
                                            if (result.getErr() == 0) {
                                                //刷新好友列表
                                                EventBus.getDefault().post(new RefreshFriendList());
                                            }
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            if (!mActivity.isFinishing()) {
                builder.create().show();
            }
        }
    }
}


