package edu.drake.teamthink;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import edu.drake.teamthink.frags.SessionListFragment;
import edu.drake.teamthink.frags.NoteDetailFragment;
import edu.drake.teamthink.frags.NoteListFragment;

public class NoteActivity extends Activity implements NoteListFragment.OnNoteSelectedListener, SessionListFragment.OnSessionSelectedListener {
	boolean initdone = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		
		Spinner spinner = (Spinner) findViewById(R.id.sort_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.sort_list, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				if(initdone) {
					NoteListFragment list = (NoteListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);
					switch (position) {
					case 0:
						list.SortList(true);
						break;
					case 1:
						list.SortList(false);
						break;
					default:
						break;
					}
				}
				else {
					initdone = true;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				return;
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_note, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	    case R.id.new_note:
	        Intent intent = new Intent(this, NewNoteActivity.class);
	        this.startActivity(intent);
	        break;
	    case R.id.refresh:
	        // refresh from DB
	        break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }

	    return true;
	}

	public void onNoteSelected(Note note) {
		/** The user selected a note in the NoteListFragment list (this is why we "implements NLF.OnNoteSelectedListener) */

		// capture the detail fragment from the activity layout
		NoteDetailFragment detail = (NoteDetailFragment) getFragmentManager().findFragmentById(R.id.detail_fragment);

		// Call updateDetail method in NoteDetailFragment to update the content
		if (detail != null) {
			try {
				detail.updateDetail(note);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onSessionSelected(Meeting session) {
		// TODO use fragment manager to replace detail view with a note list
		
	}
	
	public void addNote(View view) {
		Intent intent = new Intent(view.getContext(), NewNoteActivity.class); //when clicked, pull up an instance of the note screen activity
		startActivity(intent);
	}

}
