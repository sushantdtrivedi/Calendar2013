package com.aceappsdev.calendar2013;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;

public class Splash extends Activity {
	SharedPreferences myPrefs;

	int tim;
	boolean add;
	String curVer;
	String intentString;
	private final int ERROR_EVENT = 0;
	private final int INFO_EVENT = 1;
	private final int USER_EVENT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// forcing network operation on the main thread
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		intentString = "com.aceappsdev.calendar2013.CAL";
		setContentView(R.layout.splash);
		
		// Getting the preferences
		myPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		
		// Setting the selected date to empty for the calendar
		myPrefs.edit().putString("selDate", "Empty").commit();
		
		// Load data in the background thread
		runThread();
	}
	
	private void runThread(){
		Thread timer = new Thread() {
			public void run() {
				try {
					// Check when the data was last updated
					String dataUpdate = myPrefs.getString("updated", "2000/01/01");
					
					// Get the JSON data from the server
					String jsonString = getDataFromServer();
					
					
					if(jsonString==null || jsonString.equals("")){
					// No data found from the internet
						exitActivity();
					}else{
					// Data found from the internet
						
						// Put the data in a JSON Object
						JSONObject jsonObj = null;
						try {
							jsonObj = new JSONObject(jsonString);
						} catch (JSONException e) {
							e.printStackTrace();
							exitActivity();
							return;
						}
						
						// JSON object was created successfully
						// Check if the data on the server was updated since the last check
						
						String serverUpdate = "2000/01/01";
						try{
							serverUpdate = jsonObj.getString("updated");
						}catch (Exception e){
							e.printStackTrace();
							exitActivity();
							return;
						}
						
						if(dataUpdate.compareTo(serverUpdate)>0){
						// no need to update the data
							exitActivity();
							return;
						}else{
						// update the data
							//delete the old data
							deleteData();
							
							boolean success = true;
							
							// store the individual data in individual JSON arrays
							JSONArray jTithi=null, jInd=null, jInt=null;
							
							try{
								jTithi = jsonObj.getJSONArray("tithi");
								jInd = jsonObj.getJSONArray("ind");
								jInt = jsonObj.getJSONArray("inter");
							}catch (Exception e){
								e.printStackTrace();
								success=false;
							}
							
							if(!success) return; // if there was an error in getting the JSON objects, exit the script
							
							// add the new data in the database
							DBConnect entry = new DBConnect(Splash.this);
							entry.open();
							
							//insert tithi data
							for(int i=0; i<jTithi.length();i++){
								try{
									JSONObject jObj = jTithi.getJSONObject(i);
									entry.insertTithi(jObj.getString("date"),
											jObj.getInt("tithiYear"),
											jObj.getString("tithiMonth"),
											jObj.getString("tithiMoon"),
											jObj.getInt("tithiDate"),
											jObj.getString("tithiComment"));
								}catch (Exception e){
									e.printStackTrace();
									success = false;
								}
							}
							
							//insert indian holiday data
							for(int i=0; i<jInd.length();i++){
								try{
									JSONObject jObj = jInd.getJSONObject(i);
									entry.insertInd(jObj.getString("date"),
											jObj.getString("holiday"),
											jObj.getString("state"),
											jObj.getString("comment"));
								}catch (Exception e){
									e.printStackTrace();
									success = false;
								}
							}
							

							
							//insert International holiday data
							for(int i=0; i<jInt.length();i++){
								try{
									JSONObject jObj = jInt.getJSONObject(i);
									entry.insertInt(
											jObj.getString("country"),
											jObj.getString("date"),
											jObj.getString("holiday"),
											jObj.getString("state"),
											jObj.getString("comment")
											);
								}catch (Exception e){
									e.printStackTrace();
									success = false;
								}
							}
							
							
							entry.close();
							
							if(success) myPrefs.edit().putString("updated", serverUpdate).commit();
							
							exitActivity();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					Intent openMain = new Intent(intentString);
					startActivity(openMain);
				}
			}
		};

		timer.start();
	}
	
	private void exitActivity() {
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					Intent openMain = new Intent(intentString);
					startActivity(openMain);
				}
			}
		};

		timer.start();
	}

	private String getDataFromServer() {
		String jsonString = "";
		MyHTTP http = new MyHTTP();
		try {
			jsonString = http.getData("http://www.aceappsdev.com/calendardata.json");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return jsonString;
	}

	private void deleteData() {
		String succ[] = new String[2];
		DBConnect entry = new DBConnect(Splash.this);
		entry.open();
		succ = entry.reCreateTithiTable();
		entry.close();
		logDetail(getEvent(succ[0]), succ[0]);
		logDetail(getEvent(succ[1]), succ[1]);

		entry.open();
		succ = entry.reCreateIntTable();
		entry.close();
		logDetail(getEvent(succ[0]), succ[0]);
		logDetail(getEvent(succ[1]), succ[1]);

		entry.open();
		succ = entry.reCreateIndTable();
		entry.close();
		logDetail(getEvent(succ[0]), succ[0]);
		logDetail(getEvent(succ[1]), succ[1]);

		entry.open();
		succ = entry.reCreateUserTable();
		entry.close();
		logDetail(getEvent(succ[0]), succ[0]);
		logDetail(getEvent(succ[1]), succ[1]);

		// displayInfo();
	}

	private void logDetail(int event, String detail) {
		String res = "\n";
		switch (event) {
		case ERROR_EVENT:
			res += "Error : ";
			break;
		case INFO_EVENT:
			res += "Info : ";
			break;
		case USER_EVENT:
			res += "User : ";
			break;
		}
		res += detail;

		//tvSpDisplay.append(res);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// ensuring that the activity is not running in the background
		finish();
	}

	
	private int getEvent(String str) {
		if (str.contains("completed"))
			return INFO_EVENT;
		return ERROR_EVENT;
	}
}
