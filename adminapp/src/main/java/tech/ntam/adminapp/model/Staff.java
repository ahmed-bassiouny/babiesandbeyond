package tech.ntam.adminapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bassiouny on 04/02/18.
 */

public class Staff {

    @SerializedName("staff_request")
    private List<Request> requests;
    @SerializedName("all_staff")
    private List<Service> services;

    public List<Request> getRequests() {
        if (requests == null)
            requests = new ArrayList<>();
        return requests;
    }

    public List<Service> getServices() {
        if(services == null)
            services = new ArrayList<>();
        return services;
    }
}
