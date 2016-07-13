package com.study.mingappk.tab3.villagebbs.bbsdetail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jude.utils.JUtils;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.common.views.bigimageview.BigImageViewActivity;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.common.views.nineimage.NineGridImageView;
import com.study.mingappk.common.views.nineimage.NineGridImageViewAdapter;
import com.study.mingappk.model.bean.BBSList;
import com.study.mingappk.model.bean.BbsCommentList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.ZanList;
import com.study.mingappk.model.database.BbsDetailModel;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab2.frienddetail.FriendDetailActivity;
import com.study.mingappk.tab3.villagebbs.VillageBbsActivity;
import com.study.mingappk.tab3.villagebbs.likeusers.LikeUsersArea;
import com.study.mingappk.tmain.BackActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BbsDetailActivity extends BackActivity implements BbsDetailAdapter.OnItemClickListener {

    public static final String BBS_DETAIL = "bbs detail from VillageBbsActivity";//bbs详细信息
    public static final String IS_CLICK_COMMENT = "is_click_comment";//是否点击评论

    @Bind(R.id.tab3_bbs_detail)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.comment_edit)
    EditText commentEdit;
    @Bind(R.id.comment_post)
    Button commentPost;
    @Bind(R.id.comment_input)
    LinearLayout commentInput;

    private ImageView bbsHead;
    private ImageView bbsDel;
    private TextView bbsUname;
    private TextView bbsContents;
    private NineGridImageView nineGridImage;
    private TextView bbsCtime;
    private ImageView bbsLikeIcon;
    private TextView bbsLike;
    private ImageView bbsLiked;
    private TextView bbsComment;
    private TextView bbsReport;
    private LinearLayout bbsDetailHead;
    private LinearLayout bbsLikeLayout;
    private LikeUsersArea likeUsersArea;

    private BbsDetailAdapter mAdapter = new BbsDetailAdapter();
    private XRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    List<BbsCommentList.DataBean.ListBean> mList = new ArrayList<>();
    private BBSList.DataEntity.ListEntity bbsDetail;

    final private static int PAGE_SIZE = 10;
    private int page = 1;
    private String pid;
    private String msgNumber;
    private Bundle bundle = new Bundle();
    private Intent intent = new Intent();

    private String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_detail);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_bbs_detail);

        auth = Hawk.get(APP.USER_AUTH);

        bbsDetail = getIntent().getParcelableExtra(BBS_DETAIL);
        pid = bbsDetail.getId();

        MyDB.createDb(this);

        configXRecyclerView();//XRecyclerView配置
        setBbsDetailHead();
        getDataList(page);//获取followList数据和cnt值

        //点击村圈列表中的留言，直接打开本页，加载软键盘
        if (getIntent().getBooleanExtra(IS_CLICK_COMMENT, false)) {
            commentEdit.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) commentEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(commentEdit, 0);
        }
    }

    private void setBbsDetailHead() {
        //发帖人头像
        String headUrl = MyServiceClient.getBaseUrl() + bbsDetail.getUserinfo().getHead();
        Glide.with(BbsDetailActivity.this).load(headUrl)
                .bitmapTransform(new CropCircleTransformation(this))
                .priority(Priority.HIGH)
                .error(R.mipmap.defalt_user_circle)
                // .placeholder(R.mipmap.defalt_user_circle)
                .into(bbsHead);

        bbsHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BbsDetailActivity.this, FriendDetailActivity.class);
                String uid = bbsDetail.getUid();
                intent.putExtra(FriendDetailActivity.FRIEND_UID, uid);
                startActivity(intent);
            }
        });
        //发帖人昵称
        String userName = bbsDetail.getUname();
        if (userName.isEmpty()) {
            //若用户名为空，显示手机号，中间四位为*
            String iphone = bbsDetail.getUserinfo().getPhone();
            userName = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
        }
        bbsUname.setText(userName);
        //删除帖子按钮（仅发帖人可见）
        String uid = bbsDetail.getUid();
        if (Hawk.get(APP.ME_UID).equals(uid)) {
            bbsDel.setVisibility(View.VISIBLE);
        }
        bbsDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.Builder builder = new MyDialog.Builder(BbsDetailActivity.this);
                builder.setTitle("提示")
                        .setMessage("注意删除帖子后无法恢复。是否删除?")
                        .setNegativeButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteBbs();
                                        dialog.dismiss();
                                    }
                                })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }

            private void deleteBbs() {
                String id = bbsDetail.getId();
                MyServiceClient.getService()
                        .post_DeleteBbs(auth, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Result result) {
                                Toast.makeText(BbsDetailActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                if (result.getErr() == 0) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        });
            }
        });
        //发帖时间
        String date = bbsDetail.getCtime();
        if (date != null) {
            String time = BaseTools.getTimeFormatText(new Date(Long.valueOf(date + "000")));
            bbsCtime.setText(time);
        } else {
            bbsCtime.setText("");
        }
        //发帖消息正文
        String msgContent = bbsDetail.getConts();
        bbsContents.setText(msgContent);
        //已点赞图标显示
        int isLiked = bbsDetail.getMy_is_zan();
        if (isLiked == 1) {
            bbsLikeIcon.setVisibility(View.INVISIBLE);
            bbsLiked.setVisibility(View.VISIBLE);
        } else {
            bbsLikeIcon.setVisibility(View.VISIBLE);
            bbsLiked.setVisibility(View.INVISIBLE);
        }
        //点赞总数
        String likeNumber = bbsDetail.getZans();
        bbsLike.setText(likeNumber);
        //留言总数
        msgNumber = bbsDetail.getNums();
        bbsComment.setText(msgNumber);
        //帖子图片显示
        NineGridImageViewAdapter<BBSList.DataEntity.ListEntity.FilesEntity> nineGridViewAdapter = new NineGridImageViewAdapter<BBSList.DataEntity.ListEntity.FilesEntity>() {

            @Override
            protected void onDisplayImage(Context context, ImageView imageView, BBSList.DataEntity.ListEntity.FilesEntity filesEntity) {
                String imageUrl = MyServiceClient.getBaseUrl() + filesEntity.getSurl_2();
                if (filesEntity.getSurl_2().isEmpty()) {
                    imageUrl = MyServiceClient.getBaseUrl() + filesEntity.getSurl_1();
                }
                Glide.with(context).load(imageUrl)
                        .asBitmap()
                        .placeholder(R.mipmap.default_nine_picture)
                        .into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);

            }

            @Override
            protected void onItemImageClick(Context context, int index, List<BBSList.DataEntity.ListEntity.FilesEntity> list) {
                super.onItemImageClick(context, index, list);
                // Toast.makeText(context, "点击第" + index+"个图片", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(BbsDetailActivity.this, BigImageViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(BigImageViewActivity.IMAGE_LIST, (ArrayList<? extends Parcelable>) list);
                bundle.putInt(BigImageViewActivity.IMAGE_INDEX, index);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        };
        nineGridImage.setAdapter(nineGridViewAdapter);
        List<BBSList.DataEntity.ListEntity.FilesEntity> photoList = bbsDetail.getFiles();
        nineGridImage.setImagesData(photoList);

        //点赞人员显示区
        View.OnClickListener mOnClickUser = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(BbsDetailActivity.this, "点击点赞人头像", Toast.LENGTH_SHORT).show();
                String uid = (String) v.getTag(R.id.tag_like_user_id);
                if (!uid.isEmpty()) {
                    Intent intent = new Intent(BbsDetailActivity.this, FriendDetailActivity.class);
                    intent.putExtra(FriendDetailActivity.FRIEND_UID, uid);
                    startActivity(intent);
                }
            }
        };
        likeUsersArea = new LikeUsersArea(bbsDetailHead, BbsDetailActivity.this, mOnClickUser);
        getLikeList(pid);

        //点击评论按钮*************************************************************************************************************
        commentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    commentPost.setEnabled(false);
                } else {
                    commentPost.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bbsComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentEdit.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) commentEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(commentEdit, 0);
            }
        });
        //点击点赞按钮*************************************************************************************************************
        bbsLikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyServiceClient.getService().getCall_ClickLike(auth, pid).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Result result = response.body();
                            if (result != null) {
                                Toast.makeText(BbsDetailActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                if (result.getErr() == 0) {
                                    //点赞数+1
                                    String likeNumber = String.valueOf(Integer.parseInt(bbsDetail.getZans()) + 1);
                                    bbsLike.setText(likeNumber);
                                    //点赞图标动画
                                    Animation animPraise = AnimationUtils.loadAnimation(BbsDetailActivity.this, R.anim.scale);
                                    bbsLikeIcon.setVisibility(View.INVISIBLE);
                                    bbsLiked.setVisibility(View.VISIBLE);
                                    bbsLiked.startAnimation(animPraise);
                                    //list点赞数据变化，返回点赞标志
                                    bundle.putString(VillageBbsActivity.LIKE_NO_NEW, likeNumber);
                                    bundle.putInt(VillageBbsActivity.LIKEED_TAG, 1);
                                    intent.putExtras(bundle);
                                    setResult(RESULT_OK, intent);
                                    //点赞人头像刷新
                                    getLikeList(pid);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(BbsDetailActivity.this, "点赞出错：" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //举报相关*************************************************************************************************************
        //从数据库中取出是否举报,初始化举报状态
        final String bid = bbsDetail.getId();

        List<BbsDetailModel> bbsDetail = MyDB.getQueryAll(BbsDetailModel.class);
        boolean isReport = false;
        for (int i = 0; i < bbsDetail.size(); i++) {
            if (bid.equals(bbsDetail.get(i).getBid())) {
                isReport = true;
            }
        }
        if (isReport) {
            bbsReport.setText("已举报");
        } else {
            bbsReport.setText("举报");
        }
        //点击举报按钮
        bbsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(BbsDetailActivity.this, "举报帖子", Toast.LENGTH_SHORT).show();
                if ("举报".equals(bbsReport.getText().toString())) {
                    MyDialog.Builder builder = new MyDialog.Builder(BbsDetailActivity.this);
                    builder.setTitle("提示")
                            .setMessage("感谢你对评论内容的监督，多人举报后该评论将被隐藏，注意恶意举报会被处罚。是否举报?")
                            .setNegativeButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            reportBbs(null);
                                            dialog.dismiss();
                                        }
                                    })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                } else {
                    Toast.makeText(BbsDetailActivity.this, "你已经举报此帖子，等待审核处理。", Toast.LENGTH_SHORT).show();
                }
            }

            private void reportBbs(String conts) {
                MyServiceClient.getService()
                        .post_Report(auth, bid, conts)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Result result) {
                                if (result.getErr() == 0) {
                                    Toast.makeText(BbsDetailActivity.this, "已举报", Toast.LENGTH_SHORT).show();
                                    bbsReport.setText("已举报");
                                    BbsDetailModel bbsDetailModel = new BbsDetailModel(bid, true);
                                    MyDB.insert(bbsDetailModel);
                                }
                            }
                        });
            }
        });
    }

    private void getLikeList(String pid) {
        MyServiceClient.getService().get_ZanList(auth, pid, 1, 99)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZanList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZanList zanList) {
                        likeUsersArea.displayLikeUser(zanList);
                    }
                });
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        mAdapter.setOnItemClickListener(BbsDetailActivity.this);
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器

        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setPullRefreshEnabled(false);//关闭刷新功能

        int x = Integer.parseInt(bbsDetail.getNums());
        if (x <= PAGE_SIZE) {
            mXRecyclerView.setLoadingMoreEnabled(false);//评论数少于一页，不显示加载更多
        }
//        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);//自定义下拉刷新箭头图标

        View header = LayoutInflater.from(this).inflate(R.layout.activity_bbs_detail_head, (ViewGroup) findViewById(android.R.id.content), false);
        bbsHead = (ImageView) header.findViewById(R.id.bbs_head);
        bbsUname = (TextView) header.findViewById(R.id.bbs_uname);
        bbsDel = (ImageView) header.findViewById(R.id.bbs_click_del);
        bbsContents = (TextView) header.findViewById(R.id.bbs_contents);
        nineGridImage = (NineGridImageView) header.findViewById(R.id.nine_grid_image);
        bbsCtime = (TextView) header.findViewById(R.id.bbs_ctime);
        bbsLikeIcon = (ImageView) header.findViewById(R.id.bbs_like_icon);
        bbsLike = (TextView) header.findViewById(R.id.bbs_like);
        bbsLiked = (ImageView) header.findViewById(R.id.bbs_liked);
        bbsComment = (TextView) header.findViewById(R.id.bbs_comment);
        bbsReport = (TextView) header.findViewById(R.id.bbs_report);
        bbsDetailHead = (LinearLayout) header.findViewById(R.id.bbs_detail_head);
        bbsLikeLayout = (LinearLayout) header.findViewById(R.id.bbs_like_layout);
        mXRecyclerView.addHeaderView(header);

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                mAdapter.setItem(null);
//                mList.clear();
//                page=1;
//                getDataList(page);
//                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getDataList(++page);
                mXRecyclerView.loadMoreComplete();
            }
        });
    }

    private void getDataList(int page) {
        MyServiceClient.getService().get_BbsCommentList(auth, pid, page, PAGE_SIZE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BbsCommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BbsCommentList bbsCommentList) {
                        mList.addAll(bbsCommentList.getData().getList());
                        mAdapter.setItem(mList);
                    }
                });
    }

    @Override
    public void onItemClick(View view, final int position) {
        MyDialog.Builder builder = new MyDialog.Builder(BbsDetailActivity.this);
        builder.setTitle("提示")
                .setMessage("注意删除评论后无法恢复。是否删除?")
                .setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCommentBbs(position);
                                dialog.dismiss();
                            }
                        })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void deleteCommentBbs(final int position) {
        String id = mList.get(position).getId();
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .post_DeleteComment(auth, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result result) {
                        Toast.makeText(BbsDetailActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                        if (result.getErr() == 0) {//删除评论后
                            //评论数-1
                            msgNumber = String.valueOf(Integer.parseInt(msgNumber) - 1);
                            bbsComment.setText(msgNumber);
                            //评论数改变，返回上一页
                            bundle.putString(VillageBbsActivity.COMMENT_NO_NEW, msgNumber);
                            intent.putExtras(bundle);
                            setResult(RESULT_OK, intent);
                            //删除评论并刷新
                            mList.remove(position);
                            mAdapter.notifyItemRemoved(position + 2);//+2是因为有一个header和下拉刷新部分
//                            mAdapter.notifyItemRangeChanged(position + 2, mAdapter.getItemCount() + 2);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @OnClick(R.id.comment_post)
    public void onClick() {
        commentPost.setEnabled(false);
        //发送评论
        final String conts = commentEdit.getText().toString();
        MyServiceClient.getService().post_AddComment(auth, pid, conts)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        //关闭输入法
                        JUtils.closeInputMethod(BbsDetailActivity.this);
                        //添加评论并刷新
                        MyServiceClient.getService().get_BbsCommentList(auth, pid, 1, 3)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<BbsCommentList>() {
                                    @Override
                                    public void call(BbsCommentList bbsCommentList) {
                                        BbsCommentList.DataBean.ListBean listBean = null;
                                        int k;
                                        if(bbsCommentList.getData().getList().size()<3){
                                            k=bbsCommentList.getData().getList().size();
                                        }else{
                                            k=3;
                                        }
                                        for (int i = 0; i < k; i++) {
                                            boolean isMyComment = bbsCommentList.getData().getList().get(i).getConts().equals(conts);
                                            if (isMyComment) {
                                                listBean = bbsCommentList.getData().getList().get(i);
                                            }
                                        }
                                        if (listBean != null) {
                                            mList.add(0, listBean);
                                            mXRecyclerView.scrollToPosition(0);//滚动到详情页顶部
                                            mAdapter.notifyItemInserted(2);//+2是因为有一个header和下拉刷新部分
//                                            mAdapter.notifyItemRangeChanged(2, mAdapter.getItemCount() + 2);
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result result) {
                        //评论数+1
                        msgNumber = String.valueOf(Integer.parseInt(msgNumber) + 1);
                        bbsComment.setText(msgNumber);
                        //评论数改变，返回上一页
                        bundle.putString(VillageBbsActivity.COMMENT_NO_NEW, msgNumber);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        //清空评论
                        commentEdit.setText("");
                        Toast.makeText(BbsDetailActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
