package com.study.mingappk.model.event;

import com.study.mingappk.model.database.ChatMsgModel;

/**
 * Created by Ming on 2016/7/14.
 */
public class NewMsgEvent {
    private ChatMsgModel chatMsg;

    public NewMsgEvent(ChatMsgModel chatMsg) {
        this.chatMsg = chatMsg;
    }

    public ChatMsgModel getChatMsg() {
        return chatMsg;
    }
}
