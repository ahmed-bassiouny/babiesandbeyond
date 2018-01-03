package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 03/01/18.
 */

public class Service implements Parcelable {
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

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStartDate() {
        return Utils.getValueFromString(startDate);
    }

    public String getEndDate() {
        return Utils.getValueFromString(endDate);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public String getPrice() {
        return Utils.getValueFromString(price);
    }

    public int getServiceStatusId() {
        return serviceStatusId;
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
    }

    public Service() {
    }

    protected Service(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.location = in.readString();
        this.price = in.readString();
        this.serviceStatusId = in.readInt();
        this.serviceTypeId = in.readInt();
    }

    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {
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
