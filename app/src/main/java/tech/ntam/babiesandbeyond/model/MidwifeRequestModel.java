package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 25/02/18.
 */

public class MidwifeRequestModel implements Parcelable {
    @SerializedName("from")
    private String timeFrom;
    @SerializedName("to")
    private String timeTo;
    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private String day;

    public MidwifeRequestModel() {
    }

    public MidwifeRequestModel(String timeFrom, String timeTo, String date, String day) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.date = date;
        this.day = day;
    }

    public Date getDateTimeFrom(){
        // convert string date time to date                             // add second
        return MyDateTimeFactor.convertStringToDate(getDate()+" "+getTimeFrom24H()+":00");
    }
    public Date getDateTimeTo(){
        // convert string date time to date                             // add second
        return MyDateTimeFactor.convertStringToDate(getDate()+" "+getTimeTo24H()+":00");
    }

    public String getTimeFrom24H() {
        return Utils.getValueFromString(timeFrom);
    }

    public int getHourFrom() {
        timeFrom =  Utils.getValueFromString(timeFrom);
        return Integer.parseInt(timeFrom.split(":")[0]);
    }
    public int getHourTo() {
        timeTo =  Utils.getValueFromString(timeTo);
        return Integer.parseInt(timeTo.split(":")[0]);
    }

    public String getTimeFrom12H() {
        timeFrom = Utils.getValueFromString(timeFrom);
        return MyDateTimeFactor.convertTimeFrom24To12WithoutSecond(timeFrom);
    }
    public String getTimeTo12H() {
        timeTo = Utils.getValueFromString(timeTo);
        return MyDateTimeFactor.convertTimeFrom24To12WithoutSecond(timeTo);
    }

    public String getTimeTo24H() {
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

    protected MidwifeRequestModel(Parcel in) {
        this.timeFrom = in.readString();
        this.timeTo = in.readString();
        this.date = in.readString();
        this.day = in.readString();
    }

    public static final Parcelable.Creator<MidwifeRequestModel> CREATOR = new Parcelable.Creator<MidwifeRequestModel>() {
        @Override
        public MidwifeRequestModel createFromParcel(Parcel source) {
            return new MidwifeRequestModel(source);
        }

        @Override
        public MidwifeRequestModel[] newArray(int size) {
            return new MidwifeRequestModel[size];
        }
    };
}
