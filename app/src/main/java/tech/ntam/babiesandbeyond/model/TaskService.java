package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 19/03/18.
 */

public class TaskService {

    @SerializedName("location")
    private String location;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("rate")
    private Integer rate;
    @SerializedName("comment")
    private String comment;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("phone")
    private String userPhone;
    @SerializedName("email")
    private String userEmail;
    @SerializedName("photo")
    private String userPhoto;
    @SerializedName("is_completed")
    private Boolean isCompleted;
    @SerializedName("dates")
    private List<HistoryDate> dates;
    @SerializedName("id")
    private String id;

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public String getLongitude() {
        return Utils.getValueFromString(longitude);
    }

    public String getLatitude() {
        return Utils.getValueFromString(latitude);
    }

    public int getRate() {
        if(rate == null)
            rate = 0;
        return rate;
    }

    public String getComment() {
        return Utils.getValueFromString(comment);
    }

    public String getUserName() {
        return Utils.getValueFromString(userName);
    }

    public String getUserPhone() {
        return Utils.getValueFromString(userPhone);
    }

    public String getUserEmail() {
        return Utils.getValueFromString(userEmail);
    }

    public String getUserPhoto() {
        return Utils.getValueFromString(userPhoto);
    }

    public boolean isCompleted() {
        if(isCompleted == null)
            isCompleted = false;
        return isCompleted;
    }

    public List<HistoryDate> getDates() {
        if(dates == null)
            dates = new ArrayList<>();
        return dates;
    }

    public String getId() {
        return id;
    }
}
