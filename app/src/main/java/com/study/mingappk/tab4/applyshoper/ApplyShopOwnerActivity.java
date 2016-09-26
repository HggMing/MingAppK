package com.study.mingappk.tab4.applyshoper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyGallerFinal;
import com.study.mingappk.common.utils.PhotoOperate;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.views.MySpinner;
import com.study.mingappk.common.views.customcamera.TakePhotoActivity;
import com.study.mingappk.common.views.dialog.Dialog_Select_Date;
import com.study.mingappk.common.views.gallerfinal.model.PhotoInfo;
import com.study.mingappk.model.bean.ApplyInfo2;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.UploadFiles;
import com.study.mingappk.model.bean.UserInfo;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab4.selfinfo.UpdateAdressActivity;
import com.study.mingappk.tmain.baseactivity.BackActivity;
import com.study.mingappk.common.views.gallerfinal.FunctionConfig;
import com.study.mingappk.common.views.gallerfinal.GalleryFinal;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApplyShopOwnerActivity extends BackActivity {

    public static final String VILLAGE_ID = "village_id";
    public static String VILLAGE_NAME = "village_name";
    public static final String USER_INFO = "userInfo";
    UserInfo.DataEntity userInfo;
    private final int REQUEST_VILLAGE_ID = 1880;
    private final int REQUEST_PHOTO_ID_CARD = 1881;
    private final int REQUEST_PHOTO_ID_CARD2 = 1882;


    @Bind(R.id.icon_head)
    ImageView iconHead;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.apply_state)
    ImageView applyState;
    @Bind(R.id.layout1)
    LinearLayout layout1;
    @Bind(R.id.name2)
    TextView name2;
    @Bind(R.id.click_user)
    RelativeLayout clickUser;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_title)
    TextView tvVillageName;
    @Bind(R.id.img_photo0)
    ImageView imgPhoto0;
    @Bind(R.id.img_photo1)
    ImageView imgPhoto1;
    @Bind(R.id.img_photo2)
    ImageView imgPhoto2;
    @Bind(R.id.img_photo3)
    ImageView imgPhoto3;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.fd_send)
    RelativeLayout fdSend;
    @Bind(R.id.rootLayout)
    LinearLayout rootLayout;
    @Bind(R.id.my_sp_sex)
    MySpinner mySpSex;
    @Bind(R.id.my_sp_edu)
    MySpinner mySpEdu;

    private String auth;
    private RequestBody authBody;
    private String vid;
    private String edu = "初中";
    private int sex = 0;
    private int cid_img1 = 0;
    private int cid_img2 = 0;
    private int q_img;
    private String brithday;

    private String headUrl;
    private String showName;
    private String showName2;
    private String villageName;
    private File file1;
    private File file2;
    private String photoPath3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_shop_owner);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_apply_shop_owner);
        //用户信息的显示
        initData();
        //设置性别
        settingSex();
        //设置学历
        settingEducation();
    }

    private void settingEducation() {
        //将可选内容与ArrayAdapter连接起来
        final ArrayAdapter mAdapter = mySpEdu.getAdapter(this, R.array.educations);
        mySpEdu.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edu = (String) mAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void settingSex() {
        //将可选内容与ArrayAdapter连接起来
        mySpSex.getAdapter(this, R.array.sexs);
        mySpSex.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sex = 0;
                } else if (position == 1) {
                    sex = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_VILLAGE_ID://所属村的选择
                if (resultCode == RESULT_OK) {
                    vid = data.getStringExtra(VILLAGE_ID);
                    villageName = data.getStringExtra(VILLAGE_NAME);
                    tvVillageName.setText(villageName);
                    Hawk.put(APP.APPLY_INFO_VID + Hawk.get(APP.ME_UID), vid);
                }
                break;
            case REQUEST_PHOTO_ID_CARD://返回身份证正面
                if (resultCode == RESULT_OK) {
                    String photoPath1 = APP.FILE_PATH + "UserCache/" + TakePhotoActivity.ID_CARD + ".jpg";
                    Glide.with(this)
                            .load(photoPath1)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgPhoto0);
                    //对图片压缩处理
                    file1 = null;
                    try {
                        file1 = new PhotoOperate(this).scal(photoPath1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RequestBody requestBody = null;
                    if (file1 != null) {
                        requestBody = RequestBody.create(MediaType.parse("image/*"), file1);
                    }

                    MyServiceClient.getService()
                            .post_UploadImage(authBody, requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<UploadFiles>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(UploadFiles uploadFiles) {
                                    cid_img1 = Integer.parseInt(uploadFiles.getInsert_id());
                                }
                            });
                }
                break;
            case REQUEST_PHOTO_ID_CARD2://返回身份证反面
                if (resultCode == RESULT_OK) {
                    String photoPath2 = APP.FILE_PATH + "UserCache/" + TakePhotoActivity.ID_CARD2 + ".jpg";
                    Glide.with(this)
                            .load(photoPath2)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgPhoto1);
                    //对图片压缩处理
                    file2 = null;
                    try {
                        file2 = new PhotoOperate(this).scal(photoPath2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RequestBody requestBody = null;
                    if (file2 != null) {
                        requestBody = RequestBody.create(MediaType.parse("image/*"), file2);
                    }
                    MyServiceClient.getService()
                            .post_UploadImage(authBody, requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<UploadFiles>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(UploadFiles uploadFiles) {
                                    cid_img2 = Integer.parseInt(uploadFiles.getInsert_id());
                                }
                            });
                }
                break;
        }
    }


    private void initData() {
        auth = Hawk.get(APP.USER_AUTH);
        authBody = RequestBody.create(MediaType.parse("text/plain"), auth);

        userInfo = getIntent().getParcelableExtra(USER_INFO);
        //头像
        headUrl = MyServiceClient.getBaseUrl() + userInfo.getHead();
        Glide.with(this)
                .load(headUrl)
                .bitmapTransform(new CropCircleTransformation(this))
                .error(R.mipmap.defalt_user_circle)
                .into(iconHead);
        //昵称
        String uName = userInfo.getUname();
        if (StringTools.isEmpty(uName)) {
            String iphone = userInfo.getPhone();
            showName = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
        } else {
            showName = uName;
        }
        name.setText(showName);
        //申请状态
        applyState.setImageDrawable(getResources().getDrawable(R.mipmap.ic_apply_no));
        //账号
        String accountNo = userInfo.getLogname();
        showName2 = "账号：" + accountNo;
        name2.setText(showName2);
    }


    @OnClick({R.id.et_date, R.id.et_village, R.id.img_photo0, R.id.img_photo1, R.id.img_photo3, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_date:
                //选择生日
                final Dialog_Select_Date.Builder builder = new Dialog_Select_Date.Builder(this);
                builder.setTitle("请选择你的出生日期")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                brithday = builder.date;
                                tvDate.setText(brithday);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.et_village:
                //选择村
                final Intent intent = new Intent(this, UpdateAdressActivity.class);
                intent.putExtra(UpdateAdressActivity.SUBMIT_CODE, UpdateAdressActivity.JUST_FOR_VID);
                startActivityForResult(intent, REQUEST_VILLAGE_ID);
                break;
            case R.id.img_photo0:
                //身份证正面
                takeIdCardPhoto();
                break;
            case R.id.img_photo1:
                //身份证背面
                takeIdCardPhoto2();
                break;
            case R.id.img_photo3:
                takeIdCardPhotoOther();
                //其他照片
                break;
            case R.id.btn_send:
                String uname = etName.getEditableText().toString();
                String contact = etPhone.getEditableText().toString();
                String conts = "申请理由";

                if (StringTools.isEmpty(uname)) {
                    Toast.makeText(this, "请填写申请人真实姓名。", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringTools.isEmpty(brithday)) {
                    Toast.makeText(this, "请填写申请人生日。", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringTools.isEmpty(contact)) {
                    Toast.makeText(this, "请填写申请人手机号。", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringTools.isEmpty(vid)) {
                    Toast.makeText(this, "请选择要申请店长的村。", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cid_img1 == 0) {
                    Toast.makeText(this, "请添加身份证正面照片！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cid_img2 == 0) {
                    Toast.makeText(this, "请添加身份证背面照片！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //将申请人信息，储存到本地
                String sexText;
                if (sex == 0) {
                    sexText = "男";
                } else {
                    sexText = "女";
                }
                ApplyInfo2 applyInfo2 = new ApplyInfo2(headUrl, showName, showName2,
                        uname, sexText, brithday, contact, edu, villageName,vid, file1, file2, photoPath3);
                Hawk.put(APP.APPLY_INFO + Hawk.get(APP.ME_UID), applyInfo2);

                //提交申请
                MyServiceClient.getService()
                        .post_ApplyMaster(auth, vid, uname, contact, conts, sex, edu, cid_img1, cid_img2, q_img, brithday)
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
                                if (result.getErr() == 0) {
                                    //申请成功后，显示申请中界面
                                    Intent intent1 = new Intent(ApplyShopOwnerActivity.this, ShowApplyingActivity.class);
                                    intent1.putExtra(ShowApplyingActivity.STATUS_APPLY, "0");
                                    startActivity(intent1);
                                    finish();
                                }
                            }
                        });
                break;
        }
    }


    private void takeIdCardPhoto() {
        //使用自定义相机拍照方:身份证正面
        Intent intent = new Intent(this, TakePhotoActivity.class);
        intent.putExtra(TakePhotoActivity.TYPE, TakePhotoActivity.ID_CARD);
        startActivityForResult(intent, REQUEST_PHOTO_ID_CARD);
    }

    private void takeIdCardPhoto2() {
        //使用自定义相机拍照方:身份证反面
        Intent intent = new Intent(this, TakePhotoActivity.class);
        intent.putExtra(TakePhotoActivity.TYPE, TakePhotoActivity.ID_CARD2);
        startActivityForResult(intent, REQUEST_PHOTO_ID_CARD2);
    }

    private void takeIdCardPhotoOther() {
        //使用图库方式
        MyGallerFinal aFinal = new MyGallerFinal();
        GalleryFinal.init(aFinal.getCoreConfig(this));
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .build();
        GalleryFinal.openGallerySingle(1001, functionConfig, mOnHanlderResultCallback);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                PhotoInfo photoInfo = resultList.get(0);
                photoPath3 = "file://" + photoInfo.getPhotoPath();
                Glide.with(ApplyShopOwnerActivity.this)
                        .load(photoPath3)
                        .into(imgPhoto3);
                //对图片压缩处理
                File file = null;
                try {
                    file = new PhotoOperate(ApplyShopOwnerActivity.this).scal(photoInfo.getPhotoPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = null;
                if (file != null) {
                    requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                }
                MyServiceClient.getService()
                        .post_UploadImage(authBody, requestBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UploadFiles>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(UploadFiles uploadFiles) {
                                q_img = Integer.parseInt(uploadFiles.getInsert_id());
                            }
                        });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(ApplyShopOwnerActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
}
