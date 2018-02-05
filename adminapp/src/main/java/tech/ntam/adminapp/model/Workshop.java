package tech.ntam.adminapp.model;

import com.google.gson.annotations.SerializedName;

import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 05/02/18.
 */

public class Workshop {

    @SerializedName("name")
    private String name;
    @SerializedName("location")
    private String location;

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }
}
