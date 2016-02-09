package demo.com.androiddemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import demo.com.androiddemo.R;
import demo.com.androiddemo.model.Track;


public class TrackListAdapter extends BaseAdapter {

    private ArrayList<Track> mTrackList;

    @Override
    public int getCount() {
        int count = 0;
        if(mTrackList != null)
            count = mTrackList.size();
        return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.track_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.artistName = (TextView) convertView.findViewById(R.id.artist_name);
            viewHolder.trackName = (TextView) convertView.findViewById(R.id.track_name);
            viewHolder.trackPrice = (TextView) convertView.findViewById(R.id.track_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Track track = getItem(position);
        viewHolder.artistName.setText(track.getArtistName());
        viewHolder.trackName.setText(track.getTrackName());
        viewHolder.trackPrice.setText(convertView.getContext().getString(R.string.track_price, track.getTrackPrice()));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        long itemId = 0;
        if(mTrackList != null && mTrackList.size() > 0) {
            Track track = mTrackList.get(position);
            itemId = track.getTrackId();
        }
        return itemId;
    }

    @Override
    public Track getItem(int position) {
        if(mTrackList != null && mTrackList.size() > 0) {
            return mTrackList.get(position);
        }
        return null;
    }

    public void setTrackList(ArrayList<Track> trackList) {
        this.mTrackList = trackList;
    }

    public ArrayList<Track> getTrackList() {
        return mTrackList;
    }

    private static class ViewHolder {
        public TextView trackName;
        public TextView artistName;
        public TextView trackPrice;
    }
}
