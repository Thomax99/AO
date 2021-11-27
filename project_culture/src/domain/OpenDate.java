package domain;

/**
 * This value object represents a date of opening
 * It means a day (monday, wednesday, ...) and an hour (> 0 and < 24)
 * @author thomas
 *
 */
public class OpenDate {
		
	private final Date openDay ;
	private final int openHour ;
	public OpenDate(int year, int month, int day, int openHour) {
		this.openDay = new Date(year, month, day) ;
		if (openHour >= 24) {
			throw new IllegalArgumentException("Hour can't be > or equals to 24") ;
		} else if (openHour < 0) {
			throw new IllegalArgumentException("Hour can't be < 0") ;
		}
		this.openHour = openHour ;
	}
	public Date getOpenDay() {
		return openDay ;
	}
	public int getOpenHour() {
		return openHour ;
	}
}
