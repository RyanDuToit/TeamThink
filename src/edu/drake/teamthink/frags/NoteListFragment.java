package edu.drake.teamthink.frags;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import edu.drake.teamthink.Note;
import edu.drake.teamthink.NoteCompareDate;
import edu.drake.teamthink.NoteCompareVotes;
import edu.drake.teamthink.R;
import edu.drake.teamthink.db.DBMethods;


public class NoteListFragment extends ListFragment {
	OnNoteSelectedListener callback; // to communicate back to the parent activity

	int index = 0;
	ArrayList<Note> notes;
	private ListView listView; //getListView();
	Context context;

	public interface OnNoteSelectedListener {
		/** Called by NoteListFragment when a list item is selected (see NoteActivity) */
		public void onNoteSelected(Note note);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// make sure the container activity has implemented the callback interface, else throw exception
		try {
			callback = (OnNoteSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnNoteSelectedListener");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.fragment_notelist, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		
		Activity activity = getActivity();
		if (activity != null) {
			listView = this.getListView();
			context = listView.getContext();
			DownloadNotes downloader = new DownloadNotes();
			downloader.execute(1);
        }
		
	}

	@Override
	public void onStart() {
		super.onStart();
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); //allow only one selection
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		// notify the parent activity of the selected item, essentially starts the update process
		callback.onNoteSelected(notes.get(pos));

		// highlight the selected item in the ListView
		getListView().setItemChecked(pos, true);
	}
	
	public boolean refreshList() {
		/** Calls an AsyncTask to re-pull notes from the web server **/
		try {
			DownloadNotes downloader = new DownloadNotes();
			downloader.execute(1);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void SortList(boolean sortByDate) { //if true, sort by Date, if false sort by votes
		/** Sorts the list of notes based on spinner selection **/
		if(sortByDate)
			Collections.sort(notes, new NoteCompareDate());
		else
			Collections.sort(notes, new NoteCompareVotes());
		
		NoteListBaseAdapter adapt = new NoteListBaseAdapter(context, notes);
		setListAdapter(adapt);
	}

	private class DownloadNotes extends AsyncTask<Integer,Integer,ArrayList<Note> > {
		@Override
		protected ArrayList<Note> doInBackground(Integer... ints) {
			ArrayList<Note> result = new ArrayList<Note>();

			try {
				notes = DBMethods.getNotes(context);
			} catch (Exception e) {
				System.out.println( "Unable to retrieve web page. URL may be invalid.");
				e.printStackTrace();
			}
			return result;
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(ArrayList<Note> result) {
			NoteListBaseAdapter adapt = new NoteListBaseAdapter(context, notes);
			setListAdapter(adapt);
			Spinner spinner = (Spinner) getActivity().findViewById(R.id.sort_spinner);
			int selected = spinner.getSelectedItemPosition();
			if(selected == 0) {
				Collections.sort(notes, new NoteCompareDate());
			}
			else
				Collections.sort(notes, new NoteCompareVotes());
			
		}
	}
}
