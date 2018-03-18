package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 18/03/18.
 */

public class MessageAdmin implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("from_id")
    private String fromId;
    @SerializedName("to_id")
    private String toId;
    @SerializedName("message")
    private String message;

    public String getFromId() {
        return Utils.getValueFromString(fromId);
    }

    public String getMessage() {
        return Utils.getValueFromString(message);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fromId);
        dest.writeString(this.toId);
        dest.writeString(this.message);
    }

    public MessageAdmin() {
    }

    protected MessageAdmin(Parcel in) {
        this.id = in.readString();
        this.fromId = in.readString();
        this.toId = in.readString();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<MessageAdmin> CREATOR = new Parcelable.Creator<MessageAdmin>() {
        @Override
        public MessageAdmin createFromParcel(Parcel source) {
            return new MessageAdmin(source);
        }

        @Override
        public MessageAdmin[] newArray(int size) {
            return new MessageAdmin[size];
        }
    };
}
