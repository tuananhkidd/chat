package com.kidd.chat.presenters.chat;


import com.kidd.chat.presenters.BaseInteractor;
import com.kidd.chat.presenters.OnRequestCompleteListener;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface ChatInteractor extends BaseInteractor {
    void sendMessage(String message, OnRequestCompleteListener listener);
    void registerOnMessageChangedListener(OnMessageChangedListener listener);
    void unregisterOnMessageChangedListener();

  //  void getFriends(int pageIndex, int pageSize, OnGetMessageCompleteListener listener);
}
