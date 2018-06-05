package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 31/12/17.
 */

public class ParentResponse{

    public final static String DATA_KEY = "data";
    @SerializedName("status")
    private Boolean status = null;
    @SerializedName("message")
    private String message = "";

    public boolean getStatus() {
        if (status == null)
            status = false;
        return status;
    }

    public String getMessage() {
        if (message == null)
            message = "";
        return message;
    }
}
