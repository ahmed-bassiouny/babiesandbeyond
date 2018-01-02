package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 02/01/18.
 */

public class Event {

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
}
