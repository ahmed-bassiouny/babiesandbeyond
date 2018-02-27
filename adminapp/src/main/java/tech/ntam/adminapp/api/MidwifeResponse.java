package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.adminapp.model.Midwife;


/**
 * Created by bassiouny on 22/02/18.
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
