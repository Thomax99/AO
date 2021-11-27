package test;

import java.util.LinkedList;
import java.util.List;

import domain.Date;
import domain.OpenDate;
import domain.ShowRoom;
import junit.framework.*;

public class EntityTest extends TestCase {
	public void testShowRoomClassical() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		ShowRoom r = new ShowRoom(dates, 150) ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
		}
		r = new ShowRoom(dates, 150) ;
	}
	public void testShowRoomInvalidDates() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		ShowRoom r = new ShowRoom(dates, 150) ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
			dates.add(new OpenDate(2022, 10, i, 14)) ;
		}
		try {
			r = new ShowRoom(dates, 150) ;
			fail("IllegalArgumentException not thrown with show room with same dates multiple times") ;
		} catch (IllegalArgumentException e) {
			
		}
	}
	public void testShowRoomCapacity() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		ShowRoom r = new ShowRoom(dates, 150) ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
		}
		r = new ShowRoom(dates, 150) ;
		try {
			r = new ShowRoom(dates, -1) ;
			fail("IllegalArgumentException not thrown with capacity < 0") ;
		} catch (IllegalArgumentException e) {
			
		}
		r = new ShowRoom(dates, 150) ;
		assertEquals(r.getCapacity(), 150) ;
	}
	public void testShowRoomOpening() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		ShowRoom r = new ShowRoom(dates, 150) ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
		}
		r = new ShowRoom(dates, 150) ;
		List<OpenDate> openDates = r.getOpenDates() ;
		for (OpenDate d : dates) {
			if (!openDates.contains(d)) {
				fail("Date not contained on show room") ; 
			}
		}
		assertEquals(r.isOpened(new Date(2022, 10, 4)), 8) ;
		assertEquals(r.isOpened(new Date(2022, 9, 2)), -1) ;
		OpenDate d = new OpenDate(2022, 10, 4, 8) ;
		OpenDate d2 = new OpenDate(2022, 10, 4, 3) ;

		assertEquals(r.getLeavingPlaces(d), 150) ;
		assertEquals(r.getLeavingPlaces(d2), 0) ;
		assertEquals(r.getPlace(d), true) ;
		assertEquals(r.getPlace(d2), false) ;
		assertEquals(r.getLeavingPlaces(d), 149) ;
		assertEquals(r.getLeavingPlaces(d2), 0) ;
	}
}
