package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 02/01/18.
 */

public class UserService {

    @SerializedName("services")
    private List<Service> services;
    @SerializedName("midwife_times")
    private List<MidwifeService> midwifeServices;

    public List<Service> getServices() {
        if (services == null)
            services = new ArrayList<>();
        return services;
    }

    public List<MidwifeService> getMidwifeServices() {
        if(midwifeServices == null)
            midwifeServices = new ArrayList<>();
        return midwifeServices;
    }
}
