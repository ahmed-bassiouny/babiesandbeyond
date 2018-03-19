package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;

import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 13/03/18.
 */

public class HistoryDate implements Parcelable {

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

    public String getFullStartDate(){
        try {
            return MyDateTimeFactor.changeDateFormatFromNumberToText(getFrom());
        } catch (ParseException e) {
            return "";
        }
    }
    public String getFullEndDate(){
        try {
            return MyDateTimeFactor.changeDateFormatFromNumberToText(getTo());
        } catch (ParseException e) {
            return "";
        }
    }
    public String getFromTime(){
        return MyDateTimeFactor.getTimeFromDateTime(Utils.getValueFromString(from));
    }
    public String getToTime(){
        return MyDateTimeFactor.getTimeFromDateTime(Utils.getValueFromString(to));
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.from);
        dest.writeString(this.to);
        dest.writeString(this.date);
    }

    public HistoryDate() {
    }

    protected HistoryDate(Parcel in) {
        this.from = in.readString();
        this.to = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<HistoryDate> CREATOR = new Parcelable.Creator<HistoryDate>() {
        @Override
        public HistoryDate createFromParcel(Parcel source) {
            return new HistoryDate(source);
        }

        @Override
        public HistoryDate[] newArray(int size) {
            return new HistoryDate[size];
        }
    };
}
