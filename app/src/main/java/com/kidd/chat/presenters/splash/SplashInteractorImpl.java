package com.kidd.chat.presenters.splash;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kidd.chat.common.Constants;
import com.kidd.chat.models.User;
import com.kidd.chat.presenters.OnRequestCompleteListener;


public class SplashInteractorImpl implements SplashInteractor {

    @Override
    public void onViewDestroy() {

    }


    @Override
    public void updateUserOnlineState(String userID, boolean isOnline, OnRequestCompleteListener listener) {
        FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
                .document(userID)
                .update(User.IS_ONLINE, isOnline)
                .addOnSuccessListener(aVoid -> {
                    if (listener != null) {
                        listener.onRequestSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        listener.onRequestError(e.getMessage());
                    }
                });
    }
}
