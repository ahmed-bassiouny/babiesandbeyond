package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bassiouny on 02/01/18.
 */

public class UserService {

    @SerializedName("services")
    private List<Service> services;

    @SerializedName("service_types")
    private List<ServiceType> serviceTypes;

    public List<ServiceType> getServiceTypes() {
        if (serviceTypes == null)
            serviceTypes = new ArrayList<>();
        return serviceTypes;
    }

    public List<Service> getServices() {
        if (services == null)
            services = new ArrayList<>();
        return services;
    }
}
