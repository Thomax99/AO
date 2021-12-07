package application;

import java.util.List;

import domain.Event;

public class QueryGetEvents extends Query<List<Event>> {
	private final int cityId;
	public QueryGetEvents(int cityId) {
		this.cityId = cityId ;
	}
	public List<Event> execute() {
		return getCityService().getEvents(cityId) ;
	}
}
