package com.kidd.chat.presenters.splash;


import com.kidd.chat.presenters.BaseInteractor;
import com.kidd.chat.presenters.OnRequestCompleteListener;

public interface SplashInteractor extends BaseInteractor {
    void updateUserOnlineState(String userID, boolean isOnline, OnRequestCompleteListener listener);
}
