package com.kidd.chat.presenters.chat;


import com.kidd.chat.presenters.BasePresenter;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface ChatPresenter extends BasePresenter {
    void registerOnMessageAddedListener();
    void unregisterOnMessageAddedListener();

    void validateSendingMessage(String message);
}
