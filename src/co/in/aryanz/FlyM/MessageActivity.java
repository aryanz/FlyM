package co.in.aryanz.FlyM;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MessageActivity extends Fragment implements OnClickListener {


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_about , container , false);
		return v;
	}

	@Override
	public void onClick(View v) {

		
	}
	
}
