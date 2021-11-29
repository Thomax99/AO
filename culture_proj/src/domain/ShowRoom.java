package domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This entity object represent a show room in which event could be programmed
 * @author thomas
 *
 */
public class ShowRoom {
	private static int globalId = 0 ;
	private final int id ;
	private int capacity ;
	private final Map<OpenDate, Event> events ;
	private final Map<OpenDate, Integer> leavingCapacity ;
	public ShowRoom(List<OpenDate>openDates, int capacity) {
		this.id = globalId ++ ;
		if (capacity < 0) {
			throw new IllegalArgumentException("capacity cannot be < 0") ;
		}
		events = new TreeMap<>() ;
		this.capacity = capacity ;
		leavingCapacity = new TreeMap<>() ;
		// pour chaque element, on regarde tous les elements pour voir si il y a egalite entre les jours et si oui on throw un illegal argument
		for(int i = 0 ; i < openDates.size() ; i++) {
			Date d1 = openDates.get(i).getOpenDay() ;
			for (int j = i+1 ; j < openDates.size() ; j++) {
				Date d2 = openDates.get(j).getOpenDay() ;
				if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDay() == d2.getDay()) {
					throw new IllegalArgumentException("Two openDates equals on the ShowRoom ...") ;
				}
			}
			events.put(openDates.get(i), null) ;
			leavingCapacity.put(openDates.get(i), capacity) ;
		}
	}
	// TODO : ajouter un event ; savoir si c'est ouvert, checker la capacite, ...
	public int getCapacity() {
		return capacity;
	}
	public List<OpenDate> getOpenDates() {
		return new LinkedList<OpenDate>(events.keySet());
	}
	/**
	 * function to know if a showroom is opened at a given date
	 * @param d the date we would like to know the opening
	 * @return -1 if it is not open, the hour else
	 */
	public int isOpened(Date d) {
		for (OpenDate od : events.keySet()) {
			if (od.getOpenDay().equals(d)) {
				return od.getOpenHour() ;
			}
		}
		return -1 ;
	}
	public int getLeavingPlaces(OpenDate d) {
		return leavingCapacity.containsKey(d) ? leavingCapacity.get(d) : 0 ;
	}
	public boolean getPlace(OpenDate d) {
		int leavingplace = getLeavingPlaces(d)  ;
		if (leavingplace > 0) {
			leavingCapacity.replace(d, leavingplace-1) ;
			return true ;
		}
		return false ;
	}
	public void addEvent(Event evt, OpenDate d) {
		if (!events.containsKey(d) || events.get(d) != null) {
			throw new RuntimeException("Error : there is already an event at this date or the showroom is not opened at this date") ;
		}
		events.put(d, evt) ;
	}
	public List<Event> getEvents() {
		List<Event> backList = new LinkedList<>() ;
		events.keySet().forEach(k -> {
			if (k != null) {
				backList.add(events.get(k)) ;
			}
		});
		return backList ;
	}
	public Event getEvenByDate(OpenDate d) {
		if (!events.containsKey(d)) {
			throw new RuntimeException("Error : the showroom is not opened") ;
		}
		return events.get(d) ;
	}
}
