package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Developer on 01/01/18.
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
