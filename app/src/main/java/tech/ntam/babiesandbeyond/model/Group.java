package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 05/01/18.
 */

public class Group implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("photo")
    private String photo;
    @SerializedName("status")
    private String status;
    @SerializedName("user_status")
    private String userStatus;
    @SerializedName("created_by")
    private int createdBy;
    @SerializedName("created_by_name")
    private String createdByName;
    @SerializedName("approved_by")
    private String approvedBy;
    @SerializedName("created_at")
    private String date;
    @SerializedName("is_member")
    private boolean isMember;

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getDescription() {
        return Utils.getValueFromString(description);
    }

    public String getPhoto() {
        return Utils.getValueFromString(photo);
    }

    public String getStatusString() {
        status = Utils.getValueFromString(status);
        if(status.equals("1")) return "Approved";
        else return "Pending";
    }
    public boolean getStatus() {
        status = Utils.getValueFromString(status);
        if(status.equals("1")) return true;
        else return false;
    }


    public int getCreatedBy() {
        return createdBy;
    }

    public String getCreatedByName() {
        return Utils.getValueFromString(createdByName);
    }

    public String getDate() {
        date = Utils.getValueFromString(date);
        date = MyDateTimeFactor.getDayMonthFromDateTime(date);
        return date;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public int getId() {
        return id;
    }

    public String getUserStatus() {
        return userStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.photo);
        dest.writeString(this.status);
        dest.writeString(this.userStatus);
        dest.writeInt(this.createdBy);
        dest.writeString(this.createdByName);
        dest.writeString(this.approvedBy);
        dest.writeString(this.date);
        dest.writeByte(this.isMember ? (byte) 1 : (byte) 0);
    }

    public Group() {
    }

    protected Group(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.photo = in.readString();
        this.status = in.readString();
        this.userStatus = in.readString();
        this.createdBy = in.readInt();
        this.createdByName = in.readString();
        this.approvedBy = in.readString();
        this.date = in.readString();
        this.isMember = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
