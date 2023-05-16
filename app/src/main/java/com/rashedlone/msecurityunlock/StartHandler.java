package com.rashedlone.msecurityunlock;

import java.util.Random;
import java.util.UUID;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class StartHandler extends AppCompatActivity{


		private SharedPreferences sp;
		private SharedPreferences.Editor ed;
		private ProgressBar progressBar;
		private TextView tv;
		private TextView info;
	    private AdView mAdView;



	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_handler);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);

		tv = (TextView)findViewById(R.id.tv);
		info = (TextView)findViewById(R.id.info);

		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		    new SharedPref().execute();

//get color from preferences activity


		}//on create ends

		 public void brainteaser(UpdateResult result)
			{
			 int randd;
			 Random rand = new Random();
			 String uuid;
			 int range = 999999-111111+1;
			 int l;
			 ed = sp.edit();


			if(sp.getInt("random_user_hash", 0) != 7)
			{


			for(l=0; l<500;l++)
			{
				randd = rand.nextInt(range)+111111;
				uuid = UUID.randomUUID().toString();
				if(l<170)
				{
					ed.putInt(uuid, randd);
					ed.commit();
				}else if(l<330)
				{
					ed.putString(uuid, ""+randd);
					ed.commit();

				}else{
					if(l%2==0)
					{
					ed.putBoolean(uuid, false);
					ed.commit();
					}else{
						ed.putBoolean(uuid, true);
						ed.commit();

					}
				}

					result.updateStatus(l*2);


			}
			}



		}//brain teaser


		 public void brainteaser2(UpdateResult result)
			{
			int z;
			int ran;
			Random rand = new Random();
			String id;
			int rangee = 999999-111111+1;
			ed = sp.edit();


			for(z=0; z<50;z++)
			{
				ran = rand.nextInt(rangee)+111111;
				id = UUID.randomUUID().toString();

				ed.putInt(id, ran);
				ed.commit();
				ed.remove(id).commit();

			result.updateStatus(z*20);
			}

			}//brain teaser 2


		 public abstract class UpdateResult {
		        public abstract void updateStatus(Integer status);
		    }



		 public class SharedPref extends AsyncTask<Void, Integer, Void>
			{



				@Override
				protected void onPreExecute() {


				}
				@Override
				protected Void doInBackground(Void... params) {


					if(sp.getInt("random_user_hash", 0) != 7)
					{
						ed = sp.edit();
						ed.putString("ab_color", "#ff2233").commit();
						ed.putString("color_pref", "0");
						ed.putString("list_preference", "15");
						ed.putString("main_info", "Unlock when : On Silent Mode");
						ed.commit();


					brainteaser(new UpdateResult(){
						@Override
						public void updateStatus(Integer status){

							publishProgress(status);
						 }
						});
					}else
					{
					brainteaser2(new UpdateResult(){
						@Override
						public void updateStatus(Integer status){

							publishProgress(status);
						 }
						});

					}



				return null;

				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);{

						Intent intent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
						overridePendingTransition(0,0);
						intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						intent.putExtra("first", true);
						startActivity(intent);
						finish();



						}


					}
				 @Override
			        protected void onProgressUpdate(Integer... y) {

					 progressBar.setProgress(y[0]);

			        	if(sp.getInt("random_user_hash", 0) != 7)
						{
			        		tv.setText(y[0]/10+"%");


			        	if(y[0]/10 == 0)
			        		info.setText("Loading for the first time...");
						else if(y[0]/10 == 25)
							info.setText("Making things simplier for you...");
			        	else if(y[0]/10 == 45)
			        		info.setText("Loading fonts...");
			        	else if(y[0]/10 == 59)
				        	info.setText("Applying fonts");
			        	else if(y[0]/10 == 65)
			        		info.setText("Checking device compatibility...");
			        	else if(y[0]/10 == 80)
			        		info.setText("Loading default settings...");
			        	else if(y[0]/10 == 85)
			        		info.setText("Making device ready...");
			        	else if(y[0]/10 == 90)
			        		info.setText("Device configured successfully...");
			        	else if(y[0]/10 == 97)
		        		info.setText("Finishing...");
			        	else if(y[0]/10 == 100)
			        	{
			        	ed.putInt("random_user_hash", 7);
						ed.commit();
			        	}//main cycle ends
						}else
						{
							info.setText("Loading...");
							tv.setText(y[0]/10+"%");


						}

			        }


			}

		 
		 /*
		 public void afterRespp(){
			 

			 if(respp.equalsIgnoreCase(sp.getString("m", "")+version))
			 {
			    Intent intent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
				overridePendingTransition(0,0);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				intent.putExtra("first", true);
				startActivity(intent);											
				finish();
				
			 }else if(respp.equalsIgnoreCase(sp.getString("m", "")))
			 {
				 ed.putBoolean("status_bar", false).commit();
					
				    Intent intent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
					overridePendingTransition(0,0);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					intent.putExtra("first", true);
					startActivity(intent);											
					finish();
					
				 }else if(respp.contains("update"))
				{
					AlertDialog.Builder ab = new AlertDialog.Builder(StartHandler.this);
					ab.setCancelable(false); 
					ab.setIcon(R.drawable.ic_launcher);
					ab.setMessage(respp);
					ab.setTitle("Good News!");
					ab.setPositiveButton("Update", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {

								
								try {
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +pname)));
								} catch (android.content.ActivityNotFoundException an) {
									Toast.makeText(getApplicationContext(), an.getMessage(),Toast.LENGTH_LONG).show();
								}

								finish();
								
							}});
					ab.show();		
				
					
				}
					else if(respp.contains("block"))
					
				{

					ed.clear();
					ed.commit();
					
					AlertDialog.Builder ab = new AlertDialog.Builder(StartHandler.this);
					ab.setCancelable(false); 
					ab.setIcon(R.drawable.ic_launcher);
					ab.setMessage(respp);
					ab.setTitle("Information!");
					ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {

								finish();
																

							}});
					ab.show();		
				
				}else if(respp.contains("user"))
					
				{

					AlertDialog.Builder ab = new AlertDialog.Builder(StartHandler.this);
					ab.setCancelable(false); 
					ab.setIcon(R.drawable.ic_launcher);
					ab.setMessage(respp);
					ab.setTitle("Information!");
					ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {

								 Intent intent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
									overridePendingTransition(0,0);
									intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									intent.putExtra("first", true);
									startActivity(intent);											
									finish();							

							}});
					ab.show();		
					
				}else if(respp.contains("Try"))
					
				{

					AlertDialog.Builder ab = new AlertDialog.Builder(StartHandler.this);
					ab.setCancelable(false); 
					ab.setIcon(R.drawable.ic_launcher);
					ab.setMessage(respp);
					ab.setTitle("Information!");
					ab.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {

								 Intent intent = new Intent(getApplicationContext(), DevelopedByRashedLone.class);
									overridePendingTransition(0,0);
									intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									intent.putExtra("first", true);
									startActivity(intent);											
									finish();							

							}});
					ab.setPositiveButton("Open", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

							
								try {
									startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(offer)));
								} catch (android.content.ActivityNotFoundException an) {
									Toast.makeText(getApplicationContext(), an.getMessage(),Toast.LENGTH_LONG).show();
								}
							finish();
						}});
					
					ab.show();
					
				}else
					{
					
				AlertDialog.Builder ab = new AlertDialog.Builder(StartHandler.this);
				ab.setCancelable(false); 
				ab.setIcon(R.drawable.ic_launcher);
				ab.setMessage("Something went wrong or server is too busy!");
				ab.setTitle("oops!");
				ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

							finish();
															

						}});
				ab.show();		
		 }
			
		 
			 
		 }*/
		 
		 
		 
		 /*
				 private class UrlExists extends AsyncTask<String, Void, Boolean>
					{

						
						@Override
						protected void onPreExecute() {
							
							info.setText("Checking Internet connection...");
							
							
						}

						@Override
						protected Boolean doInBackground(String... params) {
							
							
								try {
									HttpURLConnection.setFollowRedirects(false);
									HttpURLConnection con =  (HttpURLConnection) new URL(params[0]).openConnection();
									con.setRequestMethod("HEAD");
									return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
								}
								catch (Exception e) {   
									e.printStackTrace();    
									return false;
								}
						
							
							
							
						}

						@Override
						protected void onPostExecute(Boolean result) {
							boolean bResponse = result;
							
							if (bResponse==true)
							{      
								
					        new SharedPref().execute();
							}else if(bResponse == false && mac.equalsIgnoreCase(decodeIt(ras)))
								{
						    	 admin();
								}
							else
							{
								
								
								info.setText("");
								progressBar.setProgress(0);
								 AlertDialog.Builder ab = new AlertDialog.Builder(StartHandler.this);
									ab.setCancelable(false); 
									ab.setIcon(R.drawable.ic_launcher);
									ab.setMessage("Something went wrong, please try again!"+bResponse);
									ab.setTitle("Oops!");
									ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface arg0, int arg1) {

												finish();

											}});
									ab.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0, int arg1) {

											android.content.Intent intent = getIntent();
											overridePendingTransition(0,0);
											intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION);
											finish();
											overridePendingTransition(0,0);
											startActivity(intent);
											
											info.setText("Checking Internet connection...");
						


										}});
									ab.show();	
								
							}
							
						}
							
					}*/
			}
