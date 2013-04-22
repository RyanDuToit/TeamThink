package edu.drake.teamthink;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.drake.teamthink.db.DBMethods;

public class NewNoteActivity extends Activity {
	public String userLoggedIn = "Anonymous"; // TODO: store the currently logged in username here.. or just in general
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		context = this.getApplicationContext();
		try {
			Scanner in = new Scanner(new FileReader(context.getFilesDir().getPath().toString() + "/currentUser.txt"));
			userLoggedIn = in.nextLine();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	    case R.id.save:
	        saveClicked();
	        break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }

	    return true;
	}

	public boolean saveClicked() { // what do we need to pass it here?
		EditText text =  (EditText) findViewById(R.id.note_input_text);
		if (text.getText().toString().equals("")) {
			System.out.print("empty text");
			CharSequence toastText = "Think again...";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, toastText, duration);
			toast.setGravity(Gravity.TOP, 0, 100);
			toast.show();
			return false;
		}
		else {
			Note myNote = new Note();
			Date myDate = new Date();
			
			myNote.setAuthor(userLoggedIn);
			myNote.setText(text.getText().toString());
			myNote.setCreationDate(myDate);
			myNote.setSessionDate(myDate);
			myNote.setUpVotes(0);
			UploadNote upNote = new UploadNote();
			upNote.execute(myNote);
			
			doneSaving();
			return true;
		}
	}

	public boolean doneSaving() {
		CharSequence toastText = "Good thinking!";
		int duration = Toast.LENGTH_LONG;
		
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.lightbulb_toast_view, (ViewGroup) findViewById(R.id.toast_layout_root));
		
		TextView text = (TextView) layout.findViewById(R.id.toast_text);
		text.setText(toastText);
		
		Toast toast = new Toast(context);
		toast.setView(layout);
		toast.setDuration(duration);
		toast.show();
		
		this.finish();
		
		return true;
	}
	
	private class UploadNote extends AsyncTask<Note,Integer,Integer> {
		@Override
		protected Integer doInBackground(Note... notes) {
			try {
				for(Note newNote : notes)
					DBMethods.createNote(newNote,context);
			} catch (Exception e) {
				System.out.println( "Unable to retrieve web page. URL may be invalid.");
				e.printStackTrace();
			}
			return 0;
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(Integer result) {
			doneSaving();
		}
	}
}
