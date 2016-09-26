package com.study.mingappk.shop.shoptab3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintEditText;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyGallerFinal;
import com.study.mingappk.common.utils.PhotoOperate;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.views.MySpinner;
import com.study.mingappk.common.views.gallerfinal.FunctionConfig;
import com.study.mingappk.common.views.gallerfinal.GalleryFinal;
import com.study.mingappk.common.views.gallerfinal.model.PhotoInfo;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BackActivity;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddVillageMasterActivity extends BackActivity {
    @Bind(R.id.et_name)
    TintEditText etName;
    @Bind(R.id.my_sp_sex)
    MySpinner mySpSex;
    @Bind(R.id.et_job)
    TintEditText etJob;
    @Bind(R.id.my_sp_zzmm)
    MySpinner mySpZzmm;
    @Bind(R.id.et_phone)
    TintEditText etPhone;
    @Bind(R.id.img_picture)
    ImageView imgPicture;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.layout_add)
    FrameLayout layoutAdd;

    private String auth;
    private RequestBody imgPictureBody;
    private int sex = 0;
    private String zzmm = "普通居民";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_village_master);
        ButterKnife.bind(this);
        setToolbarTitle("新增村委信息");

        auth = Hawk.get(APP.USER_AUTH);
        //设置性别
        settingSex();
        //设置政治面貌
        settingZzmm();
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

    private void settingZzmm() {
        //将可选内容与ArrayAdapter连接起来
        final ArrayAdapter mAdapter = mySpZzmm.getAdapter(this, R.array.zzmm);
        mySpZzmm.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zzmm = (String) mAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 将菜单图标添加到toolbar
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_submit) {

            String name = etName.getEditableText().toString();
            String job = etJob.getEditableText().toString();
            String phone = etPhone.getEditableText().toString();

            if (StringTools.isEmpty(name)) {
                Toast.makeText(this, "名字不能为空！", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (StringTools.isEmpty(job)) {
                Toast.makeText(this, "职位不能为空！", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (StringTools.isEmpty(phone)) {
                Toast.makeText(this, "电话不能为空！", Toast.LENGTH_SHORT).show();
                return true;
            }
            String vid=Hawk.get(APP.MANAGER_VID);
            RequestBody vidBody = RequestBody.create(MediaType.parse("text/plain"), vid);
            RequestBody authBody = RequestBody.create(MediaType.parse("text/plain"), auth);
            RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);
            RequestBody jobBody = RequestBody.create(MediaType.parse("text/plain"), job);
            String sexS=String.valueOf(sex);
            RequestBody sexBody = RequestBody.create(MediaType.parse("text/plain"), sexS);
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody zzmmBody = RequestBody.create(MediaType.parse("text/plain"), zzmm);

            MyServiceClient.getService()
                    .post_AddVillageMaster(authBody,phoneBody,jobBody,sexBody,nameBody,vidBody,zzmmBody,imgPictureBody)
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
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(AddVillageMasterActivity.this, "添加失败，请检查输入信息是否正确。", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.layout_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_add://头像添加
                addBookPicture();
                break;
        }
    }

    private void addBookPicture() {
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
                String imagPath = "file://" + photoInfo.getPhotoPath();
                imgAdd.setVisibility(View.GONE);
                Glide.with(AddVillageMasterActivity.this)
                        .load(imagPath)
                        .into(imgPicture);
                //对图片压缩处理
                File file = null;
                try {
                    file = new PhotoOperate(AddVillageMasterActivity.this).scal(photoInfo.getPhotoPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (file != null) {
                    imgPictureBody = RequestBody.create(MediaType.parse("image/*"), file);
                }
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(AddVillageMasterActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
}
