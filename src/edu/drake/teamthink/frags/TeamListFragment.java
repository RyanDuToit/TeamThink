package edu.drake.teamthink.frags;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import edu.drake.teamthink.R;
import edu.drake.teamthink.db.DBMethods;

public class TeamListFragment extends ListFragment {
	OnTeamSelectedListener callback; // to communicate back to the parent activity

	int index = 0;
	ArrayList<String> teams;
	private ListView listView;
	Context context;

	public interface OnTeamSelectedListener {
		/** Called by NoteListFragment when a list item is selected (see NoteActivity) */
		public void onTeamSelected(String team);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// make sure the container activity has implemented the callback interface, else throw exception
		try {
			callback = (OnTeamSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnTeamSelectedListener");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.fragment_teamlist, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		
		Activity activity = getActivity();
		if (activity != null) {
			listView = this.getListView();
			context = listView.getContext();
			System.out.println("in this area");
			DownloadTeams downloader = new DownloadTeams();
			downloader.execute(1);
			listView.setSelector(R.drawable.listitem_selector);
        }
	}

	@Override
	public void onStart() {
		super.onStart();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); //allow only one selection
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		callback.onTeamSelected(teams.get(pos));
		l.setItemChecked(pos, true);
	}
	private class DownloadTeams extends AsyncTask<Integer, Integer, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			
			try {
				teams = DBMethods.getTeams();
			}
			catch (Exception e)  {
				e.printStackTrace();
			}
			
			return teams;
		}
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			TeamListBaseAdapter adapt = new TeamListBaseAdapter(context, teams);
			setListAdapter(adapt);
		}
	}
	
}