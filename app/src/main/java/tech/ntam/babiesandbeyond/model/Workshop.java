package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * Created by Developer on 08/01/18.
 */

public class Workshop implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("workshop_id")
    private int workshopId;
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
    @SerializedName("service_workshop_status_name")
    private String workshopStatusName;
    @SerializedName("location")
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkshopId() {
        return workshopId;
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
            return Constant.NOT_SET;
        return price+" $";
    }

    public void setComing(boolean coming) {
        this.coming = coming;
    }

    public String getPoint() {
        return Utils.getValueFromString(point);
    }

    public boolean isComing() {
        return coming;
    }

    public void setWorkshopId(int workshopId) {
        this.workshopId = workshopId;
    }

    public int getWorkshopStatusColor() {
        switch (workshopStatusName) {
            case Constant.PENDING:
                return R.color.colorButton;
            case Constant.CONFIRMATION_WITH_PAYMENT:
            case Constant.CASH:
                return R.color.gray_bold;
            case Constant.CONFIRMATION_WITHOUT_PAYMENT:
                return R.color.gray;
            default :
                return R.color.white;
        }
    }

    public void setWorkshopStatusName(String workshopStatusName) {
        this.workshopStatusName = workshopStatusName;
    }

    public void updateWorkshopToConfirmationWithoutPayment() {
        workshopStatusName = Constant.CONFIRMATION_WITHOUT_PAYMENT;
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getWorkshopStatusName() {
        workshopStatusName = Utils.getValueFromString(workshopStatusName);
        if(workshopStatusName.equals(Constant.PENDING)){
            return Constant.PENDING;
        }else if(workshopStatusName.equals(Constant.CONFIRMATION_WITHOUT_PAYMENT)){
            return Constant.ASK_FOR_PAY;
        }else if(workshopStatusName.equals(Constant.CONFIRMATION_WITH_PAYMENT)){
            return Constant.DONE;
        }
        return workshopStatusName;
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
        dest.writeString(this.workshopStatusName);
        dest.writeString(this.location);
        dest.writeInt(this.workshopId);
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
        this.workshopStatusName = in.readString();
        this.location = in.readString();
        this.workshopId = in.readInt();
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
