package com.aceappsdev.calendar2013;

public class IntHoliday {

	private String engDate;
	private String hday;
	private String cntry;
	private String stat;
	private String cmnt;
	
	/**
	 * The Constructor
	 * 
	 * @param dt String Date in dd MMMM YYYY format. 
	 * @param holiday The holiday on the date
	 * @param country The country in which the holiday is observed
	 * @param state The state in which the holiday is observed
	 * @param comment Any comments regarding the holiday
	 */
	public IntHoliday(String dt, String holiday, String country, String state, String comment) {
			if(dt!=null) this.engDate = dt;
			else this.engDate = "";
			
			if(holiday!=null) this.hday = holiday;
			else this.hday = "";
			
			if(country!=null) this.cntry = country;
			else this.cntry = "";
			
			if(state!=null) this.stat = state;
			else this.stat = "National";
			
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
		
		if (this.stat.equals("National")) result = result + " (National)";
		else result = result + " (Only in " + this.stat + ")";

		if (this.cmnt != null) result = result + "\n\t\t" + this.cmnt;
		
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
	 * The getCountry() method to get the country in which the holiday is observed 
	 * @return a String object containing the country name
	 */
	public String getCountry(){
		return this.cntry;
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