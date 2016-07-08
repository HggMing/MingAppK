package com.study.mingappk.common.views.customcamera;

import android.graphics.Bitmap;
import android.util.Log;

import com.study.mingappk.app.APP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    private static final String TAG = "FileUtil";
    private static final String DST_FOLDER_NAME = "CameraCache/";
    private static String storagePath="";

    /**
     * @return
     */
    private static String initPath() {
        if (storagePath.isEmpty()) {
            storagePath = APP.FILE_PATH + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdirs();
            }
        }
        return storagePath;
    }

    /**
     * @param b
     */
    public static void saveBitmap(Bitmap b, String type) {
        String path = initPath();
        long dataTake = System.currentTimeMillis();
        String jpegName = path + "/" + type + ".jpg";
        Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(jpegName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            if (type.equals(TakePhotoActivity.ID_CARD)||type.equals(TakePhotoActivity.ID_CARD2)) {
                b.compress(Bitmap.CompressFormat.JPEG, 30, bos);
            } else {
                b.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            }
            bos.flush();
            bos.close();
            Log.i(TAG, "saveBitmap");
        } catch (IOException e) {
            Log.i(TAG, "saveBitmap");
            e.printStackTrace();
        }
    }
}
