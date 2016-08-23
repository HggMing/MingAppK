package com.study.mingappk.tab2.newfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintButton;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.database.NewFriendModel;
import com.study.mingappk.model.event.NewFriend2Event;
import com.study.mingappk.model.event.NewFriendEvent;
import com.study.mingappk.model.event.RefreshFriendList;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab2.frienddetail.FriendDetailActivity;
import com.study.mingappk.tmain.baseactivity.BackActivity;
import com.study.mingappk.tmain.baseactivity.BaseRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewFriendActivity extends BackActivity {

    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;

    private NewFriendAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_new_friend);

        configXRecyclerView();//XRecyclerView配置

        initDatas();//初始化数据
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        //设置布局和adapter
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewFriendAdapter(this);
        mXRecyclerView.setAdapter(mAdapter);

        mXRecyclerView.addItemDecoration(new MyItemDecoration2(this));//添加分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    /**
     * 显示数据库中消息
     */
    public void initDatas() {
        List<NewFriendModel> newFriends = MyDB.getQueryAll(NewFriendModel.class);
        //进入新的朋友界面，清零申请好友的新消息计数
        for (NewFriendModel newFriend : newFriends) {
            newFriend.setCount(0);
            MyDB.update(newFriend);
            EventBus.getDefault().post(new NewFriendEvent());
        }
        Collections.reverse(newFriends);//列表反向
        mAdapter.setItem(newFriends);
    }

    /**
     * 新朋友消息来时，在当前页面，直接刷新
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNew(NewFriend2Event event) {
        List<NewFriendModel> newFriends = MyDB.getQueryAll(NewFriendModel.class);
        //清零申请好友的新消息计数
        for (NewFriendModel newFriend : newFriends) {
            newFriend.setCount(0);
            MyDB.update(newFriend);
        }
        Collections.reverse(newFriends);//列表反向
        mAdapter.setItem(newFriends);
    }
}

class NewFriendAdapter extends BaseRecyclerViewAdapter<NewFriendModel, NewFriendAdapter.ViewHolder> {
    NewFriendActivity activity;

    public NewFriendAdapter(NewFriendActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab2_new_friend, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Context mContext = holder.itemView.getContext();
        final NewFriendModel data = mList.get(position);

        Glide.with(mContext)
                .load(data.getUicon())
                .bitmapTransform(new CropCircleTransformation(mContext))
                .error(R.mipmap.defalt_user_circle)
                .into(holder.icon);

        holder.name.setText(data.getUname() + "(" + data.getUphone() + ")");

        switch (data.getType()) {
            case 0://显示两个按钮，选择是否同意
                holder.buttonAll.setVisibility(View.VISIBLE);
                holder.status.setVisibility(View.GONE);
                break;
            case 1://已添加
                holder.buttonAll.setVisibility(View.GONE);
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText("已添加");
                break;
            case 2://已拒绝
                holder.buttonAll.setVisibility(View.GONE);
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText("已拒绝");
                break;
        }


        //点击同意按钮
        final String auth = Hawk.get(APP.USER_AUTH);
        final String uid = data.getUid();
        final RequestBody authBody = RequestBody.create(MediaType.parse("text/plain"), auth);
        final RequestBody uidBody = RequestBody.create(MediaType.parse("text/plain"), uid);

        holder.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyServiceClient.getService()
                        .post_AddAgree(authBody, uidBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                data.setType(1);
                                MyDB.update(data);
                                notifyDataSetChanged();
                                //同意后，刷新好友列表
                                EventBus.getDefault().post(new RefreshFriendList());

                                //显示已添加好友的详情页面
                                String uid = data.getUid();
                                Intent intent = new Intent(mContext, FriendDetailActivity.class);
                                intent.putExtra(FriendDetailActivity.FRIEND_UID, uid);
                                mContext.startActivity(intent);
                                activity.finish();
                            }
                        });
            }
        });
        //点击拒绝按钮
        holder.buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyServiceClient.getService()
                        .post_AddUnagree(authBody, uidBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                data.setType(2);
                                MyDB.update(data);
                                notifyDataSetChanged();
                            }
                        });
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.status)
        TextView status;
        @Bind(R.id.button_cancle)
        TintButton buttonCancle;
        @Bind(R.id.button_ok)
        TintButton buttonOk;
        @Bind(R.id.button_all)
        LinearLayout buttonAll;
        @Bind(R.id.item)
        LinearLayout item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
