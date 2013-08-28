/**
 * 
 */
package co.in.aryanz.FlyM.util;

import java.util.regex.Pattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author Bala
 *
 */
public class InputValidator implements TextWatcher {
	private EditText eTxt;

	
	public InputValidator(EditText editText){
		this.eTxt = editText;
	}
	
	/* (non-Javadoc)
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(s.length() != 0){
			switch(eTxt.getId()){
			
			case  co.in.aryanz.FlyM.R.id.loginEmail: {
				if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", s)){
					eTxt.setError("You need to enter valid Email!");
				}
				
			} break;
			
			case  co.in.aryanz.FlyM.R.id.loginPassword: {
				if(!Pattern.matches("^[0-9]{4}$", s)){
					eTxt.setError("Password must be numbers and accepts only 4 digits");
				}
			} break;
			
			
			}
		}

	}

}
