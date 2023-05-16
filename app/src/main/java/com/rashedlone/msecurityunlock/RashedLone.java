package com.rashedlone.msecurityunlock;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.ITelephony;


public class RashedLone extends BroadcastReceiver {
	
	private SharedPreferences sp;
	private String number;
	private Intent mIntent;
	public static boolean send_it;
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)

	@Override
	public void onReceive(Context context, Intent intent) {
		
		 sp = PreferenceManager.getDefaultSharedPreferences(context);
			
		       
				/*//test 
				
				if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {

					number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
					
					if(sp.getString("voice_unlock_number", "")!=null)
					{
						Toast.makeText(context, "if state" ,Toast.LENGTH_SHORT).show();
						
				 	        	mIntent = new Intent(context, ServiceAfter.class);
				 	        	context.startService(mIntent);
				 	        	send_it = true;
				 	        	if(sp.getBoolean("auto_disconnect", true)== true)
				 	        	disconnectPhoneItelephony(context);
				 	        	
					 	        	 	             
						
					}
					
					
				}
				
				//test ends
				
				*/
				
				
		
		if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
		
			mIntent = new Intent(context, ServiceAfter.class);
			 context.startService(mIntent);		 	
	
	}//ACTION_BOOT_COMPLETED
	
		if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {

				
			number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			
	         if(sp.getBoolean("voice_unlock", false) == true && sp.getString("voice_unlock_number", "").equalsIgnoreCase(number) == true && sp.getBoolean("ist_thing", false) == true && sp.getString("list_preference", "15").equals("87"))
	         {
	 	        	mIntent = new Intent(context, ServiceAfter.class);
	 	        	context.startService(mIntent);
	 	        	send_it = true;
	 	        	if(sp.getBoolean("auto_disconnect", true))
	 	        	disconnectPhoneItelephony(context);
		 	        	 	             
	 	         }
	 	     }  
	 					
	
}
	
      

	@SuppressWarnings({ "rawtypes", "unchecked" })	 
	 private void disconnectPhoneItelephony(Context context) 
	 {
		 ITelephony telephonyService;
		 TelephonyManager telephony = (TelephonyManager) 
		 context.getSystemService(Context.TELEPHONY_SERVICE);  
		 try 
		 {
			 Class c = Class.forName(telephony.getClass().getName());
			 Method m = c.getDeclaredMethod("getITelephony");
			 m.setAccessible(true);
			 telephonyService = (ITelephony) m.invoke(telephony);
		     telephonyService.endCall();
		 } 
		 catch (Exception e) 
		 {
			 e.printStackTrace();
		 }
	 }


	
	
}

