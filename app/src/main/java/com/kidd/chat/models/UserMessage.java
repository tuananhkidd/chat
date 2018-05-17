package com.kidd.chat.models;

import java.io.Serializable;

/**
 * Created by KingIT on 3/10/2018.
 */

public class UserMessage implements Serializable{
    private String id;
    private Message message;

    public UserMessage() {
    }

    public UserMessage(String id, Message message) {
        this.id = id;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
