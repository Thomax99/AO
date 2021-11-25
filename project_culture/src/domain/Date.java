package domain;

/**
 * Value object which represent a date 
 * A date contains just year, month, day, not hour
 * @author thomas
 *
 */
public class Date {
	private final int year ;
	private final int month ;
	private final int day ;
	private static final int dayNumberByMonth[] = {31, -1, 31, 30, 31,30,31,31,30,31,30,31} ;
	
	public Date(int year, int month, int day) {
		this.year = year ;
		this.day = day ;
		this.month = month ;
		if (year < 0) {
			throw new IllegalArgumentException("The year has to be a positive integer") ;
		}
		if (month <= 0) {
			throw new IllegalArgumentException("The month has to be a strictly positive integer") ;
		}
		if (day <= 0) {
			throw new IllegalArgumentException("The day has to be a strictly positive integer") ;
		}
		if (month > 12) {
			throw new IllegalArgumentException("The month has to be a lower or equals to 12") ;
		}
		if (month == 2) {
			// February month : we have to check if the year is leap
			boolean isLeap = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0) ;
			if (isLeap && day > 29) {
				throw new IllegalArgumentException("The day has to be a lower or equals to 29") ;
			} else if (!isLeap && day > 28) {
				throw new IllegalArgumentException("The day has to be a lower or equals to 28") ;
			}
		} else if (day > dayNumberByMonth[month-1]) {
			throw new IllegalArgumentException("The day has to be a lower or equals to " + dayNumberByMonth[month-1]) ;
		}
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day ;
	}
	public String toString() {
		return year +"/"+month+"/"+day ;
	}

}
