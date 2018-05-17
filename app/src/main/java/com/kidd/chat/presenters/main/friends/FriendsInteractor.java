package com.kidd.chat.presenters.main.friends;


import com.kidd.chat.models.User;
import com.kidd.chat.presenters.BaseInteractor;

public interface FriendsInteractor extends BaseInteractor {
    void getStateFriendOnline(OnGetFriendsStateCompleteListener listener);
    void getFriends(int pageIndex, int pageSize, OnGetFriendsCompleteListener listener);
    void getRoomFriendID(User user, OnGetIDroomCompleteListener listener);
    void addRoomFriendID(User user, OnGetIDroomCompleteListener listener);
}
