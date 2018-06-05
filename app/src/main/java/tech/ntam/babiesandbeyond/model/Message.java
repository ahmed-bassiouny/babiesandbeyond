package tech.ntam.babiesandbeyond.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Developer on 09/01/18.
 */

public class Message {

    public static final String USER_ID = "userId";
    public static final String TEXT_MESSAGE = "message";
    public static final String TIME_STAMP = "timeStamp";
    public static final String IMAGE_HEIGHT = "imgHeight";
    public static final String IMAGE_WIDTH = "imgWidth";
    public static final String IMAGE_URL = "imageURL";

    @SerializedName(USER_ID)
    private int userId;
    private String message;
    @SerializedName(TIME_STAMP)
    private long timeStamp;
    @SerializedName(IMAGE_HEIGHT)
    private int imgHeight;
    @SerializedName(IMAGE_WIDTH)
    private int imgWidth;
    @SerializedName(IMAGE_URL)
    private String imageURL;

    public byte[] bytes;


    public Message(int userId, long timeStamp, int imgHeight, int imgWidth, byte[] bytes) {
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.imgHeight = imgHeight;
        this.imgWidth = imgWidth;
        this.bytes = bytes;
    }

    public Message() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        if (message == null)
            message = "";
        return message;
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

    public String getImageURL() {
        if (imageURL == null)
            imageURL = "";
        return imageURL;
    }


    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
