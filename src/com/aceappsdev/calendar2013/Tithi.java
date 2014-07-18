package com.aceappsdev.calendar2013;

public class Tithi {

	private String engDate;
	private String tMonth;
	private String tMoon;
	private int tDate;
	private int tYear;
	private String tComment;
	
	public Tithi(String calDate, String tithiMonth, String tithiMoon, String tithiDate, String tithiYear, String tithiComment) {
		if(calDate!=null) this.engDate = calDate;
		else this.engDate = "";
		
		if(tithiMonth!=null) this.tMonth = tithiMonth;
		else this.tMonth = "";
		
		if(tithiMoon!=null) this.tMoon = tithiMoon;
		else this.tMoon = "";
		
		if(tithiDate!=null) this.tDate = Integer.parseInt(tithiDate);
		else this.tDate = 0;
		
		if(tithiYear!=null) this.tYear = Integer.parseInt(tithiYear);
		else this.tYear = 0;
		
		if(tithiComment!=null) this.tComment = tithiComment;
		this.tComment = "";
	}
	
	public Tithi(String calDate, String tithiMonth, String tithiMoon, int tithiDate, int tithiYear, String tithiComment) {
		if(calDate!=null) this.engDate = calDate;
		else this.engDate = "";
		
		if(tithiMonth!=null) this.tMonth = tithiMonth;
		else this.tMonth = "";
		
		if(tithiMoon!=null) this.tMoon = tithiMoon;
		else this.tMoon = "";
		
		if(tithiDate>0) this.tDate = tithiDate;
		else this.tDate = 0;
		
		if(tithiYear>0) this.tYear = tithiYear;
		else this.tYear = 0;
		
		if(tithiComment!=null) this.tComment = tithiComment;
		this.tComment = "";
	}
	
	public String getEngDate(){
		return this.engDate;
	}

	private String getMonth() {
		return this.tMonth;
	}

	private String getMoon() {
		return this.tMoon;
	}

	private int getTithiDate() {
		return this.tDate;
	}

	private int getTithiYear() {
		return this.tYear;
	}

	private String getTithiComment() {
		return this.tComment;
	}
	
	public String toString(){
		String result = "";
		
		result = this.getMonth() + " " + this.getMoon() + " ";
		switch (this.getTithiDate()) {
		case 1:
			result = result + "Ekam ";
			break;
		case 2:
			result = result + "Bij ";
			break;
		case 3:
			result = result + "Trij ";
			break;
		case 4:
			result = result + "Choth ";
			break;
		case 5:
			result = result + "Pancham ";
			break;
		case 6:
			result = result + "Chath ";
			break;
		case 7:
			result = result + "Satam ";
			break;
		case 8:
			result = result + "Aatham ";
			break;
		case 9:
			result = result + "Nom ";
			break;
		case 10:
			result = result + "Dasham ";
			break;
		case 11:
			result = result + "Agiyaras ";
			break;
		case 12:
			result = result + "Baras ";
			break;
		case 13:
			result = result + "Teras ";
			break;
		case 14:
			result = result + "Chaudas ";
			break;
		case 15:
			if ("Sud".equals(this.getMoon()))
				result = result + "Punam ";
			else
				result = result + "Amas ";
			break;
		}
		
		result = result + String.valueOf(this.getTithiYear());
		
		if(this.tComment!=null && !this.tComment.equals("")) result = result + "\n\t" + this.getTithiComment();
		
		return result;
	}

}
