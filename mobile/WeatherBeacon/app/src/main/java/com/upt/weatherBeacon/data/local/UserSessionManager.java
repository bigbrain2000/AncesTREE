package com.upt.weatherBeacon.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class UserSessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_USER_SESSION = "user_session";

    private SharedPreferences pref;
    private Gson gson;

    public UserSessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveUserSession(UserSession userSession) {
        String userSessionJson = gson.toJson(userSession);
        pref.edit().putString(KEY_USER_SESSION, userSessionJson).apply();
    }

    public UserSession getUserSession() {
        String userSessionJson = pref.getString(KEY_USER_SESSION, null);
        if (userSessionJson != null) {
            return gson.fromJson(userSessionJson, UserSession.class);
        }
        return null;
    }
}
