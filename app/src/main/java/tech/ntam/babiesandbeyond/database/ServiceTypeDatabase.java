package tech.ntam.babiesandbeyond.database;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.ServiceType;

/**
 * Created by bassiouny on 03/01/18.
 */

public class ServiceTypeDatabase {

    private static List<ServiceType> serviceTypes;

    public static List<ServiceType> getServiceTypes(){
        if(serviceTypes == null)
            serviceTypes = new ArrayList<>();
        return serviceTypes;
    }

    public static void setServiceTypes(List<ServiceType> serviceTypes) {
        ServiceTypeDatabase.serviceTypes = serviceTypes;
    }
}
