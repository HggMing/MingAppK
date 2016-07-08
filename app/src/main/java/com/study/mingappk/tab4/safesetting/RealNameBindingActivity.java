package com.study.mingappk.tab4.safesetting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.PhotoOperate;
import com.study.mingappk.common.views.customcamera.TakePhotoActivity;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RealNameBindingActivity extends BackActivity {
    @Bind(R.id.tv_notice1)
    TextView tvNotice1;
    @Bind(R.id.btn_face)
    ImageView btnFace;
    @Bind(R.id.img_face)
    ImageView imgFace;
    @Bind(R.id.takelayout_face)
    RelativeLayout takelayoutFace;
    @Bind(R.id.img_faceretake)
    ImageView imgFaceretake;
    @Bind(R.id.layout_face)
    RelativeLayout layoutFace;
    @Bind(R.id.tv_notice2)
    TextView tvNotice2;
    @Bind(R.id.btn_id_card)
    ImageView btnIdCard;
    @Bind(R.id.img_id_card)
    ImageView imgIdCard;
    @Bind(R.id.takelayout_id_card)
    RelativeLayout takelayoutIdCard;
    @Bind(R.id.img_idretake)
    ImageView imgIdretake;
    @Bind(R.id.layout_id_card)
    RelativeLayout layoutIdCard;
    @Bind(R.id.et_id_num)
    EditText etIdNum;
    @Bind(R.id.btn_ok)
    Button btnOk;

    private boolean hasFacePhoto = false;
    private boolean hasIdCardPhoto = false;
    private final int REQUEST_PHOTO_FACE = 11200;
    private final int REQUEST_PHOTO_ID_CARD = 11201;
    private String photoPath1;//人脸照片路径
    private String photoPath2;//身份证照片路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_binding);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_real_name_binging);
    }

    @OnClick({R.id.btn_face, R.id.img_faceretake, R.id.btn_id_card, R.id.img_idretake, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_face:
                takeFacePhoto();//拍正面免冠照
                break;
            case R.id.img_faceretake:
                takeFacePhoto();//重拍正面免冠照
                break;
            case R.id.btn_id_card:
                takeIdCardPhoto();//拍身份证正面照
                break;
            case R.id.img_idretake://重新拍身份证正面照
                takeIdCardPhoto();
                break;
            case R.id.btn_ok:
                if (!hasFacePhoto) {
                    btnOk.setClickable(true);
                    btnOk.setText("确定");
                    Toast.makeText(this, "请拍摄正面免冠照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!hasIdCardPhoto) {
                    btnOk.setClickable(true);
                    btnOk.setText("确定");
                    Toast.makeText(this, "请拍摄身份证正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                String idNum = etIdNum.getText().toString().trim();
                if (idNum.isEmpty() || !BaseTools.checkIdcard(idNum)) {
                    Toast.makeText(this, "请正确输入身份证号", Toast.LENGTH_SHORT).show();
                    return;
                }

                btnOk.setText("认证中,请稍等");
                realBing(idNum);
                break;
        }
    }

    private void realBing(String cardid) {
        /**
         * compid	机构id(int)
         did	设备号(string)
         phone	用户手机号码(string)
         cardid	身份证号码(string)
         sex	性别(int)	0：女，1：男
         uname	身份证姓名(string)
         brithday	生日(int)（unix时间戳）
         addr	身份证地址(string)
         pic1	身份证正面照片
         facepic	人脸照片
         fingerpic	指纹图片（可为空）
         group	是否加入照片组(int)	0：否，1：是
         sign	参数和机构KEY组合字符串的加密串
         */
        //1)、将除图片外的参数以及机构key组成一个字符串(注意顺序)
        String phone= Hawk.get(APP.LOGIN_NAME);
        int sex=1;
        String uname=URLEncoder.encode("姓名");
        int brithday=442741129;
        String addr=URLEncoder.encode("地址");
        int group=1;

        String other = "compid=9&did=123456&phone=" + phone +
                "&cardid=" + cardid + "&sex=" + sex + "&uname=" + uname +
                "&brithday=" + brithday + "&addr=" + addr + "&group=" + group;
        String str = other + "&key=69939442285489888751746749876227";

        // 2)、使用MD5算法加密上述字符串
        String sign = BaseTools.md5(str);
        //3)、最终得到参数字符串：（注意，KEY参数不带到参数列表,sign参数加入参数列表）
        String str2 = other + "&sign=" + sign;
        //4)、把上述字符串做base64加密，最终得到请求:
        String paraString = Base64.encodeToString(str2.getBytes(), Base64.NO_WRAP);

        File file_facepic = null;
        File file_pic1 = null;
        try {
            file_facepic = new PhotoOperate(this).scal(photoPath1);//人脸照片
            file_pic1 = new PhotoOperate(this).scal(photoPath2);//正面身份证照片
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file_facepic != null && file_pic1 != null) {
            final RequestBody rb_facepic = RequestBody.create(MediaType.parse("image/*"), file_facepic);
            final RequestBody rb_pic1 = RequestBody.create(MediaType.parse("image/*"), file_pic1);
            MyServiceClient.getService()
                    .post_FaceRealBinding(paraString, rb_facepic, rb_pic1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(RealNameBindingActivity.this, "验证超时，建议重新拍摄照片，再次尝试。", Toast.LENGTH_LONG).show();
                            btnOk.setText("确定");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            String s = null;
                            try {
                                s = responseBody.string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new Gson();
                            Result result = gson.fromJson(new String(Base64.decode(s, Base64.DEFAULT)), Result.class);
                            if (result.getErr() == 0) {
                                Toast.makeText(RealNameBindingActivity.this, "实名认证成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent();
                                intent.putExtra(SafeSettingActivity.IS_BINDING,true);
                                setResult(RESULT_OK,intent);
                                finish();
                            } else {
                                Toast.makeText(RealNameBindingActivity.this, result.getErr() + "：" + result.getMsg(), Toast.LENGTH_SHORT).show();
                                btnOk.setText("确定");
                            }
                        }
                    });
        }
    }

    private void takeFacePhoto() {
        //使用自定义相机拍照方案
        Intent intent = new Intent(this, TakePhotoActivity.class);
        intent.putExtra(TakePhotoActivity.TYPE, TakePhotoActivity.FACE);
        startActivityForResult(intent, REQUEST_PHOTO_FACE);
    }

    private void takeIdCardPhoto() {
        //使用自定义相机拍照方案
        Intent intent = new Intent(this, TakePhotoActivity.class);
        intent.putExtra(TakePhotoActivity.TYPE, TakePhotoActivity.ID_CARD);
        startActivityForResult(intent, REQUEST_PHOTO_ID_CARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PHOTO_FACE:
                if (resultCode == RESULT_OK) {
                    btnFace.setVisibility(View.GONE);
                    imgFaceretake.setVisibility(View.VISIBLE);
                    photoPath1 = APP.FILE_PATH + "CameraCache/" + TakePhotoActivity.FACE + ".jpg";
                    Glide.with(this)
                            .load(photoPath1)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgFace);
                    hasFacePhoto = true;
                }
                break;
            case REQUEST_PHOTO_ID_CARD:
                if (resultCode == RESULT_OK) {
                    btnIdCard.setVisibility(View.GONE);
                    imgIdretake.setVisibility(View.VISIBLE);
                    photoPath2 = APP.FILE_PATH + "CameraCache/" + TakePhotoActivity.ID_CARD + ".jpg";
                    Glide.with(this)
                            .load(photoPath2)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgIdCard);
                    hasIdCardPhoto = true;
                }
                break;
        }
    }
}
