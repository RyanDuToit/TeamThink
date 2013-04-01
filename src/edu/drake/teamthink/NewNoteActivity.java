package edu.drake.teamthink;


import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import edu.drake.teamthink.frags.NoteDetailFragment;
import edu.drake.teamthink.frags.NoteListFragment;

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
		
		 MenuItem item = menu.findItem(R.id.save);

		    if (item == null)
		        return true;

		    item.setOnMenuItemClickListener
		    (
		        new MenuItem.OnMenuItemClickListener () 
		        { 
		            public boolean onMenuItemClick(MenuItem item) 
		            { return (saveClicked(item)); }
		        } 
		    ); 
		    
		return true;
	}
	
	
	
	public boolean saveClicked(MenuItem item) {
		EditText text =  (EditText) findViewById(R.id.note_input_text);
		Note myNote = new Note();
		Date myDate = new Date();
		myNote.setAuthor("derp");
		myNote.setText(text.getText().toString());
		myNote.setCreationDate(myDate);
		myNote.setSessionDate(myDate);
		myNote.setUpVotes(0);
		this.finish();
		return true;
	}

}
