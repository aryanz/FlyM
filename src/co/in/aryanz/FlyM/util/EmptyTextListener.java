/**
 * 
 */
package co.in.aryanz.FlyM.util;

import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * @author Bala
 *
 */
public class EmptyTextListener implements OnEditorActionListener {

	private EditText eTxt;
	
	public EmptyTextListener(EditText editText) {
		this.eTxt = editText;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.TextView.OnEditorActionListener#onEditorAction(android.widget.TextView, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		
		Log.d("co.in.aryanz.FlyM", actionId + " :: " + EditorInfo.IME_ACTION_NEXT );
			if(eTxt.getText().toString().equals(""))
				eTxt.setError("Oops Empty.");
		return false;
	}

}
