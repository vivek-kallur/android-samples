package demo.com.androiddemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import demo.com.androiddemo.adapters.TrackListAdapter;
import demo.com.androiddemo.data.Data;
import demo.com.androiddemo.model.SearchResult;
import demo.com.androiddemo.model.Track;


public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private static final String LIST_DATA = "data";

    private ListView mListView;
    private TrackListAdapter mAdapter;
    private View mProgress;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        mProgress = view.findViewById(R.id.progress);
        mListView = (ListView) view.findViewById(R.id.artist_list);

        mAdapter = new TrackListAdapter();
        mListView.setAdapter(mAdapter);
        mProgress.setVisibility(View.VISIBLE);

        if(savedInstanceState != null) {
            Log.d(TAG, "Get data from parcel and update");
            ArrayList<Track> data = savedInstanceState.getParcelableArrayList(LIST_DATA);
            updateAdapterData(data);
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        setListeners(null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        ArrayList<Track> data = mAdapter.getTrackList();
        if(data == null) {
            Log.d(TAG, "Requesting tracks");
            Data.getInstance().getTrackList(resultListener, errorListener);
        }
        else {
            setListeners(resultListener, errorListener);
            updateAdapterData(data);
        }
    }

    public static void showMainFragment(Activity activity) {
        if(activity == null || activity.isFinishing())
            return;

        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment mainFragment = fragmentManager.findFragmentByTag(TAG);
        if(mainFragment == null) {
            mainFragment = new MainFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.add(R.id.container, mainFragment, TAG);
            fragmentTransaction.commit();
        }
    }

    Response.Listener<SearchResult> resultListener = new Response.Listener<SearchResult>() {
        @Override
        public void onResponse(SearchResult response) {
            updateAdapterData(response.getResults());
        }
    };

    private void updateAdapterData(ArrayList<Track> data) {
        mProgress.setVisibility(View.GONE);
        mAdapter.setTrackList(data);
        mAdapter.notifyDataSetChanged();
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mProgress.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Error, please try again later.", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_DATA, mAdapter.getTrackList());
    }

    private void setListeners(Response.Listener<SearchResult> listener, Response.ErrorListener errorListener) {
        Data.getInstance().setTrackListListeners(listener, errorListener);
    }
}
