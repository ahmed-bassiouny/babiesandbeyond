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

    private static SharedPreferences sharedPref;

    private static void getSharedPref(Context context) {
        if (sharedPref == null)
            sharedPref = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
    }

    public static void setUserInfo(Context context, String userToken,String userEmail) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_TOKEN, userToken);
        editor.putString(USER_EMAIL, userEmail);
        editor.apply();
    }
}
