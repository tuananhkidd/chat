package com.kidd.chat.presenters.auth.register;

import com.kidd.chat.presenters.BaseInteractor;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface RegisterInteractor extends BaseInteractor {
    void register(String email, String password, String firstName, String lastName, OnRegisterCompleteListener listener);
}
