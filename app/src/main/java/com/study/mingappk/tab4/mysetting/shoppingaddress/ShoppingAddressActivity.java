package com.study.mingappk.tab4.mysetting.shoppingaddress;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.melnykov.fab.FloatingActionButton;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.ShoppingAddress;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ShoppingAddressActivity extends BackActivity implements ShoppingAddressAdapter.OnItemClickListener {

    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private ShoppingAddressAdapter mAdapter;
    public static final int REFRESH = 1100;
    List<ShoppingAddress.DataBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_address);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_shopping_address);

        config();
        initData();
    }

    private void config() {
        //设置fab
        fab.attachToRecyclerView(mXRecyclerView);//fab随recyclerView的滚动，隐藏和出现
        int themeColor = ThemeUtils.getColorById(this, R.color.theme_color_primary);
        int themeColor2 = ThemeUtils.getColorById(this, R.color.theme_color_primary_dark);
        fab.setColorNormal(themeColor);//fab背景颜色
        fab.setColorPressed(themeColor2);//fab点击后背景颜色
        fab.setColorRipple(themeColor2);//fab点击后涟漪颜色
        //设置recyclerview布局和adapter
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ShoppingAddressAdapter(this);
        mXRecyclerView.setAdapter(mAdapter);
//        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mAdapter.setOnItemClickListener(this);
    }

    private void initData() {
        //设置数据
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_ShoppingAddress(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShoppingAddress>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ShoppingAddress shoppingAddress) {
                        mList.clear();
                        mList.addAll(shoppingAddress.getData());
                        if (shoppingAddress.getData().isEmpty()||shoppingAddress.getData()==null) {
                            contentEmpty.setVisibility(View.VISIBLE);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(shoppingAddress.getData());
                    }
                });
    }

    @OnClick(R.id.fab)
    public void onClick() {
//        Toast.makeText(ShoppingAddressActivity.this, "添加收货地址", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, EditShoppingAdressActivity.class);
        startActivityForResult(intent, REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFRESH) {
            if (resultCode == RESULT_OK) {
                initData();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //修改设为默认地址
        ShoppingAddress.DataBean data = mList.get(position);
        String auth = Hawk.get(APP.USER_AUTH);
        String sd_is_def = data.getIs_def();
        if ("1".equals(sd_is_def)) {
            sd_is_def = "0";
        } else {
            sd_is_def = "1";
        }

        MyServiceClient.getService()
                .post_EditShoppingAddress(auth, data.getId(), data.getUname(), data.getAddr(), data.getTel(), sd_is_def, data.getZipcode())
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
                        initData();
                    }
                });
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}

class ShoppingAddressAdapter extends BaseRecyclerViewAdapter<ShoppingAddress.DataBean, ShoppingAddressAdapter.ViewHolder> {

    Activity mActivity;

    public ShoppingAddressAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab4_shopping_address, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShoppingAddress.DataBean data = mList.get(position);

        //显示收货人姓名
        String uname = data.getUname();
        holder.name.setText(uname);
        //显示手机号
        String phone = data.getTel();
        if (phone.length() == 11) {
            phone = phone.substring(0, 3) + "****" + phone.substring(7, 11);
            holder.phone.setText(phone);
            holder.phone.setTextColor(mActivity.getResources().getColor(R.color.font_black_comment));
        } else {
            holder.phone.setText("手机号有误，请修改");
            holder.phone.setTextColor(mActivity.getResources().getColor(R.color.red));
        }
        //显示收货地址
        String address = data.getAddr();
        String zipCode = data.getZipcode();
        holder.address.setText(address + "    " + zipCode);
        //显示是否设为默认
        String isDef = data.getIs_def();
        if ("1".equals(isDef)) {
            holder.isDefault.setChecked(true);
            holder.isDefault.setText("默认地址");
        } else {
            holder.isDefault.setChecked(false);
            holder.isDefault.setText("设为默认");
        }

        holder.isDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.isDefault, position);
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mActivity, "进入编辑页面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mActivity, EditShoppingAdressActivity.class);
                intent.putExtra(EditShoppingAdressActivity.SHOPPIND_ADDRESS_DATA, data);
                mActivity.startActivityForResult(intent, ShoppingAddressActivity.REFRESH);
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按删除所选地址信息
                MyDialog.Builder builder = new MyDialog.Builder(mActivity);
                builder.setTitle("提示")
                        .setMessage("确定删除此地址信息吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String auth = Hawk.get(APP.USER_AUTH);
                                MyServiceClient.getService()
                                        .post_DelShoppingAddress(auth, data.getId())
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
                                                mList.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeRemoved(position, mList.size() - position);
                                            }
                                        });
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
                return true;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.phone)
        TextView phone;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.item)
        CardView item;
        @Bind(R.id.is_default)
        CheckBox isDefault;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}