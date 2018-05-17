package com.kidd.chat.presenters.main;

import android.content.Context;

import com.kidd.chat.common.utils.UserAuth;
import com.kidd.chat.presenters.ApplicationInteractor;
import com.kidd.chat.presenters.ApplicationInteractorImpl;
import com.kidd.chat.presenters.ApplicationPresenter;
import com.kidd.chat.presenters.OnRequestCompleteListener;


public class ApplicationPresenterImpl implements ApplicationPresenter {
    private Context context;
    private ApplicationInteractor applicationInteractor;

    public ApplicationPresenterImpl(Context context) {
        this.context= context;
        this.applicationInteractor = new ApplicationInteractorImpl();
    }

    @Override
    public void onViewDestroy() {
        context= null;
        applicationInteractor.onViewDestroy();
    }

    @Override
    public void changeOnlineState(boolean isOnline, OnRequestCompleteListener listener) {
        String userID = UserAuth.getUserID(context);
        if (userID != null) {
            applicationInteractor.updateUserOnlineState(UserAuth.getUserID(context), isOnline, listener);
        }
    }
}
