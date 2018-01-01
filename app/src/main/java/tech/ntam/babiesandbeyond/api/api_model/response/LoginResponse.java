package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.model.User;
import tech.ntam.babiesandbeyond.model.UserData;

/**
 * Created by bassiouny on 01/01/18.
 */

public class LoginResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private UserData userData;

    public UserData getUserData() {
        if (userData == null)
            userData = new UserData();
        return userData;
    }
}
