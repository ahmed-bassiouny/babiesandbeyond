package tech.ntam.babiesandbeyond.database;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Service;

/**
 * Created by bassiouny on 03/01/18.
 */

public class ServiceDatabase {

    private static List<Service> services;

    public static List<Service> getServices() {
        if (services == null)
            services = new ArrayList<>();
        return services;
    }

    public static void setServices(List<Service> services) {
        ServiceDatabase.services = services;
    }
}
