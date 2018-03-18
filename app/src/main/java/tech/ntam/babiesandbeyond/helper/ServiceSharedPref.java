package tech.ntam.babiesandbeyond.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import tech.ntam.babiesandbeyond.model.Midwife;
import tech.ntam.babiesandbeyond.model.MidwifeService;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.UserHistory;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.mylibrary.SharedPref;

/**
 * Created by bassiouny on 19/02/18.
 */

public class ServiceSharedPref extends SharedPref {

    public static void setMyService(Context context, Service service) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(service);
        editor.putString(SERVICE, json);
        editor.apply();
    }

    public static void clearMyService(Context context) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SERVICE, "");
        editor.apply();
    }
    public static void clearUserHistory(Context context) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(HISTOYR_DETAILS, "");
        editor.apply();
    }

    public static Service getMyService(Context context) {
        getSharedPref(context);
        Gson gson = new Gson();
        String json = sharedPref.getString(SERVICE, "");
        return gson.fromJson(json, Service.class);
    }
    public static UserHistory getUserHistory(Context context) {
        getSharedPref(context);
        Gson gson = new Gson();
        String json = sharedPref.getString(HISTOYR_DETAILS, "");
        return gson.fromJson(json, UserHistory.class);
    }


    public static void setMyWorkshop(Context context, Workshop workshop) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(workshop);
        editor.putString(WORKSHOP, json);
        editor.apply();
    }
    public static void setUserHistory(Context context, UserHistory userHistory) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userHistory);
        editor.putString(HISTOYR_DETAILS, json);
        editor.apply();
    }

    public static Midwife getMidwifeDetails(Context context) {
        getSharedPref(context);
        Gson gson = new Gson();
        String json = sharedPref.getString(MIDWIFE_DETAILS, "");
        return gson.fromJson(json, Midwife.class);
    }

    public static void setMidwifeDetails(Context context, Midwife midwife) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(midwife);
        editor.putString(MIDWIFE_DETAILS, json);
        editor.apply();
    }

    public static void clearMyWorkshop(Context context) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(WORKSHOP, "");
        editor.apply();
    }

    public static Workshop getMyWorkshop(Context context) {
        getSharedPref(context);
        Gson gson = new Gson();
        String json = sharedPref.getString(WORKSHOP, "");
        return gson.fromJson(json, Workshop.class);
    }


    public static void setMyMidwife(Context context, MidwifeService midwifeService) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(midwifeService);
        editor.putString(MIDWIFE, json);
        editor.apply();
    }

    public static void clearMyMidwife(Context context) {
        getSharedPref(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(MIDWIFE, "");
        editor.apply();
    }

    public static MidwifeService getMyMidwife(Context context) {
        getSharedPref(context);
        Gson gson = new Gson();
        String json = sharedPref.getString(MIDWIFE, "");
        return gson.fromJson(json, MidwifeService.class);
    }
}
