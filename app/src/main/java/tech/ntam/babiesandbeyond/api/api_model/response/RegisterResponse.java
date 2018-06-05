package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.model.UserData;

/**
 * Created by Developer on 02/01/18.
 */

public class RegisterResponse extends ParentResponse {
    @SerializedName(DATA_KEY)
    private UserData userData;

    public UserData getUserData() {
        if (userData == null)
            userData = new UserData();
        return userData;
    }
}
