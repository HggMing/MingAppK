package com.study.mingappk.common.views.customcamera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;


import java.io.IOException;
import java.util.List;

public class CameraInterface {
    private static final String TAG = "mm";
    private Camera mCamera;
    private Camera.Parameters mParams;
    private boolean isPreviewing = false;
    private float mPreviwRate = -1f;
    private static CameraInterface mCameraInterface;

    private String typeString = "";
    private Context context;

    public interface CamOpenOverCallback {
        public void cameraHasOpened();
    }

    private CameraInterface() {

    }

    public static synchronized CameraInterface getInstance() {
        if (mCameraInterface == null) {
            mCameraInterface = new CameraInterface();
        }
        return mCameraInterface;
    }

    /**
     * 启动Camera
     *
     * @param callback
     * @throws Exception
     */
    @SuppressLint("NewApi")
    public void doOpenCamera(CamOpenOverCallback callback, String tyString) throws Exception {
        Log.i(TAG, "Camera open....");
        typeString = tyString;
        try {
            int count = Camera.getNumberOfCameras();
            if (count > 0 && tyString.equals(TakePhotoActivity.FACE)) {
                mCamera = Camera.open(1);
            } else {
                mCamera = Camera.open(0);
            }
            Log.i(TAG, "Camera open over....");
            callback.cameraHasOpened();
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 开始预览照片
     *
     * @param holder
     * @param previewRate
     * @throws IOException
     */
    public void doStartPreview(SurfaceHolder holder, float previewRate) throws IOException {
        Log.i(TAG, "doStartPreview...");
        if (isPreviewing) {
            mCamera.stopPreview();
            return;
        }
        if (mCamera != null) {

            mParams = mCamera.getParameters();
            mParams.setPictureFormat(PixelFormat.JPEG);// �������պ�洢��ͼƬ��ʽ
            CamParaUtil.getInstance().printSupportPictureSize(mParams);
            CamParaUtil.getInstance().printSupportPreviewSize(mParams);
            // ����PreviewSize��PictureSize
            Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(
                    mParams.getSupportedPictureSizes(), previewRate, 800);
            mParams.setPictureSize(pictureSize.width, pictureSize.height);
            Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(
                    mParams.getSupportedPreviewSizes(), previewRate, 800);
            mParams.setPreviewSize(previewSize.width, previewSize.height);

            mCamera.setDisplayOrientation(90);

            CamParaUtil.getInstance().printSupportFocusMode(mParams);
            List<String> focusModes = mParams.getSupportedFocusModes();
            if (focusModes.contains("continuous-video")) {
                mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            mCamera.setParameters(mParams);

            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();// ����Ԥ��
                //mCamera.autoFocus(null);
            } catch (IOException e) {
                throw e;

            }

            isPreviewing = true;
            mPreviwRate = previewRate;

            mParams = mCamera.getParameters();
            Log.i(TAG, "显示:PreviewSize--With = "
                    + mParams.getPreviewSize().width + "Height = "
                    + mParams.getPreviewSize().height);
            Log.i(TAG, "显示:PictureSize--With = "
                    + mParams.getPictureSize().width + "Height = "
                    + mParams.getPictureSize().height);
        }
    }

    /**
     * 关闭Camera
     */
    public void doStopCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mPreviwRate = -1f;
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 点击快门拍照
     */
    public void doTakePicture(Context contexta) {
        if (isPreviewing && (mCamera != null)) {
            mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
        }

        context = contexta;
    }

    ShutterCallback mShutterCallback = new ShutterCallback() {
        public void onShutter() {
            Log.i(TAG, "myShutterCallback:onShutter...");
        }
    };
    PictureCallback mRawCallback = new PictureCallback()
            // �����δѹ��ԭ��ݵĻص�,����Ϊnull
    {

        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(TAG, "myRawCallback:onPictureTaken...");

        }
    };
    PictureCallback mJpegPictureCallback = new PictureCallback()

    {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(TAG, "myJpegCallback:onPictureTaken...");
            Bitmap b = null;
            if (null != data) {
                //b = BitmapFactory.decodeByteArray(data, 0, data.length);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true; // 首先设置.inJustDecodeBounds为true
                b = BitmapFactory.decodeByteArray(data, 0,
                        data.length, options); // 这时获取到的bitmap是null的，尚未调用系统内存资源
                options.inJustDecodeBounds = false; // 得到图片有宽和高的options对象后，设置.inJustDecodeBounds为false。

                options.inSampleSize = 2; // 计算得到图片缩小倍数
                b = BitmapFactory.decodeByteArray(data, 0,
                        data.length, options); // 获取真正的图片对象（缩略图）


                // SimpleDateFormat format=new
                // SimpleDateFormat("yyyyMMddHHmmss");
                // String times=format.format((new Date()));
                //
                // FileOutputStream outSteam;
                // try {
                // outSteam = new
                // FileOutputStream("/sdcard/isall/"+times+"hid.jpg");
                // outSteam.write(data);
                // outSteam.close();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }

                mCamera.stopPreview();
                isPreviewing = false;
            }

            if (null != b) {

                if (typeString.equals(TakePhotoActivity.FACE)) {
                    Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, -90.0f);
                    FileUtil.saveBitmap(rotaBitmap, TakePhotoActivity.FACE);
                } else if (typeString.equals(TakePhotoActivity.ID_CARD))  {
                    // Bitmap rotaBitmap = ImageUtil.getRotateBitmap(b, 90.0f);
                    FileUtil.saveBitmap(b, TakePhotoActivity.ID_CARD);
                }else{
                    FileUtil.saveBitmap(b, TakePhotoActivity.ID_CARD2);
                }
                if (b != null && b.isRecycled()) {
                    b.recycle();
                    b = null;
                }
                doStopCamera();
                ((TakePhotoActivity) context)
                        .setResult(((TakePhotoActivity) context).RESULT_OK);
                ((TakePhotoActivity) context).finish();

            }
            // mCamera.startPreview();
            // isPreviewing = true;
        }
    };

}
