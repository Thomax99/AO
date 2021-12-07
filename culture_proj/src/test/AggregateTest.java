package test;

import java.util.LinkedList;

import domain.City;
import domain.EventCatalog;
import domain.ShowRoom;
import junit.framework.TestCase;

public class AggregateTest extends TestCase {
	public void testCreateCity() throws Exception {
		new City(new EventCatalog(new LinkedList<>(), new LinkedList<>())) ;
	}
	public void testCityGetId() throws Exception {
		City c1 = new City(new EventCatalog(new LinkedList<>(), new LinkedList<>())),
				c2 = new City(new EventCatalog(new LinkedList<>(), new LinkedList<>())) ;
		assertEquals(c1.getId() != c2.getId(), true) ;
		assertEquals(c1.getId() < c2.getId(), true) ;
	}
	public void testCityAddAndGetShowRoom() throws Exception {
		City c1 = new City(new EventCatalog(new LinkedList<>(), new LinkedList<>())) ;
		ShowRoom r1 = new ShowRoom(null, 150), r2 = new ShowRoom(null, 180) ;
		c1.addShowRoom(r1) ;
		assertEquals(c1.getShowroom(r1.getId()), r1) ;
		assertEquals(c1.getShowroom(r2.getId()), null) ;
		c1.addShowRoom(r2) ;
		assertEquals(c1.getShowroom(r2.getId()), r2) ;
		
		boolean r1In = false, r2In = false ;
		for (ShowRoom r : c1.getShowrooms()) {
			if (r1.equals(r)) {
				r1In = true ;
			}
			else if (r2.equals(r)) {
				r2In = true ;
			}
		}
		assertEquals(r1In, true) ;
		assertEquals(r2In, true) ;
		assertEquals(c1.getShowrooms().size(), 2) ;
		c1.addShowRoom(r2) ;
		assertEquals(c1.getShowrooms().size(), 2) ;


	}
}
