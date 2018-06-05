package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tech.ntam.adminapp.model.Service;

/**
 * Created by Developer on 13/02/18.
 */

public class AvailableStaffResponse extends ParentResponse{
    @SerializedName(DATA_KEY)
    private List<Service> services;

    public List<Service> getServices() {
        return services;
    }
}
