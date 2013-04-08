package edu.drake.teamthink.db;
import android.content.Context;
import android.os.AsyncTask;

import com.jcraft.jsch.*;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
	public static boolean validateEmail(String email) {
		// BYRON: modified to speed up debugging...
		return true;
		/* Pattern emailPattern = Pattern.compile("\\b(^[_A-Za-z0-9-]+" +
						"(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])" +
						"+((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|(\\.mil)|" +
						"(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\.aero)|" +
						"(\\.arpa)|(\\.coop)|(\\.int)|(\\.jobs)|(\\.museum)|(\\.name)|" +
						"(\\.pro)|(\\.travel)|(\\.nato)|(\\..{2,3})|(\\..{2,3}\\..{2,3}))$)\\b"); //regular expression for email address http://struts.apache.org/release/2.0.x/struts2-core/apidocs/com/opensymphony/xwork2/validator/validators/EmailValidator.html
				Matcher m = emailPattern.matcher(email); //try to match pattern to email
				return m.matches(); //if it matches, return true */

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

		System.out.println("Hello");
		JSch jsch=new JSch();
		String user="asapp";
		String host="artsci.drake.edu";
		String pwd="9Gj24!L6c848FG$";
		int port=22;
		try {
			Session session=jsch.getSession(user, host, port);
			JSch.setConfig("StrictHostKeyChecking", "no");
			session.setPassword("9Gj24!L6c848FG$");
			session.connect();
			Channel channel=session.openChannel("sftp");
			channel.connect();
			ChannelSftp c=(ChannelSftp)channel;

			Note newNote = new Note();
			newNote.setAuthor("hey");
			try {
				newNote.setText(c.pwd());
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newNote.setCreationDate(new Date());
			newNote.setUpVotes(1);
			newNote.setSessionDate(new Date());
			notes.add(newNote);

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		







		// BYRON: modified this to add 30 different notes (just so they can be identified in the List and Detail views)
		/*for(int i=0;i<30;i++) {
			Note newNote = new Note();
			newNote.setAuthor("Person #" + i);
			Date creationDate = new Date();
			creationDate.setYear(creationDate.getYear()+i);
			newNote.setCreationDate(creationDate);
			newNote.setText("Note " + i + " -- In 1554, Belgian monks worked tirelessly to develop a new, heavenly brew...");
			newNote.setSessionDate(creationDate);
			newNote.setUpVotes(30-i);

			notes.add(newNote);
		}*/


		return notes;
	}
	public static void createNote(Note myNote, Context context) {
		JSch jsch=new JSch();
		String user="asapp";
		String host="artsci.drake.edu";
		String pwd="9Gj24!L6c848FG$";
		int port=22;
		try {
			Session session=jsch.getSession(user, host, port);
			JSch.setConfig("StrictHostKeyChecking", "no");
			session.setPassword("9Gj24!L6c848FG$");
			session.connect();
			Channel channel=session.openChannel("sftp");
			channel.connect();
			ChannelSftp c=(ChannelSftp)channel;
			try {
				c.cd("TeamThink");
				Date rightNow = myNote.getCreationDate();
				c.mkdir(rightNow.toString());
				c.cd(rightNow.toString());
				try { 
					String textFilePath = context.getFilesDir().getPath().toString() + "/text.txt";
					File textFile = new File(textFilePath);
					final String text = new String(myNote.getText());
					textFile.createNewFile();
					FileOutputStream fos = new FileOutputStream(textFile);
					OutputStreamWriter osw = new OutputStreamWriter(fos);
					
					osw.write(text);
					osw.flush();
					osw.close();
					
					String authorFilePath = context.getFilesDir().getPath().toString() + "/author.txt";

					File authorFile = new File(authorFilePath);
					authorFile.createNewFile();
					final String author = new String(myNote.getAuthor());

					/* We have to use the openFileOutput()-method
					 * the ActivityContext provides, to
					 * protect your file from others and
					 * This is done for security-reasons.
					 * We chose MODE_WORLD_READABLE, because
					 *  we have nothing to hide in our file */             
					FileOutputStream fOut2 = new FileOutputStream(authorFile);
					OutputStreamWriter osw2 = new OutputStreamWriter(fOut2); 

					// Write the string to the file
					osw2.write(author);

					/* ensure that everything is
					 * really written out and close */
					osw2.flush();
					osw2.close();
					
					String upVoteFilePath = context.getFilesDir().getPath().toString() + "/upVotes.txt";

					
					final int upVotes = myNote.getUpVotes();

					/* We have to use the openFileOutput()-method
					 * the ActivityContext provides, to
					 * protect your file from others and
					 * This is done for security-reasons.
					 * We chose MODE_WORLD_READABLE, because
					 *  we have nothing to hide in our file */  
					
					File upVotesFile = new File(upVoteFilePath);
					upVotesFile.createNewFile();
					FileOutputStream fOut3 = new FileOutputStream(upVotesFile);
					OutputStreamWriter osw3 = new OutputStreamWriter(fOut3); 

					// Write the string to the file
					osw3.write(upVotes);

					/* ensure that everything is
					 * really written out and close */
					osw3.flush();
					osw3.close();
					
					
					String sessionFilePath = context.getFilesDir().getPath().toString() + "/session.txt";

					final String sessionDate = new String(myNote.getSessionDate().toString());

			   
					File sessionFile = new File(sessionFilePath);
					sessionFile.createNewFile();
					FileOutputStream fOut4 = new FileOutputStream(sessionFile);
					OutputStreamWriter osw4 = new OutputStreamWriter(fOut4); 

					// Write the string to the file
					osw4.write(sessionDate);

					/* ensure that everything is
					 * really written out and close */
					osw4.flush();
					osw4.close();
					
					
					c.put(textFilePath, "text.txt");
					c.put(authorFilePath, "author.txt");
					c.put(upVoteFilePath, "upVotes.txt");
					c.put(sessionFilePath, "session.txt");
					
				}
				catch(IOException e) {
					e.printStackTrace();
				}

			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void upvote(Note myNote) {

	}
	
	public static ArrayList<Meeting> getMeetings() {
		ArrayList<Meeting> sessions = new ArrayList<Meeting>();
		
		// dummy sessions
		for (int i=0;i<20;i++) {
			Meeting newSession = new Meeting();
			Date now = new Date();
			now.setDate(now.getDay()+i);
			newSession.setMeetingDate(now);
			if (i%2==0) {
				newSession.setMeetingSummary("Today we talked about this");
			} else {
				newSession.setMeetingSummary("Today we talked about that");
			}
		}
		
		
		return sessions;
	}

	public static Note getMostUpvotedNote(Date session) {
		Note myNote = null;
		return myNote;
	}
	public static void addUser(String s){

	}


}