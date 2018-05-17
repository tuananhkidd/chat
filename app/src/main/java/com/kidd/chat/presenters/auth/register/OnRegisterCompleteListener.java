package com.kidd.chat.presenters.auth.register;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface OnRegisterCompleteListener {
    void onRegisterSuccess(String email, String password);
    void onEmailExist();
    void onPasswordWeek();
    void onError(String message);
}
