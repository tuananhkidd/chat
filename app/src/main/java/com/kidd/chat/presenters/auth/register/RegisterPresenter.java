package com.kidd.chat.presenters.auth.register;


import com.kidd.chat.presenters.BasePresenter;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface RegisterPresenter extends BasePresenter {
    void validateRegisterForm(String firstName, String lastName, String email, String password, String confirmPassword);
}
