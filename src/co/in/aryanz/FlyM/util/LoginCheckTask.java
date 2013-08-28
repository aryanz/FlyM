/**
 * 
 */
package co.in.aryanz.FlyM.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

/**
 * @author Bala
 *
 */
public class LoginCheckTask extends AsyncTask<JSONObject, Void, Boolean> {
	
	public boolean responseStatus = false;
	
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
//	private static String KEY_ERROR = "error";
//	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	
	public LoginCheckTask(JSONObject json,Context ctx,UserFunctions userFunction){
		// check for login response
		try {
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS); 
				if(Integer.parseInt(res) == 1){
					// user successfully logged in
					// Store user details in SQLite Database
					DatabaseHandler db = new DatabaseHandler(ctx);
					JSONObject json_user = json.getJSONObject("user");
					
					// Clear all previous data in database
					userFunction.logoutUser(ctx);
					db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
					responseStatus = true;
				}else{
					// Error in login
					responseStatus = false;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Boolean doInBackground(JSONObject... arg0) {


		return responseStatus;
	}

}
