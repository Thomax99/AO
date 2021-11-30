package domain;

/**
 * This value object represents a date of opening
 * It means a day (monday, wednesday, ...) and an hour (> 0 and < 24)
 * @author thomas
 *
 */
public class OpenDate implements Comparable<OpenDate> {
		
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
		return d.getOpenDay().compareTo(getOpenDay()) ;
	}

}
