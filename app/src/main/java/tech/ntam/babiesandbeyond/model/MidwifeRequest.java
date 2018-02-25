package tech.ntam.babiesandbeyond.model;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 25/02/18.
 */

public class MidwifeRequest {
    private String timeFrom;
    private String timeTo;
    private String date;
    private String day;

    public MidwifeRequest() {
    }

    public MidwifeRequest(String timeFrom, String timeTo, String date, String day) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.date = date;
        this.day = day;
    }

    public String getTimeFrom() {
        return Utils.getValueFromString(timeFrom);
    }

    public String getTimeTo() {
        return Utils.getValueFromString(timeTo);
    }

    public String getDate() {
        return Utils.getValueFromString(date);
    }

    public String getDay() {
        return Utils.getValueFromString(day);
    }

}
