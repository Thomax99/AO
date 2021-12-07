package user.model;

import java.util.LinkedList;
import java.util.List;

import domain.Concert;
import domain.DateUtilitaries;
import domain.Drama;
import domain.Event;
import domain.ShowRoom;

public class Model {
	private List<ModelShowroom> showrooms ;
	private List<ModelDrama> dramas;
	private List<ModelConcert> concerts;
	public Model () {
		this.dramas = new LinkedList<>() ;
		this.concerts = new LinkedList<>() ;
		this.showrooms = new LinkedList<>() ;
	}

	public List<ModelEvent> getEvents() {
		List<ModelEvent> output = new LinkedList<>(concerts) ;
		output.addAll(dramas) ;
		return output ;
	}
	public List<ModelShowroom> getShowrooms() {
		return new LinkedList<>(showrooms);
	}

	public void update(List<Drama> dramas, List<Concert> concerts, List<ShowRoom> showrooms) {
		this.dramas.clear();
		this.concerts.clear();
		this.showrooms.clear();
		for (Drama d : dramas) {
			this.dramas.add(new ModelDrama(DateUtilitaries.getYear(d.getStartDate()), DateUtilitaries.getMonth(d.getStartDate()),
											DateUtilitaries.getDay(d.getStartDate()), DateUtilitaries.getYear(d.getEndDate()),
											DateUtilitaries.getMonth(d.getEndDate()), DateUtilitaries.getDay(d.getEndDate()),
											d.getPlaceNumber(), 
							d.getTitleName().getName(), d.getRef())) ;
		}
		for (Concert c : concerts) {
			this.concerts.add(new ModelConcert(DateUtilitaries.getYear(c.getDate()), DateUtilitaries.getMonth(c.getDate()),
														DateUtilitaries.getDay(c.getDate()), c.getPlaceNumber(),
														c.getArtistName().getName(), c.getRef())) ;
		}
		for (ShowRoom s : showrooms) {
			List<ModelConcert> eventsConcerts = new LinkedList<>() ;
			List<ModelDrama> eventDramas = new LinkedList<>() ;
			for (Event evt : s.getEvents()) {
				if (evt instanceof Drama) {
					Drama d = (Drama) evt ;
					eventDramas.add( new ModelDrama(DateUtilitaries.getYear(d.getStartDate()), DateUtilitaries.getMonth(d.getStartDate()),
							DateUtilitaries.getDay(d.getStartDate()), DateUtilitaries.getYear(d.getEndDate()),
							DateUtilitaries.getMonth(d.getEndDate()), DateUtilitaries.getDay(d.getEndDate()),
							d.getPlaceNumber(), d.getTitleName().getName(), d.getRef())) ;
				} else {
					Concert c = (Concert) evt ;
					eventsConcerts.add(new ModelConcert(DateUtilitaries.getYear(c.getDate()), DateUtilitaries.getMonth(c.getDate()),
							DateUtilitaries.getDay(c.getDate()), c.getPlaceNumber(),
							c.getArtistName().getName(), c.getRef())) ;
				}
			}
			ModelShowroom mod = new ModelShowroom(s.getCapacity(), eventsConcerts, eventDramas, s.getOpenDatesWithoutEvent(), s.getId() );
			this.showrooms.add(mod) ;
		}

	}

	public List<ModelConcert> getConcerts() {
		return concerts;
	}
	
	public List<ModelDrama> getDramas() {
		return dramas;
	}
}
