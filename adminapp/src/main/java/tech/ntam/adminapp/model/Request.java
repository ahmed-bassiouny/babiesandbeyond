package tech.ntam.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;

import tech.ntam.adminapp.R;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * Created by Developer on 03/01/18.
 */

public class Request implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("location")
    private String location;
    @SerializedName("price")
    private String price;
    @SerializedName("service_workshop_status_id")
    private int serviceStatusId;
    @SerializedName("service_type_id")
    private int serviceTypeId;
    @SerializedName("service_workshop_status_name")
    private String serviceStatusName;
    @SerializedName("service_type_name")
    private String serviceTypeName;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_photo")
    private String userPhoto;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullStartDate() {
        return Utils.getValueFromString(startDate);
    }

    public String getStartDate() {
        startDate = Utils.getValueFromString(startDate);
        return MyDateTimeFactor.getDateFromDateTime(startDate);
    }
    public String getStartDateTime() {
        startDate = Utils.getValueFromString(startDate);
        try {
            return MyDateTimeFactor.changeDateFormatFromNumberToText(startDate);
        } catch (ParseException e) {
            return "";
        }
    }

    public String getStartTime() {
        startDate = Utils.getValueFromString(startDate);
        return MyDateTimeFactor.getTimeFromDateTime(startDate);
    }

    public String getServiceTypeName() {
        return Utils.getValueFromString(serviceTypeName);
    }

    public String getEndDate() {
        endDate = Utils.getValueFromString(endDate);
        return MyDateTimeFactor.getDateFromDateTime(endDate);
    }

    public String getEndTime() {
        endDate = Utils.getValueFromString(endDate);
        return MyDateTimeFactor.getTimeFromDateTime(endDate);
    }
    public String getEndDateTime() {
        endDate = Utils.getValueFromString(endDate);
        try {
            return MyDateTimeFactor.changeDateFormatFromNumberToText(endDate);
        } catch (ParseException e) {
            return "";
        }
    }

    public String getFullEndDate() {
        return Utils.getValueFromString(endDate);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public String getPrice() {
        return Utils.getValueFromString(price)+"$";
    }

    public int getServiceStatusId() {
        return serviceStatusId;
    }

    public String getServiceStatusName() {
        return serviceStatusName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public String getServiceStatusString() {
        return Utils.getValueFromString(serviceStatusName);
    }
    public int getServiceStatusColor() {
        switch (serviceStatusName) {
            case Constant.PENDING:
                return R.color.colorButton;
            case Constant.CONFIRMATION_WITH_PAYMENT:
                return R.color.gray_bold;
            case Constant.CONFIRMATION_WITHOUT_PAYMENT:
                return R.color.gray;
            default:
                return R.color.white;
        }
    }

    public void updateServiceStatusName() {
        this.serviceStatusName = Constant.CONFIRMATION_WITHOUT_PAYMENT;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.location);
        dest.writeString(this.price);
        dest.writeInt(this.serviceStatusId);
        dest.writeInt(this.serviceTypeId);
        dest.writeString(this.serviceStatusName);
        dest.writeString(this.serviceTypeName);
        dest.writeString(this.userName);
        dest.writeString(this.userPhoto);
    }

    public Request() {
    }

    protected Request(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.location = in.readString();
        this.price = in.readString();
        this.serviceStatusId = in.readInt();
        this.serviceTypeId = in.readInt();
        this.serviceStatusName = in.readString();
        this.serviceTypeName = in.readString();
        this.userName = in.readString();
        this.userPhoto = in.readString();
    }

    public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel source) {
            return new Request(source);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
}
