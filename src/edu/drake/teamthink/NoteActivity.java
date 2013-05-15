package edu.drake.teamthink;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.drake.teamthink.frags.NoteDetailFragment;
import edu.drake.teamthink.frags.NoteListFragment;
import edu.drake.teamthink.frags.TeamListFragment;

public class NoteActivity extends Activity implements NoteListFragment.OnNoteSelectedListener, TeamListFragment.OnTeamSelectedListener {
	boolean initdone = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master_detail);

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		/* Initialize left container */
		if (findViewById(R.id.left_container) != null) {
			TeamListFragment teamList = new TeamListFragment();
			transaction.add(R.id.left_container, teamList);
			System.out.println("list should be added...");
		}

		/* Initialize right container */
		if (findViewById(R.id.right_container) != null) {
			NoteListFragment noteList = new NoteListFragment();
			transaction.add(R.id.right_container, noteList);
			System.out.println("detail should be added...");
		}

		transaction.commit();

		TextView container_header = (TextView) findViewById(R.id.header_text);
		container_header.setText("Teams");

		Spinner spinner = (Spinner) findViewById(R.id.sort_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_list, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setVisibility(View.INVISIBLE);


		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				if(initdone) {
					NoteListFragment list = (NoteListFragment) getFragmentManager().findFragmentById(R.id.left_container);
					switch (position) {
					case 0:
						list.SortList(true);
						break;
					case 1:
						list.SortList(false);
						break;
					default:
						break;
					}
				}
				else {
					initdone = true;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				return;
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.new_note:
			Intent intent = new Intent(this, NewNoteActivity.class);
			this.startActivity(intent);
			break;
		case R.id.refresh:
			try{
				NoteListFragment listfrag = (NoteListFragment) getFragmentManager().findFragmentById(R.id.left_container);
				listfrag.refreshList();
			}
			catch(Exception e) {
				CharSequence text = "You must select a note before refreshing";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getBaseContext(), text, duration);
				toast.setGravity(Gravity.TOP, 0, 100);
				toast.show();
			}
		case R.id.menu_logout:
			Intent intentLogin = new Intent(this, LoginActivity.class);
			this.startActivity(intentLogin);
			this.finish(); // close the activity (goes to home screen)
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	public void onNoteSelected(Note note) {
		/** The user selected a note in the NoteListFragment list (this is why we "implements NLF.OnNoteSelectedListener) */
		// capture the detail fragment from the activity layout

		try { 
			NoteDetailFragment detail = (NoteDetailFragment) getFragmentManager().findFragmentById(R.id.right_container);
			// Call updateDetail method in NoteDetailFragment to update the content
			if (detail != null) {
				try {
					detail.updateDetail(note);
				} catch (NotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (ClassCastException e) {
			// note list is in right container

			
			Spinner spinner = (Spinner) findViewById(R.id.sort_spinner);
			spinner.setVisibility(View.VISIBLE); //set spinner for sorting to be visible

			
			// update header text
			TextView container_header = (TextView) findViewById(R.id.header_text);
			container_header.setText("Thoughts");

			// create or grab fragments
			NoteListFragment list = new NoteListFragment();
			NoteDetailFragment detail = new NoteDetailFragment();

			NoteListFragment oldList = (NoteListFragment) getFragmentManager().findFragmentById(R.id.right_container);
			list.setCurrentTeam(oldList.getCurrentTeam());

			FragmentTransaction transaction = getFragmentManager().beginTransaction();

			// replace containers with new fragments
			transaction.replace(R.id.left_container, list);
			transaction.replace(R.id.right_container, detail);
			transaction.addToBackStack(null);
			transaction.commit();
			getFragmentManager().executePendingTransactions();

			System.out.println("Transaction completed");

			try {
				detail.updateDetail(note);
			} catch (NotFoundException nf) {
				nf.printStackTrace();
			} catch (IOException io) {
				io.printStackTrace();
			}
		}


	}

	public void onCreateTeam() {
		final Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.dialog_createteam);
		dialog.setTitle("Create a new team");

		final EditText teamNameEdit = (EditText) dialog.findViewById(R.id.teamNameEdit);
		Button btnCreate = (Button) dialog.findViewById(R.id.buttonCreate);
		btnCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				TeamListFragment teamList = (TeamListFragment) getFragmentManager().findFragmentById(R.id.left_container);
				String teamName = teamNameEdit.getText().toString();
				if (teamName.equalsIgnoreCase("")) {
					// do nothing because they haven't entered anything... TODO: Toast here
				} else {
					teamList.teamNameEntered(teamName);
					dialog.dismiss();
				}
			}
		});
		Button btnCancel=(Button)dialog.findViewById(R.id.buttonCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v) {
				dialog.cancel();
			}
		});
		dialog.show();


		
	}

	public void teamlistLoaded() {
		Spinner spinner = (Spinner) findViewById(R.id.sort_spinner);

		spinner.setVisibility(View.INVISIBLE);
		
		TextView container_header = (TextView) findViewById(R.id.header_text);
		container_header.setText("Teams");
		
		
	}
	
	public void onTeamSelected(String team) {
		NoteListFragment noteList = (NoteListFragment) getFragmentManager().findFragmentById(R.id.right_container);		
		if(noteList != null) {
			try{
				noteList.teamSelected(team);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addNote(View view) {
		Intent intent = new Intent(view.getContext(), NewNoteActivity.class); //when clicked, pull up an instance of the note screen activity
		startActivity(intent);
	}
}
