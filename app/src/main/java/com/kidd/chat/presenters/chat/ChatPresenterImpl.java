package com.kidd.chat.presenters.chat;

import android.content.Context;
import android.util.Log;

import com.kidd.chat.models.Message;
import com.kidd.chat.models.User;
import com.kidd.chat.models.UserMessage;
import com.kidd.chat.presenters.OnRequestCompleteListener;
import com.kidd.chat.views.chat.ChatView;


/**
 * Created by TranThanhTung on 20/02/2018.
 */

public class ChatPresenterImpl implements ChatPresenter {
    public static final String TAG = "ChatPresenterImpl";

    private Context context;
    private ChatView chatView;
    private ChatInteractor chatInteractor;

    private User owner;
    private User friend;

    public ChatPresenterImpl(Context context, ChatView chatView, String roomID) {
        this.context = context;
        this.chatView = chatView;
        this.chatInteractor = new ChatInteractorImpl(context, roomID);
    }

    public void setUser(User owner, User friend) {
        this.owner = owner;
        this.friend = friend;
    }

    private boolean isOwnerMessage(Message message) {
        return message.getOwner().equals(owner.getEmail());
    }

    @Override
    public void onViewDestroy() {
        context = null;
        chatInteractor.onViewDestroy();
    }

    @Override
    public void registerOnMessageAddedListener() {
        chatInteractor.registerOnMessageChangedListener(new OnMessageChangedListener() {
            @Override
            public void onMessageAdded(UserMessage message) {
                chatView.addMessage(message);
            }

            @Override
            public void onMessageModified(UserMessage message) {
                chatView.modifiedMessage(message);
            }
        });
    }

    @Override
    public void unregisterOnMessageAddedListener() {
        chatInteractor.unregisterOnMessageChangedListener();
    }

    @Override
    public void validateSendingMessage(String message) {
        if (message.isEmpty()) {
            return;
        }
        chatInteractor.sendMessage(message, new OnRequestCompleteListener() {
            @Override
            public void onRequestSuccess() {
            }
            @Override
            public void onRequestError(String message) {
                Log.i(TAG, "onRequestError: " + message);
            }
        });
    }
}
