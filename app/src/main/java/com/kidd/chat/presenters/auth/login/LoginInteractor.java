package com.kidd.chat.presenters.auth.login;


import com.kidd.chat.presenters.BaseInteractor;

/**
 * Created by TranThanhTung on 19/02/2018.
 */

public interface LoginInteractor extends BaseInteractor {
    void login(String username, String password, OnLoginCompleteListener onLoginCompleteListener);

}
