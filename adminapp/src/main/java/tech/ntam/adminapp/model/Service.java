package tech.ntam.adminapp.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 05/02/18.
 */

public class Service {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("photo")
    private String photo;
    @SerializedName("notificationToken")
    private String notification_token;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("is_logged_in")
    private String isLoggedIn;
    @SerializedName("user_type_name")
    private String userTypeName;
    @SerializedName("is_reserved")
    private boolean isReserved;

    public int getId() {
        return id;
    }

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getEmail() {
        return Utils.getValueFromString(email);
    }

    public String getPhone() {
        return Utils.getValueFromString(phone);
    }

    public String getPhoto() {
        return Utils.getValueFromString(photo);
    }

    public String getNotification_token() {
        return Utils.getValueFromString(notification_token);
    }

    public String getBirthday() {
        return Utils.getValueFromString(birthday);
    }

    public boolean getIsLoggedIn() {
        if (isLoggedIn != null && isLoggedIn.equals("1"))
            return true;
        return false;
    }

    public String getUserTypeName() {
        return Utils.getValueFromString(userTypeName);
    }

    public String isReserved() {
        if (isReserved)
            return "Reserved";
        return "Not Reserved";
    }
}
