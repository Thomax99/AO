package application;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import domain.City;
import domain.Concert;
import domain.Drama;
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
	public List<Concert> getConcerts() {
		return events.getConcerts() ;
	}
	public List<Drama> getDramas() {
		return events.getDramas() ;
	}
	public List<Event> getEvents() {
		List<Event> events = new LinkedList<>(getDramas()) ;
		events.addAll(getConcerts()) ;
		return events ;
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
		Concert conc = null ;
		for (Concert ev : events.getConcerts()) {
			if (ev.getRef() == eventRef) {
				conc = ev ;
				break ;
			}
		}
		Drama d = null ;
		for (Drama ev : events.getDramas()) {
			if (ev.getRef() == eventRef) {
				d = ev ;
				break ;
			}
		}
		if (conc == null && d == null) {
			throw new RuntimeException("evt doesn't exists") ;
		}
		City city = repo.findCityById(cityId) ;
		boolean contain = false ;
		for (ShowRoom showroom : city.getShowrooms()) {
			if (showroom.getId() == showRoomId) {
				if (conc != null)
					showroom.addEvent(conc);
				else
					showroom.addEvent(d);
				contain = true ;
			}
		}
		if (!contain) {
			throw new RuntimeException("showroom doesn't exist") ;
		}
        setChanged();
		this.notifyObservers();
	}
}
