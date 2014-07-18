package com.aceappsdev.calendar2013;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class Main extends Activity implements OnClickListener {
	Calendar selectedDate, todaysDate;
	int day, month, selDateId, firstDayOfMonth,todayDateId;
	int firstDayofWeek;
	String selDtStr;
	String cntry;
	SharedPreferences myPrefs;
	Button fMonth, pMonth, nMonth, lMonth;
	TextView tMonth;
	TextView wk1, wk2, wk3, wk4, wk5, wk6, wk7, tvSelect;
	TextView[] tWeek;
	TextView[] tDates;
	TextView tDisplay, tInd, tInt;
	AdView myAd;
	RelativeLayout layout;
	
	MyLog lg;

	private final String[] wkDays = { "S", "M", "T", "W", "T", "F", "S" };
	
	String[] months = { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
			"JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" };

	
	private final String MY_ADMOB_ID = "a151ac6806bbf13";

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.tvMonth:
			// tDisplay.setText("");

			break;
		case R.id.btnFirstMonth:
			lg.log(MyLog.USER_EVENT, "First Month Button Clicked");
			if (month != Calendar.JANUARY) {
				month = Calendar.JANUARY;
				selectedDate.set(2013, month, 1);
				organiseDaysForMonth();
			}
			break;
		case R.id.btnPrevMonth:
			lg.log(MyLog.USER_EVENT, "Previous Month Button Clicked");
			if (month > 0) {
				month--;
				selectedDate.set(2013, month, 1);
				organiseDaysForMonth();
			}
			break;
		case R.id.btnLastMonth:
			lg.log(MyLog.USER_EVENT, "Last Month Button Clicked");
			if (month != Calendar.DECEMBER) {
				month = Calendar.DECEMBER;
				selectedDate.set(2013, month, 1);
				organiseDaysForMonth();
			}
			break;
		case R.id.btnNextMonth:
			lg.log(MyLog.USER_EVENT, "Next Month Button Clicked");
			if (month < Calendar.DECEMBER) {
				month++;
				selectedDate.set(2013, month, 1);
				organiseDaysForMonth();
			}
			break;
		case R.id.tvWeek1Day1:
		case R.id.tvWeek1Day2:
		case R.id.tvWeek1Day3:
		case R.id.tvWeek1Day4:
		case R.id.tvWeek1Day5:
		case R.id.tvWeek1Day6:
		case R.id.tvWeek1Day7:
		case R.id.tvWeek2Day1:
		case R.id.tvWeek2Day2:
		case R.id.tvWeek2Day3:
		case R.id.tvWeek2Day4:
		case R.id.tvWeek2Day5:
		case R.id.tvWeek2Day6:
		case R.id.tvWeek2Day7:
		case R.id.tvWeek3Day1:
		case R.id.tvWeek3Day2:
		case R.id.tvWeek3Day3:
		case R.id.tvWeek3Day4:
		case R.id.tvWeek3Day5:
		case R.id.tvWeek3Day6:
		case R.id.tvWeek3Day7:
		case R.id.tvWeek4Day1:
		case R.id.tvWeek4Day2:
		case R.id.tvWeek4Day3:
		case R.id.tvWeek4Day4:
		case R.id.tvWeek4Day5:
		case R.id.tvWeek4Day6:
		case R.id.tvWeek4Day7:
		case R.id.tvWeek5Day1:
		case R.id.tvWeek5Day2:
		case R.id.tvWeek5Day3:
		case R.id.tvWeek5Day4:
		case R.id.tvWeek5Day5:
		case R.id.tvWeek5Day6:
		case R.id.tvWeek5Day7:
		case R.id.tvWeek6Day1:
		case R.id.tvWeek6Day2:
		case R.id.tvWeek6Day3:
		case R.id.tvWeek6Day4:
		case R.id.tvWeek6Day5:
		case R.id.tvWeek6Day6:
		case R.id.tvWeek6Day7:
			lg.log(MyLog.USER_EVENT, "Some date Clicked");
			tvSelect = (TextView) v;
			String str = String.valueOf(tvSelect.getText());
			int dt;
			if (!(str.equals("") || str == null || str.length() == 0 || str
					.isEmpty())) {
				dt = Integer.parseInt(str);
				myPrefs.edit()
						.putString("selDate",
								String.valueOf(dt) + " " + months[month] + " 2013").commit();
				selectedDate.set(2013, month, dt);
				showDetailsForDate();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cal);
		defineVarialbles();
		try {
			loadInitValues();
		} catch (Exception e) {
			lg.log(MyLog.ERROR_EVENT, "Error InitialValues : " + e.toString());
		} finally {
			lg.log(MyLog.INFO_EVENT, "OnCreate completed");
		}
		// testings();
	}

	private void defineVarialbles() {
		lg = new MyLog(this);
		
		fMonth = (Button) findViewById(R.id.btnFirstMonth);
		fMonth.setOnClickListener(this);

		pMonth = (Button) findViewById(R.id.btnPrevMonth);
		pMonth.setOnClickListener(this);

		nMonth = (Button) findViewById(R.id.btnNextMonth);
		nMonth.setOnClickListener(this);

		lMonth = (Button) findViewById(R.id.btnLastMonth);
		lMonth.setOnClickListener(this);

		tMonth = (TextView) findViewById(R.id.tvMonth);
		tMonth.setOnClickListener(this);

		tWeek = new TextView[7];
		tWeek[0] = (TextView) findViewById(R.id.tvWeekDay1);
		tWeek[1] = (TextView) findViewById(R.id.tvWeekDay2);
		tWeek[2] = (TextView) findViewById(R.id.tvWeekDay3);
		tWeek[3] = (TextView) findViewById(R.id.tvWeekDay4);
		tWeek[4] = (TextView) findViewById(R.id.tvWeekDay5);
		tWeek[5] = (TextView) findViewById(R.id.tvWeekDay6);
		tWeek[6] = (TextView) findViewById(R.id.tvWeekDay7);

		tDates = new TextView[42];
		tDates[0] = (TextView) findViewById(R.id.tvWeek1Day1);
		tDates[0].setOnClickListener(this);
		tDates[1] = (TextView) findViewById(R.id.tvWeek1Day2);
		tDates[1].setOnClickListener(this);
		tDates[2] = (TextView) findViewById(R.id.tvWeek1Day3);
		tDates[2].setOnClickListener(this);
		tDates[3] = (TextView) findViewById(R.id.tvWeek1Day4);
		tDates[3].setOnClickListener(this);
		tDates[4] = (TextView) findViewById(R.id.tvWeek1Day5);
		tDates[4].setOnClickListener(this);
		tDates[5] = (TextView) findViewById(R.id.tvWeek1Day6);
		tDates[5].setOnClickListener(this);
		tDates[6] = (TextView) findViewById(R.id.tvWeek1Day7);
		tDates[6].setOnClickListener(this);

		tDates[7] = (TextView) findViewById(R.id.tvWeek2Day1);
		tDates[7].setOnClickListener(this);
		tDates[8] = (TextView) findViewById(R.id.tvWeek2Day2);
		tDates[8].setOnClickListener(this);
		tDates[9] = (TextView) findViewById(R.id.tvWeek2Day3);
		tDates[9].setOnClickListener(this);
		tDates[10] = (TextView) findViewById(R.id.tvWeek2Day4);
		tDates[10].setOnClickListener(this);
		tDates[11] = (TextView) findViewById(R.id.tvWeek2Day5);
		tDates[11].setOnClickListener(this);
		tDates[12] = (TextView) findViewById(R.id.tvWeek2Day6);
		tDates[12].setOnClickListener(this);
		tDates[13] = (TextView) findViewById(R.id.tvWeek2Day7);
		tDates[13].setOnClickListener(this);

		tDates[14] = (TextView) findViewById(R.id.tvWeek3Day1);
		tDates[14].setOnClickListener(this);
		tDates[15] = (TextView) findViewById(R.id.tvWeek3Day2);
		tDates[15].setOnClickListener(this);
		tDates[16] = (TextView) findViewById(R.id.tvWeek3Day3);
		tDates[16].setOnClickListener(this);
		tDates[17] = (TextView) findViewById(R.id.tvWeek3Day4);
		tDates[17].setOnClickListener(this);
		tDates[18] = (TextView) findViewById(R.id.tvWeek3Day5);
		tDates[18].setOnClickListener(this);
		tDates[19] = (TextView) findViewById(R.id.tvWeek3Day6);
		tDates[19].setOnClickListener(this);
		tDates[20] = (TextView) findViewById(R.id.tvWeek3Day7);
		tDates[20].setOnClickListener(this);

		tDates[21] = (TextView) findViewById(R.id.tvWeek4Day1);
		tDates[21].setOnClickListener(this);
		tDates[22] = (TextView) findViewById(R.id.tvWeek4Day2);
		tDates[22].setOnClickListener(this);
		tDates[23] = (TextView) findViewById(R.id.tvWeek4Day3);
		tDates[23].setOnClickListener(this);
		tDates[24] = (TextView) findViewById(R.id.tvWeek4Day4);
		tDates[24].setOnClickListener(this);
		tDates[25] = (TextView) findViewById(R.id.tvWeek4Day5);
		tDates[25].setOnClickListener(this);
		tDates[26] = (TextView) findViewById(R.id.tvWeek4Day6);
		tDates[26].setOnClickListener(this);
		tDates[27] = (TextView) findViewById(R.id.tvWeek4Day7);
		tDates[27].setOnClickListener(this);

		tDates[28] = (TextView) findViewById(R.id.tvWeek5Day1);
		tDates[28].setOnClickListener(this);
		tDates[29] = (TextView) findViewById(R.id.tvWeek5Day2);
		tDates[29].setOnClickListener(this);
		tDates[30] = (TextView) findViewById(R.id.tvWeek5Day3);
		tDates[30].setOnClickListener(this);
		tDates[31] = (TextView) findViewById(R.id.tvWeek5Day4);
		tDates[31].setOnClickListener(this);
		tDates[32] = (TextView) findViewById(R.id.tvWeek5Day5);
		tDates[32].setOnClickListener(this);
		tDates[33] = (TextView) findViewById(R.id.tvWeek5Day6);
		tDates[33].setOnClickListener(this);
		tDates[34] = (TextView) findViewById(R.id.tvWeek5Day7);
		tDates[34].setOnClickListener(this);

		tDates[35] = (TextView) findViewById(R.id.tvWeek6Day1);
		tDates[35].setOnClickListener(this);
		tDates[36] = (TextView) findViewById(R.id.tvWeek6Day2);
		tDates[36].setOnClickListener(this);
		tDates[37] = (TextView) findViewById(R.id.tvWeek6Day3);
		tDates[37].setOnClickListener(this);
		tDates[38] = (TextView) findViewById(R.id.tvWeek6Day4);
		tDates[38].setOnClickListener(this);
		tDates[39] = (TextView) findViewById(R.id.tvWeek6Day5);
		tDates[39].setOnClickListener(this);
		tDates[40] = (TextView) findViewById(R.id.tvWeek6Day6);
		tDates[40].setOnClickListener(this);
		tDates[41] = (TextView) findViewById(R.id.tvWeek6Day7);
		tDates[41].setOnClickListener(this);

		tDisplay = (TextView) findViewById(R.id.tvDisplay);
		tInd = (TextView) findViewById(R.id.tvInd);
		tInt = (TextView) findViewById(R.id.tvInt);
		//iTithi = (ImageView)findViewById(R.id.ivTithi);
		// myAd = (AdView)findViewById(R.id.avMyAd);
		layout = (RelativeLayout) findViewById(R.id.rlCalendar);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		if ("port".equals(layout.getTag().toString())) {
			params.addRule(RelativeLayout.CENTER_HORIZONTAL,
					RelativeLayout.TRUE);
		} else if ("land".equals(layout.getTag().toString())) {
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
					RelativeLayout.TRUE);
		}
		myAd = new AdView(this, AdSize.BANNER, MY_ADMOB_ID);
		layout.addView(myAd, params);
		AdRequest request = new AdRequest();
		request.setTesting(true);
		myAd.loadAd(request);

	}

	private void loadInitValues() {
		// Get the Preferences
		todaysDate = new GregorianCalendar();
		todaysDate.set(Calendar.YEAR, 2013);
		todayDateId=0;
		try {
			myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		} catch (Exception e) {
			lg.log(MyLog.ERROR_EVENT, "Error Connecting to the prefs");
		}
		String todayStr = String.valueOf(todaysDate.get(Calendar.DATE)) + " "
				+ months[todaysDate.get(Calendar.MONTH)] + " "
				+ String.valueOf(todaysDate.get(Calendar.YEAR));

		String prefDate = myPrefs.getString("selDate", "Empty");
		if (!prefDate.equals("Empty")) { //if the prefDate is not "Empty"
			String[] brokenDate = prefDate.split(" ");

			int prefDt = Integer.parseInt(brokenDate[0]);
			String prefMth = brokenDate[1];
			int prefMthInt;
			int prefYr = Integer.parseInt(brokenDate[2]);
			for (prefMthInt = 0; prefMthInt < months.length; prefMthInt++) {
				if (months[prefMthInt].equals(prefMth)) {
					break;
				}
			}
			selectedDate = new GregorianCalendar(prefYr, prefMthInt, prefDt);
		} else {
			// Set the current date as the Selected date
			selectedDate = new GregorianCalendar();
		}
		
		selectedDate.set(Calendar.YEAR, 2013); //Ensuring that the year is always 2013

		day = selectedDate.get(Calendar.DATE);
		month = selectedDate.get(Calendar.MONTH);
		selDateId = -1;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		organiseWeekDays();
		organiseDaysForMonth();
		lg.log(MyLog.INFO_EVENT, "Resume Completed");

	}

	private void organiseWeekDays() {
		try {
			firstDayofWeek = Integer.parseInt(myPrefs
					.getString("FirstDay", "1"));
			lg.log(MyLog.INFO_EVENT, "Getting fdow Pref completed");
		} catch (Exception e) {
			lg.log(MyLog.INFO_EVENT, "Error getting fdow pref");
		}

		// Sets the weekday titles and sets their background colors
		for (int i = 0; i < 7; i++) {
			if (i < 7 - firstDayofWeek) {
				tWeek[i].setText(wkDays[i + firstDayofWeek]);
			} else {
				tWeek[i].setText(wkDays[i + firstDayofWeek - 7]);
			}

			if (tWeek[i].getText().equals("S")) {
				tWeek[i].setBackgroundResource(R.drawable.backtitle);
			} else {
				tWeek[i].setBackgroundResource(R.drawable.backtitle);
			}
		}
		lg.log(MyLog.INFO_EVENT, "Changing the weekday details Completed");

		// change the backgrounds of the days
		for (int i = 0; i < tDates.length; i++) {
			int j = firstDayofWeek + i;
			try {
				if (j % 7 == 6 || j % 7 == 0) {
					tDates[i].setBackgroundResource(R.drawable.backweekend);
				} else {
					tDates[i].setBackgroundResource(R.drawable.backweekday);
				}
			} catch (Exception e) {
				lg.log(MyLog.ERROR_EVENT, "error : " + e.toString() + "\ni is "
						+ String.valueOf(i) + "\nj is " + String.valueOf(j));
			}
		}
		lg.log(MyLog.INFO_EVENT, "Changing the monthDay Background Completed");

	}

	private void organiseDaysForMonth() {

		tMonth.setText(months[month]);
		Calendar c = new GregorianCalendar(2013, month, 1);
		firstDayOfMonth = c.get(Calendar.DAY_OF_WEEK) - 1;

		lg.log(MyLog.INFO_EVENT, "FDOM = " + String.valueOf(firstDayOfMonth));
		lg.log(MyLog.INFO_EVENT, "FDOW = " + String.valueOf(firstDayofWeek));

		int numberOfDays = selectedDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		int wkDayCounter;
		int diff = firstDayOfMonth - firstDayofWeek;
		// =IF(firstDayOfMonth>=firstDayofWeek,firstDayOfMonth-firstDayofWeek,firstDayOfMonth-firstDayofWeek+7)
		if (firstDayOfMonth < firstDayofWeek)
			diff += 7;

		for (wkDayCounter = 0; wkDayCounter < diff; wkDayCounter++) {
			tDates[wkDayCounter].setText("");
		}
		for (int i = 1; i <= numberOfDays; i++, wkDayCounter++) {
			tDates[wkDayCounter].setText(String.valueOf(i));
		}
		for (; wkDayCounter < tDates.length; wkDayCounter++) {
			tDates[wkDayCounter].setText("");
		}
		showDetailsForDate();
		lg.log(MyLog.INFO_EVENT, "Changing month details Completed");
	}

	private void showDetailsForDate() {
		
		selDtStr = showSelectedDate(2);
		
		if (myPrefs.getBoolean("showTithi", true)) {
			displayTithiInfo();

		}else tDisplay.setText("");
		
		if (myPrefs.getBoolean("showInd", true)) {
			displayIndInfo();
		}else tInd.setText("");
		
		if (myPrefs.getBoolean("showInt", true)) {
			displayIntInfo(cntry);
		}else tInt.setText("");
	}

	private String showSelectedDate(int format) {
		String disp = "";

		// Changing the old selected date to normal
		if (selDateId >= 0) {
			int j = firstDayofWeek + selDateId;
			try {
				//tDates[selDateId].setTextColor(Color.BLACK);
				if (j % 7 == 6 || j % 7 == 0) {
					tDates[selDateId]
							.setBackgroundResource(R.drawable.backweekend);
				} else {
					tDates[selDateId]
							.setBackgroundResource(R.drawable.backweekday);
				}
			} catch (Exception e) {
				lg.log(
						MyLog.ERROR_EVENT,
						"error while changing the date back to normal : "
								+ e.toString());
			}
		}

		setToday();

		String dte = String.valueOf(selectedDate.get(Calendar.DATE));
		int mnth = selectedDate.get(Calendar.MONTH);
		String yr = String.valueOf(selectedDate.get(Calendar.YEAR));
		switch (format) {
		case 0: // mm\dd\yyyy
			disp = dte + "\\" + String.valueOf(mnth + 1) + "\\" + yr;
			break;
		case 1: // dd\mm\yyyy
			disp = String.valueOf(mnth + 1) + "\\" + dte + "\\" + yr;
			break;
		case 2: // dd MMM yyyy
			disp = dte + " " + months[mnth] + " " + yr;
		}

		for (int j = 0; j < tDates.length; j++) {
			if (dte.equals(tDates[j].getText())) {
				//tDates[j].setTextColor(Color.WHITE);
				tDates[j].setBackgroundResource(R.drawable.backselected);
				selDateId = j;
			}
		}

		return disp;
	}

	private void setToday() {
		// SETTING TODAY'S DATE TO A DIFFERENT BACKGROUND
		int todayMnth = todaysDate.get(Calendar.MONTH);
		// int todayYear = todaysDate.get(Calendar.YEAR);
		if (month == todayMnth) {
			int todayDate = todaysDate.get(Calendar.DATE);
			for (int i = 0; i < tDates.length; i++) {
				if (tDates[i].getText().equals(String.valueOf(todayDate))) {
					//tDates[i].setTextColor(Color.BLACK);
					tDates[i].setBackgroundResource(R.drawable.backtoday);
					todayDateId=i;
				}
			}
		}else{
			int j = firstDayofWeek + todayDateId;
			try {
				//tDates[todayDateId].setTextColor(Color.BLACK);
				if (j % 7 == 6 || j % 7 == 0) {
					tDates[todayDateId]
							.setBackgroundResource(R.drawable.backweekend);
				} else {
					tDates[todayDateId]
							.setBackgroundResource(R.drawable.backweekday);
				}
			} catch (Exception e) {
				lg.log(
						MyLog.ERROR_EVENT,
						"error while changing the today date back to normal : "
								+ e.toString());
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// return super.onOptionsItemSelected(item);
		Intent i;
		switch (item.getItemId()) {
		case R.id.allHol:
			i = new Intent("com.aceappsdev.calendar2013.HOL");
			startActivity(i);
			break;
		case R.id.action_settings:
			i = new Intent("com.aceappsdev.calendar2013.PREFS");
			startActivity(i);
			break;
		case R.id.action_about:
			i = new Intent("com.aceappsdev.calendar2013.ABOUTSUSH");
			startActivity(i);
			break;
		case R.id.action_Feedback:
			i = new Intent("com.aceappsdev.calendar2013.FEEDBACK");
			startActivity(i);
			break;
		case R.id.action_Google_signIn:
			i = new Intent("com.aceappsdev.calendar2013.AWAITING");
			startActivity(i);
			break;
		case R.id.action_exit:
			finish();
			break;
		}
		return true;
	}

	private void displayTithiInfo() {
		DBConnect info = new DBConnect(Main.this);
		info.open();
		Tithi tithi[] = info.getTithiDataForDate(selDtStr);
		info.close();
		tDisplay.setText("");
		if(tithi!=null) for(int i=0;i<tithi.length;i++) tDisplay.append(tithi[i].toString());
		else tDisplay.setText("Padtar Divas");
	}

	private void displayIndInfo() {
		DBConnect info = new DBConnect(Main.this);
		info.open();
		IndHoliday myData[] = info.getIndDataForDate(selDtStr);
		info.close();
		
		tInd.setText("");
		if(myData!=null) for(int i=0;i<myData.length;i++) tInd.append(myData[i].toString());
		else tInd.setText("No Indian holiday for this date!");
	}

	private void displayIntInfo(String cntry) {
		// TODO Auto-generated method stub
		DBConnect info = new DBConnect(Main.this);
		info.open();
		IntHoliday myData[] = info.getIntDataForDate(selDtStr, "Aus");
		info.close();
		
		tInt.setText("");
		if(myData!=null) for(int i=0;i<myData.length;i++) tInt.append(myData[i].toString());
		else tInt.setText("No Australian holiday for this date!");
		
		//tInt.setText(myData);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		layout.removeView(myAd);
		super.onDestroy();
	}
}
