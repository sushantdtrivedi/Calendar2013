package com.aceappsdev.calendar2013;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;

/**
 * This class is responsible for storing the logs in the database for troubleshooting if any user has any.
 * @author Sushant
 *
 */
public class MyLog {
	public static final int USER_EVENT = 0;
	public static final int ERROR_EVENT = 1;
	public static final int INFO_EVENT = 2;
	
	// String to store gtet the months by month number
	String[] months = { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
			"JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" };

	// Data base connection object
	private DBConnect logEntry;
	
	/**
	 * The Constructor
	 * @param con The context for which the database conneciton has to be initiated
	 */
	public MyLog(Context con) {
		logEntry = new DBConnect(con);
	}
	
	/**
	 * This method is used to store the log in the database
	 * 
	 * @param eventType The type of the event the user is logging.<br />
	 * <ul>
	 * <li> <b>0</b> indicates a user action event. <i>USER_EVENT</i> constant can also be used for this</li>
	 * <li> <b>1</b> indicates an error event.  <i>ERROR_EVENT</i> constant can also be used for this</li>
	 * <li> <b>2</b> indicates an information event.  <i>INFO_EVENT</i> constant can also be used for this</li>
	 * </ul> 
	 * @param logNote
	 * @return
	 */
	public long log(int eventType, String logNote){
		Calendar dt = new GregorianCalendar();
		String strDate;
		strDate = String.valueOf(dt.get(Calendar.DATE));
		strDate +=  " " + months[dt.get(Calendar.MONTH)];
		strDate += " " + String.valueOf(dt.get(Calendar.YEAR));
		strDate += " " + String.valueOf(dt.get(Calendar.HOUR_OF_DAY));
		strDate += ":" + String.valueOf(dt.get(Calendar.MINUTE));
		strDate += ":" + String.valueOf(dt.get(Calendar.SECOND));
		logEntry.open();
		long ret =logEntry.insertLog(strDate,eventType, logNote); 
		logEntry.close();
		return ret;
	}
}
