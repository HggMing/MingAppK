package com.study.mingappk.tmain.userlogin.facelogin;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.PhotoOperate;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.widgets.customcamera.TakePhotoActivity;
import com.study.mingappk.common.widgets.dialog.MyDialog;
import com.study.mingappk.common.widgets.gallerfinal.GalleryFinal;
import com.study.mingappk.common.widgets.gallerfinal.model.PhotoInfo;
import com.study.mingappk.model.bean.CheckPhone;
import com.study.mingappk.model.bean.Login;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.tmain.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FaceLoginActivity extends BackActivity {

    @Bind(R.id.et_phone)
    EditText etPhone;
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
    @Bind(R.id.btn_ok)
    Button btnOk;
    private PhotoInfo photoInfo;
    boolean hasFacePhoto = false;
    private final int REQUEST_PHOTO = 11800;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_login);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_face_login);

        //android6.0 获取运行时权限
        performCodeWithPermission("为正常体验软件，请进行必要的授权！", new PermissionCallback() {
            @Override
            public void hasPermission() {
                //执行获得权限后相关代码
            }

            @Override
            public void noPermission() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @OnClick({R.id.btn_face, R.id.img_faceretake, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_face:
                takePhoto();//拍正面免冠照
                break;
            case R.id.img_faceretake:
                takePhoto();//重拍正面免冠照
                break;
            case R.id.btn_ok:
                String phone = etPhone.getText().toString().trim();
                if (StringTools.isEmpty(phone)) {
                    Toast.makeText(this, "请输入认证的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!hasFacePhoto) {
                    btnOk.setClickable(true);
                    btnOk.setText("确定");
                    Toast.makeText(this, "请拍摄正面免冠照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                // btnOk.setClickable(false);
                btnOk.setText("认证中,请稍等");
                UserLogin(phone);
                break;
        }
    }

    private void takePhoto() {
        //使用系统相机拍照方案
//        MyGallerFinal aFinal = new MyGallerFinal();
//        GalleryFinal.init(aFinal.getCoreConfig(this));
//        FunctionConfig functionConfig = new FunctionConfig.Builder()
//                .build();
//        GalleryFinal.openCamera(1001, functionConfig, mOnHanlderResultCallback);
        //使用自定义相机拍照方案
        Intent intent = new Intent(this, TakePhotoActivity.class);
        intent.putExtra(TakePhotoActivity.TYPE, TakePhotoActivity.FACE);
        startActivityForResult(intent, REQUEST_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO) {
            if (resultCode == RESULT_OK) {
                btnFace.setVisibility(View.GONE);
                imgFaceretake.setVisibility(View.VISIBLE);
                photoPath = APP.FILE_PATH + "UserCache/" + TakePhotoActivity.FACE + ".jpg";
                Glide.with(FaceLoginActivity.this)
                        .load(photoPath)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imgFace);
                hasFacePhoto = true;
            }
        }
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                btnFace.setVisibility(View.GONE);
                imgFaceretake.setVisibility(View.VISIBLE);
                photoInfo = resultList.get(0);
                Glide.with(FaceLoginActivity.this)
                        .load("file://" + photoInfo.getPhotoPath())
                        .into(imgFace);
                hasFacePhoto = true;
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(FaceLoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private void UserLogin(String phone) {
        /**
         *  compid	机构id
         did	设备id
         phone	用户电话号码
         type	类型	1001:人脸验证，1002：指纹验证， 1010：综合验证
         facepic	人脸图片
         fingerpic	指纹图片
         sign	参数和机构KEY组合字符串的加密串
         */
        //1)将除图片外的参数以及机构key组成一个字符串(注意顺序)
        String other = "compid=9&did=123456&phone=" + phone + "&type=1001";
        String str = other + "&key=69939442285489888751746749876227";
        //2)使用MD5算法加密上述字符串
        String sign = BaseTools.md5(str);
        //3)最终得到参数字符串：（注意，KEY参数不带到参数列表,sign参数加入参数列表）
        String str2 = other + "&sign=" + sign;
        //4)把上述字符串做base64加密，最终得到请求:
        String paraString = Base64.encodeToString(str2.getBytes(), Base64.NO_WRAP);

        //对图片压缩处理
        File file = null;
        try {
//            file = new PhotoOperate(this).scal(photoInfo.getPhotoPath());//调用系统相机方案
            file = new PhotoOperate(this).scal(photoPath);//自定义相机方案
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file != null) {
            RequestBody data = RequestBody.create(MediaType.parse("text/plain"), paraString);
            RequestBody ficepic = RequestBody.create(MediaType.parse("image/*"), file);
            MyServiceClient.getService()
                    .post_FaceLogin(data, ficepic)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(FaceLoginActivity.this, "验证超时，建议重新拍摄清晰正面免冠照片，再次尝试登陆。", Toast.LENGTH_SHORT).show();
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
                            CheckPhone result = gson.fromJson(new String(Base64.decode(s, Base64.DEFAULT)), CheckPhone.class);
                            if (result.getErr() == 0) {
                                FaceLogin(result.getSign());
                            } else {
                                Toast.makeText(FaceLoginActivity.this, result.getErr() + "：" + result.getMsg(), Toast.LENGTH_SHORT).show();
                                btnOk.setText("确定");
                            }
                        }
                    });
        }
    }

    private void FaceLogin(String sign) {
        Toast.makeText(FaceLoginActivity.this, "人脸认证成功！", Toast.LENGTH_SHORT).show();
        MyServiceClient.getService()
                .post_FaceLogin2(sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Login>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Login login) {
                        //储存店长管理村地址
                        if (login.getShopowner().getIs_shopowner() == 1) {
                            String manager_vid = login.getShopowner().getManager_vid();
                            if (manager_vid != null && manager_vid.length() >= 12) {
                                String key_vid = manager_vid.substring(0, 12);//取出第一个店长vid
                                Login.VidInfoBean vidInfoBean = login.getVid_info().get(key_vid);
                                String vName = vidInfoBean.getProvince_name() +
                                        vidInfoBean.getCity_name() +
                                        vidInfoBean.getCounty_name() +
                                        vidInfoBean.getTown_name() +
                                        vidInfoBean.getVillage_name();//店长村详细地址
                                Hawk.chain()
                                        .put(APP.MANAGER_ADDRESS, vName)
                                        .put(APP.MANAGER_VID,key_vid)
                                        .commit();
                            }
                        }

                        Hawk.chain()
                                .put(APP.USER_AUTH, login.getAuth())//保存认证信息
                                .put(APP.ME_UID, login.getInfo().getUid())
                                .put(APP.IS_SHOP_OWNER, login.getShopowner().getIs_shopowner())
                                .put(APP.LOGIN_NAME, etPhone.getText().toString().trim())
                                .commit();
                        Intent intent = new Intent();
                        intent.setClass(FaceLoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    //**************** Android M Permission (Android 6.0权限控制代码封装)*****************************************************
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable;

    public interface PermissionCallback {
        void hasPermission();

        void noPermission();
    }

    /**
     * Android M运行时权限请求封装
     *
     * @param permissionDes 权限描述
     * @param runnable      请求权限回调
     * @param permissions   请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions) {
        if (permissions.length == 0) return;
//        this.permissionrequestCode = requestCode;
        this.permissionRunnable = runnable;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)) {
            if (permissionRunnable != null) {
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        } else {
            //permission has not been granted.
            requestPermission(permissionDes, permissionRequestCode, permissions);
        }

    }

    private boolean checkPermissionGranted(String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(String permissionDes, final int requestCode, final String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.

//            Snackbar.make(getWindow().getDecorView(), requestName,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.common_ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ActivityCompat.requestPermissions(BaseAppCompatActivity.this,
//                                    permissions,
//                                    requestCode);
//                        }
//                    })
//                    .show();
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new MyDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(FaceLoginActivity.this, permissions, requestCode);
                        }
                    })
                    .create().show();

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(FaceLoginActivity.this, permissions, requestCode);
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (permissionRunnable != null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            } else {
                Toast.makeText(FaceLoginActivity.this, "正常体验软件，请在系统设置中，为本APP授权：存储空间！", Toast.LENGTH_SHORT).show();
                if (permissionRunnable != null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    //********************** END Android M Permission ****************************************

}
