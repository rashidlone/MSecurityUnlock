package com.rashedlone.msecurityunlock;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by Rashedlone0 on 1/14/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {


    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    public static String REGISTER_URL = "http://betadevelopers.in/gcm/register.php";



    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Refreshed Token", refreshedToken);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        ed.putString("device_token", refreshedToken);
        ed.commit();

        saveToken(refreshedToken);

    }



    public void saveToken(final String s)

    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("Token",s);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }






    }
