package edu.drake.teamthink.frags;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.drake.teamthink.R;

public class TeamListBaseAdapter extends BaseAdapter {
	private static ArrayList<String> teams;
	private LayoutInflater mInflater;
	
	public TeamListBaseAdapter(Context context, ArrayList<String> teamArray) {
        teams = teamArray;
        mInflater = LayoutInflater.from(context);
    }
	
	public int getCount() {
		return teams.size();
	}
	
	public Object getItem(int position) {
        return teams.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.teamlist_row_view, null);
            holder = new ViewHolder();
            holder.team = (TextView) convertView.findViewById(R.id.team);
 
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.team.setText(teams.get(position));
        return convertView;
    }
    
    static class ViewHolder {
        TextView team;
    }
	
}
