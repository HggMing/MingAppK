package com.study.mingappk.common.receiver;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.igexin.sdk.PushConsts;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.NotifyUtil;
import com.study.mingappk.model.bean.AddFriendRequest;
import com.study.mingappk.model.bean.MessageList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.ShareMsg;
import com.study.mingappk.model.database.ChatMsgModel;
import com.study.mingappk.model.database.FriendsModel;
import com.study.mingappk.model.database.InstantMsgModel;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.database.NewFriendModel;
import com.study.mingappk.model.event.InstantMsgEvent;
import com.study.mingappk.model.event.NewFriend2Event;
import com.study.mingappk.model.event.NewFriendEvent;
import com.study.mingappk.model.event.NewMsgEvent;
import com.study.mingappk.model.event.RefreshFriendList;
import com.study.mingappk.model.service.MyService;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab2.message.ChatActivity;
import com.study.mingappk.tmain.userlogin.SplashActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PushReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();
    private boolean isAppRunning;//程序是否运行
    private boolean isChatting;//是否在聊天界面
    private boolean isNewFriendActivity;//是否在新的朋友页面
    private String me_uid;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    getMessageList(context);
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                String me = Hawk.get(APP.ME_UID);
                MyServiceClient.getService().
                        getObservable_RegisterChat(me, 1, "yxj", cid)
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

                            }
                        });
                break;
            default:
                break;
        }
    }

    /**
     * 获取消息，并本地保存
     *
     * @param context
     */
    private void getMessageList(final Context context) {
        //请求消息
        MyDB.createDb(context);
        me_uid = Hawk.get(APP.ME_UID, "");
        MyServiceClient.getService()
                .get_MessageList(me_uid, "yxj", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageList messageList) {
                        //接收到新消息！！
                        List<MessageList.LBean> lBeanList = messageList.getL();
                        Collections.reverse(lBeanList);//列表反向
                        showNotify(context, lBeanList);
                    }
                });
    }

    private void showNotify(Context context, List<MessageList.LBean> lBeanList) {

        //程序运行状态
        getStatus(context);
        //消息通知
        for (MessageList.LBean lBean : lBeanList) {
            if ("21".equals(lBean.getCt())) {//添加好友请求消息
                if ("1".equals(lBean.getFrom())) {
                    String jsonString = lBean.getTxt();
                    Gson gson = new Gson();
                    AddFriendRequest addFriendRequest = gson.fromJson(jsonString, AddFriendRequest.class);

                    //好友请求保存到数据库
                    AddFriendRequest.UinfoBean uinfo = addFriendRequest.getUinfo();
                    String uicon = MyServiceClient.getBaseUrl() + uinfo.getHead();
                    NewFriendModel newFriend = new NewFriendModel(uinfo.getUid(), uicon, uinfo.getUname(), uinfo.getSex(), uinfo.getPhone(), 1);
                    MyDB.insert(newFriend);
                    if (isNewFriendActivity) {
                        EventBus.getDefault().post(new NewFriend2Event());
                    } else {
                        EventBus.getDefault().post(new NewFriendEvent());
                    }
                }
            } else {
                //获取消息保存到数据库
                ChatMsgModel chatMsg = new ChatMsgModel();
                chatMsg.setType(ChatMsgModel.ITEM_TYPE_LEFT);//接收消息
                if ("1".equals(lBean.getFrom())) {
                    chatMsg.setFrom("10001");//系统消息由"我们村客服"发来
                } else {
                    chatMsg.setFrom(lBean.getFrom());//消息来源用户id
                }
                chatMsg.setTo(me_uid);
                chatMsg.setSt(lBean.getSt());//消息时间
                chatMsg.setCt(lBean.getCt());//消息类型
                switch (lBean.getCt()) {
                    case "1":
                        chatMsg.setTxt("[图片]");
                        break;
                    case "2":
                        chatMsg.setTxt("[语音]");
                        break;
                    case "100":
                        String jsonString = lBean.getTxt();
                        Gson gson = new Gson();
                        ShareMsg shareMsg = gson.fromJson(jsonString, ShareMsg.class);

                        chatMsg.setTxt("[分享]:\""+shareMsg.getTitle()+"\"的帖子");

                        chatMsg.setShareMsg(shareMsg.getTitle(),shareMsg.getDetail(),shareMsg.getImage(),shareMsg.getLink());
                        break;
                    default:
                        chatMsg.setTxt(lBean.getTxt());//类型：文字
                        break;
                }
                chatMsg.setLink(lBean.getLink());//类型：图片or语音
                MyDB.insert(chatMsg);//保存到数据库


                String t = lBean.getTxt();
                if ("1".equals(lBean.getFrom())&t.length() > 10) {
                    if ("已经同意添加您为好友".equals(t.substring(t.length() - 10, t.length()))) {
                        //对方同意添加好友后，刷新好友列表
                        EventBus.getDefault().post(new RefreshFriendList());
                    }
                }

                if (isChatting) {
                    //在聊天界面，不显示通知
                    EventBus.getDefault().post(new NewMsgEvent(chatMsg));
                } else {
                    String uid = chatMsg.getFrom();
                    FriendsModel friend = MyDB.createDb(context).queryById(uid, FriendsModel.class);
                    int requestCode = Integer.parseInt(uid);//唯一标识通知

                    //点击通知后操作
                    Intent intent2 = new Intent();
                    if (isAppRunning) {
                        intent2.setClass(context, ChatActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent2.putExtra(ChatActivity.UID, uid);
                        intent2.putExtra(ChatActivity.USER_NAME, friend.getUname());
                    } else {
                        intent2.setClass(context, SplashActivity.class);
                    }
                    PendingIntent pIntent = PendingIntent.getActivity(context, requestCode, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

                    //构建通知
                    int largeIcon = R.mipmap.ic_launcher;
                    int smallIcon = R.mipmap.tab1_btn1;
                    String title = friend.getUname();
                    String ticker = title + ": " + chatMsg.getTxt();
                    //新消息条数，读取及更新
                    int count = friend.getCount() + 1;
                    friend.setCount(count);
                    MyDB.update(friend);
                    String content = "[" + count + "条]" + ": " + chatMsg.getTxt();
                    //实例化工具类，并且调用接口
                    NotifyUtil notify3 = new NotifyUtil(context, requestCode);
                    notify3.notify_msg(pIntent, smallIcon, largeIcon, ticker, title, content, true, true, false);

                    //保存动态并刷新
                    InstantMsgModel msgModel = new InstantMsgModel(uid, friend.getUicon(), title, chatMsg.getSt(), chatMsg.getTxt(), count);
                    MyDB.insert(msgModel);
                    EventBus.getDefault().post(new InstantMsgEvent());
                }
            }
        }
    }

    /**
     * 获取程序运行状态
     *
     * @param context
     */
    private void getStatus(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //程序是否运行
        isAppRunning = false;
        String PackageName = "com.study.mingappk";
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(PackageName) || info.baseActivity.getPackageName().equals(PackageName)) {
                isAppRunning = true;
                break;
            }
        }
        //是否在聊天界面
        isChatting = false;
        String activityName = "com.study.mingappk.tab2.message.ChatActivity";
        String runningActivityName = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        if (activityName.equals(runningActivityName)) {
            isChatting = true;
        }
        //是否在新的朋友页面
        isNewFriendActivity = false;
        String activityName2 = "com.study.mingappk.tab2.newfriend.NewFriendActivity";
        if (activityName2.equals(runningActivityName)) {
            isNewFriendActivity = true;
        }
    }
}
