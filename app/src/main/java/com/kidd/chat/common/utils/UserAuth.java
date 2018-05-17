package com.kidd.chat.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.kidd.chat.common.Constants;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public class UserAuth {
    public static final String USER_SHARE_PREFERENCES = "user_prefs";

    public static String getUserID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.KEY_USER_EMAIL, null);
    }
    public static void saveLoginState(Context context, String userID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_USER_EMAIL, userID);
        editor.apply();
    }

    public static void saveLogoutState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_USER_EMAIL, null);
        editor.apply();
    }
    public static boolean isUserLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }
}
