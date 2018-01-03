package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.model.UserService;

/**
 * Created by bassiouny on 03/01/18.
 */

public class MyServiceResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private UserService userService;

    public UserService getUserService() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }
}
