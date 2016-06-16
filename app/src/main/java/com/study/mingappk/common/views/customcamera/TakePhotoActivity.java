package com.study.mingappk.common.views.customcamera;


import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.study.mingappk.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TakePhotoActivity extends Activity implements CameraInterface.CamOpenOverCallback {
    @Bind(R.id.btn_take_photo)
    ImageButton takePhoto;
    @Bind(R.id.bottom_img)
    RelativeLayout bottomImg;
    @Bind(R.id.camera_surfaceview)
    CameraSurfaceView surfaceView;
    @Bind(R.id.above_photo)
    ImageView abovePhoto;
    @Bind(R.id.layout_shutter)
    RelativeLayout layoutShutter;

    public static final String TYPE = "chose_camera_type";//拍摄人脸和身份证的选择
    float previewRate = -1f;
    private String type;//face或id （；id
    public static String FACE = "face";//(调用前置摄像头）拍正面照片
    public static String ID_CARD = "id_card";//（调用后置摄像头）拍身份证正面照
    private static final int WHAT_MSG = 10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra(TYPE);

        initUI();
        Thread openThread = new Thread() {
            @Override
            public void run() {
                try {
                    CameraInterface.getInstance().doOpenCamera(TakePhotoActivity.this, type);
                } catch (Exception e) {
                    handler.sendEmptyMessage(WHAT_MSG);
                }
            }
        };
        openThread.start();

        initViewParams();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CameraInterface.getInstance().doStopCamera();
    }

    @Override
    protected void onStop() {
        CameraInterface.getInstance().doStopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        CameraInterface.getInstance().doStopCamera();
        super.onDestroy();
    }

    private void initUI() {
        if (ID_CARD.equals(type)) {
            abovePhoto.setImageResource(R.mipmap.take_photo_id_card);
        }
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_MSG:
                    Toast.makeText(TakePhotoActivity.this, "打开摄像头失败,请检查权限", Toast.LENGTH_SHORT).show();
                    TakePhotoActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };


    private void initViewParams() {
        LayoutParams params = surfaceView.getLayoutParams();
        Point p = DisplayUtil.getScreenMetrics(this);
        params.width = p.x;
        params.height = p.y;
        previewRate = DisplayUtil.getScreenRate(this); //默认全屏的比例预览
        surfaceView.setLayoutParams(params);

        //手动设置拍照ImageButton的大小为120dip×120dip,原图片大小是64×64
        LayoutParams p2 = takePhoto.getLayoutParams();
        p2.width = DisplayUtil.dip2px(this, 80);
        p2.height = DisplayUtil.dip2px(this, 80);
        takePhoto.setLayoutParams(p2);
    }

    @Override
    public void cameraHasOpened() {
        try {
            SurfaceHolder holder = surfaceView.getSurfaceHolder();
            CameraInterface.getInstance().doStartPreview(holder, previewRate);
        } catch (Exception e) {
            handler.sendEmptyMessage(WHAT_MSG);
        }
    }

    @OnClick(R.id.btn_take_photo)
    public void onClick() {
        try {
            CameraInterface.getInstance().doTakePicture(TakePhotoActivity.this);
        } catch (Exception e) {
            handler.sendEmptyMessage(WHAT_MSG);
        }
    }


}
