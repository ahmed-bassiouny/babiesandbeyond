package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 10/01/18.
 */

public class Notification {
    @SerializedName("notification_id")
    private String id;
    @SerializedName("notification")
    private String notification;
    @SerializedName("notification_time")
    private String notificationTime;
    @SerializedName("price")
    private String price;
    @SerializedName("is_service")
    private String isService;


    public String getNotification() {
        return Utils.getValueFromString(notification);
    }

    public String getNotificationTime() {
        return Utils.getValueFromString(notificationTime);
    }

    public String getPrice() {
        return Utils.getValueFromString(price);
    }

    public boolean getIsService() {
        isService = Utils.getValueFromString(isService);
        if (isService.equals("1")) return true;
        return false;
    }
}
