package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 08/01/18.
 */

public class Workshop implements Parcelable {

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
    @SerializedName("is_comming")
    private boolean coming;
    @SerializedName("payment_status")
    private String paymentStatus;

    public int getId() {
        return id;
    }

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getStartDate() {
        return Utils.getValueFromString(startDate);
    }

    public String getEndDate() {
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
        return Utils.getValueFromString(price);
    }

    public String getPoint() {
        return Utils.getValueFromString(point);
    }

    public boolean isComing() {
        return coming;
    }

    public String getPaymentStatus() {
        paymentStatus = Utils.getValueFromString(paymentStatus);
        if (paymentStatus.equals(PaymentStatus.pending)) {
            return PaymentStatus.pendingString;
        } else if (paymentStatus.equals(PaymentStatus.confirmationWithoutPayment)) {
            return PaymentStatus.confirmationWithoutPaymentString;
        } else if (paymentStatus.equals(PaymentStatus.confirmationWithPayment)) {
            return PaymentStatus.confirmationWithPaymentString;
        } else {
            return "";
        }
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
        dest.writeByte(this.coming ? (byte) 1 : (byte) 0);
        dest.writeString(this.paymentStatus);
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
        this.coming = in.readByte() != 0;
        this.paymentStatus = in.readString();
    }

    public static final Parcelable.Creator<Workshop> CREATOR = new Parcelable.Creator<Workshop>() {
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
