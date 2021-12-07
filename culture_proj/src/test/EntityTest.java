package test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import domain.Concert;
import domain.DateUtilitaries;
import domain.Drama;
import domain.Event;
import domain.OpenDate;
import domain.ShowRoom;
import exceptions.CapacityNegativeException;
import exceptions.EqualsDatesException;
import exceptions.NotEnoughPlaceException;
import exceptions.NotExistantEventException;
import exceptions.NotOpenedShowRoomException;
import junit.framework.TestCase;

public class EntityTest extends TestCase {
	public void testShowRoomClassical() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		ShowRoom r1 = new ShowRoom(dates, 150);
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
		}
		ShowRoom r2 = new ShowRoom(dates, 150);
		assertEquals(r1.getId() != r2.getId(), true) ;
		assertEquals(r1.getId() < r2.getId(), true) ;

	}
	public void testShowRoomGetOpenDatesWithoutEvent() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
		}
		ShowRoom r = new ShowRoom(dates, 150);
		List<OpenDate> opendates = r.getOpenDatesWithoutEvent() ;
		for (OpenDate d : dates) {
			boolean isIn = false ;
			for (OpenDate od : opendates) {
				if (d.equals(od)) {
					isIn = true ;
				}
			}
			assertEquals(isIn, true) ;
		}
		assertEquals(opendates.size(), dates.size()) ;
		OpenDate with = new OpenDate(2022, 10, 1, 8) ;
		Concert c = new Concert(2022, 10, 1, "tototo", 120) ;
		r.addEvent(c);
		opendates = r.getOpenDatesWithoutEvent() ;

		for (OpenDate d : dates) {
			boolean isIn = false ;
			for (OpenDate od : opendates) {
				if (d.equals(od)) {
					isIn = true ;
				}
			}
			if  (d.equals(with)) 
				assertEquals(isIn, false) ;
			else
				assertEquals(isIn, true) ;
		}
		assertEquals(opendates.size(), dates.size() - 1) ;
	}
	public void testShowRoomAddEvent() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
			dates.add(new OpenDate(2022, 11, i, 8)) ;
		}
		ShowRoom r = new ShowRoom(dates, 150);
		for (int i = 1 ; i < 31 ; i++) {
			Concert c = new Concert(2022, 10, i, "tototo", 120) ;
			r.addEvent(c);
		}
		
		for (int i = 1 ; i < 31 ; i++) {
			try {
				Concert c = new Concert(2022, 10, 1, "tototo", 120) ;
				r.addEvent(c);
				fail("NotOpenedShowRoomException not thrown with two equals dates") ;
			} catch (NotOpenedShowRoomException e) {
				
			}
			try {
				Concert c = new Concert(2022, 9, 1, "tototo", 120) ;
				r.addEvent(c);
				fail("NotOpenedShowRoomException not thrown with not opened showroom") ;
			} catch (NotOpenedShowRoomException e) {
				
			}
		}
		r = new ShowRoom(dates, 150);
		try {
			Concert c = new Concert(2022, 10, 1, "tototo", 200) ;
			r.addEvent(c);
			fail("NotEnoughPlaceException not thrown with too big event") ;
		} catch (NotEnoughPlaceException e) {
			
		}
		r = new ShowRoom(dates, 150);
		Drama d = new Drama(2022, 11, 1, 2022, 11, 15, "tatata", 80) ;
		r.addEvent(d);
		try {
			d = new Drama(2022, 11, 1, 2022, 11, 15, "tatata", 80) ;
			r.addEvent(d);
			fail("NotOpenedShowRoomException not thrown with two equals dates") ;
		} catch (NotOpenedShowRoomException e) {
			
		}
		try {
			d = new Drama(2022, 8, 1, 2022, 8, 15, "tatata", 80) ;
			r.addEvent(d);
			fail("NotOpenedShowRoomException not thrown with not opened showroom") ;
		} catch (NotOpenedShowRoomException e) {
			
		}
		r = new ShowRoom(dates, 150);
		try {
			d = new Drama(2022, 11, 1, 2022, 11, 15, "tatata", 200) ;
			r.addEvent(d);
			fail("NotEnoughPlaceException not thrown with too big event") ;
		} catch (NotEnoughPlaceException e) {
			
		}
	}
	public void testShowRoomGetEvents() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
			dates.add(new OpenDate(2022, 11, i, 8)) ;
		}
		ShowRoom r = new ShowRoom(dates, 150);
		List<Event> events = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			Concert c = new Concert(2022, 10, i, "tototo", 120) ;
			r.addEvent(c);
			events.add(c) ;
		}
		Drama d = new Drama(2022, 11, 1, 2022, 11, 25, "tititi", 130 ) ;
		r.addEvent(d);
		events.add(d) ;
		List<Event> eventsGet = r.getEvents() ;
		for(Event ev : events) {
			boolean isIn = false ;
			for (Event evg : eventsGet) {
				if (evg.equals(ev)) {
					isIn = true ;
					break ;
				}
			}
			assertEquals(isIn, true) ;
		}
		assertEquals(eventsGet.size(), events.size()) ;
	}
	public void testShowRoomGetDatesByEvent() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
			dates.add(new OpenDate(2022, 11, i, 8)) ;
		}
		ShowRoom r = new ShowRoom(dates, 150);
		List<Concert> concerts = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			Concert c = new Concert(2022, 10, i, "tototo", 120) ;
			r.addEvent(c);
			concerts.add(c) ;
		}
		Drama d = new Drama(2022, 11, 1, 2022, 11, 25, "tititi", 130 ) ;
		r.addEvent(d);
		for (Concert c : concerts) {
			List<OpenDate> datesEvent = r.getDatesByEvent(c) ;
			assertEquals(datesEvent.size(), 1) ;
			assertEquals(datesEvent.get(0), new OpenDate( DateUtilitaries.getYear(c.getDate()), DateUtilitaries.getMonth(c.getDate()),
											DateUtilitaries.getDay(c.getDate()), 8)) ;
		}
		List<OpenDate> datesEvent = r.getDatesByEvent(d) ;
		List<Date> interval = DateUtilitaries.getDateInInterval(d.getStartDate(), d.getEndDate()) ;
		for (Date di : interval) {
			OpenDate id = new OpenDate(DateUtilitaries.getYear(di), DateUtilitaries.getMonth(di), DateUtilitaries.getDay(di), 8) ;
			boolean isIn = false ;
			for (OpenDate od : datesEvent) {
				if (id.equals(od)) {
					isIn = true ;
					break ;
				}
			}
			assertEquals(isIn, true) ;
		}
		Concert c = new Concert(2022, 10, 2, "tututu", 120) ;
		datesEvent = r.getDatesByEvent(c) ;
		assertEquals(datesEvent, null) ;
	}
	public void testShowRoomGetProgrammationOfDate() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
			dates.add(new OpenDate(2022, 11, i, 8)) ;
		}
		ShowRoom r = new ShowRoom(dates, 150);
		assertEquals(r.getProgrammationOfDate(DateUtilitaries.createDate(2022, 10, 2)), null) ;
		assertEquals(r.getProgrammationOfDate(DateUtilitaries.createDate(2022, 5, 2)), null) ;
		Concert c = new Concert(2022, 10, 3, "tototo", 120) ;
		r.addEvent(c);
		Drama d = new Drama(2022, 11, 1, 2022, 11, 25, "tititi", 130 ) ;
		r.addEvent(d);
		assertEquals(r.getProgrammationOfDate(DateUtilitaries.createDate(2022, 10, 3)), c) ;
		for (int i = 1 ; i < 25 ; i++) {
			assertEquals(r.getProgrammationOfDate(DateUtilitaries.createDate(2022, 11, i)), d) ;
		}
		r.removeEvent(c.getRef()) ;
		assertEquals(r.getProgrammationOfDate(DateUtilitaries.createDate(2022, 10, 3)), null) ;
		for (int i = 1 ; i < 25 ; i++) {
			assertEquals(r.getProgrammationOfDate(DateUtilitaries.createDate(2022, 11, i)), d) ;
		}
		r.removeEvent(d.getRef()) ;
		assertEquals(r.getProgrammationOfDate(DateUtilitaries.createDate(2022, 10, 3)), null) ;
		for (int i = 1 ; i < 25 ; i++) {
			assertEquals(r.getProgrammationOfDate(DateUtilitaries.createDate(2022, 11, i)), null) ;
		}
	}
	public void testShowRoomRemoveEvent() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
			dates.add(new OpenDate(2022, 11, i, 8)) ;
		}
		ShowRoom r = new ShowRoom(dates, 150);
		Concert c = new Concert(2022, 10, 3, "tototo", 120) ;
		r.addEvent(c);
		Drama d = new Drama(2022, 11, 1, 2022, 11, 25, "tititi", 130 ) ;
		r.addEvent(d);
		assertEquals(r.removeEvent(c.getRef()), c) ;
		assertEquals(r.removeEvent(d.getRef()), d) ;
		try {
			r.removeEvent(d.getRef()) ;
			fail("NotExistantEventException not thrown with not existant event removed") ;
		} catch (NotExistantEventException e) {
			
		}

	}


	public void testShowRoomGetOpenDates() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		ShowRoom r1 = new ShowRoom(dates, 150);
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
		}
		ShowRoom r2 = new ShowRoom(dates, 150);
		assertEquals(r1.getOpenDates().size(), 0) ;
		List<OpenDate> opendates = r2.getOpenDates() ;
		for (OpenDate d : dates) {
			boolean isIn = false ;
			for (OpenDate od : opendates) {
				if (d.equals(od)) {
					isIn = true ;
				}
			}
			assertEquals(isIn, true) ;
		}
		assertEquals(opendates.size(), dates.size()) ;
		
		Concert c = new Concert(2022, 10, 1,"tototo", 120) ;
		r2.addEvent(c);
		for (OpenDate d : dates) {
			boolean isIn = false ;
			for (OpenDate od : opendates) {
				if (d.equals(od)) {
					isIn = true ;
				}
			}
			assertEquals(isIn, true) ;
		}
		assertEquals(opendates.size(), dates.size()) ;
	}
	public void testShowRoomInvalidDates() throws Exception {
		List<OpenDate> dates = new LinkedList<>() ;
		new ShowRoom(dates, 150);
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 10, i, 8)) ;
			dates.add(new OpenDate(2022, 10, i, 14)) ;
		}
		
		try {
			new ShowRoom(dates, 150);
			fail("EqualsDatesException not thrown with show room with same dates multiple times") ;
		} catch (EqualsDatesException e) {
			
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
			fail("CapacityNegativeException not thrown with capacity < 0") ;
		} catch (CapacityNegativeException e) {
			
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
		assertEquals(r.getOpenHour(DateUtilitaries.createDate(2022, 10, 4)), 8) ;
		assertEquals(r.getOpenHour(DateUtilitaries.createDate(2022, 9, 2)), -1) ;
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
