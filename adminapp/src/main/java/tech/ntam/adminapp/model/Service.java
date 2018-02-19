package tech.ntam.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 05/02/18.
 */

public class Service implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("photo")
    private String photo;
    @SerializedName("notificationToken")
    private String notification_token;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("is_logged_in")
    private String isLoggedIn;
    @SerializedName("user_type_name")
    private String userTypeName;
    @SerializedName("is_reserved")
    private boolean isReserved;

    public int getId() {
        return id;
    }

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getEmail() {
        return Utils.getValueFromString(email);
    }

    public String getPhone() {
        return Utils.getValueFromString(phone);
    }

    public String getPhoto() {
        return Utils.getValueFromString(photo);
    }

    public String getNotification_token() {
        return Utils.getValueFromString(notification_token);
    }

    public String getBirthday() {
        return Utils.getValueFromString(birthday);
    }

    public boolean getIsLoggedIn() {
        if (isLoggedIn != null && isLoggedIn.equals("1"))
            return true;
        return false;
    }

    public String getUserTypeName() {
        return Utils.getValueFromString(userTypeName);
    }

    public String isReserved() {
        if (isReserved)
            return "Reserved";
        return "Not Reserved";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.photo);
        dest.writeString(this.notification_token);
        dest.writeString(this.birthday);
        dest.writeString(this.isLoggedIn);
        dest.writeString(this.userTypeName);
        dest.writeByte(this.isReserved ? (byte) 1 : (byte) 0);
    }

    public Service() {
    }

    protected Service(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.photo = in.readString();
        this.notification_token = in.readString();
        this.birthday = in.readString();
        this.isLoggedIn = in.readString();
        this.userTypeName = in.readString();
        this.isReserved = in.readByte() != 0;
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
