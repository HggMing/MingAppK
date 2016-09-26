package com.study.mingappk.shop.shoptab1.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.study.mingappk.common.views.gallerfinal.FunctionConfig;
import com.study.mingappk.common.views.gallerfinal.GalleryFinal;
import com.study.mingappk.common.views.gallerfinal.model.PhotoInfo;
import com.study.mingappk.common.views.scanner.MyScannerActivity;
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

public class AddBookActivity extends BackActivity {

    @Bind(R.id.et_num)
    TintEditText etNum;
    @Bind(R.id.et_name)
    TintEditText etName;
    @Bind(R.id.img_book)
    ImageView imgBook;
    @Bind(R.id.img_add)
    ImageView imgAdd;

    private final int REQUEST_CODE = 55423;
    private String auth;
    private RequestBody imgBookBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_add_book);

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

            String number = etNum.getEditableText().toString();
            String name = etName.getEditableText().toString();

            if (StringTools.isEmpty(number)) {
                Toast.makeText(this, "图书书号不能为空，可扫码快速添加！", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (StringTools.isEmpty(name)) {
                Toast.makeText(this, "请输入图书书名！", Toast.LENGTH_SHORT).show();
                return true;
            }

            String vid = Hawk.get(APP.MANAGER_VID);
            RequestBody authBody = RequestBody.create(MediaType.parse("text/plain"), auth);
            RequestBody vidBody = RequestBody.create(MediaType.parse("text/plain"), vid);
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody numberBody = RequestBody.create(MediaType.parse("text/plain"), number);

            MyServiceClient.getService()
                    .post_AddBook(authBody, vidBody, nameBody, numberBody, imgBookBody)
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
                                Toast.makeText(AddBookActivity.this, "添加失败，请检查输入信息是否正确。", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.scan, R.id.add_book})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan://扫描书号
                Intent intent = new Intent(this, MyScannerActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.add_book://图书封面图片
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
                Glide.with(AddBookActivity.this)
                        .load(imagPath)
                        .into(imgBook);
                //对图片压缩处理
                File file = null;
                try {
                    file = new PhotoOperate(AddBookActivity.this).scal(photoInfo.getPhotoPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (file != null) {
                    imgBookBody = RequestBody.create(MediaType.parse("image/*"), file);
                }
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(AddBookActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String numberScan = data.getStringExtra(MyScannerActivity.SCAN_RESULT);
                etNum.setText(numberScan);
            }
        }
    }
}
