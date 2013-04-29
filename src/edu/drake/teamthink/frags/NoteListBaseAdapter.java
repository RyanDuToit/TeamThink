package edu.drake.teamthink.frags;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.drake.teamthink.Note;
import edu.drake.teamthink.R;

public class NoteListBaseAdapter extends BaseAdapter {
	private static ArrayList<Note> notes;
	private LayoutInflater mInflater;
	private String noteContent;
	
	public NoteListBaseAdapter(Context context, ArrayList<Note> noteArray) {
        notes = noteArray;
        mInflater = LayoutInflater.from(context);
    }
	
	public int getCount() {
		return notes.size();
	}
	
	public Object getItem(int position) {
        return notes.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.multiline_notelist_row_view, null);
            holder = new ViewHolder();
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.upvotes = (TextView) convertView.findViewById(R.id.votes);
 
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.author.setText(notes.get(position).getAuthor());
        
        noteContent = notes.get(position).getText();
        if (noteContent.length() > 80) { holder.content.setText(noteContent.substring(0, 80) + "..."); }
        else { holder.content.setText(noteContent); }
        
        holder.upvotes.setText(Integer.toString(notes.get(position).getUpVotes()) + " brilliance");
 
        return convertView;
    }
    
    static class ViewHolder {
        TextView author;
        TextView content;
        TextView upvotes;
    }
	
}
