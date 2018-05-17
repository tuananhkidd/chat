package com.kidd.chat.presenters.main.friends;


import com.kidd.chat.models.User;
import com.kidd.chat.presenters.BasePresenter;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public interface FriendsPresenter extends BasePresenter {
    void refreshFriends();
    void loadMoreFriends();
    void getRoomID(User user);
    void addRoomID(User user);
    void checkFriendsOnline();
}
