package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 02/01/18.
 */

public class Event implements Parcelable {

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
    @SerializedName("speaker_name")
    private String speakerName;
    @SerializedName("speaker_bio")
    private String speakerBio;
    @SerializedName("location")
    private String location;
    @SerializedName("is_comming")
    private boolean coming;

    public int getId() {
        return id;
    }

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return Utils.getValueFromString(description);
    }

    public String getSpeakerName() {
        return Utils.getValueFromString(speakerName);
    }

    public String getSpeakerBio() {
        return Utils.getValueFromString(speakerBio);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public boolean isComing() {
        return coming;
    }

    public void setComing(boolean coming) {
        this.coming = coming;
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
        dest.writeString(this.speakerName);
        dest.writeString(this.speakerBio);
        dest.writeString(this.location);
        dest.writeByte(this.coming ? (byte) 1 : (byte) 0);
    }

    public Event() {
    }

    protected Event(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.description = in.readString();
        this.speakerName = in.readString();
        this.speakerBio = in.readString();
        this.location = in.readString();
        this.coming = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
