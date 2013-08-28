/**
 * 
 */
package co.in.aryanz.FlyM.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
/**
 * @author Bala
 *
 */
public class UserFunctions {
	
	private JSONParser jsonParser;
	
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
	
	private static String loginURL = "http://www.aryanz.co.in/FlyMWS/";
	private static String registerURL = "http://www.aryanz.co.in/FlyMWS/";
	private static String usersDetailsURL = "http://www.aryanz.co.in/FlyMWS/users.php";
	private static String friendsURL = "http://www.aryanz.co.in/FlyMWS/friends.php";
	
	
/*	private static String loginURL = "http://192.168.131.1/aryanz/FlyMWS/";
	private static String registerURL = "http://192.168.131.1/aryanz/FlyMWS/";
	private static String usersDetailsURL = "http://192.168.131.1/FlyMWS/users.php";
	private static String friendsURL = "http://192.168.131.1/FlyMWS/friends.php";*/
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String get_friends_tag = "getfriends";
	private static String set_friends_tag = "setfriends";
	private static String getUser_deails_tag = "userdetails";
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Register Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
	/**
	 * function make Register Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject getFriends(String uid){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", get_friends_tag));
		params.add(new BasicNameValuePair("uid", uid));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
		// return json
		return json;
	}
	
	/**
	 * function make Register Request
	 * @param uid
	 * @param name
	 * @param fid
	 * */
	public JSONObject setFriends(String uid,String name,String fid){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", set_friends_tag));
		params.add(new BasicNameValuePair("uid", uid));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("fid", fid));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(friendsURL, params);
		// return json
		return json;
	}
	
	/**
	 * function make Register Request
	 * @param uid
	 * @param name
	 * @param fid
	 * */
	public JSONObject getUserDeails(String uid,String fieldName){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getUser_deails_tag));
		params.add(new BasicNameValuePair("uid", uid));
		params.add(new BasicNameValuePair("fieldname", fieldName));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(usersDetailsURL, params);
		// return json
		return json;
	}
	
}
