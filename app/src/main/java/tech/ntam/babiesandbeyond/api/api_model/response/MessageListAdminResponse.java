package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.MessageAdmin;

/**
 * Created by Developer on 18/03/18.
 */

public class MessageListAdminResponse extends ParentResponse{

    @SerializedName(DATA_KEY)
    private List<MessageAdmin> messageAdmins;

    public List<MessageAdmin> getMessageAdmins() {
        if (messageAdmins == null)
            messageAdmins = new ArrayList<>();
        return messageAdmins;
    }
}
