package edu.drake.teamthink;

import java.util.Comparator;

public class NoteCompareVotes implements Comparator<Note> {
    public int compare(Note note1, Note note2) {
      return note2.getUpVotes() - note1.getUpVotes();		
    }
}
