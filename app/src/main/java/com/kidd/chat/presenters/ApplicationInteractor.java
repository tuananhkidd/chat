package com.kidd.chat.presenters;


public interface ApplicationInteractor extends BaseInteractor {
    void updateUserOnlineState(String userID, boolean isOnline, OnRequestCompleteListener listener);
}
