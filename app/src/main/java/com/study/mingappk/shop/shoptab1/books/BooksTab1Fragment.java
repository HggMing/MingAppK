package com.study.mingappk.shop.shoptab1.books;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.melnykov.fab.FloatingActionButton;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BaseRecyclerViewAdapter;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.model.bean.BookList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 图书列表
 */
public class BooksTab1Fragment extends Fragment {

    @Bind(R.id.m_x_recyclerview)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.m_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.content_empty)
    TextView contentEmpty;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    AppCompatActivity mActivity;
    private BooksAdapter mAdapter;
    private List<BookList.DataBean.ListBean> mList = new ArrayList<>();

    final private static int PAGE_SIZE = 10;
    private int page = 1;
    private final int REQUEST_CODE = 12331;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        config();
        initData(page);

        // 刷新时，指示器旋转后变化的颜色
        String theme = APP.getInstance().getTheme(mActivity);
        int themeColorRes = getResources().getIdentifier(theme, "color", mActivity.getPackageName());
        mRefreshLayout.setColorSchemeResources(themeColorRes);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setItem(null);
                mList.clear();
                page = 1;
                initData(page);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void config() {
        mActivity = (AppCompatActivity) getActivity();
        //设置fab
        fab.attachToRecyclerView(mXRecyclerView);//fab随recyclerView的滚动，隐藏和出现
        int themeColor = ThemeUtils.getColorById(mActivity, R.color.theme_color_primary);
        int themeColor2 = ThemeUtils.getColorById(mActivity, R.color.theme_color_primary_dark);
        fab.setColorNormal(themeColor);//fab背景颜色
        fab.setColorPressed(themeColor2);//fab点击后背景颜色
        fab.setColorRipple(themeColor2);//fab点击后涟漪颜色
        //设置recyclerview布局
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mXRecyclerView.addItemDecoration(new MyItemDecoration(mActivity));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画



        //设置adapter
        mAdapter = new BooksAdapter();
        mXRecyclerView.setAdapter(mAdapter);

        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                initData(++page);
                mXRecyclerView.loadMoreComplete();
            }
        });
    }

    private void initData(int page) {
        String vid = Hawk.get(APP.MANAGER_VID);
        MyServiceClient.getService()
                .get_BookListTab1(vid, page,PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookList>() {
                    @Override
                    public void onCompleted() {
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BookList bookList) {
                        mList.addAll(bookList.getData().getList());
                        if (mList.isEmpty()) {
                            contentEmpty.setVisibility(View.VISIBLE);
                            contentEmpty.setText(R.string.empty_book_tab1);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    @OnClick(R.id.fab)
    public void onClick() {
//        Toast.makeText(mActivity, "添加图书", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(mActivity,AddBookActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mAdapter.setItem(null);
                mList.clear();
                page = 1;
                initData(page);
            }
        }
    }

    static class BooksAdapter extends BaseRecyclerViewAdapter<BookList.DataBean.ListBean, BooksAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_tab1_books, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Context mContext = holder.itemView.getContext();
            BookList.DataBean.ListBean data = mList.get(position);
            //书籍封面图片
            String imageUrl = MyServiceClient.getBaseUrl() + data.getPic_s();
            Glide.with(mContext).load(imageUrl)
                    .centerCrop()
//                .placeholder(R.mipmap.default_nine_picture)
                    .into(holder.imgBook);
            //显示书号
            holder.tvNum.setText("书号：  " + data.getBnum());
            //显示书名
            holder.tvName.setText("书名：《"+data.getBname()+"》");
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.img_book)
            ImageView imgBook;
            @Bind(R.id.tv_num)
            TextView tvNum;
            @Bind(R.id.tv_name)
            TextView tvName;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
