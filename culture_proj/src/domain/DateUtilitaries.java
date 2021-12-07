package domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import exceptions.ForbiddenDateException;

public class DateUtilitaries {
	private static final int [] dayByNumberInMonth = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31} ;
	private static GregorianCalendar calendar = new GregorianCalendar() ;
	public synchronized static Date createDate(int year, int month, int day) throws ForbiddenDateException {
		if (month <= 0) {
			throw new ForbiddenDateException(year, month, day) ;
		}
		if (month > 12) {
			throw new ForbiddenDateException(year, month, day) ;
		}
		if (day <= 0) {
			throw new ForbiddenDateException(year, month, day) ;
		}
		if (day > dayByNumberInMonth[month-1]) {
			if (month == 2) {
				if (calendar.isLeapYear(year)) {
					if (day > 29)
						throw new ForbiddenDateException(year, month, day) ;
				} else if (day > 28) {
					throw new ForbiddenDateException(year, month, day) ;
				}
			} else {
				throw new ForbiddenDateException(year, month, day) ;
			}
		}
		
		calendar.set(year, month-1, day);
		return calendar.getTime() ;
	}
	public synchronized static Date getNextDate(Date date) {
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime() ;
	}
	public synchronized static int getDay(Date d) {
		calendar.setTime(d);
		return calendar.get(Calendar.DAY_OF_MONTH) ;
	}
	public synchronized static int getMonth(Date d) {
		calendar.setTime(d);
		return calendar.get(Calendar.MONTH)+1 ; // on ajoute 1 car le mois 1 = février
	}
	public synchronized static int getYear(Date d) {
		calendar.setTime(d);
		return calendar.get(Calendar.YEAR)  ;
	}
	/**
	 * Return a List of date from startDate to endDate, included
	 */
	public synchronized static List<Date> getDateInInterval(Date startDate, Date endDate) {
		if (startDate.compareTo(endDate) > 0) {
			throw new RuntimeException("La date de début est plus grande que la date de fin") ;
		}
		List<Date> outputList = new LinkedList<>() ;
		Date curDate = (Date) startDate.clone() ;
		while (curDate.compareTo(endDate)<= 0) {
			outputList.add((Date) curDate.clone()) ;
			calendar.setTime(curDate);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			curDate = calendar.getTime() ;
		}
		return outputList ;
		
	}
	public static Date getFirstDayOfWeek(Date date) {
		Date outputDate = (Date) date.clone() ;
		calendar.setTime(outputDate);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			outputDate = calendar.getTime() ;
		}
		return outputDate;
	}
	public static Date getLastDayOfWeek(Date date) {
		Date outputDate = (Date) date.clone() ;
		calendar.setTime(outputDate);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			outputDate = calendar.getTime() ;
		}
		return outputDate;
	}
	public static boolean isOnWeekEnd(Date startDate) {
		calendar.setTime(startDate);
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}
}
