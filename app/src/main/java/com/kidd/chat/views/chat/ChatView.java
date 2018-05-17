package com.kidd.chat.views.chat;


import com.kidd.chat.models.Message;
import com.kidd.chat.models.PageList;
import com.kidd.chat.models.UserMessage;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface ChatView {
    void addMessage(UserMessage userMessag);
    void modifiedMessage(UserMessage userMessage);
    void onMessageSeen();


    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void refreshMessages(PageList<Message> messagePageList);
}
