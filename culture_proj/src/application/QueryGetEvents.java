package application;

import java.util.List;

import domain.Event;

public class QueryGetEvents extends Query<List<Event>> {
	public List<Event> execute() {
		return getCityService().getEvents() ;
	}
}
