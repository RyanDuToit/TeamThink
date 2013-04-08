package edu.drake.teamthink;

import java.util.Date;

public class Session {
	private Date sessionDate;
	private String sessionSummary;
	
	public Date getSessionDate() {
		return sessionDate;
	}
	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}
	public String getSessionSummary() {
		return sessionSummary;
	}
	public void setSessionSummary(String summary) {
		this.sessionSummary = summary;
	}
	
	public String toString() {
		String result;
		result = "" + sessionDate + "\n" + sessionSummary;
		return result;
	}
}