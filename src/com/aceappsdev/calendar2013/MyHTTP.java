package com.aceappsdev.calendar2013;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This class is responsible for fetching data from the server
 * @author Sushant
 *
 */
public class MyHTTP {

	public String getData(String uri) throws Exception{
		if(uri==null) return null;
		BufferedReader in = null;
		String data = null;
		
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			//prepare the URI
			URI website = new URI(uri);
			
			// prepare the request
			HttpGet request = new HttpGet();
			
			// set the uri for the request
			request.setURI(website);
			
			// Capture the response
			HttpResponse response = httpClient.execute(request);
			
			// Store the response in a buffered reader
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String newLine = System.getProperty("line.seperator");
			
			// store lines one by one in the String buffer  
			while ((line = in.readLine()) != null){
				sb.append(line+newLine);
			}
			
			// close the buffered reader
			in.close();
			
			// store the data from string buffer to a String
			data = sb.toString();
		}finally{
			if(in != null){
				try{in.close();}
				catch(Exception e){e.printStackTrace();}
			}
		}
		return data;
	}
}