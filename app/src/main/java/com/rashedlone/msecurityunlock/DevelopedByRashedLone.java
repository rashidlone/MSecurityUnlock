package com.rashedlone.msecurityunlock;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rashedlone.msecurityunlock.fonts.Font;

import java.lang.reflect.Field;
import java.util.Calendar;


public class DevelopedByRashedLone extends AppCompatActivity  {

	private String full, a, b, c, d;
	private boolean enabled;
	private ImageButton bt;
	private SharedPreferences sp;
	private boolean val;
	private boolean to_finish = false;
	private TextView me;
	private SharedPreferences.Editor ed;
	private ScrollView sce;
	private MediaPlayer mp;
	private AdView mAdView;
	private String str;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		Font.applyFont(this, findViewById(R.id.txt), "fonts/rashed_lone.ttf");
		Font.applyFont(this, findViewById(R.id.method), "fonts/rashed_lone_2.ttf");

		sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ed = sp.edit();


		if (!sp.getString("ab_color", "").isEmpty()) {
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color", ""))));
			getSupportActionBar().setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color", ""))));
		}
		firstExec();

		str ="https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName();


		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);


		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ignored) {
		}


			if(sp.getString("day",null)==null)
		{
			dialog("Alert!", "Please click on ads and support development!");
			ed.putString("day","9").commit();
		}

//dialog logic ends here
	}//on create

	public void restart() {

		Intent intent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		finish();
	}


	public void locateMe() {

		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setCancelable(false);
		ab.setIcon(R.drawable.ic_launcher);
		ab.setMessage(getResources().getString(R.string.locate));
		ab.setTitle("Congratulations!");
		ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

				ed.remove("locate");
				ed.commit();

			}
		});
		ab.show();

	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {

		 menu.add(0, 1, 1,"Home").setEnabled(false).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		 menu.add(0, 2, 2,"Usage").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		 menu.add(0, 3, 3,"Settings").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		 menu.add(0, 4 ,4,"Rate").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		 menu.add(0, 5 ,5,"Share").setIcon(R.drawable.share).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 menu.add(0, 6, 6,"About").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	     menu.add(0, 7, 7,"Exit").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		 return true;
		
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){	    	
		
		case 1:
			android.content.Intent intent = new android.content.Intent(getApplicationContext(), DevelopedByRashedLone.class);
			startActivity (intent);

			break;
			
		case 2:
	     
			android.content.Intent intent1 = new android.content.Intent(getApplicationContext(), UsageActivity.class);
			startActivity (intent1);

			break;
			
		case 3:
			
			android.content.Intent intent2 = new android.content.Intent(getApplicationContext(), Preferences.class);
			startActivity (intent2);
            finish();
	    		break;

			case 4:

				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

				break;

			case 5:

				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Hey! I want you to try this app, It is a smart way to unlock your Android phone without pin, password or pattern. You don't need to format your device if you forgot your pattern or password. Just use this app. It works like wonder!\n\n"+str;
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download Smart Unlock for Android!");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				startActivity(Intent.createChooser(sharingIntent, "Share via..."));

				break;

			case 6:

				android.content.Intent intent5 = new android.content.Intent(getApplicationContext(), Developer.class);
				startActivity (intent5);
				break;
	    		
	    	case 7:
	    		finish();
	    		break;
	    		
	    	
	    	
		}
		
		return true;
	}


	public void firstExec() {

		mp = new MediaPlayer();

		sce = (ScrollView) findViewById(R.id.sce);
		sce.setBackgroundDrawable(sp.getString("color_pref", "").equals("0") ? getResources().getDrawable(R.drawable.white) : sp.getString("color_pref", "").equals("1") ? getResources().getDrawable(R.drawable.back_1) : getResources().getDrawable(R.drawable.back_2));

		full = getClass().getSimpleName();
		d = full.substring(full.length() - 4);
		full = full.substring(0, full.length() - 4);
		c = full.substring(full.length() - 6);
		full = full.substring(0, full.length() - 6);
		b = full.substring(full.length() - 2);
		full = full.substring(0, full.length() - 2);
		a = full;


		ed.putInt("random_user_hash", 7);
		ed.commit();

		val = getIntent().getBooleanExtra("first", false);
		to_finish = getIntent().getBooleanExtra("finish_it", false);//finish activity on restart() or restore() in Preference.java
		if (val)
			Toast.makeText(getApplicationContext(), a + " " + b + " : " + c + " " + d, Toast.LENGTH_SHORT).show();

		if (to_finish)
			Toast.makeText(getApplicationContext(), "Changes applied!", Toast.LENGTH_SHORT).show();

		if (sp.getString("locate", "").equalsIgnoreCase("null"))
			locateMe();

		if (sp.getBoolean("notify", true) && sp.getBoolean("ist_thing", true)) {
			Intent mIntent = new Intent(getApplicationContext(), ServiceAfter.class);
			getApplicationContext().startService(mIntent);
		}


		enabled = sp.getBoolean("ist_thing", false);
		me = (TextView) findViewById(R.id.method);
		bt = (ImageButton) findViewById(R.id.btn);
		bt.setBackgroundResource(enabled ? R.drawable.msec_on : R.drawable.msec_off);
		bt.setOnClickListener(new View.OnClickListener() {

			private Intent mIntent;

			@Override
			public void onClick(View v) {


				enabled = !enabled;
				ed.putBoolean("ist_thing", enabled);
				ed.commit();

				bt.setBackgroundResource(enabled ? R.drawable.msec_on : R.drawable.msec_off);
				mp = MediaPlayer.create(getApplicationContext(), enabled ? R.raw.sound_three : R.raw.sound_two);

				if (sp.getBoolean("play_sound", true) == true)
					mp.start();

				restart();


				if (enabled) {
					me.setText(sp.getString("main_info", ""));
					mIntent = new Intent(getApplicationContext(), ServiceAfter.class);
					getApplicationContext().startService(mIntent);
				} else {
					me.setText("");
					mIntent = new Intent(getApplicationContext(), ServiceAfter.class);
					getApplicationContext().stopService(mIntent);

				}

			}
		});


		if (enabled)
			me.setText(sp.getString("main_info", ""));


	}


	public void dialog(String t, String m)
	{


		android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(this);
		ab.setCancelable(false);
		ab.setIcon(R.drawable.ic_launcher);
		ab.setMessage(m);
		ab.setTitle(t);
		ab.setPositiveButton("Hide", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {


			}
		});

		ab.show();


	}





}

