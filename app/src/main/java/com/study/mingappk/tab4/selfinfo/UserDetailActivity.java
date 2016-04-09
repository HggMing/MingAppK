package com.study.mingappk.tab4.selfinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.dialog.Dialog_UpdateSex;
import com.study.mingappk.common.utils.MyGallerFinal;
import com.study.mingappk.main.BackActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends BackActivity {

    @Bind(R.id.icon_head2)
    CircleImageView iconHead2;
    @Bind(R.id.get_name)
    TextView getName;
    @Bind(R.id.get_sex)
    TextView getSex;
    @Bind(R.id.get_id_card)
    TextView getIdCard;
    @Bind(R.id.get_address)
    TextView getAddress;
    @Bind(R.id.get_phone)
    TextView getPhone;
    @Bind(R.id.get_login_time)
    TextView getLoginTime;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences("config", MODE_PRIVATE);
        spEditor=sp.edit();
        initView();


//        //android6.0 获取运行时权限
//        performCodeWithPermission("App请求存储权限",new BaseActivity.PermissionCallback() {
//            @Override
//            public void hasPermission() {
//                //执行获得权限后相关代码
//            }
//            @Override
//            public void noPermission() {
//            }
//        }, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void initView() {
        //头像
        String headUrl = sp.getString("MyInfo_Head", null);
        Glide.with(this)
                .load(headUrl)
                .into(iconHead2);
        //姓名
        getName.setText(sp.getString("MyInfo_Uname", null));
        //性别
        String sex = sp.getString("MyInfo_Sex", null);
        if ("0".equals(sex)) {
            getSex.setText("男");
        } else if ("1".equals(sex)) {
            getSex.setText("女");
        } else {
            getSex.setText("未知");
        }
        //身份证号
        getIdCard.setText(sp.getString("MyInfo_Cid", null));
        //地址:"province_name":"四川省", "city_name":"遂宁市", "county_name":"蓬溪县", "town_name":"红江镇", "village_name":"永益村"
       /* String address = (APP.getInstance().getUserInfo().getProvince_name() +
                APP.getInstance().getUserInfo().getCity_name() +
                APP.getInstance().getUserInfo().getCounty_name() +
                APP.getInstance().getUserInfo().getTown_name() +
                APP.getInstance().getUserInfo().getVillage_name());*/
        String address = sp.getString("MyInfo_Address", null);
        getAddress.setText(address);
        //手机号
        getPhone.setText(sp.getString("MyInfo_Logname", null));
        // 最后登录时间
    }

    @OnClick({R.id.set_head, R.id.icon_head2, R.id.set_name, R.id.set_sex, R.id.set_id_card, R.id.set_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_head2:
                break;
            case R.id.set_head:
                updateHead();
                break;
            case R.id.set_name:
                Intent intent1 = new Intent(this, UpdateUnameActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.set_sex:
                updateSex();
                break;
            case R.id.set_id_card:
                Intent intent2 = new Intent(this, UpdateIdcardActivity.class);
                startActivityForResult(intent2, 1);
                break;
            case R.id.set_address:
                Intent intent3 = new Intent(this, UpdateAdressActivity.class);
                startActivityForResult(intent3, 1);
                break;
        }
    }
    /**
     * 修改头像
     */
    private void updateHead() {
        MyGallerFinal aFinal = new MyGallerFinal();
        GalleryFinal.init(aFinal.getCoreConfig(this));
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableEdit(true)//开启编辑功能
                .setEnableCrop(true)//开启裁剪功能
                .setCropSquare(true)//裁剪正方形
                .setForceCrop(true)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
                .build();
        GalleryFinal.openGallerySingle(1001,functionConfig, mOnHanlderResultCallback);
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                PhotoInfo photoInfo = resultList.get(0);
                Glide.with(UserDetailActivity.this).load("file://" + photoInfo.getPhotoPath()).into(iconHead2);
                Bitmap bitmap = BitmapFactory.decodeFile(photoInfo.getPhotoPath());//图片文件转为Bitmap对象
                final String newHead = (bitmapToBase64(bitmap) + ".jpg");
                String auth = APP.getInstance().getAuth();
                new MyServiceClient().getService().getCall_UpdateHead(auth, newHead).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Result result = response.body();
                            if (result != null) {
                                Toast.makeText(UserDetailActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                if (result.getErr() == 0) {
                                    //上传头像成功
                                    spEditor.putBoolean("isUpdataMyInfo",false);
                                    spEditor.commit();
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                    }
                });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(UserDetailActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * bitmap转为base64
     *
     * @param bitmap 裁剪后的头像
     * @return Base64
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                //压缩大小
                int options = 90;
                while (baos.toByteArray().length / 1024 > 100) {
                    baos.reset();// 重置baos即清空baos
                    options -= 10;// 每次都减少10
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                }

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 修改性别
     */
    private void updateSex() {
        final Dialog_UpdateSex.Builder sexDialog = new Dialog_UpdateSex.Builder(UserDetailActivity.this);
        sexDialog.setTitle("修改性别");
        sexDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSex.setText(sexDialog.sexStr); //修改本页性别显示
                //将修改post到服务器
                String sexNo;
                if (sexDialog.sexStr.equals("男")) {
                    sexNo = "0";
                } else {
                    sexNo = "1";
                }
                String auth = APP.getInstance().getAuth();
                new MyServiceClient().getService().getCall_UpdateInfo(auth, null, sexNo, null, null)
                        .enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                if (response.isSuccessful()) {
                                    Result result = response.body();
                                    if (result != null) {
                                        spEditor.putBoolean("isUpdataMyInfo",false);
                                        spEditor.commit();
                                        Toast.makeText(UserDetailActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Result> call, Throwable t) {
                            }
                        });
                dialog.dismiss();
                ;
            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 11:
                String result = data.getExtras().getString("newName");
                getName.setText(result);
                spEditor.putBoolean("isUpdataMyInfo",false);
                spEditor.commit();
                break;
            case 22:
                String result2 = data.getExtras().getString("newIdcard");
                getIdCard.setText(result2);
                spEditor.putBoolean("isUpdataMyInfo",false);
                spEditor.commit();
                break;
            case 33:
                String result3 = data.getExtras().getString("newAddress");
                getAddress.setText(result3);
                spEditor.putBoolean("isUpdataMyInfo",false);
                spEditor.commit();
                break;
            default:
                break;
        }
    }


}
