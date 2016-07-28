package com.study.mingappk.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.study.mingappk.app.APP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 对图片进行压缩的工具类
 */
public class PhotoOperate {

    private Context context;

    public PhotoOperate(Context context) {
        this.context = context;
    }

    /**
     * 创建一个以当前时间命名的临时文件
     *
     * @param context
     * @return
     */
    private File getTempFile(Context context) {
        File file = null;
        try {
            String fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
            //创建图片保存目录
            File folder = new File(APP.FILE_PATH + "IMG/");
            if (!folder.exists()) {
                boolean a = folder.mkdirs();
            }
//            file = File.createTempFile(fileName, ".jpg", context.getCacheDir());
            file = File.createTempFile(fileName, ".jpg", folder);
        } catch (IOException e) {
            errorLog(e);
        }
        return file;
    }

    public File scal(String path) throws Exception {
        File outputFile = new File(path);
        if (path.toLowerCase().endsWith(".gif")) {
            return outputFile;
        }

        long fileSize = outputFile.length();
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;

            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);

            outputFile = getTempFile(context);
            FileOutputStream fos = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
//            if(outputFile.length() / 1024 <= 200){
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            }
//            int options2 = 90;
//            while (outputFile.length() / 1024 > 200) {
//                bitmap.compress(Bitmap.CompressFormat.JPEG, options2, fos);// 这里压缩options%，把压缩后的数据存放到baos中
//                options2 -= 10;// 每次都减少10
//            }

            fos.close();
            Log.d("", "sss ok " + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }

        } else {
            File tempFile = outputFile;
            outputFile = getTempFile(context);
            copyFileUsingFileChannels(tempFile, outputFile);
        }

        return outputFile;
    }

    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e) {
            errorLog(e);
        } finally {
            if (inputChannel != null) {
                inputChannel.close();
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
        }
    }

    private static void errorLog(Exception e) {
        if (e == null) {
            return;
        }

        e.printStackTrace();
        Log.e("", "" + e);
    }

}
