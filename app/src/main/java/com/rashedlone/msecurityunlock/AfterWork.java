package com.rashedlone.msecurityunlock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class AfterWork extends Activity implements SensorEventListener {
	
	
	private String msg;
	private String pn;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private SharedPreferences sp;
	private String star = "Your device has been successfully unlocked! Your keyguard will be inactive till you force close this app. Is it worth a five star?";
	private Editor ed;

	
	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.after_done);
		
		 mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		 mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		 mSensorManager.registerListener(this, mSensor,
				 SensorManager.SENSOR_DELAY_NORMAL);
	
	msg = ServiceAfter.msg_to;
	pn = getApplicationContext().getPackageName();
	sp = PreferenceManager.getDefaultSharedPreferences(this);
	ed = sp.edit();
	
	
	
	AlertDialog.Builder ab = new AlertDialog.Builder(AfterWork.this);				
	ab.setCancelable(false); 
	ab.setIcon(R.drawable.ic_launcher);
	ab.setMessage(msg);
	ab.setTitle("Woow!");
	ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

				finish();
			}}); 
	ab.setNegativeButton("Rate 5 Star!", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface arg0, int arg1) {

			try {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+pn)));
			} catch (android.content.ActivityNotFoundException an) {
				Toast.makeText(getApplicationContext(), "Playstore error!\n\nPlaystore is either disabled or not installed",Toast.LENGTH_LONG).show();
			}
			
			finish();
		}}); 
	if(msg != "true")
	ab.show();
}// on create 
	
	
	 protected void onResume() {
		 super.onResume();
		 
		 }
		
		 protected void onPause() {
		 super.onPause();
		 mSensorManager.unregisterListener(this);
		 }
		 protected void onDestroy(){
			 super.onDestroy();
			 mSensorManager.unregisterListener(this);
			 
			 
		 }
		
		 public void onAccuracyChanged(Sensor sensor, int accuracy) {
		 }
		
		 public void onSensorChanged(SensorEvent event) {
		 if (event.values[0] == 0) {
			 if(msg == "true")
			 {
			 unlock();
			 Toast.makeText(getApplicationContext(), "unlocked", Toast.LENGTH_LONG).show();
			 mSensorManager.unregisterListener(this);
			 } 
		 }
			
		 
	}

		 @SuppressLint("Wakelock")
			public void wakeDevice() {
				 PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
					WakeLock fullWakeLock = powerManager.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "FULL WAKE LOCK");
					powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PARTIAL WAKE LOCK");
			
					fullWakeLock.acquire();
			 }
			
	
	
	
	public void unlock(){
		wakeDevice();
		KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
  		KeyguardLock lock = keyguardManager.newKeyguardLock(Context.KEYGUARD_SERVICE);
  		lock.disableKeyguard();
  		
  		if(sp.getBoolean("status_no", true)== true)
   		{
   		ed.clear();
		ed.commit();
   		}
  		
  		AlertDialog.Builder ab = new AlertDialog.Builder(AfterWork.this);				
  		ab.setCancelable(false); 
  		ab.setIcon(R.drawable.ic_launcher);
  		ab.setMessage(star+"\n\nUnlock Code = SEN");
		ab.setTitle("Woow!");
  		ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
  				public void onClick(DialogInterface arg0, int arg1) {

  					finish();
  					msg = "";
  					
  			  		
  				}}); 
  		ab.setNegativeButton("Rate 5 Star!", new DialogInterface.OnClickListener() {
  			public void onClick(DialogInterface arg0, int arg1) {

  				try {
  					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+pn)));
  				} catch (android.content.ActivityNotFoundException an) {
  					Toast.makeText(getApplicationContext(), "Playstore error!\n\nPlaystore is either disabled or not installed",Toast.LENGTH_LONG).show();
  				}
  				msg = "";
  				
  		  		
  				finish();
  			}}); 
  		ab.show();
  		
	}	
	
	
}
