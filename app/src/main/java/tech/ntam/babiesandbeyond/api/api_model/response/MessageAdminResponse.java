package tech.ntam.babiesandbeyond.api.api_model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.MessageAdmin;

/**
 * Created by bassiouny on 18/03/18.
 */

public class MessageAdminResponse extends ParentResponse{

    @SerializedName(DATA_KEY)
    private MessageAdmin messageAdmin;

    public MessageAdmin getMessageAdmin() {
        if (messageAdmin == null)
            messageAdmin = new MessageAdmin();
        return messageAdmin;
    }
}
