package com.kidd.chat.presenters.chat;


import com.kidd.chat.models.UserMessage;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface OnMessageChangedListener {
    void onMessageAdded(UserMessage userMessage);
    void onMessageModified(UserMessage userMessage);
}
