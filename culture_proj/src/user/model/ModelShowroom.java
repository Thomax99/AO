package user.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
	public ModelShowroom(int capacity, List<ModelEvent> events, int id) {
		this.capacity = capacity ;
		this.events = events ;
		this.correspondingId = id ;
	}
	public int getCapacity() {
		return capacity ;
	}
	public List<String> getStringRepresentation() {
		List<String> output = new ArrayList<>() ;
		for (ModelEvent ev : events) {
			output.add(ev.getName() + " le " + ev.getStartDay() +"/"+ev.getStartMonth()+"/"+ev.getEndYear()) ;
		}
		return output ;
	}
	public int getCorrespondingId() {
		return correspondingId;
	}
	
}
