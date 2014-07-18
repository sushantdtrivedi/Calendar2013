package com.aceappsdev.calendar2013;

/**
 * This class represents Indian Holidays
 * @author Sushant
 *
 */
public class IndHoliday {

	private String engDate;
	private String hday;
	private String stat;
	private String cmnt;
	
	/**
	 * The Constructor
	 * 
	 * @param dt String Date in dd MMMM YYYY format. 
	 * @param holiday The holiday on the date
	 * @param state The state in which the holiday is observed
	 * @param comment Any comments regarding the holiday
	 */
	public IndHoliday(String dt, String holiday, String state, String comment) {
		if(dt!=null) this.engDate = dt;
		else this.engDate = "";
		
		if(holiday!=null) this.hday = holiday;
		else this.hday = "";
		
		if(state!=null) this.stat = state;
		else this.stat = "";
		
		if(comment!=null) this.cmnt = comment;
		else this.cmnt = "";
	}
	
	/**
	 * The override of the toString method of Java.Object<br />
	 * This is a String representation of the holiday.<br />
	 * 
	 * It shows a String in <b>Holiday (specific states) \n\t\t Comment</b> format for the object 
	 */
	public String toString(){
		String result = "";
		
		result = this.getHoliday();
		
		if(this.stat!=null && this.stat!="") result = result + " (Only in " + this.getState();
		
		if(this.cmnt!=null && this.cmnt!="") result = result+ "\n\t\t" + this.getComment();
		
		return result;
	}
	
	/**
	 * The getDate() method to get the date for the holiday 
	 * @return a String object containing the date of the object
	 */
	public String getDate(){
		return this.engDate;
	}

	/**
	 * The getHoliday() method to get the date for the holiday 
	 * @return a String object containing the holiday of the object
	 */
	public String getHoliday() {
		return this.hday;
	}

	/**
	 * The getState() method to get the states in which the holiday is observed 
	 * @return a String object containing the states
	 */
	public String getState() {
		return this.stat;
	}

	/**
	 * The getComment() method to get the comments for the holiday 
	 * @return a String object containing the comments of the object
	 */
	public String getComment() {
		return this.cmnt;
	}

}
