package edu.drake.teamthink;

import java.util.Comparator;

public class NoteCompareVotes implements Comparator<Note> {
    public int compare(Note note1, Note note2) {
      return note1.getUpVotes() - note2.getUpVotes();		
    }
}
