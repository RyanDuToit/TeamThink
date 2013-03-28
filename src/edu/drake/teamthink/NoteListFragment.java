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
		if(savedState!=null) {
			index = savedState.getInt("index",0);
		}
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		ArrayList<String> myList = new ArrayList<String>();
		
		//dummy list
		myList.add("hashtag");
		myList.add("yolo");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("hashtag");
		myList.add("yolo");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("hashtag");
		myList.add("yolo");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("hashtag");
		myList.add("yolo");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		myList.add("lol");
		
		ArrayAdapter<String> myAA = new ArrayAdapter<String>(this.getListView().getContext(),  android.R.layout.simple_list_item_1,myList);
		setListAdapter(myAA);
		setListShown(true);
		showDetails(index);
	}

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notelist,
				container, false);
		return view;
	}*/
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		showDetails(pos);
	}
	public void showDetails(int index) {
	}


	@Override
	public void onDetach() {
		super.onDetach();
	}

	// May also be triggered from the Activity
	public void updateDetail() {

	}
}