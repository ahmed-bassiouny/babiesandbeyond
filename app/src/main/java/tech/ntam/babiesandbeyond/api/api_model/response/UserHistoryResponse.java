package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.UserHistory;

/**
 * Created by Developer on 13/03/18.
 */

public class UserHistoryResponse extends ParentResponse{

    @SerializedName(DATA_KEY)
    private List<UserHistory> userHistory;

    public List<UserHistory> getUserHistory() {
        if(userHistory == null)
            userHistory = new ArrayList<>();
        return userHistory;
    }
}
