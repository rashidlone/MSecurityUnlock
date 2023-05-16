package com.rashedlone.msecurityunlock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

public class ImageArrayAdapter extends ArrayAdapter<CharSequence> {
	
	private int index = 0;
	
	private View row;

	private CheckedTextView checkedTextView;

	private SharedPreferences sp;

	
	public ImageArrayAdapter(Context context, int textViewResourceId,
			CharSequence[] objects, int[] ids, int i) {
		super(context, textViewResourceId, objects);

		index = i;
		
		sp = PreferenceManager.getDefaultSharedPreferences(context);			
		
	} 
	@Override
	public boolean isEnabled(int position){
		
		if(position > 1 && sp.getBoolean("status_bar", false) == false)
		return false;
		else
		return true;
		
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
		if(position > 1)
		row = inflater.inflate(R.layout.listitempro, parent, false);
		else
		row = inflater.inflate(R.layout.listitem, parent, false);			
		
		 checkedTextView = (CheckedTextView)row.findViewById(R.id.check);		
		checkedTextView.setText(getItem(position));
		
		
		if (position == index) 
			checkedTextView.setChecked(true);
		
		
		
		return row;
		
	}
}
