package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 24/02/18.
 */

public class TimeSlotsMidwife implements Parcelable {

    @SerializedName("day")
    private String day;
    @SerializedName("time_slots")
    private List<AvailableTimeMidwife> availableTimeMidwives;

    public String getDay() {
        return Utils.getValueFromString(day);
    }

    public List<AvailableTimeMidwife> getAvailableTimeMidwives() {
        if (availableTimeMidwives == null)
            availableTimeMidwives = new ArrayList<>();
        return availableTimeMidwives;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.day);
        dest.writeList(this.availableTimeMidwives);
    }

    public TimeSlotsMidwife() {
    }

    protected TimeSlotsMidwife(Parcel in) {
        this.day = in.readString();
        this.availableTimeMidwives = new ArrayList<AvailableTimeMidwife>();
        in.readList(this.availableTimeMidwives, AvailableTimeMidwife.class.getClassLoader());
    }

    public static final Parcelable.Creator<TimeSlotsMidwife> CREATOR = new Parcelable.Creator<TimeSlotsMidwife>() {
        @Override
        public TimeSlotsMidwife createFromParcel(Parcel source) {
            return new TimeSlotsMidwife(source);
        }

        @Override
        public TimeSlotsMidwife[] newArray(int size) {
            return new TimeSlotsMidwife[size];
        }
    };
}
