package com.study.mingappk.tmain.update;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.widgets.dialog.Dialog_UpdateApp;
import com.study.mingappk.common.widgets.dialog.MyDialog;
import com.study.mingappk.model.bean.UpdateAppBack;
import com.study.mingappk.tab4.scommon.AboutActivity;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ming on 2016/11/16.
 */

public class UpdateApp {

    private static String showMsg;

    public interface DoOnActivity {
        void notNeedUpdate();
    }

    /**
     * versionCode增加，强制升级
     *
     * @param context A
     */
    public static void updateAuto(final Context context, final DoOnActivity mDoOnActivity) {

        String id = "57f9d66a959d690397000963";
        String id2 = "6ca6a446496800dabbe4c95f4f9cc4d1";

        MyServiceClient.getService()
                .get_UpdateApp(id, id2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UpdateAppBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mDoOnActivity.notNeedUpdate();
                    }

                    @Override
                    public void onNext(UpdateAppBack updateAppBack) {
                        String changelog = updateAppBack.getChangelog();//更新日志
                        Hawk.put(AboutActivity.KEY_CHANGE_LOG,changelog);//储存更新信息，在about页面展示
                        final String updateUrl = updateAppBack.getInstallUrl();
                        //服务器版本信息
                        int versionOnline = Integer.parseInt(updateAppBack.getVersion());
                        String versionNameOnline = updateAppBack.getVersionShort();
                        //本地版本信息
                        int versionCode = getVersionCode(context);
                        String versionName = getVersionName(context);

                        String fileSize = convertFileSize(updateAppBack.getBinary().getFsize());
                        showMsg = "版本" + "v" + versionName + "(" + versionCode + ")" + "→" + "v" + versionNameOnline + "(" + versionOnline + ")\n"
                                + "大小：" + fileSize;
                        String showMsg2 = showMsg + "\n" + changelog;
                        if (versionCode < versionOnline) {
                            toUpdateAuto(showMsg2, updateUrl, context);
                        } else {
                            mDoOnActivity.notNeedUpdate();
                        }
                    }
                });
    }

    /**
     * 手动检测更新，可检测到versionName变大的版本
     *
     * @param context A
     */
    public static void updateCheck(final Context context) {

        String id = "57f9d66a959d690397000963";
        String id2 = "6ca6a446496800dabbe4c95f4f9cc4d1";

        MyServiceClient.getService()
                .get_UpdateApp(id, id2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UpdateAppBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpdateAppBack updateAppBack) {
                        String changelog = updateAppBack.getChangelog();//更新日志
                        Hawk.put(AboutActivity.KEY_CHANGE_LOG,changelog);//储存更新信息，在about页面展示
                        final String updataUrl = updateAppBack.getInstall_url();
                        //服务器版本信息
                        int versionOnline = Integer.parseInt(updateAppBack.getVersion());
                        String versionNameOnline = updateAppBack.getVersionShort();
                        //本地版本信息
                        int versionCode = getVersionCode(context);
                        String versionName = getVersionName(context);

                        String fileSize = convertFileSize(updateAppBack.getBinary().getFsize());
                        String showMsg = "版本" + "v" + versionName + "(" + versionCode + ")" + "→" + "v" + versionNameOnline + "(" + versionOnline + ")\n"
                                + "大小：" + fileSize + "\n" + changelog;
                        try {
                            int compareVersion = compareVersion(versionName, versionNameOnline);
                            if (versionCode < versionOnline || compareVersion < 0) {//低于服务器版本
                                toUpdate(showMsg, updataUrl, context);
                            } else {
                                showNoUpdate(context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 没有版本更新版本
     *
     * @param context A
     */
    private static void showNoUpdate(Context context) {
        MyDialog.Builder builder = new MyDialog.Builder(context);
        builder.setTitle("提示")
                .setCannel(false)
                .setMessage("当前版本已是最新版本！")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .create().show();
    }

    /**
     * 去执行升级
     *
     * @param changelog 版本升级log信息
     * @param updataUrl 升级app下载地址
     * @param context   A
     */
    private static void toUpdateAuto(String changelog, final String updataUrl, final Context context) {
        MyDialog.Builder builder = new MyDialog.Builder(context);
        builder.setTitle("我们村版本升级")
                .setCannel(false)
                .setMessage(changelog)
                .setPositiveButton("升级",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                //由于fir.im下载重定向，先要获取真正的url
                                MyServiceClient.getService()
                                        .get_UpdateAppUrl(updataUrl)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<Response<Void>>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                            }

                                            @Override
                                            public void onNext(Response<Void> voidResponse) {
                                                String url = voidResponse.raw().request().url().toString();
                                                showUpdateAuto(context, url);
                                                dialog.dismiss();
                                            }
                                        });
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /**
     * 显示下载过程对话框
     *
     * @param context   context
     * @param updateUrl 下载地址
     */
    private static void showUpdateAuto(final Context context, String updateUrl) {
        final Dialog_UpdateApp.Builder builder = new Dialog_UpdateApp.Builder(context);
        builder.setTitle("我们村版本升级")
                .setCannel(false)
                .setMessage(showMsg)
                .setUrl(updateUrl)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.unsubscrbe();//取消下载
                        ((Activity) context).finish();
                        dialog.dismiss();
                    }
                }).create().show();
    }


    /**
     * 去执行升级
     *
     * @param changelog 版本升级log信息
     * @param updataUrl 升级app下载地址
     * @param context   A
     */
    private static void toUpdate(String changelog, final String updataUrl, final Context context) {
        MyDialog.Builder builder = new MyDialog.Builder(context);
        builder.setTitle("我们村版本升级")
                .setMessage(changelog)
                .setPositiveButton("升级",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(context, UpdateService.class);
                                intent.putExtra(UpdateService.INTENT_DOWNLOAD_URL, updataUrl);
                                intent.putExtra(UpdateService.INTENT_SAVE_NAME, "slove.apk");
                                context.startService(intent);
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param version1 本地版本
     * @param version2 服务器版本
     * @return int
     */
    private static int compareVersion(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("compareVersion error:illegal params.");
        }
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用.；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    //版本名
    private static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    private static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 文件大小，转换为好阅读方式
     *
     * @param size
     * @return
     */
    private static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
}
