package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Group;

/**
 * Created by Developer on 05/01/18.
 */

public class GroupResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private List<Group> groups;

    public List<Group> getGroups() {
        if (groups == null)
            groups = new ArrayList<>();
        return groups;
    }
}
