package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 05/01/18.
 */

public class Group {

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
    @SerializedName("created_by")
    private String createdBy;
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


    public String getCreatedBy() {
        return Utils.getValueFromString(createdBy);
    }

    public String getDate() {
        date = Utils.getValueFromString(date);
        date = date.split(" ")[0];
        return date;
    }

    public boolean isMember() {
        return isMember;
    }

    public int getId() {
        return id;
    }
}
