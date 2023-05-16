package com.rashedlone.msecurityunlock;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;



public class Sense extends Activity implements SensorEventListener{
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	
	@Override protected void onCreate(Bundle s){
		
		super.onCreate(s);
		setContentView(R.layout.after_done);
		
	 mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	 mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	
	 }
		
		 protected void onResume() {
		 super.onResume();
		 mSensorManager.registerListener(this, mSensor,
		 SensorManager.SENSOR_DELAY_NORMAL);
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
		
		 public void onSensorChanged(Sensor event) {
		/* if (event.values[0] == 0) {
		 Toast.makeText(getApplicationContext(), "you tappped proximity sensor", Toast.LENGTH_LONG).show();
		 } else {
			 Toast.makeText(getApplicationContext(), "you left proximity sensor", Toast.LENGTH_LONG).show();
				
		 
		}*/
		 
	}

		@Override
		public void onSensorChanged(SensorEvent arg0) {
			// TODO Auto-generated method stub
			
		}



}
