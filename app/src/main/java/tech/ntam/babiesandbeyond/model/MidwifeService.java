package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * Created by bassiouny on 27/02/18.
 */

public class MidwifeService implements Parcelable {
    @SerializedName("name")
    private String midwifeName;
    @SerializedName("email")
    private String midwifeEmail;
    @SerializedName("phone")
    private String midwifePhone;
    @SerializedName("photo")
    private String midwifePhoto;
    @SerializedName("status")
    private String midwifeStatus;
    @SerializedName("time_slots")
    private List<MidwifeRequestModel> timeSlots;
    @SerializedName("id")
    private String uniqueKey;
    @SerializedName("price_per_hour")
    private String pricePerHour;
    @SerializedName("bio")
    private String bio;


    public String getMidwifeName() {
        return Utils.getValueFromString(midwifeName);
    }

    public String getMidwifeEmail() {
        return Utils.getValueFromString(midwifeEmail);
    }

    public String getMidwifePhone() {
        return Utils.getValueFromString(midwifePhone);
    }

    public String getMidwifePhoto() {
        return Utils.getValueFromString(midwifePhoto);
    }

    public int getPricePerHour() {
        pricePerHour = Utils.getValueFromString(pricePerHour);
        return Integer.parseInt(pricePerHour);
    }

    public List<MidwifeRequestModel> getTimeSlots() {
        if (timeSlots == null)
            timeSlots = new ArrayList<>();
        return timeSlots;
    }

    public String getUniqueKey() {
        return Utils.getValueFromString(uniqueKey);
    }

    public String getBio() {
        return Utils.getValueFromString(bio);
    }
    public int getMidwifeStatusColor() {
        midwifeStatus = Utils.getValueFromString(midwifeStatus);
        switch (midwifeStatus) {
            case Constant.PENDING:
                return R.color.colorButton;
            case Constant.CONFIRMATION_WITH_PAYMENT:
            case Constant.CASH:
                return R.color.gray_bold;
            case Constant.CONFIRMATION_WITHOUT_PAYMENT:
                return R.color.gray;
            default:
                return R.color.white;
        }
    }
    public String getMidwifeStatus() {
        midwifeStatus = Utils.getValueFromString(midwifeStatus);
        if (midwifeStatus.equals(Constant.PENDING)) {
            return Constant.PENDING;
        } else if (midwifeStatus.equals(Constant.CONFIRMATION_WITHOUT_PAYMENT)) {
            return Constant.ASK_FOR_PAY;
        } else if (midwifeStatus.equals(Constant.CONFIRMATION_WITH_PAYMENT)) {
            return Constant.DONE;
        }
        return midwifeStatus;
    }

    public void setMidwifeStatus(String midwifeStatus) {
        this.midwifeStatus = midwifeStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.midwifeName);
        dest.writeString(this.midwifeEmail);
        dest.writeString(this.midwifePhone);
        dest.writeString(this.midwifePhoto);
        dest.writeString(this.midwifeStatus);
        dest.writeTypedList(this.timeSlots);
        dest.writeString(this.uniqueKey);
        dest.writeString(this.pricePerHour);
        dest.writeString(this.bio);
    }

    public MidwifeService() {
    }

    protected MidwifeService(Parcel in) {
        this.midwifeName = in.readString();
        this.midwifeEmail = in.readString();
        this.midwifePhone = in.readString();
        this.midwifePhoto = in.readString();
        this.midwifeStatus = in.readString();
        this.timeSlots = in.createTypedArrayList(MidwifeRequestModel.CREATOR);
        this.uniqueKey = in.readString();
        this.pricePerHour = in.readString();
        this.bio = in.readString();
    }

    public static final Creator<MidwifeService> CREATOR = new Creator<MidwifeService>() {
        @Override
        public MidwifeService createFromParcel(Parcel source) {
            return new MidwifeService(source);
        }

        @Override
        public MidwifeService[] newArray(int size) {
            return new MidwifeService[size];
        }
    };
}
