package com.example.anuagarwal.sample.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anu Agarwal on 12/23/2015.
 */
public class Json {

    private static Context mContext;

    public static JSONObject getJson(Context context){

        InputStream is = null;
        String result = "";
        JSONObject jsonObject = null;
        mContext = context;

        // HTTP
        try {
            URL u = new URL("http://staging.couponapitest.com/task.txt");
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.connect();
            is = c.getInputStream();
        } catch(Exception e) {
            //Toast.makeText(mContext,"No network available",Toast.LENGTH_LONG).show();
            return  null;
        }

        // Read response to string
        try {
            if(is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line.trim());
                }
                is.close();
                result = sb.toString();
            }
        } catch(Exception e) {
            return null;
        }

        // Convert string to object
        try {
            jsonObject = new JSONObject(result);
        } catch(JSONException e) {
            return null;
        }

        return jsonObject;

    }

}
