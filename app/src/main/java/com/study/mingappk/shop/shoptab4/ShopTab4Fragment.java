package com.study.mingappk.shop.shoptab4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.InsuranceOrderList;
import com.study.mingappk.model.bean.MyVillUsers;
import com.study.mingappk.model.bean.VillageInfo;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 本村用户列表
 */
public class ShopTab4Fragment extends Fragment {
    AppCompatActivity mActivity;
    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    private String auth;
    private List<MyVillUsers.DataBean.ListBean> mList = new ArrayList<>();
    private ShopTab4Adapter mAdapter=new ShopTab4Adapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_tab4, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();

        auth = Hawk.get(APP.USER_AUTH);
        configXRecyclerView();//XRecyclerView配置
        initData();//获取数据
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));//设置布局管理器
        mXRecyclerView.addItemDecoration(new MyItemDecoration2(mActivity));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        //mAdapter.setOnItemClickListener(ShopTab4Fragment.this);
    }

    private void initData() {

        MyServiceClient.getService()
                .get_MyVillUsers(auth, 1, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyVillUsers>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyVillUsers myVillUsers) {
                        mList.clear();
                        mList.addAll(myVillUsers.getData().getList());
                        if (mList.isEmpty()) {
                            contentEmpty.setVisibility(View.VISIBLE);
                            contentEmpty.setText(R.string.empty_my_village_user);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    static class ShopTab4Adapter extends BaseRecyclerViewAdapter<MyVillUsers.DataBean.ListBean, ShopTab4Adapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_tab4, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Context mContext = holder.itemView.getContext();
            final MyVillUsers.DataBean.ListBean data = mList.get(position);

            //用户头像的显示
            String imageUrl = MyServiceClient.getBaseUrl() + data.getHead();
            Glide.with(mContext).load(imageUrl)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .error(R.mipmap.defalt_user_circle)
                    .into(holder.userHead);
            //用户名字显示
            String showName = data.getUname();
            if (!StringTools.isEmpty(showName)) {
                holder.userName.setText(showName);
            } else {
                String phone = data.getPhone();
                showName = phone.substring(0, 3) + "****" + phone.substring(7, 11);
                holder.userName.setText(showName);
            }
            //点击事件
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,VillagerInfoActivity.class);
                    intent.putExtra(VillagerInfoActivity.VILLAGE_USER_INFO,data);
                    mContext.startActivity(intent);
                }
            });
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.user_head)
            ImageView userHead;
            @Bind(R.id.user_name)
            TextView userName;
            @Bind(R.id.item)
            RelativeLayout item;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

}
