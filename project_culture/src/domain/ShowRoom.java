package domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This entity object represent a show room in which event could be programmed
 * @author thomas
 *
 */
public class ShowRoom {
	private static int globalId ;
	private final int id ;
	private int capacity ;
	private final List<Event> eventList ;
	private final List<OpenDate> openDays ;
	public ShowRoom(List<OpenDate>openDates, int capacity) {
		this.id = globalId ++ ;
		if (capacity < 0) {
			throw new IllegalArgumentException("capacity cannot be < 0") ;
		}
		eventList = new LinkedList<>() ;
		this.capacity = capacity ;
		openDays = new LinkedList<>() ;
		// pour chaque element, on regarde tous les elements pour voir si il y a egalite entre les jours et si oui on throw un illegal argument
		for(int i = 0 ; i < openDates.size() ; i++) {
			Date d1 = openDates.get(i).getOpenDay() ;
			for (int j = i+1 ; j < openDates.size() ; j++) {
				Date d2 = openDates.get(j).getOpenDay() ;
				if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDay() == d2.getDay()) {
					throw new IllegalArgumentException("Two openDates equals on the ShowRoom ...") ;
				}
			}
			openDays.add(openDates.get(i)) ;
		}
	}
	// TODO : ajouter un event ; savoir si c'est ouvert, checker la capacite, ...
}
