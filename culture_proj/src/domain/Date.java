package domain;

import java.util.LinkedList;
import java.util.List;

/**
 * Value object which represent a date 
 * A date contains just year, month, day, not hour
 * @author thomas
 *
 */
public class Date implements Comparable<Date> {
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
	public int compareTo(Date d) {
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
	/**
	 * Return the interval of dates between the current date and the given date
	 * @param toDate the end of the interval, included
	 * @return the corresponding list of date
	 * @see the given date has to be >= to the end date
	 */
	public List<Date> getDatesInInterval(Date toDate) {
		List<Date> dates = new LinkedList<>() ;
		if (compareTo(toDate) < 0) {
			throw new RuntimeException("Error : the given date is smaller than the date") ;
		}
		int currentYear = getYear(), currentMonth = getMonth(), currentDay = getDay() ;
		while (currentYear <= toDate.getYear() && currentMonth <= toDate.getMonth() && currentDay <= toDate.getMonth()) {
			dates.add(new Date(currentYear, currentMonth, currentDay)) ;
			currentDay ++ ;
			if (currentDay > dayNumberByMonth[currentMonth]) {
				if (currentMonth == 2) {
					//fevrier : carefullish
					boolean isLeap = currentYear % 4 == 0 && (currentYear % 100 != 0 || currentYear % 400 == 0) ;
					if (isLeap && currentDay > 29 || currentDay > 28) {
						currentMonth ++ ;
						currentDay = 1 ;
					}
				} else {
					currentMonth ++ ;
					currentDay = 1 ;
				}
			}
			if (currentMonth > 12) {
				currentMonth = 1 ;
				currentYear ++ ;
			}
		}
		return dates ;
	}

}
