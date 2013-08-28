package co.in.aryanz.FlyM;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import co.in.aryanz.FlyM.util.UserFunctions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class ContactActivity extends ListFragment {

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_FRIENDS = "friends";
	private static String KEY_COUNT = "count";
	ArrayAdapter<String> adapter;

	private static String APP_SHARED_PREF = "FlyMUserPref";
	ListView lv;
	private static boolean contactLoaded = false;
	SharedPreferences pref;
	ProgressDialog progressDialog;

	JSONObject json;
	ArrayList<String> data;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		if(savedInstanceState != null){
			Log.d("co.in.aryanz.FlyM", savedInstanceState.size() + "");
			new FriendsListLoaderTask().execute();
			/*if(savedInstanceState.getInt("flist") == 1){
				new FriendsListLoaderTask().execute();
			}*/
		}
		
		if (contactLoaded == false) {
			new FriendsListLoaderTask().execute();
		}

	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("flist", 1);
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_contact, container, false);
		setListAdapter(adapter);
		ImageButton btnRefresh = (ImageButton) v.findViewById(R.id.btnRefresh);
		ImageButton btnExit = (ImageButton) v.findViewById(R.id.btnExit);
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 
					new FriendsListLoaderTask().execute();
			}
		});
		
		
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
		Toast.makeText(getActivity().getApplicationContext(), "in Create View", Toast.LENGTH_SHORT).show();
		return v;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		/*
		 * AlertDialogManager dilog = new AlertDialogManager();
		 * Log.d("co.in.aryanz.FlyM",
		 * data.get(position).split(":")[1].toString());
		 * dilog.showAlertDialog(getActivity(), "Friend",
		 * data.get(position).split(":")[0] .toString(), false);
		 * Toast.makeText(getActivity().getApplicationContext(),
		 * data.get(position), Toast.LENGTH_LONG).show();
		 */
		Intent messageEditor = new Intent(v.getContext(),
				MessageEditorActivity.class);
		messageEditor.putExtra("messageto", data.get(position).split(":")[1]);
		messageEditor.putExtra("email", data.get(position).split(":")[2]);
		startActivityForResult(messageEditor, 1);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getActivity().getApplicationContext(), "Resume", Toast.LENGTH_SHORT).show();
	}

	public class FriendsListLoaderTask extends AsyncTask<Object, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(getActivity(),
					"Loading Friends",
					"Please wait till we retrive your friends", false, false);

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result.equals("1")) {
				progressDialog.dismiss();
				adapter = new ArrayAdapter<String>(getActivity(),
						R.layout.contact_list, data);
				contactLoaded = true;
				setListAdapter(adapter);
			}

		}

		@Override
		protected String doInBackground(Object... arg0) {
			String result = null;

			pref = getActivity().getApplicationContext().getSharedPreferences(
					APP_SHARED_PREF, 0); // 0 - for private mode
			String uid = pref.getString("uid", "");

			UserFunctions userFunction = new UserFunctions();
			json = userFunction.getFriends(uid);

			try {
				if (json.getString(KEY_SUCCESS) != null) {

					String res = json.getString(KEY_SUCCESS);
					int count = json.getInt(KEY_COUNT);
					int inc;
					data = new ArrayList<String>();
					if (count > 0) {
						for (inc = 0; inc < count; inc++) {
							data.add(json.getString(KEY_FRIENDS + "" + inc));
							Log.d("co.in.aryanz.FlyM", data.get(inc));
						}

					}
					Log.d("co.in.aryanz.FlyM", " Result:::" + res);
					result = res;
					return result;
				} else {
					progressDialog.dismiss();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

	}



}
