package tech.ntam.babiesandbeyond.api.api_model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tech.ntam.babiesandbeyond.model.MidwifeRequestModel;

/**
 * Created by Developer on 25/02/18.
 */

public class MidwifeRequestRequest {

    @SerializedName("midwife_id")
    private int midwifeId;
    @SerializedName("location")
    private String location;
    @SerializedName("longitude")
    private double lng;
    @SerializedName("latitude")
    private double lat;
    @SerializedName("dates")
    private List<MidwifeRequestModel> midwifeRequestModels;

    public MidwifeRequestRequest(int midwifeId, String location, double lng, double lat, List<MidwifeRequestModel> midwifeRequestModels) {
        this.midwifeId = midwifeId;
        this.location = location;
        this.lng = lng;
        this.lat = lat;
        this.midwifeRequestModels = midwifeRequestModels;
    }
}
