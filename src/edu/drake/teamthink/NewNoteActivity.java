package edu.drake.teamthink;


import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NewNoteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
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
		
		Context context = getApplicationContext();
		CharSequence toastText = "Good thinking!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, toastText, duration);
		toast.show();
		
		this.finish();
		
		return true;
	}

}
