package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 13/03/18.
 */

public class ServiceFeedback implements Parcelable {


    private final static int EVENT = 0;
    private final static int WORKSHOP = 1;
    private final static int MIDWIFE = 3;
    private final static int NURSE = 4;
    private final static int BABYSITTER = 5;


    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private String id;
    @SerializedName("rate")
    private String rate;
    @SerializedName("comment")
    private String comment;

    public String getType() {
        switch (type) {
            case MIDWIFE:
                return "Midwife";
            case WORKSHOP:
                return "Workshop";
            case EVENT:
                return "Event";
            case NURSE:
                return "nurse";
            case BABYSITTER:
                return "babysitter";
            default:
                return "";
        }
    }

    public String getId() {
        return Utils.getValueFromString(id);
    }

    public float getRate() {
        rate = Utils.getValueFromString(rate);
        if (rate.isEmpty())
            return 0;
        return Float.parseFloat(rate);
    }

    public String getComment() {
        return Utils.getValueFromString(comment);
    }

    public void setRate(float rate) {
        this.rate = String.valueOf(rate);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isMidwife() {
        return type == MIDWIFE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.id);
        dest.writeString(this.rate);
        dest.writeString(this.comment);
    }

    public ServiceFeedback() {
    }

    protected ServiceFeedback(Parcel in) {
        this.type = in.readInt();
        this.id = in.readString();
        this.rate = in.readString();
        this.comment = in.readString();
    }

    public static final Creator<ServiceFeedback> CREATOR = new Creator<ServiceFeedback>() {
        @Override
        public ServiceFeedback createFromParcel(Parcel source) {
            return new ServiceFeedback(source);
        }

        @Override
        public ServiceFeedback[] newArray(int size) {
            return new ServiceFeedback[size];
        }
    };
}
