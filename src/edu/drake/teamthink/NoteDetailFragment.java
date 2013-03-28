package edu.drake.teamthink;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class NoteDetailFragment extends Fragment {

	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    TextView text = new TextView(getActivity());
	    text.setText("YOLO");
	    return text;
	  }



	  @Override
	  public void onDetach() {
	    super.onDetach();
	  }

	  // May also be triggered from the Activity
	  public void updateDetail() {

	  }
}