package demo.com.androiddemo.rest;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;


public class JsonVolleyRequest<T> extends Request<T> {

    private static final String TAG = "JsonVolleyRequest";

    private Response.Listener listener;
    private Class<T> cls;

    private static Gson gson = null;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public JsonVolleyRequest(int method, String url, Class<T> cls, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.cls = cls;
        this.listener = listener;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        T result = null;
        String data = null;
        try {
            data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d(TAG, data);
            result = gson.fromJson(data, cls);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
