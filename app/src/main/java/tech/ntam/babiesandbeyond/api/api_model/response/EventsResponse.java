package tech.ntam.babiesandbeyond.api.api_model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Event;

/**
 * Created by Developer on 02/01/18.
 */

public class EventsResponse extends ParentResponse {
    @SerializedName(DATA_KEY)
    private List<Event> event;

    public List<Event> getEvent() {
        if(event == null)
            event = new ArrayList<>();
        return event;
    }
}
