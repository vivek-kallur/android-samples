package demo.com.androiddemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResult {
    @SerializedName("resultCount")
    private int resultCount;
    @SerializedName("results")
    private ArrayList<Track> results;

    public int getResultCount() {
        return resultCount;
    }

    public ArrayList<Track> getResults() {
        return results;
    }
}
