package edu.drake.teamthink;


import java.util.ArrayList;
import java.util.Date;

import edu.drake.teamthink.db.DBMethods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class NewNoteActivity extends Activity {

	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		context = this.getApplicationContext();
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
	    case R.id.cancel:
	        this.finish();
	        break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }

	    return true;
	}


	public boolean saveClicked() { // what do we need to pass it here?
		EditText text =  (EditText) findViewById(R.id.note_input_text);
		Note myNote = new Note();
		Date myDate = new Date();
		
		myNote.setAuthor("derp");
		myNote.setText(text.getText().toString());
		myNote.setCreationDate(myDate);
		myNote.setSessionDate(myDate);
		myNote.setUpVotes(0);
		UploadNote upNote = new UploadNote();
		upNote.execute(myNote);
		return true;
	}

	public boolean doneSaving() {
		Context context = getApplicationContext(); //use a toast to notify user of incorrect pwd and email
		CharSequence toastText = "Good thinking!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, toastText, duration);
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
