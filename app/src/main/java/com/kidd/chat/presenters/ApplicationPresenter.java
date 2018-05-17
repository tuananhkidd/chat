package com.kidd.chat.presenters;


public interface ApplicationPresenter extends BasePresenter {
    void changeOnlineState(boolean isOnline, OnRequestCompleteListener listener);
}
