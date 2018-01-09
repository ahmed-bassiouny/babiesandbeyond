package tech.ntam.babiesandbeyond.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bassiouny on 03/01/18.
 */

public class ServiceTypeList {

    private static List<ServiceType> serviceTypes;

    public static List<ServiceType> getServiceTypes() {
        if (serviceTypes == null)
            serviceTypes = new ArrayList<>();
        return serviceTypes;
    }

    public static void setServiceTypes(List<ServiceType> serviceTypes) {
        ServiceTypeList.serviceTypes = serviceTypes;
    }

    public static String getServiceTypeNameFromId(int id) {
        String result = "";
        for (ServiceType item : serviceTypes) {
            if (item.getId() == id)
                result = item.getName();
        }
        return result;
    }
}
