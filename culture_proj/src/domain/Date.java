package domain;

/**
 * Value object which represent a date 
 * A date contains just year, month, day, not hour
 * @author thomas
 *
 */
public class Date implements Comparable {
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
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass()) {
			return false ;
		}
		Date d = (Date) o ;
		return d.getDay() == getDay() && d.getMonth() == getMonth() && d.getYear() == getYear() ;
	}
	@Override
	public int compareTo(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return 0 ;
		}
		Date d = (Date) o ;
		if (getYear() < d.getYear()) {
			return -1 ;
		} else if (getYear() > d.getYear()) {
			return 1 ;
		} else {
			if (getMonth() < d.getMonth()) {
				return -1 ;
			} else if (getMonth() > d.getMonth()) {
				return 1 ;
			} else {
				if (getDay() < d.getDay()) {
					return -1 ;
				} else if (getDay() > d.getDay()) {
					return 1 ;
				}
				return 0 ;
			}
		}
		
	}

}
