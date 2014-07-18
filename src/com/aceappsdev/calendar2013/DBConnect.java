package com.aceappsdev.calendar2013;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DBConnect {

	/**
	 * Database details
	 */
	public static final String DATABASE_NAME = "SushCalendarDB";
	public static final int DATABASE_VERSION = 1;

	public static final String DATABASE_TABLE_TITHI = "TithiCalendar";
	public static final String DATABASE_TABLE_INT = "IntCalendar";
	public static final String DATABASE_TABLE_IND = "IndCalendar";
	public static final String DATABASE_TABLE_USER = "UserCalendar";
	public static final String DATABASE_TABLE_LOG = "LogTable";

	/**
	 * The fields of the tables
	 */
	public static final String KEY_DATE = "date";
	public static final String KEY_ROW = "row_id";

	public static final String KEY_TITHI_YEAR = "tithi_year";
	public static final String KEY_TITHI_MONTH = "tithi_month";
	public static final String KEY_TITHI_MOON = "tithi_moon";
	public static final String KEY_TITHI_DATE = "tithi_date";
	public static final String KEY_TITHI_COMMENT = "tithi_comments";

	public static final String KEY_COUNTRY = "country";
	public static final String KEY_INT_HOL = "int_holiday";
	public static final String KEY_INT_STATE = "int_state";
	public static final String KEY_INT_COMMENTS = "int_comments";

	public static final String KEY_IND = "ind_holiday";
	public static final String KEY_IND_STATE = "ind_state";
	public static final String KEY_IND_COMMENTS = "ind_comments";
	public static final String KEY_PRIVATE_EVENT = "private_event";

	private static final String KEY_EVENT_TYPE = "eventType";
	private static final String KEY_EVENT_DETAIL = "eventDetail";

	private DBHelper myHelper;
	private Context myContext;
	private SQLiteDatabase myDB;

	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		/**
		 * Creates all the tables for the database
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			createUserTable(db);
			createTithiTable(db);
			createIntTable(db);
			createIndTable(db);
			createLogTable(db);
		}

		/**
		 * Creates the log table in the specified database <br />
		 * The table contains 3 fields<br />
		 * date, eventType and eventDetail
		 * 
		 * @param db
		 */
		private void createLogTable(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_LOG + " (" + KEY_DATE
					+ " TEXT PRIMARY KEY, " + KEY_EVENT_TYPE
					+ " INTEGER NOT NULL, " + KEY_EVENT_DETAIL
					+ " TEXT NOT NULL" + ");");
		}

		/**
		 * Creates the user table in the specified database <br />
		 * The table contains 2 fields<br />
		 * date and eventDetail
		 * 
		 * @param db
		 */
		private void createUserTable(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_USER + " (" + KEY_DATE
					+ " TEXT PRIMARY KEY, " + KEY_PRIVATE_EVENT
					+ " TEXT NOT NULL" + ");");
		}

		/**
		 * Creates the tithi table in the specified database <br />
		 * The table contains 6 fields<br />
		 * rowId&#09;AutoInteger&#09;Primary Key<br />
		 * date&#09;Text&#09;Not Null<br />
		 * year&#09;Integer&#09;<br />
		 * month&#09;Text&#09;<br />
		 * moon&#09;Text&#09;<br />
		 * tithi&#09;Integer&#09;
		 * 
		 * @param db
		 */
		private void createTithiTable(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_TITHI + " (" + KEY_ROW
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE
					+ " TEXT NOT NULL, " + KEY_TITHI_YEAR + " NUMBER, "
					+ KEY_TITHI_MONTH + " TEXT, " + KEY_TITHI_MOON + " TEXT, "
					+ KEY_TITHI_DATE + " Integer, " + KEY_TITHI_COMMENT
					+ " TEXT);");
		}

		/**
		 * Creates the International holiday table in the specified database <br />
		 * The table contains 3 fields<br />
		 * rowId&#09;AutoInteger&#09;Primary Key<br />
		 * country&#09;Text&#09;Not Null<br />
		 * holiday&#09;Text&#09;<br />
		 * state&#09;Text&#09;<br />
		 * comments&#09;Text&#09;<br />
		 * 
		 * @param db
		 */
		private void createIntTable(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_INT + " (" + KEY_ROW
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE
					+ " TEXT NOT NULL, " + KEY_COUNTRY + " TEXT NOT NULL, "
					+ KEY_INT_HOL + " TEXT NOT NULL, " + KEY_INT_STATE
					+ " TEXT, " + KEY_INT_COMMENTS + " TEXT" + ");");
		}

		/**
		 * Creates the Indianholiday table in the specified database <br />
		 * The table contains 3 fields<br />
		 * rowId&#09;AutoInteger&#09;Primary Key<br />
		 * holiday&#09;Text&#09;<br />
		 * state&#09;Text&#09;<br />
		 * comments&#09;Text&#09;<br />
		 * 
		 * @param db
		 */
		private void createIndTable(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_IND + " (" + KEY_ROW
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE
					+ " TEXT NOT NULL, " + KEY_IND + " TEXT NOT NULL, "
					+ KEY_IND_STATE + " TEXT, " + KEY_IND_COMMENTS + " TEXT"
					+ ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TITHI);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_INT);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_IND);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_USER);
			onCreate(db);
		}

	}

	public DBConnect(Context c) {
		myContext = c;
	}

	/**
	 * This method opens the connection to the database and allows you to
	 * manipulate the database
	 * 
	 * @return DBConnect object with a wirtableDatabase
	 * @throws SQLException
	 */
	public DBConnect open() throws SQLException {
		myHelper = new DBHelper(myContext);
		myDB = myHelper.getWritableDatabase();
		return this;
	}

	/**
	 * This method closes the connection to the database so another connection
	 * can be performed
	 */
	public void close() {
		myHelper.close();
	}

	/**
	 * This method inserts a Tithi Record in the database and returns the id of the newly created row
	 * 
	 * @param insDate The date for which the tithi data is being inserted
	 * @param yearTithi The year of the tithi
	 * @param monthTithi The month of the tithi
	 * @param moonTithi the moon position of the tithi
	 * @param dateTithi the date of the month
	 * @param cmntTithi Comments on the tithi info
	 * 
	 * @return returns the id of the newly created row<br />
	 * Returns -1 if inserting the record is unsuccessful
	 */
	public long insertTithi(String insDate, int yearTithi, String monthTithi,
			String moonTithi, int dateTithi, String cmntTithi) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_DATE, insDate);
		cv.put(KEY_TITHI_YEAR, yearTithi);
		cv.put(KEY_TITHI_MONTH, monthTithi);
		cv.put(KEY_TITHI_MOON, moonTithi);
		cv.put(KEY_TITHI_DATE, dateTithi);
		cv.put(KEY_TITHI_COMMENT, cmntTithi);
		return myDB.insert(DATABASE_TABLE_TITHI, null, cv);
	}

	/**
	 * This method inserts an Indian Holiday Record in the database and returns the id of the newly created row
	 * 
	 * @param insDate The Date of the holiday
	 * @param indHol  The name of the holiday
	 * @param state   The state in which the holiday is observed
	 * @param comments The comments on the holiday
	 * 
	 * @return returns the id of the newly created row<br />
	 * Returns -1 if inserting the record is unsuccessful
	 */
	public long insertInd(String insDate, String indHol, String state,
			String comments) {
		// TODO Auto-generated method stub
		if (insDate == null || insDate.isEmpty() || insDate.equals("")
				|| indHol == null || indHol.isEmpty() || indHol.equals(""))
			return -1;
		ContentValues cv = new ContentValues();
		cv.put(KEY_DATE, insDate);
		cv.put(KEY_IND, indHol);
		if (state != null && !state.isEmpty())
			cv.put(KEY_IND_STATE, state);
		if (comments != null && !comments.isEmpty())
			cv.put(KEY_IND_COMMENTS, comments);
		return myDB.insert(DATABASE_TABLE_IND, null, cv);
	}

	/**
	 * This method inserts an international holiday record in the database and returns the id of the newly created row
	 * 
	 * @param cntry the country where the holiday is observed
	 * @param insDate the date of the holiday
	 * @param intHol the name of the holiday
	 * @param state the state in which the holiday is observed
	 * @param comments Comments for the holiday
	 * 
	 * @return Returns the id of the newly created row<br />
	 * Returns -1 if inserting the record is unsuccessful
	 */
	public long insertInt(String cntry, String insDate, String intHol,
			String state, String comments) {
		// TODO Auto-generated method stub
		if (cntry == null || cntry.isEmpty() || cntry.equals("")
				|| insDate == null || insDate.isEmpty() || insDate.equals("")
				|| intHol == null || intHol.isEmpty() || intHol.equals(""))
			return -1;
		ContentValues cv = new ContentValues();
		cv.put(KEY_COUNTRY, cntry);
		cv.put(KEY_DATE, insDate);
		cv.put(KEY_INT_HOL, intHol);
		if (state != null && !state.isEmpty())
			cv.put(KEY_INT_STATE, state);
		if (comments != null && !comments.isEmpty())
			cv.put(KEY_INT_COMMENTS, comments);
		return myDB.insert(DATABASE_TABLE_INT, null, cv);
	}

	/**
	 * This method adds a log record in the database
	 * 
	 * @param date The date on which the log entry occured
	 * @param event The type of the log <br />
	 * 0 indicates User event <br />
	 * 1 indicates error event<br />
	 * 2 indicates info event <br />
	 * 
	 * @param log The detail of the log
	 * 
	 * @return returns the id of the newly created row.<br />
	 * Returns -1 if inserting the record is unsuccessful
	 */
	public long insertLog(String date, int event, String log) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (event > 2 || event < 0 || log.equals("") || log == null
				|| log.isEmpty())
			return -1;

		ContentValues cv = new ContentValues();
		cv.put(KEY_DATE, date);
		cv.put(KEY_EVENT_DETAIL, log);
		cv.put(KEY_EVENT_TYPE, event);
		return myDB.insert(DATABASE_TABLE_LOG, null, cv);
	}
	
	/**
	 * This method deletes the Tithi table and recreates it
	 * @return Returns a string indicating the outcome of the operation
	 */
	public String[] reCreateTithiTable() {
		// TODO Auto-generated method stub
		String str[] = new String[2];
		str[0] = "";
		str[1] = "";
		try {
			myDB.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TITHI);
			str[0] += "Deleting Tithi table completed";
		} catch (Exception e) {
			str[0] += "Error while deleting the Tithi table : " + e.toString();
		}

		try {
			myHelper.createTithiTable(myDB);
			str[1] += "Creating Tithi table completed";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			str[1] += "Error while creating the Tithi table : " + e.toString();
		}
		return str;
	}

	/**
	 * This method deletes the International holiday table and recreates it
	 * @return Returns a string indicating the outcome of the operation
	 */
	public String[] reCreateIntTable() {
		// TODO Auto-generated method stub
		String str[] = new String[2];
		str[0] = "";
		str[1] = "";
		try {
			myDB.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_INT);
			str[0] += "Deleting Int table completed";
		} catch (Exception e) {
			str[0] += "Error while deleting the Int table : " + e.toString();
		}
		try {
			myHelper.createIntTable(myDB);
			str[1] += "Creating Int table completed";
		} catch (Exception e) {
			str[1] += "Error while creating the Int table : " + e.toString();
		}
		return str;
	}

	/**
	 * This method deletes the Indian holiday table and recreates it
	 * @return Returns a string indicating the outcome of the operation
	 */
	public String[] reCreateIndTable() {
		// TODO Auto-generated method stub
		String str[] = new String[2];
		str[0] = "";
		str[1] = "";
		try {
			myDB.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_IND);
			str[0] += "Deleting Ind table completed";
		} catch (Exception e) {
			str[0] += "Error while deleting the Ind table : " + e.toString();
		}
		try {
			myHelper.createIndTable(myDB);
			str[1] += "Creating Ind table completed";
		} catch (Exception e) {
			str[1] += "Error while Creating the Ind table : " + e.toString();
		}
		return str;
	}

	/**
	 * This method deletes the custom dates table and recreates it
	 * @return Returns a string indicating the outcome of the operation
	 */
	
	public String[] reCreateUserTable() {
		// TODO Auto-generated method stub
		String str[] = new String[2];
		str[0] = "";
		str[1] = "";
		try {
			myDB.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_USER);
			str[0] += "Deleting User table completed";
		} catch (Exception e) {
			str[0] += "Error while creating the User table : " + e.toString();
		}
		try {
			myHelper.createUserTable(myDB);
			str[1] += "Creating User table completed";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			str[1] += "Error while deleting the User table : " + e.toString();
		}
		return str;
	}
	
	/**
	 * This method finds matching tithi info for the given date and returns a Tithi array with all the tithi info
	 *  
	 * @param dt The date for which the tithi info is required (this requires dd mmmm yyyy format in my implementation)
	 * 
	 * @return Returns a Tithi array containing all the tithi objects matching to the given date
	 * 
	 * @throws SQLException if there was an exception in handing the database operation 
	 */
	public Tithi[] getTithiDataForDate(String dt) throws SQLException {
		String[] columns = new String[] { KEY_TITHI_YEAR, KEY_TITHI_MONTH,
				KEY_TITHI_MOON, KEY_TITHI_DATE, KEY_TITHI_COMMENT };
		Cursor c = myDB.query(DATABASE_TABLE_TITHI, columns, KEY_DATE
				+ " LIKE '" + dt + "'", null, null, null, null);

		
		int iTithiYear = c.getColumnIndex(KEY_TITHI_YEAR);
		int iTithiMonth = c.getColumnIndex(KEY_TITHI_MONTH);
		int iTithiMoon = c.getColumnIndex(KEY_TITHI_MOON);
		int iTithiDate = c.getColumnIndex(KEY_TITHI_DATE);
		int iTithiCmnt = c.getColumnIndex(KEY_TITHI_COMMENT);

		
		if (c.getCount() == 0)
			return null;
		Tithi tithi[] = new Tithi[c.getCount()];
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tithi[c.getPosition()] = new Tithi(dt, c.getString(iTithiMonth), c.getString(iTithiMoon), c.getString(iTithiDate), c.getString(iTithiYear), c.getString(iTithiCmnt));
			

		}

		return tithi;
	}

	/**
	 * This method finds matching indian holiday info for the given date and returns a IndHoliday array with all the holiday info
	 *  
	 * @param dt The date for which the indian holiday info is required (this requires dd mmmm yyyy format in my implementation)
	 * 
	 * @return Returns a IndHoliday array containing all the IndHoliday objects matching to the given date
	 * 
	 * @throws SQLException if there was an exception in handing the database operation 
	 */
	public IndHoliday[] getIndDataForDate(String dt) throws SQLException {
		String[] columns = new String[] { KEY_IND, KEY_IND_STATE,
				KEY_IND_COMMENTS };
		Cursor c = myDB.query(DATABASE_TABLE_IND, columns, KEY_DATE + " LIKE '"
				+ dt + "'", null, null, null, null);

		int iInd = c.getColumnIndex(KEY_IND);
		int iState = c.getColumnIndex(KEY_IND_STATE);
		int iComment = c.getColumnIndex(KEY_IND_COMMENTS);

		if (c.getCount() == 0)
			return null;
		
		IndHoliday ind[] = new IndHoliday[c.getCount()];
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			ind[c.getPosition()] = new IndHoliday(dt, c.getString(iInd), c.getString(iState), c.getString(iComment));
		}
		
		return ind;
	}

	/**
	 * This method finds matching International holiday info for the given date and returns a IntHoliday array with all the holiday info
	 *  
	 * @param dt The date for which the international holiday info is required (this requires dd mmmm yyyy format in my implementation)
	 * 
	 * @return Returns a IntHoliday array containing all the IntHoliday objects matching to the given date
	 * 
	 * @throws SQLException if there was an exception in handing the database operation 
	 */
	public IntHoliday[] getIntDataForDate(String dt, String cntry) throws SQLException {
		String[] columns = new String[] { KEY_INT_HOL, KEY_INT_STATE,
				KEY_INT_COMMENTS };
		Cursor c = myDB.query(DATABASE_TABLE_INT, columns, KEY_DATE + " LIKE '"
				+ dt + "' AND " + KEY_COUNTRY + " LIKE '" + cntry + "'", null,
				null, null, null);

		int iAus = c.getColumnIndex(KEY_INT_HOL);
		int iState = c.getColumnIndex(KEY_INT_STATE);
		int iComment = c.getColumnIndex(KEY_INT_COMMENTS);

		if (c.getCount() == 0) return null;
			//return "No Australia holiday for this date!";
		
		IntHoliday hol[] = new IntHoliday[c.getCount()];
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			hol[c.getPosition()] = new IntHoliday(dt, c.getString(iAus), cntry, c.getString(iState), c.getString(iComment));
			
		}

		return hol;
	}

	/**
	 * This method allows the user of the class to run a custom query in the database
	 * 
	 * @param sqlQuery the string containing the SQL Query
	 * 
	 * @throws SQLException if the query encouners any issues while performing the SQL operation
	 */
	public void runSQL(String sqlQuery) throws SQLException {
		myDB.execSQL(sqlQuery);
	}

	/**
	 * This method checks if the Tithi table is empty
	 * @return True if the table is empty
	 * @return False if the table is not empty
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_DATE };
		Cursor c = myDB.query(DATABASE_TABLE_TITHI, columns, null, null, null,
				null, null);

		if (c.getCount() == 0) {

			return true;
		}
		return false;
	}

	/**
	 * This method returns a string containing all the log entries for the given log type 
	 * 
	 * @param evntType The type events for which the log needs to looked up<br />
	 * 0 indicates User event <br />
	 * 1 indicates error event<br />
	 * 2 indicates info event <br />
	 * 
	 * @return returns a String containing all the log entries seperated by new lines
	 */
	public String getLog(int evntType) {

		String[] columns = new String[] { KEY_DATE, KEY_EVENT_TYPE,
				KEY_EVENT_DETAIL };
		Cursor c = myDB.query(DATABASE_TABLE_LOG, columns, KEY_EVENT_TYPE + "="
				+ String.valueOf(evntType), null, null, null, null);

		String results = "";
		int iDate = c.getColumnIndex(KEY_DATE);
		int iEvTyp = c.getColumnIndex(KEY_EVENT_TYPE);
		int iDet = c.getColumnIndex(KEY_EVENT_DETAIL);

		if (c.getCount() == 0)
			return "No Log\n";
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			results = results + c.getString(iDate) + "," + c.getString(iEvTyp)
					+ "," + c.getString(iDet) + "\n";
		}
		return results;
	}

	/**
	 * This method returns all the log entries in a string format
	 * 
	 * @return returns a String containing all the log entries in the database seperated by new lines
	 */
	public String getLog() {
		String[] columns = new String[] { KEY_DATE, KEY_EVENT_TYPE,
				KEY_EVENT_DETAIL };
		Cursor c = myDB.query(DATABASE_TABLE_LOG, columns, null, null, null,
				null, null);

		String results = "";
		int iDate = c.getColumnIndex(KEY_DATE);
		int iEvTyp = c.getColumnIndex(KEY_EVENT_TYPE);
		int iDet = c.getColumnIndex(KEY_EVENT_DETAIL);

		if (c.getCount() == 0)
			return "No Log\n";
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			results = results + c.getString(iDate) + "," + c.getString(iEvTyp)
					+ "," + c.getString(iDet) + "\n";
		}
		return results;
	}
}
