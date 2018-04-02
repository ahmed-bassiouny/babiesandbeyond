package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;

import tech.ntam.mylibrary.MyDateTimeFactor;
import tech.ntam.mylibrary.Utils;

/**
 * Created by bassiouny on 02/04/18.
 */

public class Article implements Parcelable {

    @SerializedName("body")
    private String body;
    @SerializedName("title")
    private String title;
    @SerializedName("created_at")
    private String date;

    public String getBody() {
        return Utils.getValueFromString(body);
    }

    public String getTitle() {
        return Utils.getValueFromString(title);
    }

    public String getDate() {
        date = Utils.getValueFromString(date);
        try {
            return MyDateTimeFactor.changeDateFormatFromNumberToText(date);
        } catch (ParseException e) {
            return "";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeString(this.title);
        dest.writeString(this.date);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.body = in.readString();
        this.title = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
