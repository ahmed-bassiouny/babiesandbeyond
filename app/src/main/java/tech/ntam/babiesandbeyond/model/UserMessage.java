package tech.ntam.babiesandbeyond.model;

import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 08/02/18.
 */

public class UserMessage {

    private String name;
    private String photo;
    private Message message;

    public UserMessage(String name, String photo, Message message) {
        this.name = name;
        this.photo = photo;
        this.message = message;
    }

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getPhoto() {
        return Utils.getValueFromString(photo);
    }

    public Message getMessage() {
        if(message == null)
            message = new Message();
        return message;
    }

}
