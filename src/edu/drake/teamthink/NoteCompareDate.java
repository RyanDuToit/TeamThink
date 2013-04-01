package edu.drake.teamthink;

import java.util.Comparator;

public class NoteCompareDate implements Comparator<Note> {
    public int compare(Note note1, Note note2) {
        return note1.getCreationDate().compareTo(note2.getCreationDate());
    }
}
