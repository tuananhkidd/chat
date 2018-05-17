package com.kidd.chat.views.main.friends;

import com.kidd.chat.models.User;

import java.util.List;

/**
 * Created by TranThanhTung on 19/02/2018.
 */

public interface FriendsView {
    void showRefreshingProgress();
    void hideRefreshingProgress();

    void showLoadingMoreProgress();
    void hideLoadingMoreProgress();

    void enableLoadingMore(boolean enable);
    void enableRefreshing(boolean enable);

    void refreshUsers(List<User> users);
    void addMoreUsers(List<User> users);

    void getRoomID(String id, User user);
    void addRoomID(String id, User user);

    void getStateFriends(User user);
}
