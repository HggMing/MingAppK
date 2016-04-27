package com.study.mingappk.tab3.villagebbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jude.utils.JUtils;
import com.melnykov.fab.FloatingActionButton;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.model.bean.BBSList;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab3.newpost.NewPostActivity;
import com.study.mingappk.tab3.villagesituation.VillageSituationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VillageBbsActivity extends AppCompatActivity implements VillageBbsAdapter.OnItemClickListener {
    public static final String VILLAGE_ID = "village_id";
    public static final String VILLAGE_NAME = "village_name";
    public static final String VILLAGE_PIC = "village_pic";
    @Bind(R.id.toolbar_bbs)
    Toolbar toolbar;
    @Bind(R.id.tab3_bbs_list)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.collapsing_toolbar_layout_bbs)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.village_image)
    ImageView villageImage;
    @Bind(R.id.fab)
    FloatingActionButton fab;
//    @Bind(R.id.comment_edit)
//    EditText commentEdit;
//    @Bind(R.id.comment_post)
//    Button commentPost;
//    @Bind(R.id.comment_input)
//    LinearLayout commentInput;

    private VillageBbsAdapter mAdapter = new VillageBbsAdapter();
    private XRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    private List<BBSList.DataEntity.ListEntity> mList = new ArrayList<>();

    final private static int PAGE_SIZE = 5;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_tab3_bbs_list);
        ButterKnife.bind(this);

        BaseTools.transparentStatusBar(this);//透明状态栏

        fab.attachToRecyclerView(mXRecyclerView);//fab随recyclerView的滚动，隐藏和出现

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_back);
        }

        //顶部村名和村图片的加载
        String villageName = getIntent().getStringExtra(VILLAGE_NAME);
        mCollapsingToolbarLayout.setTitle(villageName);
        Glide.with(this)
                .load(getIntent().getStringExtra(VILLAGE_PIC))
                .priority(Priority.IMMEDIATE)
                .placeholder(R.mipmap.default_village)
                .into(villageImage);

        configXRecyclerView();//XRecyclerView配置
        getBBSList(page);//获取bbsList数据
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getBBSList(int page) {
        String auth = APP.getInstance().getAuth();
        String mVid = getIntent().getStringExtra(VILLAGE_ID);
        MyServiceClient.getService()
                .getObservable_BBSList(auth, mVid, page, PAGE_SIZE)
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

    //配置RecyclerView
    private void configXRecyclerView() {
        //设置adapter
        mAdapter.setOnItemClickListener(VillageBbsActivity.this);
        mXRecyclerView.setAdapter(mAdapter);
        //设置布局管理器
        mXRecyclerView.setLayoutManager(mLayoutManager);
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
//        mXRecyclerView.addItemDecoration(new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST, 30));//添加分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
       /* mXRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                commentInput.setVisibility(View.GONE);
                JUtils.closeInputMethod(VillageBbsActivity.this);
                fab.setVisibility(View.VISIBLE);
            }
        });*/
        //设置XRecyclerView相关
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);//自定义下拉刷新箭头图标
        mXRecyclerView.setPullRefreshEnabled(false);//关闭刷新功能

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


    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.bbs_item:
                Toast.makeText(this, "点击整个选项操作", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bbs_comment:
                Toast.makeText(this, "点击留言操作", Toast.LENGTH_SHORT).show();
//                commentInput.setVisibility(View.VISIBLE);
//                commentEdit.requestFocus();
//                fab.setVisibility(View.GONE);
//                InputMethodManager inputManager =
//                        (InputMethodManager)commentEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(commentEdit, 0);
//                commentEdit.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        if(count==0){
//                            commentPost.setClickable(false);
//                            commentPost.setTextColor(getResources().getColor(R.color.font_black_comment));
//                            commentPost.setBackgroundColor(getResources().getColor(R.color.input_background));
//                        }
//                        else{
//                            commentPost.setTextColor(getResources().getColor(R.color.white));
//                            commentPost.setBackgroundResource(R.drawable.button_green_common);
//                            commentPost.setClickable(true);
//                        }
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });

                break;
            default:
                break;

        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @OnClick({R.id.icon_specialty, R.id.icon_village, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_specialty:
                break;
            case R.id.icon_village:
                Intent intent = new Intent(this, VillageSituationActivity.class);
                startActivity(intent);
                break;
            case R.id.fab:
                Intent intent1 = new Intent(this, NewPostActivity.class);
                String mVid = getIntent().getStringExtra(VILLAGE_ID);
                intent1.putExtra(NewPostActivity.VILLAGE_ID, mVid);
                startActivityForResult(intent1, 11);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                if (resultCode == RESULT_OK) {
                    mAdapter.setItem(null);
                    mList.clear();
                    page = 1;
                    getBBSList(page);
                }
                break;
        }
    }

}
