package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 03/01/18.
 */

public class Service {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("location")
    private String location;
    @SerializedName("price")
    private String price;
    @SerializedName("service_workshop_status_id")
    private int serviceStatusId;
    @SerializedName("service_type_id")
    private int serviceTypeId;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStartDate() {
        return Utils.getValueFromString(startDate);
    }

    public String getEndDate() {
        return Utils.getValueFromString(endDate);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public String getPrice() {
        return Utils.getValueFromString(price);
    }

    public int getServiceStatusId() {
        return serviceStatusId;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }
}
