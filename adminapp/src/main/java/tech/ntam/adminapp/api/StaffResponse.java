package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;

import tech.ntam.adminapp.model.Staff;

/**
 * Created by bassiouny on 04/02/18.
 */

public class StaffResponse extends ParentResponse {
    @SerializedName(DATA_KEY)
    private Staff staff;

    public Staff getStaff() {
        if(staff == null)
            staff = new Staff();
        return staff;
    }
}
