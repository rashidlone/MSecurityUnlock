package com.rashedlone.msecurityunlock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Rashedlone0 on 1/17/2017.
 */

public class Notification extends AppCompatActivity {

    private  TextView main_header, main_info;
    private Button main_button;
    private ImageView main_image;
    private Intent intent;
    private Bitmap bitmap;
    private SharedPreferences sp;
    private AdView mAdView;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_layout);

        sp = PreferenceManager.getDefaultSharedPreferences(this);



        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color",""))));
        ab.setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor(sp.getString("ab_color",""))));


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        main_header = (TextView)findViewById(R.id.main_header);
        main_info = (TextView)findViewById(R.id.main_info);
        main_button = (Button)findViewById(R.id.main_button);
        main_image = (ImageView)findViewById(R.id.main_image);

        intent = getIntent();
        String main_header_i = intent.getStringExtra("main_header");
        String main_info_i = intent.getStringExtra("main_info");
        String main_button_i = intent.getStringExtra("main_button");
        final String main_button_link = intent.getStringExtra("main_button_link");

        bitmap = (Bitmap) intent.getParcelableExtra("main_image");

        if(main_button_i.isEmpty())
            main_button.setVisibility(View.GONE);

        main_header.setText(main_header_i);
        main_info.setText(main_info_i.replace("<br>","\n"));
        main_button.setText(main_button_i);
        main_image.setImageBitmap(bitmap);

        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(main_button_link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }

    @Override public void onBackPressed()
    {
        finish();
        Intent mIntent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
        getApplicationContext().stopService(mIntent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                Intent mIntent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
                getApplicationContext().stopService(mIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   /* private String readStream(String url) {

        String t = null;
        String err = null;
        HttpClient httpclient=new DefaultHttpClient();
        HttpPost httppost=new  HttpPost(url);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            err = e.getMessage();
        }
        try {
           t =  EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            err = e.getMessage();
        }
        if(err != null)
          return t;
        else
            return err;
    }*/
}
