package com.study.mingappk.common.receiver;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.userlogin.SplashActivity;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Deprecated
public class PushReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

//        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
        /*    case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                byte[] payload = bundle.getByteArray("payload");

                ActivityManager am = (ActivityManager) context
                        .getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
                boolean isAppRunning = false;
                String PackageName = "com.study.mingappk";
                for (ActivityManager.RunningTaskInfo info : list) {
                    if (info.topActivity.getPackageName().equals(PackageName)
                            || info.baseActivity.getPackageName().equals(PackageName)) {
                        isAppRunning = true;
                        break;
                    }
                }

                if (payload != null) {
                    if (isAppRunning) {
                        System.out.println("=c=");
                        String data = new String(payload);
                        Intent intent1 = new Intent();
                        intent1.setAction(MyMsgBroadcastReceiver.MYMSG_ACTION);
                        intent1.putExtra("data", data);
                        context.sendBroadcast(intent1);
                    } else {
                        // 创建一个NotificationManager的引用
                        String ns = context.NOTIFICATION_SERVICE;
                        NotificationManager mNotificationManager = (NotificationManager) context
                                .getSystemService(ns);

                        int icon = R.mipmap.icon; // 通知图标
                        CharSequence tickerText = "你有一条新消息"; // 状态栏显示的通知文本提示
                        long when = System.currentTimeMillis(); // 通知产生的时间，会在通知信息里显示
                        // 用上面的属性初始化 Nofification
                        Notification notification = new Notification(icon,
                                tickerText, when);
                        notification.flags = Notification.FLAG_AUTO_CANCEL;
                        //notification.defaults=Notification.DEFAULT_SOUND;//系统默认声音
                        notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ring);

                        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
                        vibrator.vibrate(500);

                        Context context1 = context.getApplicationContext();
                        CharSequence contentTitle = "我们村"; // 通知栏标题
                        CharSequence contentText = "你有一条新消息"; // 通知栏内容
                        Intent notificationIntent = new Intent(context,
                                SplashActivity.class); // 点击该通知后要跳转的Activity
                        PendingIntent contentIntent = PendingIntent.getActivity(
                                context, 0, notificationIntent, 0);
                        mNotificationManager.notify(1, notification);
                    }
                }
                break;*/

           /* case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                String me = Hawk.get(APP.ME_UID);
                MyServiceClient.getService().
                        getObservable_RegisterChat(me, 1, "yxj", cid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Result result) {
                            }
                        });
                break;*/
           /* default:
                break;*/
    }
}

