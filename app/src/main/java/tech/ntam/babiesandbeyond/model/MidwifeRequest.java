package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 25/02/18.
 */

public class MidwifeRequest implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.timeFrom);
        dest.writeString(this.timeTo);
        dest.writeString(this.date);
        dest.writeString(this.day);
    }

    protected MidwifeRequest(Parcel in) {
        this.timeFrom = in.readString();
        this.timeTo = in.readString();
        this.date = in.readString();
        this.day = in.readString();
    }

    public static final Parcelable.Creator<MidwifeRequest> CREATOR = new Parcelable.Creator<MidwifeRequest>() {
        @Override
        public MidwifeRequest createFromParcel(Parcel source) {
            return new MidwifeRequest(source);
        }

        @Override
        public MidwifeRequest[] newArray(int size) {
            return new MidwifeRequest[size];
        }
    };
}
