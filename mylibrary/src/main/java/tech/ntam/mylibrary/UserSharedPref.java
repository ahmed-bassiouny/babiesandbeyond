package tech.ntam.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bassiouny on 02/01/18.
 */

public class UserSharedPref {

    private final static String sharedPrefName = "BabiesAndBeyond";
    private final static String USER_TOKEN = "user_token";
    private final static String USER_EMAIL = "user_email";
    private final static String USER_ID = "user_id";
    private final static String USER_NAME = "user_name";
    private final static String USER_PHOTO = "user_photo";
    private final static String USER_PHONE = "user_phone";
    private final static String IS_STAFF = "is_staff";
    private final static String NOTIFICATION_TOKEN = "notification_token";
    private final static String TOKEN_HEADER_KEY = "Bearer ";

    private static SharedPreferences sharedPref;

    private static void getSharedPref(Context context) {
        if (sharedPref == null)
            sharedPref = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
    }

    public static void setUserInfo(Context context, String userToken, String userEmail, int id,String name,String photo,String phone,boolean isStaff) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_TOKEN, userToken);
        editor.putString(USER_EMAIL, userEmail);
        editor.putInt(USER_ID, id);
        editor.putString(USER_NAME, name);
        editor.putString(USER_PHOTO, photo);
        editor.putString(USER_PHONE, phone);
        editor.putBoolean(IS_STAFF, isStaff);
        editor.commit();
    }
    public static void setUserInfo(Context context,String name,String photo,String phone) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_NAME, name);
        editor.putString(USER_PHOTO, photo);
        editor.putString(USER_PHONE, phone);
        editor.commit();
    }
    public static void setUserInfo(Context context,String name,String phone) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_NAME, name);
        editor.putString(USER_PHONE, phone);
        editor.commit();
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
    public static String getName(Context context) {
        getSharedPref(context);
        return sharedPref.getString(USER_NAME, "");
    }
    public static String getPhoto(Context context) {
        getSharedPref(context);
        return sharedPref.getString(USER_PHOTO, "");
    }
    public static String getPhone(Context context) {
        getSharedPref(context);
        return sharedPref.getString(USER_PHONE, "");
    }
    public static String getTokenWithHeader(Context context) {
        getSharedPref(context);
        return TOKEN_HEADER_KEY + sharedPref.getString(USER_TOKEN, "");
    }

    public static int getId(Context context) {
        getSharedPref(context);
        return sharedPref.getInt(USER_ID, 0);
    }
    public static boolean isStaff(Context context) {
        getSharedPref(context);
        return sharedPref.getBoolean(IS_STAFF,false);
    }
    public static String getNotificationToken(Context context) {
        getSharedPref(context);
        return sharedPref.getString(NOTIFICATION_TOKEN,"");
    }
    public static void clearShared(Context context){
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_EMAIL, "");
        editor.commit();
    }
}