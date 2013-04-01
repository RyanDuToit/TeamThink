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
	//final static String ARG_NOTE = "position";
	Note currentNote;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//if (savedInstanceState != null) {
		//	currentNote = savedInstanceState.getNote(ARG_POSITION);
		//}
		return inflater.inflate(R.layout.fragment_notedetail, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		/*
		// At startup, we should check for arguments passed to the fragment
		Bundle args = getArguments();
		if (args != null) {
			// set based on argument passed in
			updateDetail(args.getInt(ARG_POSITION));
		} else if (currentPosition != -1) {
			// set based on saved instance state defined during onCreateView
			updateDetail(currentPosition);
		}
		*/
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
		//outState.put(ARG_POSITION, currentPosition);
	}
	
	// BYRON: This is triggered through the Activity by its implementation of the listener defined in NoteListFragment
	public void updateDetail(Note note) {
		/** Given an ArrayList of Note objects (the same as displayed in NoteListFragment), update the Detail view for the selected note */
		
		// get all of the views we can change ( / need to change )
		TextView noteAuthor = (TextView) getActivity().findViewById(R.id.note_author);
		TextView notePostDate = (TextView) getActivity().findViewById(R.id.note_post_date);
		TextView noteContent = (TextView) getActivity().findViewById(R.id.note_content);
		TextView noteUpvotes = (TextView) getActivity().findViewById(R.id.note_upvote_count);


		
		// with that array of notes, update the Views in our NoteDetailFragment
		noteAuthor.setText(note.getAuthor());
		noteContent.setText(note.getText());		
		notePostDate.setText("on " + note.getCreationDate()); // TODO: format the date
		noteUpvotes.setText(note.getUpVotes() + " Upvotes");
		
		currentNote = note;
	}
	
	public void onVoteButtonClick(int position) {
		// TODO: implement what happens when they click the upvote button (part of this Fragment)
	}
}