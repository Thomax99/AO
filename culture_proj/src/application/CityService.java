package application;

import java.util.List;
import java.util.Observable;

import domain.Event;
import domain.Repository;
import infra.EventCatalog;

public class CityService extends Observable {
	private Repository repo ;
	private EventCatalog events ;
	public CityService(Repository repo, EventCatalog events) {
		this.repo = repo ;
		this.events = events ;
	}
	public List<Event> getEvents() {
		return events.getEvents() ;
	}
}
