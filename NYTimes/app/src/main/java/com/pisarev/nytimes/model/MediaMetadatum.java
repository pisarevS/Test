
package com.pisarev.nytimes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaMetadatum implements Parcelable
{

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("height")
    @Expose
    private long height;
    @SerializedName("width")
    @Expose
    private long width;
    public final static Creator<MediaMetadatum> CREATOR = new Creator<MediaMetadatum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MediaMetadatum createFromParcel(Parcel in) {
            return new MediaMetadatum(in);
        }

        public MediaMetadatum[] newArray(int size) {
            return (new MediaMetadatum[size]);
        }

    }
    ;

    protected MediaMetadatum(Parcel in) {
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.format = ((String) in.readValue((String.class.getClassLoader())));
        this.height = ((long) in.readValue((long.class.getClassLoader())));
        this.width = ((long) in.readValue((long.class.getClassLoader())));
    }

    public MediaMetadatum() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(url);
        dest.writeValue(format);
        dest.writeValue(height);
        dest.writeValue(width);
    }

    public int describeContents() {
        return  0;
    }

}
