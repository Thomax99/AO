package domain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is also an entity object, like a showroom
 * @author thomas
 *
 */
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

	public void removeConcert(Concert c) {
		Iterator<Concert> it = concerts.iterator() ;
		while (it.hasNext()) {
			Concert conc = it.next() ;
			if (conc.equals(c)) {
				it.remove();
				break ;
			}
		}
	}
	public void removeDrama(Drama d) {
		Iterator<Drama> it = dramas.iterator() ;
		while (it.hasNext()) {
			Drama dr = it.next() ;
			if (dr.equals(d)) {
				it.remove();
				break ;
			}
		}
	}

	public void addConcert(Concert evt) {
		concerts.add(evt) ;
	}
	public void addDrama(Drama evt) {
		dramas.add(evt) ;
	}
}
