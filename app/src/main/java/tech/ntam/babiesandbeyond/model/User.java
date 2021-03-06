package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 01/01/18.
 */

public class User {

    public static final String USER = "1";
    public static final String NURSE = "3";
    public static final String BABYSITTER = "4";
    public static final String MIDWIFE = "5";

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
    @SerializedName("notification_token")
    private String notificationToken;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("user_type_id")
    private String userTypeId;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("user_token")
    private String user_token;
    @SerializedName("is_activate")
    private String active;
    @SerializedName("verification_code")
    private String verificationCode;

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

    public String getNotificationToken() {
        return Utils.getValueFromString(notificationToken);
    }

    public String getBirthday() {
        return Utils.getValueFromString(birthday);
    }

    public String getUserTypeId() {
        return Utils.getValueFromString(userTypeId);
    }

    public String getCreated_by() {
        return Utils.getValueFromString(created_by);
    }

    public String getUser_token() {
        return Utils.getValueFromString(user_token);
    }

    public boolean getIsActive() {
        if (active == null)
            return false;
        return active.equals("1");
    }

    public String getVerificationCode() {
        return Utils.getValueFromString(verificationCode);
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
