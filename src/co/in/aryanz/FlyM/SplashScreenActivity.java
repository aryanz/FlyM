package co.in.aryanz.FlyM;

import co.in.aryanz.FlyM.util.InetStatus;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.widget.Toast;

public class SplashScreenActivity extends Activity {

	private Intent backIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/*
		 * Make the Activity Display without Title Bar
		 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*
		 * Make the Activity Display without Title Bar
		 */

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash_screen);
		
		showSplashScreen();
		
	}

	/*
	 * function checkInterNetConnection
	 * @return boolean
	 */
	private boolean checkInterNetConnection() {
		if (InetStatus.getInstance(this).isOnline(this)) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * function showSplashScreen
	 * return void
	 */
	private void showSplashScreen() {

		new CountDownTimer(3500, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {

				// txt_progressStatus.setText("Please Wait... ");
				// Toast.makeText(getBaseContext(), " " +
				// checkInternetConnectivity(),Toast.LENGTH_LONG).show();
				if (!(checkInterNetConnection())) {
					// txt_progressStatus.setText("Network Connection Failed!! Check Your Internet Connectivity & Re-open the App.");
				backIntent = new Intent();
				setResult(RESULT_CANCELED, backIntent);
					finish();
				}else{
					backIntent = new Intent();
					setResult(RESULT_OK, backIntent);
					finish();
				}

			}
		}.start();

	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		Toast.makeText(getApplicationContext(), "Loading Please wait...", Toast.LENGTH_SHORT).show();
	}
	
}
