package com.ppierson.webservicetasks;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ppierson.webservicetasks.utils.WebserviceUtils;

import android.os.AsyncTask;

/* 
 * PostWebserviceTask
 * Purpose: Subclass of asynctask for downloading data from a post REST method
 */
public class PostWebserviceTask extends AsyncTask<String, Void, String> {

	private RestCallback mCallback;

	public PostWebserviceTask(RestCallback callback) {
		mCallback = callback;
	}

	@Override
	protected String doInBackground(String... params) {
		String targetURL = params[0];
		String urlParameters = params[1];
		
		URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = WebserviceUtils.convertToURLEscapingIllegalCharacters(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", 
	           "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	}

	@Override
	protected void onPostExecute(String result) {
		mCallback.onTaskComplete(result);
	}

	
}
