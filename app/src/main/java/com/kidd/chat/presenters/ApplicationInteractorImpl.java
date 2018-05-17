package com.kidd.chat.presenters;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kidd.chat.common.Constants;
import com.kidd.chat.models.User;


public class ApplicationInteractorImpl implements ApplicationInteractor {
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
