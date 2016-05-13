package com.study.mingappk.tab2.frienddetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.BBSList;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab3.villagebbs.VillageBbsActivity;
import com.study.mingappk.tab3.villagebbs.VillageBbsAdapter;
import com.study.mingappk.tab3.villagebbs.bbsdetail.BbsDetailActivity;
import com.study.mingappk.tmain.BackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FriendBbsActivity extends BackActivity implements VillageBbsAdapter.OnItemClickListener {

    public static String UID="查看用户帖子，提供用户id";
    public static String USER_NAME="显示的用户名，用于标题";
    @Bind(R.id.friend_bbs_list)
    XRecyclerView mXRecyclerView;

    private VillageBbsAdapter mAdapter = new VillageBbsAdapter();
    private XRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    private List<BBSList.DataEntity.ListEntity> mList = new ArrayList<>();
    private BBSList.DataEntity.ListEntity bbsDetail;

    final private static int PAGE_SIZE = 5;
    private int page = 1;
    private int ppid;//点击查看详情的帖子号
    private final int REQUEST_LIKE_COMMENT_NUMBER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_bbs);
        ButterKnife.bind(this);
        setToolbarTitle(getIntent().getStringExtra(USER_NAME));

        configXRecyclerView();//XRecyclerView配置
        getBBSList(page);//获取bbsList数据
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        //设置adapter
        mAdapter.setOnItemClickListener(FriendBbsActivity.this);
        mXRecyclerView.setAdapter(mAdapter);
        //设置布局管理器
        mXRecyclerView.setLayoutManager(mLayoutManager);
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
        //设置XRecyclerView相关
        mXRecyclerView.setPullRefreshEnabled(false);//关闭刷新功能
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                getBBSList(++page);
                mXRecyclerView.loadMoreComplete();
            }
        });
    }

    private void getBBSList(int page) {
        String auth = Hawk.get(APP.USER_AUTH);
        String mUid =getIntent().getStringExtra(UID);
        MyServiceClient.getService()
                .getObservable_FriendBbsList(auth, mUid, page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BBSList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("mm", e.getMessage());
                    }

                    @Override
                    public void onNext(BBSList bbsList) {
                        if (bbsList != null && bbsList.getErr() == 0) {
                            mList.addAll(bbsList.getData().getList());
                            mAdapter.setItem(mList);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.bbs_item:
//                Toast.makeText(this, "点击整个选项操作", Toast.LENGTH_SHORT).show();
                bbsDetail = mList.get(position);
                ppid = position;
                Intent intent = new Intent(this, BbsDetailActivity.class);
                intent.putExtra(BbsDetailActivity.BBS_DETAIL, bbsDetail);
                startActivityForResult(intent, REQUEST_LIKE_COMMENT_NUMBER);
                break;
            case R.id.bbs_comment:
                Toast.makeText(this, "点击留言操作", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }
    @Override
    public void onItemLongClick(View view, int position) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LIKE_COMMENT_NUMBER://帖子详情页，点赞或评论后，就数据返回显示
                if (resultCode == RESULT_OK) {
                    String newCommentNumber = data.getExtras().getString(VillageBbsActivity.COMMENT_NO_NEW);
                    if (newCommentNumber != null && !newCommentNumber.isEmpty()) {
                        mList.get(ppid).setNums(newCommentNumber);
                    }
                    int isCliked = data.getExtras().getInt(VillageBbsActivity.LIKEED_TAG);
                    if (isCliked == 1) {
                        mList.get(ppid).setMy_is_zan(1);
                        String newLikeNumber = data.getExtras().getString(VillageBbsActivity.LIKE_NO_NEW);
                        mList.get(ppid).setZans(newLikeNumber);
                    }
                    mAdapter.setItem(mList);
                }
                break;
        }
    }
}
