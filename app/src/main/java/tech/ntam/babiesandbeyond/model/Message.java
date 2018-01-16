package tech.ntam.babiesandbeyond.model;

/**
 * Created by bassiouny on 09/01/18.
 */

public class Message {

    private static final String textType = "text";
    private static final String photoType = "photo";

    private int imgHeight;
    private int imgWidth;
    private String message;
    private long timeStamp;
    private String type;
    private int userId;

    private void setMessage(String message, long timeStamp, int userId) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.userId = userId;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    /*
    public  textMessage() {
        type = textType;
        this.imgHeight = 0;
        this.imgWidth = 0;
    }*/

    public Message(String message, int userId, long timeStamp) {
        setMessage(message, timeStamp, userId);
        type = textType;
        this.imgHeight = 0;
        this.imgWidth = 0;
    }

    public Message(String message, int userId, long timeStamp, int imgHeight, int imgWidth) {
        setMessage(message, timeStamp, userId);
        type = textType;
        this.imgHeight = imgHeight;
        this.imgWidth = imgWidth;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMe(int userId) {
        return this.userId == userId;
    }

}
