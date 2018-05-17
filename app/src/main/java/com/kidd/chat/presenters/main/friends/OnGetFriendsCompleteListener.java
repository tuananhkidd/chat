package com.kidd.chat.presenters.main.friends;


import com.kidd.chat.models.User;

import java.util.List;

public interface OnGetFriendsCompleteListener {
    void onGetFriendsSuccess(List<User> users);
    void onError(String message);
}
