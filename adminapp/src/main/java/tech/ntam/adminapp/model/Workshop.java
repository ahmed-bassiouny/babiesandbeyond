package tech.ntam.adminapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.adminapp.R;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * Created by Developer on 05/02/18.
 */

public class Workshop implements Parcelable{
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("description")
    private String description;
    @SerializedName("speaker_bio")
    private String speakerBio;
    @SerializedName("speaker_name")
    private String speakerName;
    @SerializedName("price")
    private String price;
    @SerializedName("point")
    private String point;
    @SerializedName("location")
    private String location;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_workshop_id")
    private String userWorkshopId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return Utils.getValueFromString(name);
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

    public String getDescription() {
        return Utils.getValueFromString(description);
    }

    public String getSpeakerBio() {
        return Utils.getValueFromString(speakerBio);
    }

    public String getSpeakerName() {
        return Utils.getValueFromString(speakerName);
    }

    public String getPrice() {
        price = Utils.getValueFromString(price);
        if(price.isEmpty())
            return "not determined";
        return price+" $";
    }

    public String getUserName() {
        return Utils.getValueFromString(userName);
    }

    public String getUserId() {
        return Utils.getValueFromString(userId);
    }

    public String getPoint() {
        return Utils.getValueFromString(point);
    }

    public int getUserWorkshopId() {
        userWorkshopId = Utils.getValueFromString(userWorkshopId);
        return Integer.parseInt(userWorkshopId);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.description);
        dest.writeString(this.speakerBio);
        dest.writeString(this.speakerName);
        dest.writeString(this.price);
        dest.writeString(this.point);
        dest.writeString(this.location);
        dest.writeString(this.userName);
        dest.writeString(this.userId);
        dest.writeString(this.userWorkshopId);
    }

    public Workshop() {
    }

    protected Workshop(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.description = in.readString();
        this.speakerBio = in.readString();
        this.speakerName = in.readString();
        this.price = in.readString();
        this.point = in.readString();
        this.location = in.readString();
        this.userName = in.readString();
        this.userId = in.readString();
        this.userWorkshopId = in.readString();
    }

    public static final Creator<Workshop> CREATOR = new Creator<Workshop>() {
        @Override
        public Workshop createFromParcel(Parcel source) {
            return new Workshop(source);
        }

        @Override
        public Workshop[] newArray(int size) {
            return new Workshop[size];
        }
    };
}
