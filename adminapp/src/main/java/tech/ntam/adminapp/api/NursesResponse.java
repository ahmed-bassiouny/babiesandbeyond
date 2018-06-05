package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.adminapp.model.Client;
import tech.ntam.adminapp.model.Service;

/**
 * Created by Developer on 20/03/18.
 */

public class NursesResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private List<Service> services;

    public List<Service> getNurses() {
        if (services == null)
            services = new ArrayList<>();
        return services;
    }
}
