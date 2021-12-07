package domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A city is an aggregate of ShowRoom ; whith also an event catalog attached
 * @author thomas
 *
 */
public class City {
	private Map<Integer, ShowRoom> showrooms ;
	private EventCatalog events ;
	private final int id ;
	private static int globalId = 0 ;
	public City(EventCatalog events) {
		this.id = globalId++ ;
		this.events = events ;
		showrooms = new TreeMap<>() ;
	}
	public int getId() {
		return id ;
	}
	public void addShowRoom(ShowRoom showroom) {
		showrooms.put(showroom.getId(), showroom) ;
	}
	public ShowRoom getShowroom(int id) {
		return showrooms.get(id) ;
	}
	public List<ShowRoom> getShowrooms() {
		return new LinkedList<ShowRoom>(showrooms.values()) ;
	}
	public List<Concert> getConcertsInCatalog() {
		return events.getConcerts() ;
	}
	public List<Drama> getDramasInCatalog() {
		return events.getDramas() ;
	}
	public List<Event> getEventsInCatalog() {
		List<Event> events = new LinkedList<>(getDramasInCatalog()) ;
		events.addAll(getConcertsInCatalog()) ;
		return events ;
	}
	public void addConcertInCatalog(Concert evt) {
		events.addConcert(evt);
	}
	public void addDramaInCatalog(Drama evt) {
		events.addDrama(evt);
	}
	public void removeConcertInCatalog(Concert conc) {
		events.removeConcert(conc);
	}
	public void removeDramaInCatalog(Drama d) {
		events.removeDrama(d);
	}

}
