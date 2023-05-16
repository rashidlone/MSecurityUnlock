package com.rashedlone.msecurityunlock;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import android.preference.PreferenceManager;

import static android.R.attr.bitmap;
import static android.R.attr.label;


@SuppressWarnings("deprecation")
public class ServiceAfter extends Service 
{

	private static final String tag = ServiceAfter.class.getSimpleName();
	private boolean val = false;		
	private int status;
	private SharedPreferences sp;
	private String unlock;
	private boolean isProximity;
	private PackageManager pm;
	private Context context = this;
	private AudioManager am;
	private SharedPreferences.Editor ed;
	private String star = "Your device has been successfully unlocked!";
	public static String msg_to = "";
	private NotificationManager notificationManager;

	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(tag, "onBind");
		return null;
	}
	

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d(tag, "onCreate");
		
	
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(tag, "onStart startId=" + startId);
		
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(tag, "onStartCommand startId=" + startId);
		
		inBackground();
		
		return super.onStartCommand(intent, flags, startId);
				
		
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(tag, "onUnbind");
		
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		
		Log.d(tag, "onDestroy");
		notificationManager.cancel(0);
	}
	
	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		

	}
	
	 
	
	
	public void inBackground(){
		

		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		ed = sp.edit();
	
		noti();
		
		 unlock = sp.getString("list_preference", "15");
		 val = RashedLone.send_it;
		 pm = getPackageManager();
		 isProximity =  pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY);
		 
		
			am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			
			
			//Initialization ends
			
		if(sp.getBoolean("ist_thing", false) == true)
			{
			switch(Integer.valueOf(unlock)){
			
			case 15:
				
				if(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT)
				{
					unlock();
					msg_to = star+"\n\nUnlock Code = SIL" ;
					
				}
				
							
				break;
				
			case 27:
				
				if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE)
				{
					unlock();
					msg_to = star+"\n\nUnlock Code = VIB" ;
				}
				
				break;
				
			case 30:
				
				if(status == 1)
				{
					unlock();
					msg_to = star+"\n\nUnlock Code = FLI" ;
					
				}
				
				break;
				
            case 44:
            	
            	if(isCharging())
				{
					unlock();
					msg_to = star+"\n\nUnlock Code = CHR" ;
					
				}
            	
            	break;
            	
           case 82:
        	   
        	   
            	if(am.isWiredHeadsetOn())
				{
            		unlock();
            		msg_to = star+"\n\nUnlock Code = HDP" ;
					
				}
            	break;
            	
            	
            case 63:
            	
            	unlock();
            	msg_to = star+"\n\nUnlock Code = DIR" ;
            	
            	break;
            	
            case 72:
            	
            //work needed
            	if(!isProximity)
            	 Toast.makeText(getApplicationContext(), "This device doesn't have Proximity sensor." ,Toast.LENGTH_SHORT).show();
				else if(sp.getBoolean("sensor_unlock", false)==true) {
					Intent intent = new Intent(getApplicationContext(), AfterWork.class);
			   		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					msg_to = "true";
					
				}
                break;
            	
            case 87:
            	//work needed
            	if(val)
            	{
            		unlock();
            		msg_to = star+"\n\nUnlock Code = VOI" ;
            	}
            	
            	break;
            	
            case 99:
            	
            	
            	break;
            	
            
            	
			
			}
			
			}//first_thing boolean
		
	
		
	}//in background ends

	
	private void noti() {

		Intent intent = new Intent(this, DevelopedByRashedLone.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
				PendingIntent.FLAG_ONE_SHOT);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
						R.drawable.ic_launcher)).setSmallIcon(getNotificationIcon()).setOngoing(true)
				.setContentTitle(getResources().getString(R.string.app_name)).setContentText((sp.getBoolean("ist_thing", true))?sp.getString("main_info", ""):"Smart Unlock is Running...")
				.setContentIntent(pendingIntent).setAutoCancel(false);
		notificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, notificationBuilder.build());



	}



	private int getNotificationIcon() {
		boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
		return useWhiteIcon ? R.drawable.ic_about : R.drawable.ic_launcher;
	}


	public boolean isCharging() {
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	    Intent batteryStatus = registerReceiver(null, filter);

	    boolean strState;
	    
	    int chargeState = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

	    switch (chargeState) {
	        case BatteryManager.BATTERY_STATUS_CHARGING:
	        case BatteryManager.BATTERY_STATUS_FULL:
	            strState = true;
	            break;
	        default:
	            strState = false;
	    }
		return strState;

		
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
   		Intent intent = new Intent(getApplicationContext(), AfterWork.class);
   		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
   		
		
	}
	

	
	
	
}
