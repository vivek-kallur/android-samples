package demo.com.androiddemo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class Track implements Parcelable {
    private static final String TAG = "Track";

    public Track() {

    }

    public Track(Parcel parcel) {
        trackId = parcel.readLong();
        artistName = parcel.readString();
        trackName = parcel.readString();
        trackPrice = parcel.readDouble();
    }

    // Reading only the fields which are needed
    @SerializedName("trackId")
    private long trackId;
    @SerializedName("artistName")
    private String artistName;
    @SerializedName("trackName")
    private String trackName;
    @SerializedName("trackPrice")
    private double trackPrice;

    public long getTrackId() {
        return trackId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    @Override
    public int describeContents() {
        Log.d(TAG, "describeContents");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d(TAG, "describeContents");
        dest.writeLong(trackId);
        dest.writeString(artistName);
        dest.writeString(trackName);
        dest.writeDouble(trackPrice);
    }

    public static final Parcelable.Creator<Track> CREATOR
            = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
