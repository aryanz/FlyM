package co.in.aryanz.FlyM.util;

import java.util.List;

import co.in.aryanz.FlyM.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendArrayAdapter extends ArrayAdapter<FriendsPOJO> {
	
	private final LayoutInflater mInflater;
	
	public FriendArrayAdapter(Context context) {
		
		super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}
	
	public void setData(List<FriendsPOJO> data) {
        clear();
        if (data != null) {
            for (FriendsPOJO appEntry : data) {
                add(appEntry);
            }
        }
    }
	
	/**
     * Populate new items in the list.
     */
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View view;
 
        if (convertView == null) {
            view = mInflater.inflate(R.layout.single_layout, parent, false);
        } else {
            view = convertView;
        }
 
        FriendsPOJO item = getItem(position);
        ((TextView)view.findViewById(R.id.tv_label)).setText(item.getUid());
        ((TextView)view.findViewById(R.id.tv_id)).setText(item.getFid()[0]);
 
        return view;
    }

}
