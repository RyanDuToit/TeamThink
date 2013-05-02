package edu.drake.teamthink.frags;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import edu.drake.teamthink.Note;
import edu.drake.teamthink.R;
import edu.drake.teamthink.db.DBMethods;


public class NoteDetailFragment extends Fragment {
	//final static String ARG_NOTE = "position";
	Note currentNote;
	ImageButton voteButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_notedetail, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		voteButton = (ImageButton) getActivity().findViewById(R.id.note_vote_button);
		
		voteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onVoteButtonClick(currentNote);
			}
		});
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
	
	public void updateDetail(Note note) throws NotFoundException, IOException {
		/** Given an ArrayList of Note objects (the same as displayed in NoteListFragment), update the Detail view for the selected note */
		
		System.out.println("updateDetail in NoteDetailFragment begin execution");
		
		// get all of the views we can change ( / need to change )
		TextView noteAuthor = (TextView) getActivity().findViewById(R.id.note_author);
		TextView notePostDate = (TextView) getActivity().findViewById(R.id.note_post_date);
		TextView noteContent = (TextView) getActivity().findViewById(R.id.note_content);
		TextView noteUpvotes = (TextView) getActivity().findViewById(R.id.note_upvote_count);

		SimpleDateFormat fmt = new SimpleDateFormat ("E, MMM d, yyyy 'at' h:mm a", Locale.US);

		// with that array of notes, update the Views in our NoteDetailFragment
		noteAuthor.setText(note.getAuthor() + " thinks...");
		noteContent.setText(note.getText());		
		notePostDate.setText(fmt.format(note.getCreationDate())); // TODO: format the date
		if(note.getUpVotes() == 1)
		{
			noteUpvotes.setText("1 brilliance");
		}
		noteUpvotes.setText(note.getUpVotes() + " brilliance");


		if(hasbeenupvoted(note, this.getActivity().getBaseContext()))
		{
			voteButton.setImageDrawable(getResources().getDrawable(R.drawable.brilliant_yes_upvote));
			voteButton.setClickable(false);
		}
		else {
			voteButton.setImageDrawable(getResources().getDrawable(R.drawable.brilliant_no_upvote));
			voteButton.setClickable(true);
		}
		currentNote = note;
	}

	boolean hasbeenupvoted(Note note, Context context) throws IOException
	{
		String dateString = note.getCreationDate().toString().replace(" ", "_"); 

		String filePath = context.getFilesDir().getPath().toString() + "/notesvoted.txt";
		File file = new File(filePath);
		if(!file.exists()) //if file does not exist, create it
		{
			file.createNewFile();
		}
		FileInputStream fis = new FileInputStream(file);
		Scanner scan = new Scanner(fis);
		while(scan.hasNext()) {
			if(scan.next().equals(dateString))
				return true;
		}
		return false;
	}
	public void onVoteButtonClick(Note note) {
		// TODO: implement what happens when they click the upvote button (part of this Fragment)
		try {
			UpVoteNotes upvoter = new UpVoteNotes();
			int count = note.getUpVotes();
			upvoter.execute(note);
			voteButton.setImageDrawable(getResources().getDrawable(R.drawable.brilliant_yes_upvote));
			voteButton.setClickable(false);
			TextView noteUpvotes = (TextView) getActivity().findViewById(R.id.note_upvote_count);
			if(count == 0) {
				noteUpvotes.setText((count+1) +" brilliance");
			}
			else {
				noteUpvotes.setText((count+1) +" brilliance");
			}

		}
		catch(Exception e){
			System.out.println(e.getStackTrace());
		}

	}


	private class UpVoteNotes extends AsyncTask<Note,Integer,Note > {
		@Override
		protected Note doInBackground(Note... notes) {
			Note note = notes[0];
			try {
				currentNote.setUpVotes(currentNote.getUpVotes()+1);
				DBMethods.upvote(currentNote, getActivity().getBaseContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return note;
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(Note note) {


			String dateString = note.getCreationDate().toString().replace(" ", "_"); 
			String filePath = getActivity().getBaseContext().getFilesDir().getPath().toString() + "/notesvoted.txt";
			File file = new File(filePath);
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				FileWriter fw = new FileWriter(filePath ,true); //the true will append the new data
				fw.write(dateString+"\n");//appends the string to the file
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}


}
