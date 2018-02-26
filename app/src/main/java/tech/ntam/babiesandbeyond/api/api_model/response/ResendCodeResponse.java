package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 26/02/18.
 */

public class ResendCodeResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private String code;

    public String getCode() {
        return Utils.getValueFromString(code);
    }
}
