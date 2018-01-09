package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bassiouny on 09/01/18.
 */

public class AboutResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private String aboutText;

    public String getAboutText() {
        return aboutText;
    }
}
