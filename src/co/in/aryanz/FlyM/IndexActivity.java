package co.in.aryanz.FlyM;

import org.json.JSONException;
import org.json.JSONObject;

import co.in.aryanz.FlyM.util.DatabaseHandler;
import co.in.aryanz.FlyM.util.EmptyTextListener;
import co.in.aryanz.FlyM.util.InputValidator;
import co.in.aryanz.FlyM.util.UserFunctions;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IndexActivity extends Activity {

	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;
	JSONObject json;
	Drawable error_indicator;
	SharedPreferences pref;
	ProgressDialog progressDialog;

	private static String APP_SHARED_PREF = "FlyMUserPref";
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		showSplashScreenActivity();
		newUserLinkAction();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.detectAll().penaltyLog().build();
		StrictMode.setThreadPolicy(policy);

		InitLoginForm();
		isFormEmpty();

	}

	private void isFormEmpty() {
		error_indicator = getResources().getDrawable(
				R.drawable.small_error_icon);
		int left = 0;
		int top = 0;

		int right = error_indicator.getIntrinsicHeight();
		int bottom = error_indicator.getIntrinsicWidth();

		error_indicator.setBounds(new Rect(left, top, right, bottom));
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);

		inputEmail.addTextChangedListener(new InputValidator(inputEmail));
		inputPassword.addTextChangedListener(new InputValidator(inputPassword));

		inputEmail.setOnEditorActionListener(new EmptyTextListener(inputEmail));
		inputPassword.setOnEditorActionListener(new EmptyTextListener(
				inputPassword));

	}

	class LoginAsyncTask extends AsyncTask<Object, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			loginErrorMsg.setText("");
			progressDialog = ProgressDialog.show(IndexActivity.this,
					"Login in", "Wait a moment please", true, false);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			UserFunctions userFunction = new UserFunctions();
			if (result == null || result.equals("")) {
				progressDialog.dismiss();
			}
			try {
				if (result.equals("1")) {
					progressDialog.dismiss();
					// user successfully logged in
					// Store user details in SQLite Database
					DatabaseHandler db = new DatabaseHandler(
							getApplicationContext());
					JSONObject json_user = json.getJSONObject("user");

					// Clear all previous data in database
					userFunction.logoutUser(getApplicationContext());
					db.addUser(json_user.getString(KEY_NAME),
							json_user.getString(KEY_EMAIL),
							json.getString(KEY_UID),
							json_user.getString(KEY_CREATED_AT));

					// Launch Dashboard Screen
					Intent homeActivity = new Intent(getApplicationContext(),
							HomeActivity.class);

					// Close all views before launching Dashboard
					homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					homeActivity.addCategory(Intent.CATEGORY_HOME);
					startActivity(homeActivity);
					// Close Login Screen
					finish();
				} else {
					// Error in login
					progressDialog.dismiss();
					loginErrorMsg.setText("Invalid Login");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		protected String doInBackground(Object... params) {
			String result = null;
			String email = inputEmail.getText().toString();
			String password = inputPassword.getText().toString();
			UserFunctions userFunction = new UserFunctions();
			json = userFunction.loginUser(email, password);

			// check for login response

			try {
				if (json.getString(KEY_SUCCESS) != null) {

					String res = json.getString(KEY_SUCCESS);
					if(res.equals("1")){
						String uid = json.getString(KEY_UID);
						pref = getApplicationContext().getSharedPreferences(
								APP_SHARED_PREF, 0); // 0 - for private mode
						Editor editor = pref.edit();
						editor.putString("uid", uid);
						editor.commit();
						Log.d("co.in.aryanz.FlyM", " Result:::" + res);
						result = res;
					}
					return result;
				} else if (json.getString(KEY_ERROR) == "1") {
					loginErrorMsg.setText(json.getString(KEY_ERROR_MSG));
					progressDialog.dismiss();
				} else if (json == null) {
					progressDialog.dismiss();
					loginErrorMsg.setText("Network/Server is Down");
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
			return result;
		}

	}

	private void InitLoginForm() {
		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnSignup);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				loginErrorMsg.setText("");
				new LoginAsyncTask().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	private void showSplashScreenActivity() {
		Intent splashScreenIntent = new Intent(this, SplashScreenActivity.class);
		splashScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
		startActivityForResult(splashScreenIntent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("co.in.aryanz.FlyM", resultCode + " :: " + requestCode);
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(getBaseContext(), "Please login",
						Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(
						getBaseContext(),
						"Network Connection Failed!! Check Your Internet Connectivity & Re-open the App.",
						Toast.LENGTH_LONG).show();
				createNetErrorDialog().show();
			}
		}
	}

	protected Builder createNetErrorDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
				.setTitle("Unable to connect")
				.setCancelable(false)
				.setPositiveButton("Settings",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent(
										Settings.ACTION_WIRELESS_SETTINGS);
								startActivity(i);
							}
						})
				.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								IndexActivity.this.finish();
							}
						}).create();
		return builder;
	}

	private void newUserLinkAction() {
		TextView newUserLink = (TextView) findViewById(R.id.txtSignupLink);

		newUserLink.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent signUpIntent = new Intent(getApplicationContext(),
						SignUpActivity.class);
				startActivity(signUpIntent);
			}
		});
	}
}
