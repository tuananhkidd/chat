package com.kidd.chat.presenters;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface OnRequestCompleteListener {
    void onRequestSuccess();
    void onRequestError(String message);
}
