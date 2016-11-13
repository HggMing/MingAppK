package com.study.mingappk.tab2.message;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.widgets.dialog.MyDialog;
import com.study.mingappk.common.widgets.stickyrecyclerheaders.StickyRecyclerHeadersDecoration;
import com.study.mingappk.model.bean.FriendList;
import com.study.mingappk.model.bean.RecommendList;
import com.study.mingappk.model.database.ChatMsgModel;
import com.study.mingappk.model.database.FriendsModel;
import com.study.mingappk.model.database.InstantMsgModel;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.event.InstantMsgEvent;
import com.study.mingappk.tab2.chat.ChatActivity;
import com.study.mingappk.tab2.friendlist.FriendListAdapter;
import com.study.mingappk.tab2.friendlist.FriendListFragment;

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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 动态
 */
public class MessageFragment extends Fragment implements MessageAdapter.OnItemClickListener {
    AppCompatActivity mActivity;

    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;
    @Bind(R.id.tv_recommend)
    TextView tvRecommend;
    @Bind(R.id.m_x_recyclerview2)
    XRecyclerView mXRecyclerView2;

    private MessageAdapter mAdapter;
    private List<InstantMsgModel> mList;

    private RecommendAdapter mAdapter2;
    private List<RecommendList.DataBean.ListBean> mList2 = new ArrayList<>();//特产推荐列表


    final private static int PAGE_SIZE = 10;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();

        configXRecyclerView();//XRecyclerView配置

        initFriendInfo();//加载用户好友信息
        initDatas();//加载动态列表
//        initData2(page);//加载产品推荐列表
        EventBus.getDefault().register(this);
    }

    private void initFriendInfo() {
        page = 1;
        String auth = Hawk.get(APP.USER_AUTH);

        MyServiceClient.getService().getCall_FriendList(auth, page, PAGE_SIZE).enqueue(new Callback<FriendList>() {
            @Override
            public void onResponse(Call<FriendList> call, Response<FriendList> response) {
                if (response.isSuccessful()) {
                    FriendList friendList = response.body();
                    if (friendList != null && friendList.getErr() == 0) {
                        List<FriendList.DataBean.ListBean> mFList = friendList.getData().getList();

                        //存储好友列表到本地数据库
                        for (FriendList.DataBean.ListBean user : mFList) {
                            String save_uid = user.getUid();
                            String save_uicon = MyServiceClient.getBaseUrl() + user.getHead();
                            String save_uname = user.getName();
                            FriendsModel friendsModel = new FriendsModel(save_uid, save_uname, save_uicon,true);
                            MyDB.insert(friendsModel);
                        }

                        //生成欢迎语动态
                        FriendList.DataBean.ListBean user2 = mFList.get(1);//我们村客服
                        InstantMsgModel user2_msg = MyDB.createDb(mActivity).queryById(user2.getUid(), InstantMsgModel.class);
                        if (user2_msg == null) {//动态不存在才添加
                            String user2_icon = MyServiceClient.getBaseUrl() + user2.getHead();
                            String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);//13位时间戳，截取前10位，主要统一
                            String content = "你好！" + mFList.get(2).getName() + ",欢迎来到我们村！";
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

                        //储存好友信息
                        List<String> friendUids = new ArrayList<>();
                        for (int i = 0; i < mList.size(); i++) {
                            String uid = mList.get(i).getUid();
                            if (uid != null) {
                                friendUids.add(uid);
                            }
                        }
                        Hawk.put(APP.FRIEND_LIST_UID, friendUids);
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendList> call, Throwable t) {

            }
        });
    }


    //动态消息数据
    private void initDatas() {
        mList = MyDB.getQueryAll(InstantMsgModel.class);
//        if (mList.isEmpty() || mList == null) {
//            contentEmpty.setVisibility(View.VISIBLE);
//        } else {
//            contentEmpty.setVisibility(View.GONE);
//        }
        mAdapter.setItem(mList);
    }

    //产品推荐数据
    private void initData2(int page) {
        MyServiceClient.getService()
                .get_RecommendList(page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecommendList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RecommendList recommendList) {
                        mList2.addAll(recommendList.getData().getList());
                        if (mList2.isEmpty()) {
                            tvRecommend.setVisibility(View.GONE);
                            mXRecyclerView2.setVisibility(View.GONE);
                        } else {
                            tvRecommend.setVisibility(View.VISIBLE);
                            mXRecyclerView2.setVisibility(View.VISIBLE);
                        }
                        mAdapter2.setItem(mList2);
                    }
                });
    }


    /**
     * 更新动态列表
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showInstantMsg(InstantMsgEvent event) {
        if (mList != null) {
            mList.clear();
        }
        mAdapter.clear();
        mList = MyDB.getQueryAll(InstantMsgModel.class);
//        if (mList.isEmpty() || mList == null) {
//            contentEmpty.setVisibility(View.VISIBLE);
//        } else {
//            contentEmpty.setVisibility(View.GONE);
//        }
        mAdapter.setItem(mList);
    }


    //配置RecyclerView
    private void configXRecyclerView() {
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));//设置布局管理器
        mXRecyclerView.addItemDecoration(new MyItemDecoration2(mActivity));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mAdapter = new MessageAdapter();
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mAdapter.setOnItemClickListener(this);

        //推荐页配置
       /* mXRecyclerView2.setLayoutManager(new GridLayoutManager(mActivity,2, GridLayoutManager.VERTICAL, false));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        mXRecyclerView2.addItemDecoration(new SpaceItemDecoration(spacingInPixels));//添加间距
        mXRecyclerView2.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView2.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mAdapter2 = new RecommendAdapter();
        mXRecyclerView2.setAdapter(mAdapter2);

        mXRecyclerView2.setPullRefreshEnabled(false);
        mXRecyclerView2.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView2.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                initData2(++page);
                mXRecyclerView2.loadMoreComplete();
            }
        });*/

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        //点击动态，进入聊天界面
        Intent intent2 = new Intent(mActivity, ChatActivity.class);
        intent2.putExtra(ChatActivity.UID, mList.get(position).getUid());
        intent2.putExtra(ChatActivity.USER_NAME, mList.get(position).getUname());
        startActivity(intent2);
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        //长按删除此条动态
        MyDialog.Builder builder = new MyDialog.Builder(mActivity);
        builder.setTitle("提示")
                .setMessage("删除该聊天？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //数据库中删除此条动态
                        InstantMsgModel iMsg = new InstantMsgModel();
                        iMsg.setUid(mList.get(position).getUid());
                        MyDB.delete(iMsg);
                        //刷新动态
                        EventBus.getDefault().post(new InstantMsgEvent());
                        dialog.dismiss();
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
