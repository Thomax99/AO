package user.model;

import java.util.LinkedList;
import java.util.List;

import domain.Concert;
import domain.Drama;
import domain.Event;
import domain.ShowRoom;

public class Model {
	private List<ModelEvent> events ;
	private List<ModelShowroom> showrooms ;
	public Model () {
		this.events = new LinkedList<>() ;
		this.showrooms = new LinkedList<>() ;
	}
	
	public void update(List<Event> realEvents, List<ShowRoom> realShowrooms) {
		showrooms.clear();
		events.clear() ;
		for (ShowRoom s : realShowrooms) {
			List<ModelEvent> eventsOfShowroom = new LinkedList<>() ;
			for (Event ev : s.getEvents()) {
				ModelEvent mod = null ;
				if (ev instanceof Drama) { // DEGUEULASSE : il faut faire des ModelEvents generiques je pense
					Drama d = (Drama) ev ;
					mod = new ModelDrama(d.getStartDate().getYear(), d.getStartDate().getMonth(), d.getStartDate().getDay(),
							d.getEndDate().getYear(), d.getEndDate().getMonth(), d.getEndDate().getDay(),d.getPlaceNumber(),  d.getTitleName().getName(), d.getRef()) ;
					
				
				} else if (ev instanceof Concert) {
					Concert c = (Concert) ev ;
					mod = new ModelConcert(c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay(), c.getPlaceNumber(),  c.getArtistName().getName(), c.getRef()) ;
				}
				eventsOfShowroom.add(mod) ;
			}
			ModelShowroom mod = new ModelShowroom(s.getCapacity(), eventsOfShowroom, s.getOpenDates(), s.getId() );
			showrooms.add(mod) ;
		}
		for (Event evt : realEvents) {
			ModelEvent mod = null ;
			if (evt instanceof Drama) { // DEGUEULASSE : il faut faire des ModelEvents generiques je pense
				Drama d = (Drama) evt ;
				mod = new ModelDrama(d.getStartDate().getYear(), d.getStartDate().getMonth(), d.getStartDate().getDay(),
						d.getEndDate().getYear(), d.getEndDate().getMonth(), d.getEndDate().getDay(),d.getPlaceNumber(),  d.getTitleName().getName(), d.getRef()) ;
				
			
			} else if (evt instanceof Concert) {
				Concert c = (Concert) evt ;
				mod = new ModelConcert(c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay(), c.getPlaceNumber(),  c.getArtistName().getName(), c.getRef()) ;
			}
			events.add(mod) ;
		}
	}

	public List<ModelEvent> getEvents() {
		return new LinkedList<>(events);
	}
	public List<ModelShowroom> getShowrooms() {
		return new LinkedList<>(showrooms);
	}
}
