package domain;

import java.util.ArrayList;
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
	private final Map<Date, OpenDate> opendates ;
	private final Map<OpenDate, Integer> leavingCapacity ;
	public ShowRoom(List<OpenDate>openDates, int capacity) {
		this.id = globalId ++ ;
		if (capacity < 0) {
			throw new IllegalArgumentException("capacity cannot be < 0") ;
		}
		events = new TreeMap<>() ;
		this.capacity = capacity ;
		leavingCapacity = new TreeMap<>() ;
		opendates = new TreeMap<>() ;
		// pour chaque element, on regarde tous les elements pour voir si il y a egalite entre les jours et si oui on throw un illegal argument
		for(int i = 0 ; i < openDates.size() ; i++) {
			Date d1 = openDates.get(i).getOpenDay() ;
			for (int j = i+1 ; j < openDates.size() ; j++) {
				Date d2 = openDates.get(j).getOpenDay() ;
				if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDay() == d2.getDay()) {
					throw new IllegalArgumentException("Two openDates equals on the ShowRoom ...") ;
				}
			}
			System.err.println("Le " +openDates.get(i).getOpenDay().getDay()+"/"+openDates.get(i).getOpenDay().getMonth()+"/"+openDates.get(i).getOpenDay().getYear()+" a " + openDates.get(i).getOpenHour()) ;

			events.put(openDates.get(i), null) ;
			leavingCapacity.put(openDates.get(i), capacity) ;
			opendates.put(d1, openDates.get(i)) ;
		}
		System.err.println("\n\n") ;
	}
	public int getCapacity() {
		return capacity;
	}
	public List<OpenDate> getOpenDates() {
		return new LinkedList<OpenDate>(opendates.values());
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
	public int getId() {
		return this.id ;
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
	public void addEvent(Event evt, List<Date> dates) {
		for (Date d : dates) {
			if (!opendates.containsKey(d)) { 
				throw new RuntimeException("Error : the showroom is not opened at this date") ;
			}
			OpenDate od = opendates.get(d) ;
			if (events.get(od) != null) {
				throw new RuntimeException("Error : there is already an event at this date") ;
			}
			if (leavingCapacity.get(od) < evt.getPlaceNumber()) {
				throw new RuntimeException("Error : impossible to put this event : not enough place") ;
			}
			events.put(od, evt) ;
			opendates.remove(d) ;
			leavingCapacity.put(od, evt.getPlaceNumber()) ;
		}
	}
	public void addEvent(Drama dr) {
		addEvent(dr, dr.getStartDate().getDatesInInterval(dr.getEndDate())) ;
	}
	public void addEvent(Concert c) {
		Date d =  new Date(c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay()) ;
		List<Date> dates = new ArrayList<>() ;
		dates.add(d) ;
		addEvent(c, dates) ;
	}
	public List<Event> getEvents() {
		List<Event> backList = new LinkedList<>() ;
		for(OpenDate k : events.keySet()) {
			if (events.get(k) != null) {
				backList.add(events.get(k)) ;
			}
		}
		return backList ;
	}
	public Event getEvenByDate(OpenDate d) {
		if (!events.containsKey(d)) {
			throw new RuntimeException("Error : the showroom is not opened") ;
		}
		return events.get(d) ;
	}
}
