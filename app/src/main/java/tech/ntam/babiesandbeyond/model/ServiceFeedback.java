package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 13/03/18.
 */

public class ServiceFeedback implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("rate")
    private String rate;
    @SerializedName("comment")
    private String comment;

    public int getId() {
        return id;
    }

    public float getRate() {
        rate = Utils.getValueFromString(rate);
        if(rate.isEmpty())
            return 0;
        return Float.parseFloat(rate);
    }

    public String getComment() {
        return Utils.getValueFromString(comment);
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.rate);
        dest.writeString(this.comment);
    }

    public ServiceFeedback() {
    }

    protected ServiceFeedback(Parcel in) {
        this.id = in.readInt();
        this.rate = in.readString();
        this.comment = in.readString();
    }

    public static final Parcelable.Creator<ServiceFeedback> CREATOR = new Parcelable.Creator<ServiceFeedback>() {
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
