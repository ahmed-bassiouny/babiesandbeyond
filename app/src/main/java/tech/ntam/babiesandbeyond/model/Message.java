package tech.ntam.babiesandbeyond.model;

/**
 * Created by bassiouny on 09/01/18.
 */

public class Message {

    private String message;
    private boolean me;
    private String type;

    public Message(String message, boolean me, String type) {
        this.message = message;
        this.me = me;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMe() {
        return me;
    }

    public boolean isPhoto() {
        if (type.equals("photo"))
            return true;
        return false;
    }
}
