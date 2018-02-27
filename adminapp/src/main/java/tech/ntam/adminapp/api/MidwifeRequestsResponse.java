package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.adminapp.model.Midwife;
import tech.ntam.adminapp.model.MidwifeService;


/**
 * Created by bassiouny on 22/02/18.
 */

public class MidwifeRequestsResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private List<MidwifeService> midwifeServices;

    public List<MidwifeService> getMidwifeServices() {
        if (midwifeServices == null)
            midwifeServices = new ArrayList<>();
        return midwifeServices;
    }
}
