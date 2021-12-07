package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.CapacityNegativeException;
import exceptions.EqualsDatesException;
import exceptions.ForbiddenDateIntervalException;
import exceptions.NotEnoughPlaceException;
import exceptions.NotExistantEventException;
import exceptions.NotOpenedShowRoomException;

/**
 * This entity object represent a show room in which event could be programmed
 * @author thomas
 *
 */
public class ShowRoom {
	private static int globalId = 0 ;
	private final int id ;
	private int capacity ;
	private final Map<Event, List<OpenDate>> programmatedEvents ;
	private final Map<Date, OpenDate> opendates ;
	private final Map<Date, Boolean> isProgrammated ;
	private final Map<OpenDate, Integer> leavingCapacity ;
	public ShowRoom(List<OpenDate>openDates, int capacity)
			throws CapacityNegativeException, EqualsDatesException {
		this.id = globalId ++ ;
		if (capacity < 0) {
			throw new CapacityNegativeException() ;
		}
		programmatedEvents = new TreeMap<>() ;
		isProgrammated = new TreeMap<>() ;
		this.capacity = capacity ;
		leavingCapacity = new TreeMap<>() ;
		opendates = new TreeMap<>() ;
		// pour chaque element, on regarde tous les elements pour voir si il y a egalite entre les jours et si oui on throw un illegal argument
		if (openDates != null) {
			for(int i = 0 ; i < openDates.size() ; i++) {
				Date d1 = openDates.get(i).getOpenDay() ;
				for (int j = i+1 ; j < openDates.size() ; j++) {
					Date d2 = openDates.get(j).getOpenDay() ;
					if (d1.equals(d2)) {
						throw new EqualsDatesException() ;
					}
				}
				leavingCapacity.put(openDates.get(i), capacity) ;
				opendates.put(d1, openDates.get(i)) ;
				isProgrammated.put(d1, false) ;
			}
		}
	}
	public int getCapacity() {
		return capacity;
	}
	public List<OpenDate> getOpenDatesWithoutEvent() {
		List<OpenDate> output = new LinkedList<>() ;
		for (Date d : opendates.keySet()) {
			if (!isProgrammated.get(d)) {
				output.add(opendates.get(d)) ;
			}
		}
		return output ;
	}
	public List<OpenDate> getOpenDates() {
		return new LinkedList<OpenDate>(opendates.values());
	}
	/**
	 * function to know if a showroom is opened at a given date
	 * it means with or without a concert on it again
	 * @param d the date we would like to know the opening
	 * @return -1 if it is not open, the hour else
	 */
	public int getOpenHour(Date d) {
		if (opendates.get(d) != null) {
			return opendates.get(d).getOpenHour() ;
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
	public void addEvent(Event evt, List<Date> dates) throws NotOpenedShowRoomException, NotEnoughPlaceException {
		List<OpenDate> pDates = new LinkedList<>() ;		
		for (Date d : dates) {
			if (!isProgrammated.containsKey(d) || isProgrammated.get(d) == true) { 
				throw new NotOpenedShowRoomException(this.id, d) ;
			}
			OpenDate od = opendates.get(d) ;
			if (leavingCapacity.get(od) < evt.getPlaceNumber()) {
				throw new NotEnoughPlaceException(this.id, leavingCapacity.get(od), evt.getPlaceNumber()) ;
			}
			pDates.add(od) ;
			isProgrammated.put(d, true) ;
			leavingCapacity.put(od, evt.getPlaceNumber()) ;
		}
		programmatedEvents.put(evt, pDates) ;
	}
	public void addEvent(Drama dr) throws NotOpenedShowRoomException, NotEnoughPlaceException, ForbiddenDateIntervalException {
		addEvent(dr, DateUtilitaries.getDateInInterval(dr.getStartDate(), dr.getEndDate())) ;
	}
	public void addEvent(Concert c) throws NotOpenedShowRoomException, NotEnoughPlaceException {
		Date d = (Date) c.getDate().clone() ;
		List<Date> dates = new ArrayList<>() ;
		dates.add(d) ;
		addEvent(c, dates) ;
	}
	public List<Event> getEvents() {
		List<Event> backList = new LinkedList<>(programmatedEvents.keySet()) ;
		return backList ;
	}
	public List<OpenDate> getDatesByEvent(Event evt) {
		return programmatedEvents.get(evt) ;
	}
	public Event getProgrammationOfDate(Date date) {
		if (getOpenHour(date) == -1 || !isProgrammated.get(date))
				return null;
		// sinon on cherche dans les events si il y en a un qui est programme a cette date
		for(Event ev : programmatedEvents.keySet()) {
			for (OpenDate d : programmatedEvents.get(ev)) {
				if (d.getOpenDay().equals(date)) {
					return ev ;
				}
			}
		}
		return null ; // pas sense arriver
	}
	public Event removeEvent(int eventRef) throws NotExistantEventException {
		Event backEvent = null ;
		for(Event ev : programmatedEvents.keySet()) {
			if (ev.getRef() == eventRef) {
				backEvent = ev ;
				break ;
			}
		}
		if (backEvent == null) {
			throw new NotExistantEventException(eventRef) ;
		}
		for (OpenDate prog : programmatedEvents.get(backEvent)) {
			isProgrammated.put(prog.getOpenDay(), false) ;
			leavingCapacity.put(prog, capacity) ;
			opendates.put(prog.getOpenDay(), prog) ;
		}
		programmatedEvents.remove(backEvent) ;
		return backEvent;
	}
}
