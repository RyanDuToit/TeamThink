package edu.drake.teamthink.frags;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import android.app.Activity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.drake.teamthink.Note;
import edu.drake.teamthink.NoteCompareDate;
import edu.drake.teamthink.NoteCompareVotes;
import edu.drake.teamthink.R;
import edu.drake.teamthink.db.DBMethods;

public class NoteListFragment extends ListFragment {
	OnNoteSelectedListener callback; // this basically lets the fragment communicate to the other fragment THROUGH the parent activity

	int index = 0;
	ArrayList<Note> notes;
	private ListView listView;

	public interface OnNoteSelectedListener {
		/** Called by NoteListFragment when a list item is selected (see NoteActivity) */
		public void onNoteSelected(Note note);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		if (savedState!=null) { //if nothing selected already, just choose top note
			index = savedState.getInt("index", 0);
		}
		listView=this.getListView();

		DownloadNotes downloader = new DownloadNotes();
		downloader.execute(1);


		// BYRON: changed from simple_list_item_1 to simple_list_item_activated_1 to enable highlighting of the selected note (in the list)
		//ArrayAdapter<Note> myAA = new ArrayAdapter<Note>(this.getListView().getContext(), android.R.layout.simple_list_item_activated_1, notes);  //create an array adapter (this thing interfaces with the listview)

		//Collections.sort(notes, new NoteCompareDate()); //sort array by date

		//setListAdapter(myAA); //set list adapter to our array adapter
		//setListShown(true); //show the list; BYRON: Do we need this? Commenting it out during debugging didn't seem to change anything for me
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
		callback.onNoteSelected(notes.get(pos));

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

	public void SortList(boolean sortByDate) { //if true, sort by Date, if false sort by votes
		if(sortByDate)
			Collections.sort(notes, new NoteCompareDate());
		else
			Collections.sort(notes, new NoteCompareVotes());

		ArrayAdapter<Note> myAA = new ArrayAdapter<Note>(this.getListView().getContext(), android.R.layout.simple_list_item_activated_1, notes);  //create an array adapter (this thing interfaces with the listview)
		setListAdapter(myAA); //set list adapter to our array adapter
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}


	private class DownloadNotes extends AsyncTask<Integer,Integer,ArrayList<Note> > {
		@Override
		protected ArrayList<Note> doInBackground(Integer... ints) {
			ArrayList<Note> result = new ArrayList<Note>();

			try {
				notes = DBMethods.getNotes();
			} catch (Exception e) {
				System.out.println( "Unable to retrieve web page. URL may be invalid.");
				e.printStackTrace();
			}
			return result;
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(ArrayList<Note> result) {
			ArrayAdapter<Note> myAA = new ArrayAdapter<Note>(listView.getContext(), android.R.layout.simple_list_item_activated_1, notes);  //create an array adapter (this thing interfaces with the listview)
			setListAdapter(myAA);
		}
	}
}
