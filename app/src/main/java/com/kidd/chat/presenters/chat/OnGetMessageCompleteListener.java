package com.kidd.chat.presenters.chat;




import com.kidd.chat.models.Message;

import java.util.List;

/**
 * Created by KingIT on 3/11/2018.
 */

public interface OnGetMessageCompleteListener {
    void onGetMessagesSuccess(List<Message> messages);
    void onError(String message);
}
