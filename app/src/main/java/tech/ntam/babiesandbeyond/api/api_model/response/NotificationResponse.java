package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Notification;

/**
 * Created by bassiouny on 10/01/18.
 */

public class NotificationResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        if (notifications == null)
            notifications = new ArrayList<>();
        return notifications;
    }
}
