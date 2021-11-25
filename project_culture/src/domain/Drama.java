package domain;

public class Drama {
	private final Date startDate, endDate;
	private final Name titleName;

	public Drama(int startYear, int startMonth, int startDay,
				int endYear, int endMonth, int endDay, String titleName) {
		if (startYear > endYear || (startYear == endYear && (startMonth > endMonth || (startMonth == endMonth && startDay > endDay)))) {
			throw new IllegalArgumentException("The starting date is greater than the ending date") ;
		}
		this.startDate = new Date(startYear, startMonth, startDay) ;
		this.endDate = new Date(endYear, endMonth, endDay) ;
		this.titleName = new Name(titleName) ;
	}
	
	public Date getStartDate() {
		return startDate ; // we can return it directly because this is well encapsulated
	}
	public Date getEndDate() {
		return endDate ; // we can return it directly because this is well encapsulated
	}
	public Name getArtistName() {
		return titleName ;
	}
}
