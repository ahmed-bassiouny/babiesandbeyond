package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/01/18.
 */

public class Task extends Service {
    @SerializedName("is_completed")
    private int isCompleted;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_photo")
    private String userPhoto;
    @SerializedName("rate")
    private String rate;

    public boolean getIsCompleted() {
        if (isCompleted == 1)
            return true;
        return false;
    }

    public String getUserName() {
        return Utils.getValueFromString(userName);
    }

    public String getUserPhoto() {
        return Utils.getValueFromString(userPhoto);
    }

    public String getRate() {
        return Utils.getValueFromString(rate);
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
