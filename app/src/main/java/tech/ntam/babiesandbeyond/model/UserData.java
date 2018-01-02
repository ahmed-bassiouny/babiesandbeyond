package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bassiouny on 01/01/18.
 */

public class UserData {

    @SerializedName("user_data")
    private User user;
    @SerializedName("home_page")
    private UserService userService;

    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }

    public UserService getUserService() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }
}
