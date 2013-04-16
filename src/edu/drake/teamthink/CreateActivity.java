package edu.drake.teamthink;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import edu.drake.teamthink.db.DBMethods;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class CreateActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}

	public void submit(View view) {
		EditText emailText = (EditText) findViewById(R.id.email); //grab email and pwd
		EditText passwordText = (EditText) findViewById(R.id.password);
		EditText password2Text = (EditText) findViewById(R.id.passwordConfirm);
		EditText FirstNameText = (EditText) findViewById(R.id.FirstName);
		EditText LastNameText = (EditText) findViewById(R.id.LastName);
		String email = emailText.getText().toString();
		String password = passwordText.getText().toString();
		String password2 = password2Text.getText().toString();
		String firstName = FirstNameText.getText().toString();
		String lastName = LastNameText.getText().toString();
		String username = firstName.concat(lastName);
		String entry = email.concat(";".concat(password.concat(";".concat(username))));
		if (DBMethods.validateEmail(email)) { //check if email is good
			if (password.equals(password2)) { //see if the passwords supplied match
				if(!password.contains(";") && !username.contains(";")){ //makes sure neither the username nor password has a semicolon in it
				DBMethods.addUser(entry);
				Context context = getApplicationContext();
				CharSequence text = "Account Created";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				this.finish();
				}
				else{
					Context context = getApplicationContext(); //use a toast to notify user of invalid username or password
					CharSequence text = "Username or Password invalid";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
			else{
				Context context = getApplicationContext(); //use a toast to notify user of non-matching passwords.
				CharSequence text = "Passwords do not match";
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

}
