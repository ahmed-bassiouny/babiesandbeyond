package tech.ntam.babiesandbeyond.api.api_model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;

/**
 * Created by bassiouny on 25/02/18.
 */

public class MidwifeRequestRequest {

    @SerializedName("midwife_id")
    private int midwifeId;
    @SerializedName("dates")
    private List<MidwifeRequestModel> midwifeRequestModels;

    public MidwifeRequestRequest(int midwifeId, List<MidwifeRequestModel> midwifeRequestModels) {
        this.midwifeId = midwifeId;
        this.midwifeRequestModels = midwifeRequestModels;
    }
}
