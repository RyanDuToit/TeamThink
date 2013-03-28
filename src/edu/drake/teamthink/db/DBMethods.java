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
		return notes;
	}
	public static ArrayList<Note> getNotes() { //return all notes
		ArrayList<Note> notes = new ArrayList<Note>(); 
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