package infra;

import java.util.LinkedList;
import java.util.List;

import domain.Event;

public class EventCatalog {
	private List<Event> events ;
	public EventCatalog(List<Event> events) {
		this.events = new LinkedList<>(events) ;
	}
	
	public List<Event> getEvents() {
		return new LinkedList<>(events) ;
	}
}
