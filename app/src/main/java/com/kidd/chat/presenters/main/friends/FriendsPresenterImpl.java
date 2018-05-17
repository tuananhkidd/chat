package com.kidd.chat.presenters.main.friends;

import android.content.Context;
import android.util.Log;


import com.kidd.chat.common.Constants;
import com.kidd.chat.models.User;
import com.kidd.chat.services.FriendStateOnlineEvent;
import com.kidd.chat.views.main.friends.FriendsView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class FriendsPresenterImpl implements FriendsPresenter {
    private static final String TAG = "FriendsPresenterImpl";

    private Context context;
    private FriendsView friendsView;
    private FriendsInteractor friendsInteractor;
    private int currentPage = 0;

    public FriendsPresenterImpl(Context context, FriendsView friendsView) {
        this.context = context;
        this.friendsView = friendsView;
        friendsInteractor = new FriendsInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        friendsInteractor.onViewDestroy();
    }

    @Override
    public void refreshFriends() {
        friendsView.showRefreshingProgress();
        friendsInteractor.getFriends(0, Constants.PAGE_SIZE,
                new OnGetFriendsCompleteListener() {
                    @Override
                    public void onGetFriendsSuccess(List<User> users) {
                        currentPage = 0;
                        if (users.size() < Constants.PAGE_SIZE) {
                            friendsView.enableLoadingMore(false);
                        } else {
                            friendsView.enableLoadingMore(true);
                        }
                        friendsView.hideRefreshingProgress();
                        friendsView.refreshUsers(users);
                    }

                    @Override
                    public void onError(String message) {
                        Log.i(TAG, "onError: " + message);
                        friendsView.hideRefreshingProgress();
                    }
                });
    }

    @Override
    public void loadMoreFriends() {
        friendsView.showLoadingMoreProgress();
        friendsInteractor.getFriends(currentPage + 1, Constants.PAGE_SIZE,
                new OnGetFriendsCompleteListener() {
                    @Override
                    public void onGetFriendsSuccess(List<User> users) {
                        currentPage++;
                        if (users.size() < Constants.PAGE_SIZE) {
                            friendsView.enableLoadingMore(false);
                        }
                        friendsView.hideLoadingMoreProgress();
                        friendsView.addMoreUsers(users);
                    }

                    @Override
                    public void onError(String message) {
                        Log.i(TAG, "onError: " + message);
                        friendsView.hideLoadingMoreProgress();
                    }
                });
    }

    @Override
    public void getRoomID(User user) {
        friendsInteractor.getRoomFriendID(user, new OnGetIDroomCompleteListener() {
            @Override
            public void onGetIDRoomSuccess(String id) {
                friendsView.getRoomID(id,user);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void addRoomID(User user) {
        friendsInteractor.addRoomFriendID(user, new OnGetIDroomCompleteListener() {
            @Override
            public void onGetIDRoomSuccess(String id) {
                friendsView.addRoomID(id, user);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void checkFriendsOnline() {
        friendsInteractor.getStateFriendOnline(new OnGetFriendsStateCompleteListener() {
            @Override
            public void onGetFriendState(User user) {
                friendsView.getStateFriends(user);
                EventBus.getDefault().post(new FriendStateOnlineEvent(user));
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
