package tech.ntam.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bassiouny on 19/02/18.
 */

public class SharedPref {
    public final static String sharedPrefName = "BabiesAndBeyond";
    public final static String USER_TOKEN = "user_token";
    public final static String USER_EMAIL = "user_email";
    public final static String USER_ID = "user_id";
    public final static String USER_NAME = "user_name";
    public final static String USER_PHOTO = "user_photo";
    public final static String USER_PHONE = "user_phone";
    public final static String USER_PASSWORD = "user_password";
    public final static String IS_STAFF = "is_staff";
    public final static String NOTIFICATION_TOKEN = "notification_token";
    public final static String TOKEN_HEADER_KEY = "Bearer ";
    public final static String SERVICE = "service";
    public final static String WORKSHOP = "workshop";
    public final static String MIDWIFE = "midwife";
    public final static String IS_ACTIVE = "active";
    public final static String ACTIVATION_CODE = "code";
    public final static String MIDWIFE_DETAILS = "midwife_details";
    public final static String HISTOYR_DETAILS = "history_details";

    public static SharedPreferences sharedPref;

    public static void getSharedPref(Context context) {
        if (sharedPref == null)
            sharedPref = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
    }

}
