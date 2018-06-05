package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.model.MidwifeService;

/**
 * Created by Developer on 27/02/18.
 */

public class ReserveMidwifeResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private MidwifeService midwifeService;

    public MidwifeService getMidwifeService() {
        if(midwifeService == null)
            midwifeService = new MidwifeService();
        return midwifeService;
    }
}
