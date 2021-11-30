package application;

import java.util.List;
import java.util.Observable;

import domain.Event;
import domain.Repository;
import domain.ShowRoom;
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
	public List<ShowRoom> getShowRooms(int cityId) {
		if (repo.findCityById(cityId) == null) {
			throw new RuntimeException("id not contained") ;
		}
		return repo.findCityById(cityId).getShowrooms() ;
	}
}
