package co.in.aryanz.FlyM;

import java.util.ArrayList;

import co.in.aryanz.FlyM.util.AlertDialogManager;

import android.R.anim;
import android.os.Bundle;
import android.app.ListActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class CopyOfContactActivity extends ListFragment {
	ArrayList<String> data;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		data = new ArrayList<String>();
		data.add("Bala:123");
		data.add("Balamurugan:1232");
		data.add("pooja:5343");
		data.add("Roja:90899989800");
		data.add("raja:9089922322");
		data.add("nagesh:908999465600");
		data.add("nayanthara:67699989800");
		data.add("lavanya:8977989800");
		data.add("babu:9082322800");
		data.add("mukesh:90899989454");
		data.add("android:90899989111");
		data.add("apple:12199989800");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.contact_list, data);
		
		
		ListView lv = (ListView) getView().findViewById(android.R.id.list);
		
		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				AlertDialogManager dilog = new AlertDialogManager();
				
				Log.d("co.in.aryanz.FlyM",data.get(arg2).split(":")[1].toString());
				
				dilog.showAlertDialog(getActivity(), "Friend", data.get(arg2).split(":")[1].toString() , false);
				Toast.makeText(getActivity().getApplicationContext(), data.get(arg2), Toast.LENGTH_LONG).show();
			}
			
		});
		
		
		    setListAdapter(adapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.activity_contact , container , false);
		return v;
	}

	
		
}
