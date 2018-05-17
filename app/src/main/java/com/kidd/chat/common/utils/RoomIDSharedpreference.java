package com.kidd.chat.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KingIT on 3/10/2018.
 */

public class RoomIDSharedpreference {
    public static final String ROOM_SHARE_PREFERENCES = "room_ids";
    public static void saveRoomID(Context context, String friend, String id){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ROOM_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(friend, id);
        editor.apply();
    }
    public static String getUserID(Context context, String friend) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ROOM_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(friend, null);
    }
}
