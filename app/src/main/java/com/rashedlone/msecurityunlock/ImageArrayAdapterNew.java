package com.rashedlone.msecurityunlock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.rashedlone.msecurityunlock.R.id.check;
import static com.rashedlone.msecurityunlock.R.id.img;
import static java.sql.Types.NULL;

public class ImageArrayAdapterNew extends ArrayAdapter<CharSequence> {

	private int index = 0;

	private View row;

	private CheckedTextView checkedTextView;

    private ImageView imgg;

	private SharedPreferences sp;

    private  Context context;



	public ImageArrayAdapterNew(Context context, int textViewResourceId,
								CharSequence[] objects, int[] ids, int i) {
		super(context, textViewResourceId, objects);

		index = i;
		
		sp = PreferenceManager.getDefaultSharedPreferences(context);			
		
	} 
	@Override
	public boolean isEnabled(int position){

		return true;
		
	}



	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();

		row = inflater.inflate(R.layout.listitem_new, parent, false);
		
		 checkedTextView = (CheckedTextView)row.findViewById(R.id.check);		
		checkedTextView.setText(getItem(position));
        imgg = (ImageView)row.findViewById(R.id.img);

        if(position==0)
        {
            checkedTextView.setTextSize(20);
        }else {
            checkedTextView.setTextSize(16);
            //checkedTextView.setTextColor(Color.parseColor(getStr(""+getItem(position))));
            imgg.setBackgroundColor(Color.parseColor(getStr(""+getItem(position))));



        }
		if (position == index) 
			checkedTextView.setChecked(true);
		
		
		
		return row;
		
	}

    private String getStr(String aString) {
        String packageName = MyApplication.getAppContext().getPackageName();
        int resId = MyApplication.getAppContext().getResources().getIdentifier(aString, "string", packageName);
        return MyApplication.getAppContext().getString(resId);
    }


}
