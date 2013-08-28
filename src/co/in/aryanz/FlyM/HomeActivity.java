package co.in.aryanz.FlyM;

import android.os.Bundle;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity {
	public static FragmentTabHost tabHost;
	public static int backKeyPressedState = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);
		
		if(savedInstanceState != null){
			savedInstanceState.get("stateChanges");
		}
		
		
		backKeyPressedState = 0;
		Resources ressources = getResources(); 
		tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost); 
		tabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
		tabHost.clearAllTabs();
		tabHost.addTab(
				tabHost.newTabSpec("Contact").setIndicator("Contact",  ressources.getDrawable(R.drawable.icon_contact_config))
				, ContactActivity.class, null);  
		
		
		tabHost.addTab(
				tabHost.newTabSpec("Message").setIndicator("Message",  ressources.getDrawable(R.drawable.icon_message_config))
				, MessageActivity.class, null);  
		
		tabHost.addTab(
				tabHost.newTabSpec("About").setIndicator("About",  ressources.getDrawable(R.drawable.icon_about_config))
				, AboutActivity.class, null);  
	
 
		//set Windows tab as default (zero based)
		//tabHost.setCurrentTab(0);
	}
		
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);
			this.getMenuInflater().inflate(R.menu.contact, menu);

		}

		@Override
		protected void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt("stateChanges", 1);
		}

	@Override
	public void onBackPressed() {
		if(backKeyPressedState == 0){
			Toast.makeText(getApplicationContext(), "Press Again to Exit", Toast.LENGTH_SHORT).show();
			backKeyPressedState = 1;
		}
		else if(backKeyPressedState == 1){
			HomeActivity.this.finish();
		}
	}
}
