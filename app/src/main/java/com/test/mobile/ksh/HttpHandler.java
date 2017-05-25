package com.test.mobile.ksh;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by KSH on 2016-11-14.
 */

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {

    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = conn.getInputStream();
            response = convertStreamToString(new InputStreamReader(in, "UTF-8"));
        } catch (MalformedURLException e) {
            if(Debug.DEBUG){
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            }
        } catch (ProtocolException e) {
            if(Debug.DEBUG){
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            }
        } catch (IOException e) {
            if(Debug.DEBUG){
                Log.e(TAG, "IOException: " + e.getMessage());
            }
        } catch (Exception e) {
            if(Debug.DEBUG){
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
        return response;
    }

    private String convertStreamToString(InputStreamReader inputStreamReader) {
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
