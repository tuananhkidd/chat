package com.kidd.chat.presenters.auth.login;

/**
 * Created by TranThanhTung on 19/02/2018.
 */

public interface OnLoginCompleteListener {
    void onLoginSuccess();
    void onWrongEmailOrPassword();
    void onError(String message);
}
