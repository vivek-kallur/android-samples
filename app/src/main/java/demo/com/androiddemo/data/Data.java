package demo.com.androiddemo.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import demo.com.androiddemo.App;
import demo.com.androiddemo.model.SearchResult;
import demo.com.androiddemo.rest.JsonVolleyRequest;

public class Data {

    private static final String TRACK_LIST_TAG = "track_list";
    private static final String URL = "https://itunes.apple.com/search?term=taylor";

    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;

    private static Data sInstance;

    private Data() {

    }

    public static Data getInstance() {
        if(sInstance == null) {
            synchronized (Data.class) {
                sInstance = new Data();
            }
        }
        return sInstance;
    }

    private void add(Request request) {
        App.getInstance().getRequestQueue().add(request);
    }

    public void getTrackList(Response.Listener<SearchResult> listener, Response.ErrorListener errorListener) {
        mListener = listener;
        mErrorListener = errorListener;

        JsonVolleyRequest<SearchResult> jsonVolleyRequest = new JsonVolleyRequest<>(Request.Method.GET, URL, SearchResult.class,
                new Response.Listener<SearchResult>() {
                    @Override
                    public void onResponse(SearchResult response) {
                        if(mListener != null)
                            mListener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mErrorListener != null)
                            mErrorListener.onErrorResponse(error);
                    }
                });

        jsonVolleyRequest.setTag(TRACK_LIST_TAG);
        add(jsonVolleyRequest);
    }

    public void setTrackListListeners(Response.Listener<SearchResult> listener, Response.ErrorListener errorListener) {
        mListener = listener;
        mErrorListener = errorListener;
    }
}
