package tech.ntam.adminapp.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 20/03/18.
 */

public class Client {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("photo")
    private String photo;
    @SerializedName("birthday")
    private String birthday;

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

    public String getBirthday() {
        birthday =  Utils.getValueFromString(birthday);
        return MyDateTimeFactor.getDateFromDateTime(birthday);
    }
}
