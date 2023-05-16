package com.rashedlone.msecurityunlock;

import java.io.UnsupportedEncodingException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rashedlone.msecurityunlock.fonts.Font;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class Developer extends AppCompatActivity {
	private ImageView img;
	private SharedPreferences sp;
	private String version;
	private PackageManager pm;
	private Context context = this;
	private PackageInfo p;
	private TextView tv;
	private ScrollView sce;
	private int i;
	private Editor ed;
	private AdView mAdView;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.developer);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        Font.applyFont(this, findViewById(R.id.b), "fonts/rashed_lone.ttf");
        Font.applyFont(this, findViewById(R.id.d), "fonts/rashed_lone_3.ttf");
        Font.applyFont(this, findViewById(R.id.e), "fonts/rashed_lone_2.ttf");


        ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		if(!sp.getString("ab_color", "").isEmpty()) {
			ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color", ""))));
			ab.setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color", ""))));
		}
		sce = (ScrollView)findViewById(R.id.sce);
		sce.setBackgroundDrawable(sp.getString("color_pref", "").equals("0")?getResources().getDrawable(R.drawable.white):sp.getString("color_pref", "").equals("1")?getResources().getDrawable(R.drawable.back_1):getResources().getDrawable(R.drawable.back_2));


		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		pm = context.getPackageManager();

		img = (ImageView)findViewById(R.id.a);
		img.setImageResource(sp.getBoolean("status_bar", false) ? R.drawable.ic_img_pro : R.drawable.ic_img);
		img.setOnClickListener(new OnClickListener(){;


		@Override public void onClick(View v)
		{
			++i;
			if(i==10) {
				if(sp.getString("device_token", null)==null) {
					dialog("Info!", "Please connect to internet and restart the app, it is required for the first time only.\n\n\nDidn't Work?\n\n\nPossible Reasons : \n\n1. You have restored app. settings to default.\n2. Please update Google Play Services to latest versions.\n\nFix:\n\nClear app data and re-launch. :)");
					i = 0;
				}
				else
				aboutToPro();
			}

		}
		});



		try {
			p = pm.getPackageInfo(context .getPackageName(), 0);
			version = p.versionName;

		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}

		tv = (TextView)findViewById(R.id.c);
		tv.setText("v"+version+" ("+p.versionCode+")");



	}//on create ends

	private void aboutToPro() {
		// TODO Auto-generated method stub

		i=0;
        if(sp.getBoolean("status_bar", false)==true)
       dialog("Awesome!","You are already a premium user. \n\nYou can click on ads instead to support development, it costs nothing. Thank You!");
        else {
            ed = sp.edit();
            ed.putBoolean("status_bar", true);
            ed.commit();
            img.setImageResource(sp.getBoolean("status_bar", true) ? R.drawable.ic_img_pro : R.drawable.ic_img);
           dialog("Premium!", getResources().getString(R.string.locate)+"\nPlease click on ads and support development.");
        }
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void dialog(String t, String m)
	{


		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setCancelable(false);
		ab.setIcon(R.drawable.ic_launcher);
		ab.setMessage(m);
		ab.setTitle(t);
		ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {


			}
		});

		ab.show();


	}

	
}
