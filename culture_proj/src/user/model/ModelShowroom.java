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
		for (OpenDate d : dates) {
			output.add(d.getOpenDay().getDay() +"/" + d.getOpenDay().getMonth()+"/"+d.getOpenDay().getYear() + " à " + d.getOpenHour()) ;
		}
		return output ;
	}
	public int getCorrespondingId() {
		return correspondingId;
	}
	
}
