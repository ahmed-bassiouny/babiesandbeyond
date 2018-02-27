package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 24/02/18.
 */

public class AvailableTimeMidwife implements Parcelable {

    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;

    public String getFrom() {
        return MyDateTimeFactor.convertTimeFrom24To12(Utils.getValueFromString(from));
    }

    public String getTo() {
        return MyDateTimeFactor.convertTimeFrom24To12(Utils.getValueFromString(to));
    }

    public AvailableTimeMidwife(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.from);
        dest.writeString(this.to);
    }

    public AvailableTimeMidwife() {
    }

    protected AvailableTimeMidwife(Parcel in) {
        this.from = in.readString();
        this.to = in.readString();
    }

    public static final Parcelable.Creator<AvailableTimeMidwife> CREATOR = new Parcelable.Creator<AvailableTimeMidwife>() {
        @Override
        public AvailableTimeMidwife createFromParcel(Parcel source) {
            return new AvailableTimeMidwife(source);
        }

        @Override
        public AvailableTimeMidwife[] newArray(int size) {
            return new AvailableTimeMidwife[size];
        }
    };
}
