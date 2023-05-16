package com.rashedlone.msecurityunlock;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.ListAdapter;


public class ImageList extends ListPreference {

	private int[] resourceIds = null;
	public ImageList(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs,
			R.styleable.ImageList);

		String[] imageNames = context.getResources().getStringArray(
			typedArray.getResourceId(typedArray.getIndexCount()-1, -1));

		resourceIds = new int[imageNames.length];

		for (int i=0;i<imageNames.length;i++) {
			String imageName = imageNames[i].substring(
				imageNames[i].indexOf('/') + 1,
				imageNames[i].lastIndexOf('.'));

			resourceIds[i] = context.getResources().getIdentifier(imageName,
				null, context.getPackageName());
		}

		typedArray.recycle();
	}
	
	protected void onPrepareDialogBuilder(Builder builder) {
		int index = findIndexOfValue(getSharedPreferences().getString(
			getKey(), "#ff2233"));
		

		ListAdapter listAdapter = new ImageArrayAdapterNew(getContext(),
			R.layout.listitem_new, getEntries(), resourceIds, index);
		
		builder.setAdapter(listAdapter, this);
		super.onPrepareDialogBuilder(builder);
	}
}
