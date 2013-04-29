package edu.drake.teamthink;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
		if (DBMethods.isNetworkConnected(context)) {
			if (DBMethods.validateEmail(email)) { //check if email is good
				if (inLogin == false) {
					UserLogIn uLI = new UserLogIn();
					System.out.println("user is in the login file: ");
					uLI.execute(email,password);
				  
					System.out.println(inLogin);
				}


			}
			else { //use toast to notify user of bad email address
				CharSequence text = "Invalid email address";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}
		else {
			CharSequence text = "Check Internet Connection";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

	private class UserLogIn extends AsyncTask<String,Integer,String> {
		@Override
		protected String doInBackground(String... strings) {
			String result;
			try {
				System.out.println(strings[0] + "/" + strings[1]);
				return DBMethods.checkLogin(strings[0],strings[1],context);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		@Override
		protected void onPostExecute(String result) { //result = UserName
			if (!result.equals("")) {	
				inLogin = true;
			}
			if (inLogin) { //see if login was correct
				Intent intent = new Intent(myView.getContext(), NoteActivity.class); //when clicked, pull up an instance of the note screen activity

				//Create login file that is used for authorname
				String userNameFilePath = context.getFilesDir().getPath().toString() + "/currentUser.txt";
				//File userNameFile = new File(userNameFilePath);
				//userNameFile.createNewFile();
				FileWriter fw = null;
				try {
					fw = new FileWriter(userNameFilePath);
					fw.write(result);
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				startActivity(intent);
				finish(); // closes the login activity; when the user presses back from the Notes activity, the app closes to the home screen
			}
			else{
				CharSequence text = "Invalid email or password";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}
	}
}
