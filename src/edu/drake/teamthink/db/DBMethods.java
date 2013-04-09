package edu.drake.teamthink.db;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateFormat;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

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
	public static ArrayList<Note> getNotes(Context context) throws IOException, ParseException { //return all notes
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

			try {
				c.cd("TeamThink");
				Vector<LsEntry> notedirs = c.ls(".");
				System.out.println(notedirs.size());
				for(int i = 2; i < notedirs.size(); i++) {
					Note newNote = new Note();
					System.out.println(i);
	                c.cd(notedirs.get(i).getFilename());
					System.out.println(c.pwd());
	                
					String textFilePath = context.getFilesDir().getPath().toString() + "/textin.txt";
					File textFile = new File(textFilePath);
					textFile.createNewFile();
					FileOutputStream fos = new FileOutputStream(textFile);
					c.get("text.txt", fos);
					fos.close();
					FileInputStream fis = new FileInputStream(textFile);
					Scanner scan = new Scanner(fis);
					String text = "";
					while(scan.hasNext()) {
						text+=scan.next()+" ";
					}
					newNote.setText(text);
					System.out.println(text);
					
					String authorFilePath = context.getFilesDir().getPath().toString() + "/authorin.txt";
					File authorFile = new File(authorFilePath);
					authorFile.createNewFile();
					FileOutputStream fos2 = new FileOutputStream(authorFile);
					c.get("author.txt", fos2);
					fos2.close();
					FileInputStream fis2 = new FileInputStream(authorFile);
					Scanner scan2 = new Scanner(fis2);
					String author = "";
					while(scan2.hasNext()) {
						author+=scan2.next();
					}
					newNote.setAuthor(author);
					
					
					String upFilePath = context.getFilesDir().getPath().toString() + "/upvotesin.txt";
					File upFile = new File(upFilePath);
					upFile.createNewFile();
					FileOutputStream fos3 = new FileOutputStream(upFile);
					c.get("upVotes.txt", fos3);
					fos3.close();
					FileInputStream fis3 = new FileInputStream(upFile);
					Scanner scan3 = new Scanner(fis3);
					int upVotes = 0;
					//while(scan3.hasNext()) {
						//upVotes=scan3.nextInt();
					//}  //TODO-something wrong with upvotes
					newNote.setUpVotes(upVotes);
					
					
					
					String meetingFilePath = context.getFilesDir().getPath().toString() + "/meetingin.txt";
					File meetingFile = new File(meetingFilePath);
					meetingFile.createNewFile();
					FileOutputStream fos4 = new FileOutputStream(meetingFile);
					c.get("session.txt", fos4);
					fos.close();
					FileInputStream fis4 = new FileInputStream(meetingFile);
					Scanner scan4 = new Scanner(fis4);
					String meeting = "";
					while(scan4.hasNext()) {
						meeting+=scan4.next();
					}
					newNote.setSessionDate(new Date()); //TODO-Fix this
					
					
					String date = notedirs.get(i).getFilename();
					SimpleDateFormat df = new SimpleDateFormat("E_MMM_d_hh:mm:ss_z_yyyy");
					newNote.setCreationDate(df.parse(date)); //TODO -Fix this
					notes.add(newNote);
					c.cd("..");
					System.out.println(c.pwd());
				}
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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
				String rightNowString = rightNow.toString().replace(" ", "_"); 
				c.mkdir(rightNowString);
				c.cd(rightNowString);
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