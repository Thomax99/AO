package domain;

import exceptions.NegativePlaceQuantityException;

/**
 * Event class ; represents an event as a value object ; it means something which need a number of places
 * @author thomas
 *
 */
public abstract class Event implements Comparable<Event> {
	private final int placeNumber ;
	private static int globalRef = 0;
	private final int ref ;
	
	/**
	 * Constructor used by repo
	 */
	public Event(int placeNumber, int ref) throws NegativePlaceQuantityException {
		if (placeNumber <= 0) {
			throw new NegativePlaceQuantityException() ;
		}
		this.placeNumber = placeNumber ;
		this.ref = ref ;
		if (ref >= globalRef) {
			globalRef = ref ;
			globalRef ++ ;
		}
	}
	public Event(int placeNumber) throws NegativePlaceQuantityException {
		if (placeNumber <= 0) {
			throw new NegativePlaceQuantityException() ;
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
	@Override
	public int compareTo(Event o) {
		return getRef() - o.getRef() ;
	}

}
