package tech.ntam.babiesandbeyond.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * Created by bassiouny on 03/01/18.
 */

public class Service implements Parcelable {
    @SerializedName("id")
    private String id;
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
    @SerializedName("staff_name")
    private String staffName;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_photo")
    private String userPhoto;

    public int getId() {
        return Integer.parseInt(Utils.getValueFromString(id));
    }
    public String getIdString() {
        return Utils.getValueFromString(id);
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

    public String getStartTime() {
        startDate = Utils.getValueFromString(startDate);
        return MyDateTimeFactor.getTimeFromDateTime(startDate);
    }

    public String getServiceTypeName() {
        return Utils.getValueFromString(serviceTypeName);
    }

    public void setServiceStatusName(String serviceStatusName) {
        this.serviceStatusName = serviceStatusName;
    }

    public String getEndDate() {
        endDate = Utils.getValueFromString(endDate);
        return MyDateTimeFactor.getDateFromDateTime(endDate);
    }

    public String getEndTime() {
        endDate = Utils.getValueFromString(endDate);
        return MyDateTimeFactor.getTimeFromDateTime(endDate);
    }

    public String getFullEndDate() {
        return Utils.getValueFromString(endDate);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public String getPrice() {
        price = Utils.getValueFromString(price);
        if (price.isEmpty())
            return Constant.NOT_SET;
        return price + "$";
    }

    public String getServiceStatusString() {
        serviceStatusName = Utils.getValueFromString(serviceStatusName);
        if (serviceStatusName.equals(Constant.PENDING)) {
            return Constant.PENDING;
        } else if (serviceStatusName.equals(Constant.CONFIRMATION_WITHOUT_PAYMENT)) {
            return Constant.ASK_FOR_PAY;
        } else if (serviceStatusName.equals(Constant.CONFIRMATION_WITH_PAYMENT)) {
            return Constant.DONE;
        }
        return serviceStatusName;
    }

    public String getStaffName() {
        return Utils.getValueFromString(staffName);
    }

    public int getServiceStatusColor() {
        serviceStatusName = Utils.getValueFromString(serviceStatusName);
        switch (serviceStatusName) {
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

    public double getLatitude() {
        latitude = Utils.getValueFromString(latitude);
        return Double.parseDouble(latitude);
    }

    public double getLongitude() {
        longitude = Utils.getValueFromString(longitude);
        return Double.parseDouble(longitude);
    }

    public String getUserName() {
        return Utils.getValueFromString(userName);
    }

    public String getUserPhoto() {
        return Utils.getValueFromString(userPhoto);
    }

    public void updateServiceStatusName() {
        this.serviceStatusName = Constant.CONFIRMATION_WITHOUT_PAYMENT;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.userId);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.location);
        dest.writeString(this.price);
        dest.writeInt(this.serviceStatusId);
        dest.writeInt(this.serviceTypeId);
        dest.writeString(this.serviceStatusName);
        dest.writeString(this.serviceTypeName);
        dest.writeString(this.staffName);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.userName);
        dest.writeString(this.userPhoto);
    }

    public Service() {
    }

    protected Service(Parcel in) {
        this.id = in.readString();
        this.userId = in.readInt();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.location = in.readString();
        this.price = in.readString();
        this.serviceStatusId = in.readInt();
        this.serviceTypeId = in.readInt();
        this.serviceStatusName = in.readString();
        this.serviceTypeName = in.readString();
        this.staffName = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.userName = in.readString();
        this.userPhoto = in.readString();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel source) {
            return new Service(source);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}
