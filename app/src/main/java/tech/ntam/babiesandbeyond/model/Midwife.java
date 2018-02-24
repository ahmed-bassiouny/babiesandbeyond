package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 22/02/18.
 */

public class Midwife implements Parcelable {

    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;
    @SerializedName("time_slots")
    private List<TimeSlotsMidwife> timeSlotsMidwifeList;

    public int getId() {
        return id;
    }

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getPhoto() {
        return Utils.getValueFromString(photo);
    }

    public List<TimeSlotsMidwife> getTimeSlotsMidwifeList() {
        if(timeSlotsMidwifeList == null)
            timeSlotsMidwifeList = new ArrayList<>();
        return timeSlotsMidwifeList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.photo);
        dest.writeList(this.timeSlotsMidwifeList);
    }

    public Midwife() {
    }

    protected Midwife(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.photo = in.readString();
        this.timeSlotsMidwifeList = new ArrayList<>();
        in.readList(this.timeSlotsMidwifeList, TimeSlotsMidwife.class.getClassLoader());
    }

    public static final Creator<Midwife> CREATOR = new Creator<Midwife>() {
        @Override
        public Midwife createFromParcel(Parcel source) {
            return new Midwife(source);
        }

        @Override
        public Midwife[] newArray(int size) {
            return new Midwife[size];
        }
    };
}
