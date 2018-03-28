package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.Workshop;

/**
 * Created by Developer on 15/01/18.
 */

public class AddWorkshopResponse extends ParentResponse {
    @SerializedName(DATA_KEY)
    private Workshop workshop;

    public Workshop getWorkshop() {
        if(workshop == null)
            workshop = new Workshop();
        return workshop;
    }
}
