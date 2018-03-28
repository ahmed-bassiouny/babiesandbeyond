package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.model.Service;

/**
 * Created by Developer on 15/01/18.
 */

public class AddServiceResponse extends ParentResponse {
    @SerializedName(DATA_KEY)
    private Service service;

    public Service getService() {
        if(service == null)
            service = new Service();
        return service;
    }
}
