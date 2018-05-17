package com.kidd.chat.presenters.splash;

import android.content.Context;

import com.kidd.chat.common.utils.UserAuth;
import com.kidd.chat.presenters.OnRequestCompleteListener;
import com.kidd.chat.views.splash.SplashView;


public class SplashPresenterImpl implements SplashPresenter {
    private Context context;
    private SplashView splashView;
    private SplashInteractor splashInteractor;

    public SplashPresenterImpl(Context context, SplashView splashView) {
        this.context= context;
        this.splashView = splashView;
        this.splashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void onViewDestroy() {
        context= null;
        splashInteractor.onViewDestroy();
    }

    @Override
    public void goToOnlineState() {
        splashInteractor.updateUserOnlineState(UserAuth.getUserID(context), true, new OnRequestCompleteListener() {
            @Override
            public void onRequestSuccess() {
                splashView.completeLoading();
            }

            @Override
            public void onRequestError(String message) {
                splashView.showErrorDialog();
            }
        });
    }
}
