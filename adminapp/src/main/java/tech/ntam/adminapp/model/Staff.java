package tech.ntam.adminapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.adminapp.model.Service;

/**
 * Created by bassiouny on 04/02/18.
 */

public class Staff {

    @SerializedName("staff_request")
    private List<Service> services;

    public List<Service> getServices() {
        if (services == null)
            services = new ArrayList<>();
        return services;
    }
}
