package domain;

import java.util.Map;
import java.util.TreeMap;

/**
 * A city is an aggregate of ShowRoom 
 * @author thomas
 *
 */
public class City {
	private Map<Integer, ShowRoom> showrooms ;
	private final int id ;
	private static int globalId = 0 ;
	public City() {
		this.id = globalId++ ;
		showrooms = new TreeMap<>() ;
	}
	public int getId() {
		return id ;
	}
	public void addShowRoom(ShowRoom showroom) {
		showrooms.put(showroom.getId(), showroom) ;
	}

}
