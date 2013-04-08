package edu.drake.teamthink.frags;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.drake.teamthink.Meeting;
import edu.drake.teamthink.db.DBMethods;

public class SessionListFragment extends ListFragment {
	OnSessionSelectedListener callback;
	
	ArrayList<Meeting> sessions;
	
	public interface OnSessionSelectedListener {
		/** Called by SessionListFragment when a list item is selected */
		public void onSessionSelected(Meeting session);
	}
	
	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

	    sessions = DBMethods.getMeetings();
		
	    ArrayAdapter<Meeting> myAA = new ArrayAdapter<Meeting>(this.getListView().getContext(), android.R.layout.simple_list_item_activated_1, sessions);
	    
		setListAdapter(myAA);
		setListShown(true);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); //allow only one selection
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// make sure the container activity has implemented the callback interface, else throw exception
		try {
			callback = (OnSessionSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnSessionSelectedListener");
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		// notify the parent activity of the selected item, essentially starts the update process
		callback.onSessionSelected(sessions.get(pos));

		// highlight the selected item in the ListView
		getListView().setItemChecked(pos, true);

	}
	
	@Override
	public void onDetach() {
		super.onDetach();
	}
	
}
