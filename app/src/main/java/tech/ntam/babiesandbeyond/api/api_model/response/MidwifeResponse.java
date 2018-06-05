package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Midwife;

/**
 * Created by Developer on 22/02/18.
 */

public class MidwifeResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private List<Midwife> midwifeList;

    public List<Midwife> getMidwifeList() {
        if (midwifeList == null)
            midwifeList = new ArrayList<>();
        return midwifeList;
    }
}
