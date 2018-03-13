package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 13/03/18.
 */

public class UserHistory {

    private final static int EVENT = 0;
    private final static int WORKSHOP = 1;
    private final static int SERVICE = 2;
    private final static int MIDWIFE = 3;

    @SerializedName("type")
    private int type;
    @SerializedName("rate")
    private String rate;
    @SerializedName("name")
    private String name;
    @SerializedName("comment")
    private String comment;
    @SerializedName("location")
    private String location;
    @SerializedName("dates")
    private List<HistoryDate> historyDates;

    public int getType() {
        return type;
    }

    public String getRate() {
        return Utils.getValueFromString(rate);
    }

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getComment() {
        return Utils.getValueFromString(comment);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public List<HistoryDate> getHistoryDates() {
        if(historyDates == null)
            historyDates = new ArrayList<>();
        return historyDates;
    }
}
