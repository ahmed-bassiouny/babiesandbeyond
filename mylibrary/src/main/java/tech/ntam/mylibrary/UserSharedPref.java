package tech.ntam.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bassiouny on 02/01/18.
 */

public class UserSharedPref extends SharedPref {

    // shared pref belong to user
    public static void setUserInfo(Context context, String userToken, String userEmail,
                                   int id,String name,String photo,String phone,
                                   boolean isStaff,String activationCode,String password,boolean isActive) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_TOKEN, userToken);
        editor.putString(USER_EMAIL, userEmail);
        editor.putInt(USER_ID, id);
        editor.putString(USER_NAME, name);
        editor.putString(USER_PHOTO, photo);
        editor.putString(USER_PHONE, phone);
        editor.putBoolean(IS_STAFF, isStaff);
        editor.putString(ACTIVATION_CODE,activationCode);
        editor.putBoolean(IS_ACTIVE, isActive);
        editor.putString(USER_PASSWORD, password);
        editor.apply();
    }
    // shared pref belong to nurse
    public static void setUserInfo(Context context, String userToken, String userEmail,
                                   int id,String name,String photo,String phone,
                                   boolean isStaff) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_TOKEN, userToken);
        editor.putString(USER_EMAIL, userEmail);
        editor.putInt(USER_ID, id);
        editor.putString(USER_NAME, name);
        editor.putString(USER_PHOTO, photo);
        editor.putString(USER_PHONE, phone);
        editor.putBoolean(IS_STAFF, isStaff);
        editor.apply();
    }
    public static void setUserInfo(Context context,String name,String photo,String phone) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_NAME, name);
        editor.putString(USER_PHOTO, photo);
        editor.putString(USER_PHONE, phone);
        editor.apply();
    }
    public static void setUserInfo(Context context,String name,String phone) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_NAME, name);
        editor.putString(USER_PHONE, phone);
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
    public static String getPassword(Context context) {
        getSharedPref(context);
        return sharedPref.getString(USER_PASSWORD,"");
    }
    public static void clearShared(Context context){
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_EMAIL, "");
        editor.putString(ACTIVATION_CODE, "");
        editor.putBoolean(IS_ACTIVE, false);
        editor.apply();
    }
    public static boolean isActive(Context context) {
        getSharedPref(context);
        return sharedPref.getBoolean(IS_ACTIVE,false);
    }
    public static String getActivationCode(Context context) {
        getSharedPref(context);
        return sharedPref.getString(ACTIVATION_CODE, "");
    }

    public static void setActiveCode(Context context,String activationCode) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACTIVATION_CODE,activationCode);
        editor.apply();
    }
    public static void setIsActive(Context context) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_ACTIVE,true);
        editor.apply();
    }
}
