package edu.drake.teamthink.db;
import java.util.ArrayList;
import java.util.Date;

import edu.drake.teamthink.*;
public class DBMethods {
	public static boolean checkLogin(String username, String password){
		return true;
	}
	public static boolean userExists(String username) {
		return false;
	}
	public static ArrayList<Note> getNotes(Date session) { //return notes from a session
		ArrayList<Note> notes = new ArrayList<Note>(); 
		//dummy note (should pull from db later
		Note newNote = new Note();
		newNote.setAuthor("a person");
		Date creationDate = new Date();
		newNote.setCreationDate(creationDate);
		newNote.setText("hello, I am a person writing a random note, I hope you enjoy reading this note that I wrote");
		newNote.setSessionDate(session);
		newNote.setUpVotes(42);
		
		for(int i=0;i<10;i++)
			notes.add(newNote);
		
		return notes;
	}
	public static ArrayList<Note> getNotes() { //return all notes
		ArrayList<Note> notes = new ArrayList<Note>(); 
		//dummy note (should pull from db later)
		
		// BYRON: modified this to add 30 different notes (just so they can be identified in the List and Detail views)
		for(int i=0;i<30;i++) {
			Note newNote = new Note();
			newNote.setAuthor("Person #" + i);
			Date creationDate = new Date();
			creationDate.setYear(creationDate.getYear()+i);
			newNote.setCreationDate(creationDate);
			newNote.setText("Note " + i + " -- In 1554, Belgian monks worked tirelessly to develop a new, heavenly brew...");
			newNote.setSessionDate(creationDate);
			newNote.setUpVotes(30-i);
			
			notes.add(newNote);
		}
			
		
		return notes;
	}
	public static void createNote() {
		
	}
	public static void upvote(Note myNote) {
		
	}
	
	public static ArrayList<Date> getCourseSessions() {
		ArrayList<Date> sessions = new ArrayList<Date>();
		return sessions;
	}
	
	public static Note getMostUpvotedNote(Date session) {
		Note myNote = null;
		return myNote;
	}
}