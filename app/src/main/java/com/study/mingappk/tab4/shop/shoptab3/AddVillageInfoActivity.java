package com.study.mingappk.tab4.shop.shoptab3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintEditText;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyGallerFinal;
import com.study.mingappk.common.utils.PhotoOperate;
import com.study.mingappk.common.utils.StringTools;
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

public class AddVillageInfoActivity extends BackActivity {
    @Bind(R.id.et_title)
    TintEditText etTitle;
    @Bind(R.id.et_content)
    TintEditText etContent;
    @Bind(R.id.img_picture)
    ImageView imgPicture;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.layout_add)
    FrameLayout layoutAdd;

    private String auth;
    public static String TYPE = "the_title_name";
    private int type;//1、荣誉室2、活动3、村委（Item不同，单独写）4、美食

    private RequestBody imgPictureBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_village_info);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra(TYPE, 0);
        switch (type) {
            case 1:
                setToolbarTitle("新增荣誉");
                break;
            case 2:
                setToolbarTitle("新增活动");
                break;
            case 4:
                setToolbarTitle("新增美食");
                break;
        }

        auth = Hawk.get(APP.USER_AUTH);
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

            String title = etTitle.getEditableText().toString();
            String content = etContent.getEditableText().toString();

            if (StringTools.isEmpty(title)) {
                Toast.makeText(this, "标题不能为空！", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (StringTools.isEmpty(content)) {
                Toast.makeText(this, "请输入详细内容！", Toast.LENGTH_SHORT).show();
                return true;
            }
            String typeS = String.valueOf(type);
            RequestBody authBody = RequestBody.create(MediaType.parse("text/plain"), auth);
            RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
            RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), content);
            RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), typeS);

            MyServiceClient.getService()
                    .post_AddVillageInfo(authBody, titleBody, contentBody, typeBody, imgPictureBody)
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
                                Toast.makeText(AddVillageInfoActivity.this, "添加失败，请检查输入信息是否正确。", Toast.LENGTH_SHORT).show();
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
            case R.id.layout_add://图书封面图片
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
                Glide.with(AddVillageInfoActivity.this)
                        .load(imagPath)
                        .into(imgPicture);
                //对图片压缩处理
                File file = null;
                try {
                    file = new PhotoOperate(AddVillageInfoActivity.this).scal(photoInfo.getPhotoPath());
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
            Toast.makeText(AddVillageInfoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
}
