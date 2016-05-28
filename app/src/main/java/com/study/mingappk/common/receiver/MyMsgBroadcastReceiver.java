package com.study.mingappk.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jude.utils.JUtils;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.MessageList;
import com.study.mingappk.model.database.ChatMsgModel;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.service.MyServiceClient;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MyMsgBroadcastReceiver extends BroadcastReceiver {
    public static final String MYMSG_ACTION = "com.study.mingappk.msg";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (MYMSG_ACTION.equals(action)) {
            MyDB.createDb(context);
            getMessageList(context);
        }
    }

    private void getMessageList(final Context context) {
        String me_uid = Hawk.get(APP.ME_UID, "");
        MyServiceClient.getService()
                .getObservable_MessageList(me_uid, "yxj", 1)
                .flatMap(new Func1<MessageList, Observable<MessageList.LBean>>() {
                    @Override
                    public Observable<MessageList.LBean> call(MessageList messageList) {
                        return Observable.from(messageList.getL());
                    }
                })
                .map(new Func1<MessageList.LBean, ChatMsgModel>() {
                    @Override
                    public ChatMsgModel call(MessageList.LBean lBean) {
                        ChatMsgModel chatMsg = new ChatMsgModel();
                        chatMsg.setType(ChatMsgModel.ITEM_TYPE_LEFT);//接收消息
                        chatMsg.setSt(lBean.getSt());//消息时间
                        chatMsg.setFrom(lBean.getFrom());//消息来源用户id
                        chatMsg.setCt(lBean.getCt());//消息类型
                        chatMsg.setTxt(lBean.getTxt());//类型：文字
                        chatMsg.setLink(lBean.getLink());//类型：图片or语音
                        MyDB.insert(chatMsg);
                        return chatMsg;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMsgModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("mm", e.getMessage());
                    }

                    @Override
                    public void onNext(ChatMsgModel chatMsgModel) {
                        if (chatMsgModel != null) {
                            JUtils.Toast("接收到新消息！！");
                            Intent intent = new Intent();
                            intent.setAction("com.isall.yxj.newmsg");
                            context.sendBroadcast(intent);
                        }
                    }
                });
    }

}

