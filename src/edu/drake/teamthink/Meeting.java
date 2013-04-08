package edu.drake.teamthink;

import java.util.Date;

public class Meeting {
	private Date meetingDate;
	private String meetingSummary;
	
	public Date getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}
	public String getMeetingSummary() {
		return meetingSummary;
	}
	public void setMeetingSummary(String summary) {
		this.meetingSummary = summary;
	}
	
	public String toString() {
		String result;
		result = "" + meetingDate + "\n" + meetingSummary;
		return result;
	}
}