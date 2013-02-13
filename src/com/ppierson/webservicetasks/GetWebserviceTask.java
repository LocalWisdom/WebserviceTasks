package com.ppierson.webservicetasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.util.Log;

import com.ppierson.webservicetasks.utils.Constants;
import com.ppierson.webservicetasks.utils.WebserviceUtils;


/* 
 * GetWebserviceTask
 * Purpose: Subclass of asynctask for downloading data from a get REST method
 */
public class GetWebserviceTask extends AsyncTask<String, Void, String> {
	private RestCallback mCallback;
	
	public GetWebserviceTask(RestCallback callback) {
		// TODO Auto-generated constructor stub
		this.mCallback = callback;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		if(params.length == 0) return null;
		String urlString = params[0];
		URI uri = WebserviceUtils.convertToURIEscapingIllegalCharacters(urlString);
		
		HttpClient client = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet get = new HttpGet(uri);
		String responseString = null;
		
		try{
			HttpResponse response = client.execute(get, localContext);
			HttpEntity entity = response.getEntity();
			responseString = getASCIIContectFromEntity(entity);
		}catch(Exception e){
			Log.d(Constants.TAG, "Error using GetWebserviceTask for url: " + urlString);
			e.printStackTrace();
			return null;
		}
		
		return responseString;
	}
	
	@Override 
	protected void onPostExecute(String result) {
		if(this.isCancelled()){
			return;
		}else{
			mCallback.onTaskComplete(result);
		}
	}
	
	protected String getASCIIContectFromEntity(HttpEntity entity) throws IllegalStateException, IOException{
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0){
			byte[] b = new byte[4096];
			n = in.read(b);
			if(n > 0) out.append(new String(b, 0 ,n));
		}
		return out.toString();
	}
}


