package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 01/01/18.
 */

public class UserData {

    @SerializedName("user_data")
    private User user;
    @SerializedName("verification_code")
    private String verificationCode;

    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }

    public String getVerificationCode() {
        return Utils.getValueFromString(verificationCode);
    }
}
