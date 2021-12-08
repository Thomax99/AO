package domain;

import java.util.Date;

import exceptions.ForbiddenDateException;
import exceptions.ForbiddenDateIntervalException;
import exceptions.NegativePlaceQuantityException;

public class Drama extends Event {
	private final Date startDate, endDate;
	private final Name titleName;
	
	/**
	 * For repository
	 */
	public Drama(int startYear, int startMonth, int startDay,
			int endYear, int endMonth, int endDay, String titleName, int placeNumber, int ref)
					throws NegativePlaceQuantityException, ForbiddenDateIntervalException, ForbiddenDateException {
		super(placeNumber, ref) ;
		this.startDate = DateUtilitaries.createDate(startYear, startMonth, startDay) ;
		this.endDate = DateUtilitaries.createDate(endYear, endMonth, endDay) ;
		if (this.startDate.after(this.endDate)) {
			throw new ForbiddenDateIntervalException(this.startDate, this.endDate) ;
		}
		this.titleName = new Name(titleName) ;
	}
 

	public Drama(int startYear, int startMonth, int startDay,
				int endYear, int endMonth, int endDay, String titleName, int placeNumber) throws NegativePlaceQuantityException, ForbiddenDateIntervalException, ForbiddenDateException {
		super(placeNumber) ;
		this.startDate = DateUtilitaries.createDate(startYear, startMonth, startDay) ;
		this.endDate = DateUtilitaries.createDate(endYear, endMonth, endDay) ;
		if (this.startDate.after(this.endDate)) {
			throw new ForbiddenDateIntervalException(this.startDate, this.endDate) ;
		}
		this.titleName = new Name(titleName) ;
	}
	
	public Date getStartDate() {
		return (Date) startDate.clone() ; // we can return it directly because this is well encapsulated
	}
	public Date getEndDate() {
		return (Date) endDate.clone() ; // we can return it directly because this is well encapsulated
	}
	public Name getTitleName() {
		return titleName ;
	}

}
