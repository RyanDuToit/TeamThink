package edu.drake.teamthink;
import java.util.ArrayList;
import java.util.List;

import edu.drake.teamthink.db.DBMethods;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


public class NoteListFragment extends ListFragment {

	int index = 0;

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		if(savedState!=null) { //if nothing selected already, just choose top note
			index = savedState.getInt("index",0);
		}
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); //allow only one selection
		
		ArrayList<Note> myList = DBMethods.getNotes(); //get all notes in an array list
		
		ArrayAdapter<Note> myAA = new ArrayAdapter<Note>(this.getListView().getContext(),  android.R.layout.simple_list_item_1,myList);  //create an array adapter (this thing interfaces with the listview)
		setListAdapter(myAA); //set list adapter to our array adapter
		setListShown(true); //show the list
		showDetails(index); //call showdetails of the selected index
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		showDetails(pos);
	}
	public void showDetails(int index) {
		//TODO - impliment this method so the note shows on the notedetail fragment
	}


	@Override
	public void onDetach() {
		super.onDetach();
	}

	// May also be triggered from the Activity
	public void updateDetail() {

	}
}
