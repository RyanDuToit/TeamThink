package edu.drake.teamthink.frags;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.drake.teamthink.Note;
import edu.drake.teamthink.R;
import edu.drake.teamthink.db.DBMethods;

public class NoteListFragment extends ListFragment {
	OnNoteSelectedListener callback; // this basically lets the fragment communicate to the other fragment THROUGH the parent activity

	int index = 0;

	public interface OnNoteSelectedListener {
		/** Called by NoteListFragment when a list item is selected (see NoteActivity) */
		public void onNoteSelected(int position);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		if (savedState!=null) { //if nothing selected already, just choose top note
			index = savedState.getInt("index", 0);
		}

		ArrayList<Note> myList = DBMethods.getNotes(); //get all notes in an array list
		// BYRON: changed from simple_list_item_1 to simple_list_item_activated_1 to enable highlighting of the selected note (in the list)
		ArrayAdapter<Note> myAA = new ArrayAdapter<Note>(this.getListView().getContext(), android.R.layout.simple_list_item_activated_1, myList);  //create an array adapter (this thing interfaces with the listview)
		setListAdapter(myAA); //set list adapter to our array adapter
		setListShown(true); //show the list; BYRON: Do we need this? Commenting it out during debugging didn't seem to change anything for me
	}

	@Override
	public void onStart() {
		super.onStart();

		// BYRON: moved from onActivityCreated to ensure the list view is available when it tries to set the choice mode
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); //allow only one selection
	}


	@Override
	public void onAttach(Activity activity) {
		/* BYRON: added onAttach because the official documentation's example said so :) */
		super.onAttach(activity);

		// make sure the container activity has implemented the callback interface, else throw exception
		try {
			callback = (OnNoteSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnNoteSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		// notify the parent activity of the selected item, essentially starts the update process
		callback.onNoteSelected(pos);

		// highlight the selected item in the ListView
		getListView().setItemChecked(pos, true);

		// BYRON: Shouldn't need to do this. In fact, if we get rid of it, it still works
		//showDetails(pos);
	}
	public void showDetails(int index) {
		//TODO - implement this method so the note shows on the notedetail fragment
		// BYRON - I don't think we need this. I think this happens in the NoteDetailFragment only.
		//			(by way of the OnNoteSelectedListener in NoteListFrag and the NoteActivity)
	}


	@Override
	public void onDetach() {
		super.onDetach();
	}
}