package com.aceappsdev.calendar2013;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	private EditText tDetails;
	private Button bSubmit, bCancel;
	private CheckBox attLog;
	private RadioGroup rg;
	private TextView tError;
	
	private int feedbackType;
	String sub;

	MyLog mlg;

	public static final int BUG_FEED = 0;
	public static final int SUG_FEED = 1;
	public static final int REQ_FEED = 2;

	String[] months = { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
			"JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// tNotification = new TextView(this);
		// tNotification.setText("Please send the feedback on admin@aceappsdev.com");
		mlg = new MyLog(this);

		setContentView(R.layout.feedback);
		
		tError = (TextView)findViewById(R.id.tvError);

		tDetails = (EditText) findViewById(R.id.etBody);
		tDetails.setOnClickListener(this);

		bSubmit = (Button) findViewById(R.id.btnSubmit);
		bSubmit.setOnClickListener(this);

		bCancel = (Button) findViewById(R.id.btnCancel);
		bCancel.setOnClickListener(this);

		attLog = (CheckBox) findViewById(R.id.chkLog);
		rg = (RadioGroup) findViewById(R.id.rgType);
		rg.setOnCheckedChangeListener(this);

		feedbackType = BUG_FEED;
		sub="Bug Report";
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSubmit:
			sendFeedBack();
			break;
		case R.id.btnCancel:
			finish();
			break;
		}
	}

	

	private String fetchLog() {
		DBConnect con = new DBConnect(this);
		String strData = "Log not attached";
		con.open();
		if (attLog.isChecked())
			strData = con.getLog();
		else if (feedbackType == BUG_FEED) {
			strData = con.getLog(MyLog.ERROR_EVENT);
		}
		con.close();
		return strData;
	}

	private void sendFeedBack() {
		String det = tDetails.getText().toString();
		String log = fetchLog();
		det = det + "\n\n" + log;
		//String arg = "email=" + frm + "&" + "message=" + det+ "&" + "subject=" + sub;
		//String res = "";
		if (isNetworkAvailable()) {
						
			Intent intnt = new Intent(Intent.ACTION_SEND);
			intnt.setType("text/email");
			intnt.putExtra(Intent.EXTRA_SUBJECT, sub);
			intnt.putExtra(Intent.EXTRA_TEXT, det);
			intnt.putExtra(Intent.EXTRA_EMAIL,new String[] {"admin@aceappsdev.com"} ); // or just "mailto:" for blank
			//intnt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
			try {
				startActivity(Intent.createChooser(intnt, "Send feedback..."));
			} catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(Feedback.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
			
			//res = tDetails.getText().toString();
		}else{
			tError.setText("Internet connection not available");
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch (arg1) {
		case R.id.rBug:
			sub = "Bug Report";
			feedbackType = BUG_FEED;
			break;
		case R.id.rReq:
			sub = "Feature Request";
			feedbackType = REQ_FEED;
			break;
		case R.id.rSug:
			sub = "Suggestion";
			feedbackType = SUG_FEED;
			break;
		}
	}

}
