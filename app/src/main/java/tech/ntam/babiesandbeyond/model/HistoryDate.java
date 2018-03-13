package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 13/03/18.
 */

public class HistoryDate {

    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("date")
    private String date;

    public String getFrom() {
        return Utils.getValueFromString(from);
    }

    public String getTo() {
        return Utils.getValueFromString(to);
    }

    public String getDate() {
        return Utils.getValueFromString(date);
    }
}
