package domain;

/**
 * The concert class is a value object which represents a concert
 * it is a couple of a date and an artist name
 * @author thomas
 *
 */
public class Concert {
	private final Date date;
	private final Name name;

	public Concert(int year, int month, int day, String artistName) {
		this.date = new Date(year, month, day) ;
		this.name = new Name(artistName) ;
	}
	
	public Date getDate() {
		return date ; // we can return it directly because this is well encapsulated
	}
	public Name getArtistName() {
		return name ;
	}

}
