package domain;

import java.util.LinkedList;
import java.util.List;

/**
 * A city is an aggregate of ShowRoom 
 * @author thomas
 *
 */
public class City {
	private List<ShowRoom> showrooms ;
	private final int id ;
	private static int globalId = 0 ;
	public City() {
		this.id = globalId++ ;
		showrooms = new LinkedList<>() ;
	}
	public int getId() {
		return id ;
	}
	public void addShowRoom(ShowRoom showroom) {
		showrooms.add(showroom) ;
	}

}
