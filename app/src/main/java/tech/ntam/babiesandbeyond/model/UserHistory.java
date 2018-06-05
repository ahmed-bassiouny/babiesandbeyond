package tech.ntam.babiesandbeyond.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 13/03/18.
 */

public class UserHistory extends ServiceFeedback {

    @SerializedName("name")
    private String name;
    @SerializedName("location")
    private String location;
    @SerializedName("dates")
    private List<HistoryDate> historyDates;

    public String getName() {
        return Utils.getValueFromString(name);
    }

    public String getLocation() {
        return Utils.getValueFromString(location);
    }

    public List<HistoryDate> getHistoryDates() {
        if(historyDates == null)
            historyDates = new ArrayList<>();
        return historyDates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeList(this.historyDates);
    }

    public UserHistory() {
    }

    protected UserHistory(Parcel in) {
        super(in);
        this.name = in.readString();
        this.location = in.readString();
        this.historyDates = new ArrayList<HistoryDate>();
        in.readList(this.historyDates, HistoryDate.class.getClassLoader());
    }

    public static final Creator<UserHistory> CREATOR = new Creator<UserHistory>() {
        @Override
        public UserHistory createFromParcel(Parcel source) {
            return new UserHistory(source);
        }

        @Override
        public UserHistory[] newArray(int size) {
            return new UserHistory[size];
        }
    };
}
