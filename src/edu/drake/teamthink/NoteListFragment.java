package edu.drake.teamthink;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class NoteListFragment extends Fragment {

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_notelist,
	        container, false);
	    return view;
	  }



	  @Override
	  public void onDetach() {
	    super.onDetach();
	  }

	  // May also be triggered from the Activity
	  public void updateDetail() {

	  }
}