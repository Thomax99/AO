package domain;

import java.util.Date;

import exceptions.ForbiddenDateException;
import exceptions.NegativePlaceQuantityException;

/**
 * The concert class is a value object which represents a concert
 * it is a couple of a date and an artist name
 * @author thomas
 *
 */
public class Concert extends Event {
	private final Date date;
	private final Name name;

	public Concert(int year, int month, int day, String artistName, int placeNumber) throws ForbiddenDateException, NegativePlaceQuantityException {
		super(placeNumber) ;
		this.date = DateUtilitaries.createDate(year, month, day) ;
		this.name = new Name(artistName) ;
	}
	
	public Date getDate() {
		return (Date) date.clone() ; // we can return it directly because this is well encapsulated
	}
	public Name getArtistName() {
		return name ;
	}

}
