package com.study.mingappk.tab2.frienddetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.FriendDetail;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.tab2.chat.ChatActivity;
import com.study.mingappk.common.base.BackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FriendDetailActivity extends BackActivity {

    public static String FRIEND_UID = "the_friend_uid";//所选择用户的UID
    public static String NEW_NAME = "the_user_new_name";//添加或修改后的用户备注名
    @Bind(R.id.icon_head)
    ImageView iconHead;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.name2)
    TextView name2;
    @Bind(R.id.fd_remarks)
    RelativeLayout fdRemarks;
    @Bind(R.id.divide_remarks)
    View divideRemarks;
    @Bind(R.id.get_address)
    TextView getAddress;
    @Bind(R.id.fd_photos_layout)
    LinearLayout fdPhotosL;
    @Bind(R.id.fd_photo0)
    ImageView fdPhoto0;
    @Bind(R.id.fd_photo1)
    ImageView fdPhoto1;
    @Bind(R.id.fd_photo2)
    ImageView fdPhoto2;
    @Bind(R.id.fd_photo3)
    ImageView fdPhoto3;
    @Bind(R.id.fd_more)
    RelativeLayout fdMore;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.rootLayout)
    LinearLayout rootLayout;
    @Bind(R.id.fd_photos)
    RelativeLayout fdPhotos;
    @Bind(R.id.line_12)
    View line12;

    private FriendDetail.DataBean.UserinfoBean userinfoBean;
    private String uid;
    private final int SET_REMARK_NAME = 11;//设置备注名
    private boolean isFriend;//用于判定是否为好友
    private boolean isMySelf;//用于判定是否为自己

    private String auth;
    private String uName;//昵称
    private String aliasName;//备注名
    private String showName;//带*手机号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_friend_detail);

        //用于判定是否为好友
        uid = getIntent().getStringExtra(FRIEND_UID);
        List<String> friendUids = Hawk.get(APP.FRIEND_LIST_UID);
        isFriend = friendUids.contains(uid);
        //判定是否为本人
        String me_uid = Hawk.get(APP.ME_UID);
        isMySelf = me_uid.equals(uid);

        getFriendDetail();
    }

    private void getFriendDetail() {
        //非好友界面设置
        if (!isFriend) {
            line12.setVisibility(View.GONE);
            divideRemarks.setVisibility(View.GONE);
            fdRemarks.setVisibility(View.GONE);
            fdPhotos.setVisibility(View.GONE);
            fdMore.setVisibility(View.GONE);
            btnSend.setText("请求添加为联系人");
        }
        //点击自己，屏蔽发消息
        if (isMySelf) {
            btnSend.setVisibility(View.GONE);
        }

        auth = Hawk.get(APP.USER_AUTH);
        Subscriber<FriendDetail> subscriber = new Subscriber<FriendDetail>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Glide.with(FriendDetailActivity.this)
                        .load(R.mipmap.defalt_user_circle)
                        .into(iconHead);
            }

            @Override
            public void onNext(FriendDetail friendDetail) {
                userinfoBean = friendDetail.getData().getUserinfo();

                //用户头像
                String headUrl = MyServiceClient.getBaseUrl() + userinfoBean.getHead();
                Glide.with(FriendDetailActivity.this)
                        .load(headUrl)
                        .bitmapTransform(new CropCircleTransformation(FriendDetailActivity.this))
                        .error(R.mipmap.defalt_user_circle)
                        .into(iconHead);
                //用户昵称
                uName = userinfoBean.getUname();//昵称
                aliasName = userinfoBean.getAlias_name();//备注名
                String iphone = userinfoBean.getPhone();
                showName = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
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
                //用户地址
                String address = userinfoBean.getProvince_name() + userinfoBean.getCity_name() + userinfoBean.getCounty_name()
                        + userinfoBean.getTown_name() + userinfoBean.getVillage_name();
                getAddress.setText(address);
                //个人相册图片
                if (isFriend) {
                    List<FriendDetail.DataBean.BbsTopPic4Bean> bbsTopPic4List = friendDetail.getData().getBbs_top_pic4();
                    List<ImageView> imageViews = new ArrayList<>();
                    imageViews.add(fdPhoto0);
                    imageViews.add(fdPhoto1);
                    imageViews.add(fdPhoto2);
                    imageViews.add(fdPhoto3);
                    if (bbsTopPic4List.isEmpty()) {
                        fdPhotosL.setVisibility(View.GONE);
                    } else {
                        for (int i = 0; i < 4; i++) {
                            Glide.with(FriendDetailActivity.this)
                                    .load(MyServiceClient.getBaseUrl() + bbsTopPic4List.get(i).getSurl_1())
                                    .centerCrop()
                                    .into(imageViews.get(i));
                        }
                    }
                }
            }
        };
        MyServiceClient.getService().get_FriendDetail(auth, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    @OnClick({R.id.fd_remarks, R.id.fd_photos, R.id.fd_more, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fd_remarks:
                Intent intent1 = new Intent(this, RemarkNameActivity.class);
                intent1.putExtra(RemarkNameActivity.OLD_ANAME, userinfoBean.getAlias_name());
                intent1.putExtra(RemarkNameActivity.UID, uid);
                startActivityForResult(intent1, SET_REMARK_NAME);
                break;
            case R.id.fd_photos:
                Intent intent2 = new Intent(this, FriendBbsActivity.class);
                intent2.putExtra(FriendBbsActivity.UID, uid);
                intent2.putExtra(FriendBbsActivity.USER_NAME, name.getText().toString());
                startActivity(intent2);
                break;
            case R.id.fd_more:
//                Toast.makeText(FriendDetailActivity.this, "更多，暂时没写", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(this, FriendMoreActivity.class);
                intent3.putExtra(FriendMoreActivity.FRIEND_UID, uid);
                startActivity(intent3);
                break;
            case R.id.btn_send:
//                Toast.makeText(FriendDetailActivity.this, "发消息", Toast.LENGTH_SHORT).show();
                if (isFriend) {
                    Intent intent4 = new Intent(this, ChatActivity.class);
                    intent4.putExtra(ChatActivity.UID, uid);
                    intent4.putExtra(ChatActivity.USER_NAME, name.getText().toString());
                    startActivity(intent4);
                } else {
//                    Toast.makeText(FriendDetailActivity.this, "请求添加好友", Toast.LENGTH_SHORT).show();
                    String phone = userinfoBean.getPhone();
                    MyServiceClient.getService()
                            .get_AddFriendRequest(auth, phone)
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
                                    Toast.makeText(FriendDetailActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SET_REMARK_NAME:
                if (resultCode == Activity.RESULT_OK) {
                    String result = data.getStringExtra(NEW_NAME);
                    if (!StringTools.isEmpty(result)) {
                        name.setText(result);
                        name2.setVisibility(View.VISIBLE);
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
                }
                break;
        }
    }
}
