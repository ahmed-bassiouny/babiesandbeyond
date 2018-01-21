package tech.ntam.babiesandbeyond.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bassiouny on 09/01/18.
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
    @SerializedName(TEXT_MESSAGE)
    private String txtMessage;
    @SerializedName(TIME_STAMP)
    private long timeStamp;
    @SerializedName(IMAGE_HEIGHT)
    private int imgHeight;
    @SerializedName(IMAGE_WIDTH)
    private int imgWidth;
    @SerializedName(IMAGE_URL)
    private String imageURL;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        if (txtMessage == null)
            txtMessage = "";
        return txtMessage;
    }

    public void setMessage(String message) {
        this.txtMessage = message;
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
