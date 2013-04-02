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
		EditText emailText = (EditText) findViewById(R.id.emailField); //grab email and pwd
		EditText passwordText = (EditText) findViewById(R.id.passwordField);
		String email = emailText.getText().toString();
		String password = passwordText.getText().toString();
		if (DBMethods.validateEmail(email)) { //check if email is good
			if (DBMethods.checkLogin(email,password)) { //see if login was correct
				Intent intent = new Intent(view.getContext(), NoteActivity.class); //when clicked, pull up an instance of the note screen activity
				startActivity(intent);
			}
			else{
				Context context = getApplicationContext(); //use a toast to notify user of incorrect pwd and email
				CharSequence text = "Invalid Email or Password";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}
		else { //use toast to notify user of bad email address
			Context context = getApplicationContext();
			CharSequence text = "Invalid Email Address";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	public void register(View view)
	{
		Intent intent = new Intent(view.getContext(), CreateActivity.class); //When clicked, the "register" button pulls up an instance of the Create Account activity
		startActivity(intent);
	}

}
