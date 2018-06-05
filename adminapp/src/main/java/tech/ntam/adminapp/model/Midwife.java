package tech.ntam.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 22/02/18.
 */

public class Midwife implements Parcelable {

    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;
    @SerializedName("midwife_times")
    private List<MidwifeRequestModel> timeSlotsMidwifeList;

    public int getId() {
        return id;
    }

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getPhoto() {
        return Utils.getValueFromString(photo);
    }

    public List<MidwifeRequestModel> getTimeSlotsMidwifeList() {
        if(timeSlotsMidwifeList == null)
            timeSlotsMidwifeList = new ArrayList<>();
        return timeSlotsMidwifeList;
    }

    public Midwife() {
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
        dest.writeTypedList(this.timeSlotsMidwifeList);
    }

    protected Midwife(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.photo = in.readString();
        this.timeSlotsMidwifeList = in.createTypedArrayList(MidwifeRequestModel.CREATOR);
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
