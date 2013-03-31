package edu.drake.teamthink;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import edu.drake.teamthink.frags.*;

public class NoteActivity extends Activity implements NoteListFragment.OnNoteSelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_note, menu);
		return true;
	}
	
	public void onNoteSelected(int position) {
		/** The user selected a note in the NoteListFragment list (this is why we "implements NLF.OnNoteSelectedListener) */
		
		// capture the detail fragment from the activity layout
		NoteDetailFragment detail = (NoteDetailFragment) getFragmentManager().findFragmentById(R.id.detail_fragment);
		
		// Call updateDetail method in NoteDetailFragment to update the content
		if (detail != null) {
			detail.updateDetail(position);
		}
	}

}
