
package com.pisarev.nytimes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultList implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private long numResults;
    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = new ArrayList<Result>();
    public final static Creator<ResultList> CREATOR = new Creator<ResultList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResultList createFromParcel(Parcel in) {
            return new ResultList(in);
        }

        public ResultList[] newArray(int size) {
            return (new ResultList[size]);
        }

    }
    ;

    protected ResultList(Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.copyright = ((String) in.readValue((String.class.getClassLoader())));
        this.numResults = ((long) in.readValue((long.class.getClassLoader())));
        in.readList(this.results, (com.pisarev.nytimes.model.Result.class.getClassLoader()));
    }

    public ResultList() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public long getNumResults() {
        return numResults;
    }

    public void setNumResults(long numResults) {
        this.numResults = numResults;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(copyright);
        dest.writeValue(numResults);
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
