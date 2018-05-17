package com.kidd.chat.services;


import com.kidd.chat.models.User;

/**
 * Created by KingIT on 3/22/2018.
 */

public class FriendStateOnlineEvent {
    private User user;

    public FriendStateOnlineEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
