package com.study.mingappk.tab4.shop.shoptab1.books;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.melnykov.fab.FloatingActionButton;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.model.bean.Book2List;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 已借图书
 */
public class BooksTab2Fragment extends Fragment {

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
    private List<Book2List.DataBean.ListBean> mList = new ArrayList<>();

    final private static int PAGE_SIZE = 10;
    private int page = 1;


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
        fab.setVisibility(View.GONE);
        //设置recyclerview布局
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mXRecyclerView.addItemDecoration(new NoDecoration(mActivity));//添加空白分割线
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

    private void initData(final int page) {
        String vid = Hawk.get(APP.MANAGER_VID);
        MyServiceClient.getService()
                .get_BookListTab2(vid, page, PAGE_SIZE)
                .flatMap(new Func1<Book2List, Observable<Book2List.DataBean.ListBean>>() {
                    @Override
                    public Observable<Book2List.DataBean.ListBean> call(Book2List book2List) {
                        return Observable.from(book2List.getData().getList());
                    }
                })
                .filter(new Func1<Book2List.DataBean.ListBean, Boolean>() {
                    @Override
                    public Boolean call(Book2List.DataBean.ListBean listBean) {
                        return "0".equals(listBean.getStatus());//0为已借，1为已还
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book2List.DataBean.ListBean>() {
                    @Override
                    public void onCompleted() {
                        if (mList.isEmpty()) {
                            contentEmpty.setVisibility(View.VISIBLE);
                            contentEmpty.setText(R.string.empty_book_tab1);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Book2List.DataBean.ListBean listBean) {
                        mList.add(listBean);
                    }
                });
    }

    static class BooksAdapter extends BaseRecyclerViewAdapter<Book2List.DataBean.ListBean, BooksAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab4_books2, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Book2List.DataBean.ListBean data = mList.get(position);
            //借阅人姓名
            holder.tvUser.setText("借阅人：" + data.getName());
            //借阅人手机号
            holder.tvPhone.setText(data.getPhone());
            //显示书号
            holder.tvNum.setText("书号：  " + data.getBooknum());
            //显示书名,需要先做base64解码
            String bookName = new String(Base64.decode(data.getBookname(), Base64.DEFAULT));
            holder.tvName.setText("书名：《" + bookName + "》");
            //显示借书时间
            String date = data.getCtime();
            String timeFormat = BaseTools.formatDateByFormat(date, "yyyy-MM-dd HH:mm");
            holder.tvTime.setText("借阅时间：" + timeFormat);
        }


        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_user)
            TextView tvUser;
            @Bind(R.id.tv_phone)
            TextView tvPhone;
            @Bind(R.id.tv_num)
            TextView tvNum;
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.tv_time)
            TextView tvTime;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
