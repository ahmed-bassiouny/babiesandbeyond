package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 06/01/18.
 */

public class History {

    @SerializedName("name")
    private String name;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("location")
    private String location;
    @SerializedName("price")
    private String price;

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getStartDate() {
        return Utils.getValueFromString(startDate).split(" ")[0];
    }

    public String getEndDate() {
        return Utils.getValueFromString(endDate).split(" ")[0];
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public String getPrice() {
        return Utils.getValueFromString(price)+"$";
    }
}
