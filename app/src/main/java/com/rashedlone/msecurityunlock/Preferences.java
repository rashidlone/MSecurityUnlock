package com.rashedlone.msecurityunlock;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private SharedPreferences sp;
	private String[] list = {"remote_unlock_data", "remote_unlock", "sensor_unlock", "voice_unlock"};
	private String[] proo = {"remote_unlock_data", "status_no"};
	private ListPreference lp;
	private CheckBoxPreference erase;
	private CheckBoxPreference pro;
	private CheckBoxPreference remote;
	private CheckBoxPreference data;
	private CheckBoxPreference voice;
	private CheckBoxPreference sensor;
	private String rf = "status_bar";
	private SharedPreferences.Editor ed;
	private PreferenceScreen thanks;
	private String[] values;
	private PackageManager pm;
	private boolean isProximity;
	private PreferenceCategory hy;
	private CheckBoxPreference notify;
	private Intent mIntent;
	private PreferenceScreen version;
	private PackageInfo p;
	private CheckBoxPreference deff;
	private EditText userid;
	private CheckBoxPreference sound;

	//private CheckBoxPreference enab;
	private AppCompatDelegate mDelegate;
	private  Context context;


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		getDelegate().onPostCreate(savedInstanceState);
	}

	public ActionBar getSupportActionBar() {
		return getDelegate().getSupportActionBar();
	}

	public void setSupportActionBar(@Nullable Toolbar toolbar) {
		getDelegate().setSupportActionBar(toolbar);
	}

	@Override
	public MenuInflater getMenuInflater() {
		return getDelegate().getMenuInflater();
	}

	@Override
	public void setContentView(@LayoutRes int layoutResID) {
		getDelegate().setContentView(layoutResID);
	}

	@Override
	public void setContentView(View view) {
		getDelegate().setContentView(view);
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {
		getDelegate().setContentView(view, params);
	}

	@Override
	public void addContentView(View view, ViewGroup.LayoutParams params) {
		getDelegate().addContentView(view, params);
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		getDelegate().onPostResume();
	}

	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		super.onTitleChanged(title, color);
		getDelegate().setTitle(title);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		getDelegate().onConfigurationChanged(newConfig);
	}

	@Override
	protected void onStop() {
		super.onStop();
		getDelegate().onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getDelegate().onDestroy();
		sp.unregisterOnSharedPreferenceChangeListener(this);

	}

	public void invalidateOptionsMenu() {
		getDelegate().invalidateOptionsMenu();
	}

	private AppCompatDelegate getDelegate() {
		if (mDelegate == null) {
			mDelegate = AppCompatDelegate.create(this, null);
		}
		return mDelegate;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		getDelegate().installViewFactory();
		getDelegate().onCreate(savedInstanceState);
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		sp.registerOnSharedPreferenceChangeListener(this);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		if(!sp.getString("ab_color", "").isEmpty()) {
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color", ""))));
			getSupportActionBar().setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color", ""))));
		}


			/*sce = (PreferenceCategory)findPreference("sce");
			sce.setLayoutResource(R.layout.author);
*/

		pro = (CheckBoxPreference) findPreference("status_bar");
		//enab = (CheckBoxPreference)findPreference("ist_thing");
		lp = (ListPreference) findPreference("list_preference");
		erase = (CheckBoxPreference) findPreference("status_no");
		remote = (CheckBoxPreference) findPreference("remote_unlock");
		notify = (CheckBoxPreference) findPreference("notify");
		data = (CheckBoxPreference) findPreference("remote_unlock_data");
		voice = (CheckBoxPreference) findPreference("voice_unlock");
		sensor = (CheckBoxPreference) findPreference("sensor_unlock");
		thanks = (PreferenceScreen) findPreference("pro_version");
		hy = (PreferenceCategory) findPreference("hyhy");
		version = (PreferenceScreen) findPreference("version");
		sound = (CheckBoxPreference) findPreference("play_sound");
		sound.setDefaultValue(true);
		deff = (CheckBoxPreference) findPreference("restore");
		deff.setDefaultValue(false);
		remote.setEnabled(false);
		remote.setChecked(false);
		voice.setEnabled(false);
		sensor.setEnabled(false);

		//ab_clr.setDefaultValue(getResources().getColor(R.color.Tomato));



		getPreferenceScreen().removePreference(hy);


		data();

		version();

		if (sp.getString("voice_unlock_number", "").length() < 1)
			if (voice.isChecked())
				dialog("Info!","Please add Voice Unlock Number or this feature will not work!");


		pm = getPackageManager();
		isProximity = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY);


	}

	protected void onResume() {
		super.onResume();

	}

	public void onSharedPreferenceChanged(SharedPreferences spp,
										  String key) {


		data();



		if (key.equalsIgnoreCase("status_bar") && !pro.isChecked())
			ed.putString("list_preference", "15").commit();
		else if (key.equalsIgnoreCase("restore")) {

			restore();

		} else if (key.equalsIgnoreCase("color_pref") || key.equalsIgnoreCase("ab_color")) {

			restart();
		}

	}


	public void restart() {
        finish();
        Intent intent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
        intent.putExtra("finish_it", true);
        sleep(100);
        {
            startActivity(intent);
        }
	}



    public void sleep(int x)
    {

        try {
            Thread.sleep(x);
        }catch(Exception e)
        {
            t(e.getMessage());
		}
    }
	public void restore() {


		Random rand = new Random();
		int range = 9999 - 1111 + 1;
		final int hash = rand.nextInt(range) + 1111;

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Enter " + hash + " to continue!");
		userid = new EditText(this);
		alert.setView(userid);
		alert.setCancelable(false);
		alert.setPositiveButton("Restore", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String pass = userid.getText().toString();

				if (pass.equals("" + hash)) {
					ed.clear();
					ed.commit();
					finish();
					t("Default settings restored!");
					sleep(1000);
					t("Please start the application manually now!");

				} else {


					t("Restoring to default failed!");
				}

			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {


			public void onClick(DialogInterface dialog, int which) {


			}
		});

		alert.show();

	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen ps, Preference pff) {

		String k = pff.getKey();

		if (k != null) {
			if (sp.getBoolean(rf, false)==false && Arrays.asList(list).contains(k)) {
				premium();
				if (k.equalsIgnoreCase("voice_unlock"))
					voice.setChecked(false);
				else if (k.equalsIgnoreCase("sensor_unlock"))
					sensor.setChecked(false);
				else if (k.equalsIgnoreCase("remote_unlock"))
					remote.setChecked(false);
				else if (k.equalsIgnoreCase("remote_unlock_data"))
					data.setChecked(false);


			} else if (k.equals("remote_unlock")) {

				dialog("Sorry!","This feature is currently avaliable only on some selected devices. More devices will be added in new versions. Sorry for now. :)");

			} else if (sp.getBoolean(rf, false)==false && Arrays.asList(proo).contains(k)) {
				premium();
				erase.setChecked(true);
				data.setChecked(false);
			} else if (sp.getBoolean(rf, false)==false&& k.equalsIgnoreCase("pro_version")) {

				premium();
			}

			drag();

			if (!isProximity && k.equalsIgnoreCase("sensor_unlock")) {
				t( "This device doesn't have Proximity sensor.");
				sensor.setChecked(false);
			}

		}//k!=null

		return false;


	}

	public int lp_position() {
		int pos = 0;

		switch (Integer.valueOf(sp.getString("list_preference", "15"))) {
			case 15:

				pos = 0;

				break;

			case 27:

				pos = 1;

				break;

			case 30:

				pos = 2;
				break;

			case 44:

				pos = 3;

				break;

			case 82:

				pos = 4;

				break;


			case 63:

				pos = 5;
				break;

			case 72:

				pos = 6;

				break;

			case 87:


				pos = 7;
				break;

			case 99:

				pos = 8;
				break;
		}

		return pos;


	}

	public void data() {


		//starts
		remote.setChecked(false);
		ed = sp.edit();


		//notify
		if (notify.isChecked() && sp.getBoolean("ist_thing", true)) {
			mIntent = new Intent(getApplicationContext(), ServiceAfter.class);
			getApplicationContext().startService(mIntent);

		} else {
			mIntent = new Intent(getApplicationContext(), ServiceAfter.class);
			getApplicationContext().stopService(mIntent);

		}


		drag();
		values = getResources().getStringArray(R.array.listNames);

		lp.setSummary("Unlock when : " + values[lp_position()]);
		ed.putString("main_info", "" + lp.getSummary());
		ed.commit();
		if (!pro.isChecked()) {

			what();

			if (!erase.isChecked()) {
				ed.putBoolean("status_no", true).commit();
				erase.setChecked(true);
			}
			if (data.isChecked()) {
				data.setChecked(false);
				ed.putBoolean("remote_unlock_data", false).commit();
			}
		} else {
			erase.setChecked(false);
			ed.putBoolean("status_no", false).commit();
			thanks.setEnabled(false);
			thanks.setSummary("Thanks for your support!");
			thanks.setTitle("Pro version Installed!");
		}


		lp.setEnabled(sp.getBoolean("ist_thing", false));

		if (sp.getString("list_preference", "15").equalsIgnoreCase("72")) {
			remote.setEnabled(false);
			voice.setEnabled(false);
			sensor.setEnabled(true);

		} else if (sp.getString("list_preference", "15").equalsIgnoreCase("87")) {
			remote.setEnabled(false);
			voice.setEnabled(true);
			sensor.setEnabled(false);

		} else if (sp.getString("list_preference", "15").equalsIgnoreCase("99")) {
			remote.setEnabled(true);
			voice.setEnabled(false);
			sensor.setEnabled(false);


		} else {
			remote.setEnabled(false);
			voice.setEnabled(false);
			sensor.setEnabled(false);

		}

		//ends

	}


	public void version() {

		pm = getApplicationContext().getPackageManager();

		try {
			p = pm.getPackageInfo(getApplicationContext().getPackageName(), 0);
			version.setSummary(p.versionName + " (" + p.versionCode + ")");

		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}


	}


	public void drag() {

		if (pro.isChecked()) {
			thanks.setEnabled(false);
			thanks.setSummary("Thanks for your support!");
			thanks.setTitle("Pro version Installed!");
		} else {
			thanks.setEnabled(true);
			thanks.setSummary("Unlock all premium features and support development.");
			thanks.setTitle("Go Premium");

		}

	}


	public void what()

	{

		if (voice.isChecked())
			voice.setChecked(false);
		else if (remote.isChecked())
			remote.setChecked(false);
		else if (sensor.isChecked())
			sensor.setChecked(false);


	}




	public void premium() {

		dialog("Go Premium!","Restart the app. and go to menu >> About >> Tap on Smart Unlock icon 10 times. Your premium subscription will be activated for free!");

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
			case android.R.id.home:
				start();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

    public void start(){

		finish();
        Intent intent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);



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

	public  void t(String s)
	{

		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();


	}

    @Override
    public void onBackPressed()
    {

start();

    }
}
