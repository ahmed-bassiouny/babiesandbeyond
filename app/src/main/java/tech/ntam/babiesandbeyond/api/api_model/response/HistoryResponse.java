package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.History;

/**
 * Created by bassiouny on 06/01/18.
 */

public class HistoryResponse extends ParentResponse {
    @SerializedName(DATA_KEY)
    private List<History> historyList;

    public List<History> getHistoryList() {
        if (historyList == null)
            historyList = new ArrayList<>();
        return historyList;
    }
}
