package edu.drake.teamthink;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import edu.drake.teamthink.db.DBMethods;

public class LoginActivity extends Activity {

	Context context;
	boolean inLogin = false;
	View myView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context = this.getApplicationContext();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	    case R.id.register:
	        Intent intent = new Intent(this, CreateActivity.class);
	        this.startActivity(intent);
	        break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }

	    return true;
	}

	public void submit(View view) throws IOException {
		myView = view;
		EditText emailText = (EditText) findViewById(R.id.emailField); //grab email and pwd
		EditText passwordText = (EditText) findViewById(R.id.passwordField);
		String email = emailText.getText().toString();
		String password = passwordText.getText().toString();
		
		if (DBMethods.validateEmail(email)) { //check if email is good
			if (inLogin == false) {
				UserLogIn uLI = new UserLogIn();
				System.out.println("user is in the login file: ");
				uLI.execute(email,password);
				System.out.println(inLogin);
			}
			
			
		}
		else { //use toast to notify user of bad email address
			Context context = getApplicationContext();
			CharSequence text = "Invalid email address";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
	private class UserLogIn extends AsyncTask<String,Integer,Integer> {
		@Override
		protected Integer doInBackground(String... strings) {
			try {
					System.out.println(strings[0] + "/" + strings[1]);
					if(DBMethods.checkLogin(strings[0],strings[1],context)) { // returns true if they are in the login file
						return 1;
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {	
				inLogin = true;
			}
			if (inLogin) { //see if login was correct
				Intent intent = new Intent(myView.getContext(), NoteActivity.class); //when clicked, pull up an instance of the note screen activity
				startActivity(intent);
			}
			else{
				Context context = getApplicationContext(); //use a toast to notify user of incorrect pwd and email
				CharSequence text = "Invalid email or password";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
			
		}
	}
}
