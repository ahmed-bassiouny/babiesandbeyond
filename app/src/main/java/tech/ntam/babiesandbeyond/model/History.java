package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;

import tech.ntam.mylibrary.MyDateTimeFactor;
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
        try {
            startDate = MyDateTimeFactor.changeDateFormatFromNumberToText(startDate);
        } catch (ParseException e) {
            startDate = "";
        }
        return startDate;
    }

    public String getEndDate() {
        try {
            endDate = MyDateTimeFactor.changeDateFormatFromNumberToText(endDate);
        } catch (ParseException e) {
            endDate = "";
        }
        return endDate;
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public String getPrice() {
        return Utils.getValueFromString(price) + "$";
    }
}
