package edu.drake.teamthink;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.drake.teamthink.db.DBMethods;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public void submit(View view) {
	    // Do something in response to button
		System.out.println("in submit");
		EditText emailText = (EditText) findViewById(R.id.emailField);
		EditText passwordText = (EditText) findViewById(R.id.passwordField);
		String email = emailText.getText().toString();
		String password = passwordText.getText().toString();
		if (validateEmail(email)) {
			if (DBMethods.checkLogin(email,password)) {
				Intent intent = new Intent(view.getContext(), NoteActivity.class); //when clicked, pull up an instance of the note screen activity
				startActivity(intent);
			}
			else{
				//TODO - login failure error
			}
		}
		else {
			Context context = getApplicationContext();
			CharSequence text = "Invalid Email Address";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}


	}

	private boolean validateEmail(String email) {
		// TODO Validate the email (blahblahblahblah@foo.bar)
		Pattern emailPattern = Pattern.compile("\\b(^[_A-Za-z0-9-]+" +
				"(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])" +
				"+((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|(\\.mil)|" +
				"(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\.aero)|" +
				"(\\.arpa)|(\\.coop)|(\\.int)|(\\.jobs)|(\\.museum)|(\\.name)|" +
				"(\\.pro)|(\\.travel)|(\\.nato)|(\\..{2,3})|(\\..{2,3}\\..{2,3}))$)\\b"); //regular expression for email address http://struts.apache.org/release/2.0.x/struts2-core/apidocs/com/opensymphony/xwork2/validator/validators/EmailValidator.html
		Matcher m = emailPattern.matcher(email); //try to match pattern to email
		return m.matches(); //if it matches, return true
	}

}
