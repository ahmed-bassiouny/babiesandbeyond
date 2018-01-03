package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bassiouny on 01/01/18.
 */

public class UserData {

    @SerializedName("user_data")
    private User user;

    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }
}
