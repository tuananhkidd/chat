package com.kidd.chat.presenters.auth.login;


import com.kidd.chat.presenters.BasePresenter;

/**
 * Created by TranThanhTung on 19/02/2018.
 */

public interface LoginPresenter extends BasePresenter {
    void validateEmailAndPassword(String email, String password);
}
