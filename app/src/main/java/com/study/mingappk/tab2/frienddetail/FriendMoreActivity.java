package com.study.mingappk.tab2.frienddetail;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.FriendDetail;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FriendMoreActivity extends BackActivity {

    @Bind(R.id.icon_head)
    ImageView iconHead;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.name2)
    TextView name2;
    @Bind(R.id.click_user)
    RelativeLayout clickUser;
    @Bind(R.id.m_x_recyclerview_01)
    RecyclerView mXRecyclerView01;
    @Bind(R.id.healthinfo)
    LinearLayout healthinfo;
    @Bind(R.id.m_x_recyclerview_02)
    RecyclerView mXRecyclerView02;
    @Bind(R.id.scoreinfo)
    LinearLayout scoreinfo;
    @Bind(R.id.layout_more)
    LinearLayout layoutMore;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    public static String FRIEND_UID = "所选择用户的UID";
    FriendMoreAdapter01 mAdapter01;
    FriendMoreAdapter02 mAdapter02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_more);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_friend_more);

        configXRecyclerView();//XRecyclerView配置
        getFriendDetail();
    }

    private void configXRecyclerView() {
        mXRecyclerView01.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
        mAdapter01 = new FriendMoreAdapter01();
        mXRecyclerView01.setAdapter(mAdapter01);//设置adapter
        mXRecyclerView01.addItemDecoration(new MyItemDecoration2(this));//添加分割线
        mXRecyclerView01.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView01.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView02.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
        mAdapter02 = new FriendMoreAdapter02();
        mXRecyclerView02.setAdapter(mAdapter02);//设置adapter
        mXRecyclerView02.addItemDecoration(new MyItemDecoration2(this));//添加分割线
        mXRecyclerView02.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView02.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    private void getFriendDetail() {
        String auth = Hawk.get(APP.USER_AUTH);
        String uid = getIntent().getStringExtra(FRIEND_UID);

        MyServiceClient.getService().get_FriendDetail(auth, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FriendDetail>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   Glide.with(FriendMoreActivity.this)
                                           .load(R.mipmap.defalt_user_circle)
                                           .into(iconHead);
                               }

                               @Override
                               public void onNext(FriendDetail friendDetail) {
                                   //健康信息
                                   List<FriendDetail.DataBean.HealthinfoBean> healthList = friendDetail.getData().getHealthinfo();
                                   if (healthList.isEmpty()) {
                                       healthinfo.setVisibility(View.GONE);
                                   } else {
                                       healthinfo.setVisibility(View.VISIBLE);
                                       mAdapter01.setItem(healthList);
                                   }
                                   //学习成绩
                                   List<FriendDetail.DataBean.ScoreinfoBean> scoreList = friendDetail.getData().getScoreinfo();
                                   if (scoreList.isEmpty()) {
                                       scoreinfo.setVisibility(View.GONE);
                                   } else {
                                       scoreinfo.setVisibility(View.VISIBLE);
                                       mAdapter02.setItem(scoreList);
                                   }

                                   if (healthList.isEmpty() && scoreList.isEmpty()) {
                                       contentEmpty.setVisibility(View.VISIBLE);
                                   } else {
                                       contentEmpty.setVisibility(View.GONE);
                                   }

                                   FriendDetail.DataBean.UserinfoBean userinfoBean = friendDetail.getData().getUserinfo();
                                   //用户头像
                                   String headUrl = MyServiceClient.getBaseUrl() + userinfoBean.getHead();
                                   Glide.with(FriendMoreActivity.this)
                                           .load(headUrl)
                                           .bitmapTransform(new CropCircleTransformation(FriendMoreActivity.this))
                                           .error(R.mipmap.defalt_user_circle)
                                           .into(iconHead);
                                   //用户昵称
                                   String uName = userinfoBean.getUname();//昵称
                                   String aliasName = userinfoBean.getAlias_name();//备注名
                                   String iphone = userinfoBean.getPhone();
                                   String showName = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
                                   if (!StringTools.isEmpty(aliasName)) {
                                       name.setText(aliasName);
                                       if (!StringTools.isEmpty(uName)) {
                                           name2.setText("昵称：" + uName);
                                       } else {
                                           name2.setText("账号：" + showName);
                                       }
                                   } else {
                                       name2.setVisibility(View.INVISIBLE);
                                       if (!StringTools.isEmpty(uName)) {
                                           name.setText(uName);
                                       } else {
                                           name.setText(showName);
                                       }
                                   }
                                   //用户性别
                                   String sexNumber = userinfoBean.getSex();
                                   if ("0".equals(sexNumber)) {
                                       name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_sex_boy,0);

                                   } else {
                                       name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_sex_girl,0);
                                   }
                               }
                           }
                );
    }
}

class FriendMoreAdapter01 extends BaseRecyclerViewAdapter<FriendDetail.DataBean.HealthinfoBean, FriendMoreAdapter01.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab2_friend_more, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String tTitle = mList.get(position).getK();
        String tNum = mList.get(position).getV();
        holder.title.setText(tTitle);
        holder.number.setText(tNum);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        View icon;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.number)
        TextView number;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

class FriendMoreAdapter02 extends BaseRecyclerViewAdapter<FriendDetail.DataBean.ScoreinfoBean, FriendMoreAdapter02.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab2_friend_more, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String tTitle = mList.get(position).getK();
        String tNum = mList.get(position).getV();
        holder.title.setText(tTitle);
        holder.number.setText(tNum);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        View icon;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.number)
        TextView number;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}