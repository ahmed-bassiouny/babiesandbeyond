package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.model.User;

/**
 * Created by bassiouny on 06/01/18.
 */

public class ProfileResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private User user;

    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }
}
