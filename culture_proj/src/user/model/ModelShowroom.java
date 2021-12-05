package user.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import domain.OpenDate;
import javafx.scene.layout.Pane;

/**
 * A value object which represents a showroom for the MVC
 * @author thomas
 *
 */
public class ModelShowroom {
	private final int capacity ;
	private final int correspondingId ;
	private final List<ModelEvent> events ;
	private final List<OpenDate> dates ;
	public ModelShowroom(int capacity, List<ModelEvent> events, List<OpenDate> dates, int id) {
		this.capacity = capacity ;
		this.events = events ;
		this.correspondingId = id ;
		this.dates = dates ;
	}
	public int getCapacity() {
		return capacity ;
	}
	public List<String> getStringRepresentation() {
		List<String> output = new ArrayList<>() ;
		output.add("Évenements disponibles :") ;
		for (ModelEvent ev : events) {
			output.add(ev.toString()) ;
		}
		output.add("Dates disponibles : ") ;
		OpenDate startDate = null, endDate = null ;
		for (OpenDate d : dates) {
			if (startDate == null) {
				startDate = d ;
				endDate = d ;
			} else {
				if (endDate.next().equals(d)) {
					endDate = d ;
				} else {
					if (startDate.equals(endDate)) {
						output.add("le" +startDate.getOpenDay().getDay() +"/" + startDate.getOpenDay().getMonth()+"/"+startDate.getOpenDay().getYear() + " à " + startDate.getOpenHour()+" h") ;
					} else {
						output.add("Du " + startDate.getOpenDay().getDay() +"/" + startDate.getOpenDay().getMonth()+"/"+startDate.getOpenDay().getYear() +
								" à " + endDate.getOpenDay().getDay() +"/" + endDate.getOpenDay().getMonth()+"/"+endDate.getOpenDay().getYear() + " à " + startDate.getOpenHour() + " h") ;
					}
					startDate = endDate = d ;
				}
			}
		}
		return output ;
	}
	public int getCorrespondingId() {
		return correspondingId;
	}
	
}
