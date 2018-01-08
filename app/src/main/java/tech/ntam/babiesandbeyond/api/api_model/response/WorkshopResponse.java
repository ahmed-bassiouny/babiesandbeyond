package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Workshop;

/**
 * Created by bassiouny on 08/01/18.
 */

public class WorkshopResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private List<Workshop> workshops;

    public List<Workshop> getWorkshops() {
        if (workshops == null)
            workshops = new ArrayList<>();
        return workshops;
    }
}
