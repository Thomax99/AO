package application;

import java.util.List;
import java.util.Observable;

import domain.City;
import domain.Event;
import domain.OpenDate;
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
	public void addEvent(int cityId, int showRoomId, int eventRef) {
		if (repo.findCityById(cityId) == null) {
			throw new RuntimeException("id not contained") ;
		}
		Event evt = null ;
		for (Event ev : events.getEvents()) {
			if (ev.getRef() == eventRef) {
				evt = ev ;
				break ;
			}
		}
		if (evt == null) {
			throw new RuntimeException("evt doesn't exists") ;
		}
		City city = repo.findCityById(cityId) ;
		boolean contain = false ;
		for (ShowRoom showroom : city.getShowrooms()) {
			if (showroom.getId() == showRoomId) {
				showroom.addEvent(evt);
				contain = true ;
			}
		}
		if (!contain) {
			throw new RuntimeException("showroom doesn't exist") ;
		}
		System.err.println("here") ;
        setChanged();

		this.notifyObservers();
	}
}
