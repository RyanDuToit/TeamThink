package edu.drake.teamthink.db;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import edu.drake.teamthink.*;

public class DBMethods {
	public static String currentTeam = "";
	public static String checkLogin(String email, String password, Context context) throws IOException {
		String[] logins = new String[400];

		JSch jsch=new JSch();
		String user="asapp";
		String host="artsci.drake.edu";
		String pwd="9Gj24!L6c848FG$";
		int port=22;

		try {
			Session session=jsch.getSession(user, host, port);
			JSch.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pwd);
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp c = (ChannelSftp) channel;
			System.out.println("Made it to channel open");

			try {
				c.cd("TeamThink");

				String FILENAME = "login.csv";
				FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
				c.get("login.csv", fos);
				fos.close();

				FileInputStream fis = context.openFileInput("login.csv");
				Scanner scan = new Scanner(fis);

				int i = 0;

				while(scan.hasNext()) {
					logins[i] = scan.nextLine(); // put next line in the logins string array
					i++;
				}

				// Split current line into its parts, make comparison, iterate through all logins until match
				String currEmail = "";
				String currPassword = "";
				String currUsername = "";

				for(int j = 0; j < logins.length; j++) {
					String[] currentLine = logins[j].split(";");
					currEmail = currentLine[0];
					currPassword = currentLine[1];
					currUsername = currentLine[2];

					System.out.println(currEmail);
					System.out.println(currPassword);
					System.out.println(currUsername);

					if (currEmail.equals(email) && currPassword.equals(password)) {
						System.out.println("Got username: " + currUsername);
						scan.close();
						return currUsername;
					}
				}

				// final case, not in the login file -- do not allow them to proceed
				return "";


			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "";
	}
	public static boolean userExists(String username) {
		return false;
	}
	public static boolean validateEmail(String email) {
		Pattern emailPattern = Pattern.compile("\\b(^[_A-Za-z0-9-]+" +
				"(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])" +
				"+((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|(\\.mil)|" +
				"(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\.aero)|" +
				"(\\.arpa)|(\\.coop)|(\\.int)|(\\.jobs)|(\\.museum)|(\\.name)|" +
				"(\\.pro)|(\\.travel)|(\\.nato)|(\\..{2,3})|(\\..{2,3}\\..{2,3}))$)\\b"); //regular expression for email address http://struts.apache.org/release/2.0.x/struts2-core/apidocs/com/opensymphony/xwork2/validator/validators/EmailValidator.html
		Matcher m = emailPattern.matcher(email); //try to match pattern to email
		return m.matches(); //if it matches, return true

	}
	public static ArrayList<String> getTeams() {
		ArrayList<String> teams = new ArrayList<String>();
		JSch jsch = new JSch();
		String user = "asapp";
		String host = "artsci.drake.edu";
		String pwd="9Gj24!L6c848FG$";
		int port=22;
		try {
			Session session = jsch.getSession(user, host, port);
			JSch.setConfig("StrictHostKeyChecking","no");
			session.setPassword(pwd);
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp c = (ChannelSftp)channel;
			c.cd("TeamThink");
			c.cd("teams");
			Vector<LsEntry> teamdirs = c.ls(".");
			for(int i = 2; i < teamdirs.size(); i++) {
				teams.add(teamdirs.get(i).getFilename());
			}
			System.out.println("team size" + " " + teams.size());
		}
		catch(Exception e) {
			e.printStackTrace();
		}


		return teams;

	}

	public static ArrayList<Note> getNotes(Date session) { //return notes from a session

		ArrayList<Note> notes = new ArrayList<Note>(); 
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
	public static void createTeam(Context context,String team) {
		JSch jsch=new JSch();
		String user="asapp";
		String host="artsci.drake.edu";
		String pwd="9Gj24!L6c848FG$";
		int port=22;
		try {
			Session session=jsch.getSession(user, host, port);
			JSch.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pwd);
			session.connect();
			Channel channel=session.openChannel("sftp");
			channel.connect();
			ChannelSftp c=(ChannelSftp)channel;

			c.cd("TeamThink");
			c.cd("teams");
			c.mkdir(team);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static ArrayList<Note> getNotes(Context context,String team) throws IOException, ParseException { //return all notes
		ArrayList<Note> notes = new ArrayList<Note>(); 
		currentTeam = team;
		JSch jsch=new JSch();
		String user="asapp";
		String host="artsci.drake.edu";
		String pwd="9Gj24!L6c848FG$";
		int port=22;
		try {
			Session session=jsch.getSession(user, host, port);
			JSch.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pwd);
			session.connect();
			Channel channel=session.openChannel("sftp");
			channel.connect();
			ChannelSftp c=(ChannelSftp)channel;

			try {
				c.cd("TeamThink");
				c.cd("teams");
				c.cd(team);
				Vector<LsEntry> notedirs = c.ls(".");
				for(int i = 2; i < notedirs.size(); i++) {
					Note newNote = new Note();
					c.cd(notedirs.get(i).getFilename());

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

					String authorFilePath = context.getFilesDir().getPath().toString() + "/authorin.txt";
					File authorFile = new File(authorFilePath);
					authorFile.createNewFile();
					FileOutputStream fos2 = new FileOutputStream(authorFile);
					c.get("author.txt", fos2);
					fos2.close();
					FileInputStream fis2 = new FileInputStream(authorFile);
					Scanner scan2 = new Scanner(fis2);
					String author = "";
					author = scan2.nextLine();
					newNote.setAuthor(author);
					//System.out.println(author);

					String upFilePath = context.getFilesDir().getPath().toString() + "/upvotesin.txt";
					File upFile = new File(upFilePath);
					upFile.createNewFile();
					FileOutputStream fos3 = new FileOutputStream(upFile);
					c.get("upVotes.txt", fos3);
					fos3.close();
					FileInputStream fis3 = new FileInputStream(upFile);
					Scanner scan3 = new Scanner(fis3);
					int upVotes = 0;
					while(scan3.hasNext()) {
						upVotes=scan3.nextInt();
					}  //TODO-something wrong with upvotes
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
			session.setPassword(pwd);
			session.connect();
			Channel channel=session.openChannel("sftp");
			channel.connect();
			ChannelSftp c=(ChannelSftp)channel;
			try {
				c.cd("TeamThink");
				c.cd("teams");
				c.cd(currentTeam);

				System.out.println("To " + currentTeam);
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
					osw3.write("0");

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
	public static void upvote(Note myNote, Context context) {
		JSch jsch=new JSch();
		String user="asapp";
		String host="artsci.drake.edu";
		String pwd="9Gj24!L6c848FG$";
		int port=22;
		try {
			Session session=jsch.getSession(user, host, port);
			JSch.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pwd);
			session.connect();
			Channel channel=session.openChannel("sftp");
			channel.connect();
			ChannelSftp c=(ChannelSftp)channel;
			c.cd("TeamThink");
			c.cd("teams");
			c.cd(currentTeam);
			String date = myNote.getCreationDate().toString().replace(" ", "_");
			c.cd(date);

			String upVoteFilePath = context.getFilesDir().getPath().toString() + "/upVotes.txt";
			System.out.println(upVoteFilePath);
			final Integer upVotes = (Integer) myNote.getUpVotes(); 
			File upVotesFile = new File(upVoteFilePath);
			upVotesFile.createNewFile();
			FileOutputStream fOut3 = new FileOutputStream(upVotesFile);
			OutputStreamWriter osw3 = new OutputStreamWriter(fOut3); 
			// Write the string to the file
			osw3.write(upVotes.toString());
			/* ensure that everything is
			 * really written out and close */
			osw3.flush();
			osw3.close();

			c.rm("upVotes.txt");
			c.put(upVoteFilePath, "upVotes.txt");

		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}


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
	public static Integer addUser(String entry, Context context){
		JSch jsch=new JSch();
		String user="asapp";
		String host="artsci.drake.edu";
		String pwd="9Gj24!L6c848FG$";
		int port=22;
		try {
			Session session=jsch.getSession(user, host, port);
			JSch.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pwd);
			session.connect();
			Channel channel=session.openChannel("sftp");
			channel.connect();
			ChannelSftp c=(ChannelSftp)channel;
			c.cd("TeamThink");
			String filePath = context.getFilesDir().getPath().toString() + "/login.csv";					
			FileOutputStream fos = new FileOutputStream(filePath);
			c.get("login.csv", fos);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write("\n");
			osw.write(entry);
			osw.flush();
			osw.close();
			fos.close();
			c.put(filePath,"login.csv");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		} else
			return true;
	}

}