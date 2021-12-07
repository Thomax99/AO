package domain;

import java.util.Date;

import exceptions.ForbiddenDateException;
import exceptions.ForbiddenHourException;

/**
 * This value object represents a date of opening
 * It means a day (monday, wednesday, ...) and an hour (> 0 and < 24)
 * @author thomas
 *
 */
public class OpenDate implements Comparable<OpenDate> {
		
	private final Date openDay ;
	private final int openHour ;
	public OpenDate(int year, int month, int day, int openHour) throws ForbiddenHourException, ForbiddenDateException {
		this.openDay = DateUtilitaries.createDate(year, month, day) ;
		if (openHour >= 24) {
			throw new ForbiddenHourException(openHour) ;
		} else if (openHour < 0) {
			throw new ForbiddenHourException(openHour) ;
		}
		this.openHour = openHour ;
	}
	public Date getOpenDay() {
		return openDay ;
	}
	public int getOpenHour() {
		return openHour ;
	}
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false ;
		}
		OpenDate d = (OpenDate) o ;
		return d.getOpenDay().equals(getOpenDay()) && d.getOpenHour() == getOpenHour() ;
	}

	@Override
	public int compareTo(OpenDate d) {
		if (d.getOpenDay().equals(getOpenDay())) {
			if (getOpenHour() < d.getOpenHour()) {
				return -1 ;
			} else if (getOpenHour() > d.getOpenHour()) {
				return 1 ;
			}
			return 0 ;
		}
		return getOpenDay().compareTo(d.getOpenDay()) ;
	}
	public OpenDate next() throws ForbiddenHourException, ForbiddenDateException {
		Date next = DateUtilitaries.getNextDate(getOpenDay()) ;
		return new OpenDate(DateUtilitaries.getYear(next), DateUtilitaries.getMonth(next), DateUtilitaries.getDay(next), getOpenHour()) ;
	}

}
