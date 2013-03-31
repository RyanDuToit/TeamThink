package edu.drake.teamthink.frags;
import java.util.ArrayList;
import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.drake.teamthink.Note;
import edu.drake.teamthink.R;


public class NoteDetailFragment extends Fragment {
	final static String ARG_POSITION = "position";
	int currentPosition = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			currentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
		return inflater.inflate(R.layout.fragment_notedetail, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// At startup, we should check for arguments passed to the fragment
		Bundle args = getArguments();
		if (args != null) {
			// set based on argument passed in
			updateDetail(args.getInt(ARG_POSITION));
		} else if (currentPosition != -1) {
			// set based on saved instance state defined during onCreateView
			updateDetail(currentPosition);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		/** Save the current position so that on changes like screen orientation switch (vert to horizontal) it keeps the same detail pulled up */
		super.onSaveInstanceState(outState);
		
		// save the current selection
		outState.putInt(ARG_POSITION, currentPosition);
	}
	
	// BYRON: This is triggered through the Activity by its implementation of the listener defined in NoteListFragment
	public void updateDetail(int position) {
		/** Given an ArrayList of Note objects (the same as displayed in NoteListFragment), update the Detail view for the selected note */
		
		// get all of the views we can change ( / need to change )
		TextView noteAuthor = (TextView) getActivity().findViewById(R.id.note_author);
		TextView notePostDate = (TextView) getActivity().findViewById(R.id.note_post_date);
		TextView noteContent = (TextView) getActivity().findViewById(R.id.note_content);
		TextView noteUpvotes = (TextView) getActivity().findViewById(R.id.note_upvote_count);

		// BYRON: here, we get the Notes to work with, this might be where we'd query the DB to get the list of notes?
		
		// 		...instead, for now, we just create a list to work with
		// --------Copied from DBMethods--------
		ArrayList<Note> notes = new ArrayList<Note>();
		for(int i=0;i<30;i++) {
			Note newNote = new Note();
			newNote.setAuthor("Person #" + i);
			Date creationDate = new Date();
			newNote.setCreationDate(creationDate);
			// BYRON: I think we should rename .setText in the Note class to .setContent; help differentiate from our class and Android functions
			newNote.setText("Note " + i + " -- In 1554, Belgian monks worked tirelessly to develop a new, heavenly brew...");
			newNote.setSessionDate(creationDate);
			newNote.setUpVotes(i);

			notes.add(newNote);
		} // ----------------End----------------
		
		// with that array of notes, update the Views in our NoteDetailFragment
		noteAuthor.setText(notes.get(position).getAuthor());
		noteContent.setText(notes.get(position).getText());		
		notePostDate.setText("on " + notes.get(position).getCreationDate()); // TODO: format the date
		noteUpvotes.setText(notes.get(position).getUpVotes() + " Upvotes");
		
		currentPosition = position;
	}
	
	public void onVoteButtonClick(int position) {
		// TODO: implement what happens when they click the upvote button (part of this Fragment)
	}
}