package com.study.mingappk.tab2.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.MessageList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.database.ChatMsgModel;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab2.message.keyboard.ChatAppsGridView;
import com.study.mingappk.tab2.message.keyboard.ChatKeyBoard;
import com.study.mingappk.tmain.BackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

public class ChatActivity extends BackActivity implements ChatAdapter.OnItemClickListener, FuncLayout.OnFuncKeyBoardListener {

    public static String UID = "接收消息，发送消息用户id";
    public static String USER_NAME = "显示的用户名，用于标题";
    @Bind(R.id.chat_list)
    RecyclerView mXRecyclerView;
    @Bind(R.id.ek_bar)
    ChatKeyBoard ekBar;

    private ChatAdapter mAdapter = new ChatAdapter();
    private XRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
    private List<ChatMsgModel> mList = new ArrayList<>();
    private ChatMsgModel chatMsg;
    String me;
    String other;
    public static final String MYCHAT_ACTION = "com.study.mingappk.newmsg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        setToolbarTitle(getIntent().getStringExtra(USER_NAME));

        initEmoticonsKeyBoardBar();//自定义聊天键盘的配置

        configXRecyclerView();//XRecyclerView配置
        MyDB.createDb(ChatActivity.this);
        //接收实时消息BroadcastReciver
        NewMsgBroadcastReceiver msReciver = new NewMsgBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MYCHAT_ACTION);
        this.registerReceiver(msReciver, intentFilter);
        initDatas();//初始化数据
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyDB.liteOrm.close();
    }

    private void initDatas() {
        me = Hawk.get(APP.ME_UID);
        other = getIntent().getStringExtra(UID);
        QueryBuilder<ChatMsgModel> qb = new QueryBuilder<>(ChatMsgModel.class)
                .whereEquals("_from", other)
                .whereAppendAnd()
                .whereEquals("_to", me)
                .whereAppendAnd()
                .whereEquals("_type", 1)
                .whereAppendOr()
                .whereEquals("_from", me)
                .whereAppendAnd()
                .whereEquals("_to", other)
                .whereAppendAnd()
                .whereEquals("_type", 0);

        List<ChatMsgModel> chatMsgModels = MyDB.liteOrm.query(qb);
        mList.addAll(chatMsgModels);
        mAdapter.addData(mList);
    }

    //配置表情键盘
    private void initEmoticonsKeyBoardBar() {
//        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
//        ekBar.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));
        ekBar.addOnFuncKeyBoardListener(this);
        ekBar.addFuncView(new ChatAppsGridView(this));

        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scrollToBottom();
            }
        });
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(ekBar.getEtChat().getText().toString());
                ekBar.getEtChat().setText("");
            }
        });
        /*ekBar.getEmoticonsToolBarView().addFixedToolItemView(false, R.mipmap.icon_add, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "添加表情库", Toast.LENGTH_SHORT).show();
            }
        });*/
       /* ekBar.getEmoticonsToolBarView().addToolItemView(R.mipmap.icon_setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "表情库设置", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        //设置adapter
        mAdapter.setOnItemClickListener(ChatActivity.this);
        mXRecyclerView.setAdapter(mAdapter);
        //设置布局管理器
        mXRecyclerView.setLayoutManager(mLayoutManager);
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    private void getMessageList() {
        String me_uid = Hawk.get(APP.ME_UID, "");
        MyServiceClient.getService()
                .get_MessageList(me_uid, "yxj", 1)
                .flatMap(new Func1<MessageList, Observable<MessageList.LBean>>() {
                    @Override
                    public Observable<MessageList.LBean> call(MessageList messageList) {
                        return Observable.from(messageList.getL());
                    }
                })
                .map(new Func1<MessageList.LBean, ChatMsgModel>() {
                    @Override
                    public ChatMsgModel call(MessageList.LBean lBean) {
                        chatMsg = new ChatMsgModel();
                        chatMsg.setType(ChatMsgModel.ITEM_TYPE_LEFT);//接收消息
                        chatMsg.setSt(lBean.getSt());//消息时间
                        chatMsg.setFrom(lBean.getFrom());//消息来源用户id
                        chatMsg.setCt(lBean.getCt());//消息类型
                        chatMsg.setTxt(lBean.getTxt());//类型：文字
                        chatMsg.setLink(lBean.getLink());//类型：图片or语音
                        return chatMsg;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMsgModel>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.addData(mList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("mm", e.getMessage());
                    }

                    @Override
                    public void onNext(ChatMsgModel chatMsgModel) {
                        if (chatMsgModel != null) {
                            mList.add(chatMsgModel);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    /**
     * 发送文字消息
     *
     * @param msg 消息内容
     */
    private void OnSendBtnClick(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            chatMsg = new ChatMsgModel();
            chatMsg.setType(ChatMsgModel.ITEM_TYPE_RIGHT);//发送消息
            chatMsg.setFrom(me);
            chatMsg.setTo(other);
            chatMsg.setSt(String.valueOf(System.currentTimeMillis()));//发送消息时间：当前时间
            chatMsg.setCt("0");//消息类型：文字
            chatMsg.setTxt(msg);//消息内容
            MyServiceClient.getService()
                    .post_sendMessage(me, other, "0", "yxj", msg, null, null, 1, "2")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            chatMsg.setIsShowPro(0);
                            mAdapter.addData(chatMsg, true, false);
                            MyDB.insert(chatMsg);
                        }

                        @Override
                        public void onError(Throwable e) {
                            chatMsg.setIsShowPro(2);
                            mAdapter.addData(chatMsg, true, false);
                            MyDB.insert(chatMsg);
                        }

                        @Override
                        public void onNext(Result result) {
                        }
                    });
        }
    }

    private void OnSendImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            OnSendBtnClick("[img]" + image);
        }
    }

    private void scrollToBottom() {
        mXRecyclerView.requestLayout();
        mXRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mXRecyclerView.scrollToPosition(mXRecyclerView.getBottom());
            }
        });
    }

    @Override
    public void OnFuncPop(int i) {
    }

    @Override
    public void OnFuncClose() {

    }

    class NewMsgBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MYCHAT_ACTION.equals(intent.getAction())){
                initDatas();
            }
        }
    }
}

