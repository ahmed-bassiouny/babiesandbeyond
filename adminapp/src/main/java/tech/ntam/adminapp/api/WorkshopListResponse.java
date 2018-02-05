package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.adminapp.model.Workshop;

/**
 * Created by bassiouny on 05/02/18.
 */

public class WorkshopListResponse extends ParentResponse {
    @SerializedName(DATA_KEY)
    private List<Workshop> workshops;

    public List<Workshop> getWorkshops() {
        if(workshops == null)
            workshops = new ArrayList<>();
        return workshops;
    }
}
