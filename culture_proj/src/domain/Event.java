package domain;

/**
 * Event class ; represents an event as a value object ; it means something which need a number of places
 * @author thomas
 *
 */
public abstract class Event {
	private final int placeNumber ;
	private static int globalRef = 0;
	private final int ref ;
	public Event(int placeNumber) {
		if (placeNumber <= 0) {
			throw new IllegalArgumentException("place number has to be > 0") ;
		}
		ref = globalRef++ ;
		this.placeNumber = placeNumber ;
	}
	public int getPlaceNumber() {
		return placeNumber ;
	}
	public int getRef() {
		return ref;
	}

}
