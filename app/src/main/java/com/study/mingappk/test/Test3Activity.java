package com.study.mingappk.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.common.utils.MyGallerFinal;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class Test3Activity extends AppCompatActivity {
    MyGallerFinal aFinal;
    @Bind(R.id.imageView2)
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        ButterKnife.bind(this);
        aFinal = new MyGallerFinal();
    }

    @OnClick(R.id.button3)
    public void onClick() {
        GalleryFinal.init(aFinal.getCoreConfig(this));
       // int REQUEST_CODE_GALLERY = 1001;
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableEdit(true)//开启编辑功能
                .setEnableCrop(true)//开启裁剪功能
                .setCropSquare(true)//裁剪正方形
                .setForceCrop(true)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
                .build();
        GalleryFinal.openGallerySingle(11,functionConfig, mOnHanlderResultCallback);

    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
//                mPhotoList.addAll(resultList);
//                mChoosePhotoListAdapter.notifyDataSetChanged();
                PhotoInfo photoInfo = resultList.get(0);
                Log.d("mm", photoInfo.getPhotoPath());
                Glide.with(Test3Activity.this).load("file://" + photoInfo.getPhotoPath()).into(imageView2);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(Test3Activity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
}
