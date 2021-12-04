package infra;

import java.util.LinkedList;
import java.util.List;

import domain.Concert;
import domain.Drama;
import domain.Event;

public class EventCatalog {
	private List<Concert> concerts ;
	private List<Drama> dramas ;
	public EventCatalog(List<Concert> concerts, List<Drama> dramas) {
		this.concerts = new LinkedList<>(concerts) ;
		this.dramas = new LinkedList<>(dramas) ;
	}
	
	public List<Concert> getConcerts() {
		return new LinkedList<>(concerts) ;
	}
	public List<Drama> getDramas() {
		return new LinkedList<>(dramas) ;
	}
}
