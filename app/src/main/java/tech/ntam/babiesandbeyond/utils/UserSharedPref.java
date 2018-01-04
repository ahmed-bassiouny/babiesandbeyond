package tech.ntam.babiesandbeyond.utils;

import android.content.Context;
import android.content.SharedPreferences;

import tech.ntam.babiesandbeyond.model.User;

/**
 * Created by bassiouny on 02/01/18.
 */

public class UserSharedPref {

    private final static String sharedPrefName = "BabiesAndBeyond";
    private final static String USER_TOKEN = "user_token";
    private final static String USER_EMAIL = "user_email";
    private final static String USER_ID = "user_id";
    private final static String NOTIFICATION_TOKEN = "notification_token";
    private final static String TOKEN_HEADER_KEY = "Bearer ";


    private static SharedPreferences sharedPref;

    private static void getSharedPref(Context context) {
        if (sharedPref == null)
            sharedPref = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
    }

    public static void setUserInfo(Context context, String userToken, String userEmail, int id) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_TOKEN, userToken);
        editor.putString(USER_EMAIL, userEmail);
        editor.putInt(USER_ID, id);
        editor.apply();
    }
    public static void setNotificationToken(Context context, String notificationToken) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NOTIFICATION_TOKEN, notificationToken);
        editor.apply();
    }

    public static String getEmail(Context context) {
        getSharedPref(context);
        return sharedPref.getString(USER_EMAIL, "");
    }

    public static String getTokenWithHeader(Context context) {
        getSharedPref(context);
        return TOKEN_HEADER_KEY + sharedPref.getString(USER_TOKEN, "");
    }

    public static int getId(Context context) {
        getSharedPref(context);
        return sharedPref.getInt(USER_ID, 0);
    }
    public static String getNotificationToken(Context context) {
        getSharedPref(context);
        return "ahmed";//sharedPref.getString(NOTIFICATION_TOKEN, " ");
    }
}
