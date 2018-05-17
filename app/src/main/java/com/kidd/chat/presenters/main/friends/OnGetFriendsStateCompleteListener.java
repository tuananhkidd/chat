package com.kidd.chat.presenters.main.friends;


import com.kidd.chat.models.User;

/**
 * Created by KingIT on 3/21/2018.
 */

public interface OnGetFriendsStateCompleteListener {
    void onGetFriendState(User user);
    void onError(String message);
}
