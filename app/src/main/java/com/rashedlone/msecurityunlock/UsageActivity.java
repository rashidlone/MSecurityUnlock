package com.rashedlone.msecurityunlock;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class UsageActivity extends AppCompatActivity {

	private SharedPreferences sp;
	private ScrollView sce;
    private AdView mAdView;

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = PreferenceManager.getDefaultSharedPreferences(this);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(!sp.getString("ab_color", "").isEmpty()) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color", ""))));
            getSupportActionBar().setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color", ""))));
        }
        setContentView(R.layout.usage_layout);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        sce = (ScrollView)findViewById(R.id.sce);
		sce.setBackgroundDrawable(sp.getString("color_pref", "").equals("0")?getResources().getDrawable(R.drawable.white):sp.getString("color_pref", "").equals("1")?getResources().getDrawable(R.drawable.back_1):getResources().getDrawable(R.drawable.back_2));



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





}
