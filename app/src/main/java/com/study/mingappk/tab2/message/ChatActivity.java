package com.study.mingappk.tab2.message;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.MessageList;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab2.message.keyboard.ChatAppsGridView;
import com.study.mingappk.tab2.message.keyboard.ChatKeyBoard;
import com.study.mingappk.tmain.BackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

public class ChatActivity extends BackActivity implements ChatAdapter.OnItemClickListener,FuncLayout.OnFuncKeyBoardListener {

    public static String UID = "接收消息，发送消息用户id";
    public static String USER_NAME = "显示的用户名，用于标题";
    @Bind(R.id.chat_list)
    RecyclerView mXRecyclerView;
    @Bind(R.id.ek_bar)
    ChatKeyBoard ekBar;

    private ChatAdapter mAdapter = new ChatAdapter();
    private XRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    private List<MessageList.LBean> mList = new ArrayList<>();
    private MessageList.LBean leftMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        setToolbarTitle(getIntent().getStringExtra(USER_NAME));

        initEmoticonsKeyBoardBar();//自定义聊天键盘的配置

        configXRecyclerView();//XRecyclerView配置
        getMessageList();//获取bbsList数据
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
        ekBar.getEmoticonsToolBarView().addFixedToolItemView(false, R.mipmap.icon_add, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "ADD", Toast.LENGTH_SHORT).show();
            }
        });
        ekBar.getEmoticonsToolBarView().addToolItemView(R.mipmap.icon_setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "SETTING", Toast.LENGTH_SHORT).show();
            }
        });
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
                .getObservable_MessageList(me_uid, "yxj", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MessageList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("mm", e.getMessage());
                    }

                    @Override
                    public void onNext(MessageList messageList) {
                        if (messageList != null && messageList.getErr() == 0) {
                            mList.addAll(messageList.getL());
                            mAdapter.setItem(mList);
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
    private void OnSendBtnClick(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            MessageList.LBean bean = new MessageList.LBean();
//            bean.setContent(msg);
            mAdapter.addData(bean, true, false);
            scrollToBottom();
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
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {

    }
}

