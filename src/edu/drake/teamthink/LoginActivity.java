package edu.drake.teamthink;

import edu.drake.teamthink.db.DBMethods;
import android.os.Bundle;
import android.app.Activity;
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
			//TODO - error screen for bad email
		}


	}

	private boolean validateEmail(String email) {
		// TODO Validate the email (blahblahblahblah@foo.bar)
		return true;
	}

}
