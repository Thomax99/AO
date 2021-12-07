package test;
import java.util.Date;
import java.util.List;

import domain.Concert;
import domain.DateUtilitaries;
import domain.Drama;
import domain.Name;
import domain.OpenDate;
import exceptions.ForbiddenDateException;
import exceptions.ForbiddenDateIntervalException;
import exceptions.ForbiddenHourException;
import exceptions.NegativePlaceQuantityException;
import junit.framework.TestCase;

public class ValueObjectTest extends TestCase {
	/**
	 * Test in a classical way the good use of date
	 * it means without limits
	 * @throws Exception
	 */
	public void testDateClassical() throws Exception {
		Date date = DateUtilitaries.createDate(2022, 11, 15) ;
		assertEquals(DateUtilitaries.getYear(date), 2022) ;
		assertEquals(DateUtilitaries.getMonth(date), 11) ;
		assertEquals(DateUtilitaries.getDay(date), 15) ;
		Date d2 = DateUtilitaries.getNextDate(date) ;
		assertEquals(DateUtilitaries.getYear(d2), 2022) ;
		assertEquals(DateUtilitaries.getMonth(d2), 11) ;
		assertEquals(DateUtilitaries.getDay(d2), 16) ;
		Date firstDay = DateUtilitaries.getFirstDayOfWeek(date) ;
		Date realF = DateUtilitaries.createDate(2022, 11, 14) ;
		assertEquals(firstDay, realF) ;
		firstDay = DateUtilitaries.getFirstDayOfWeek(d2) ;
		assertEquals(firstDay, realF) ;
		Date lastDay = DateUtilitaries.getLastDayOfWeek(date) ;
		Date realL = DateUtilitaries.createDate(2022, 11, 20) ;
		assertEquals(lastDay, realL) ;
		lastDay = DateUtilitaries.getLastDayOfWeek(d2) ;
		assertEquals(lastDay, realL) ;
		assertEquals(DateUtilitaries.isOnWeekEnd(lastDay), true) ;
		realL = DateUtilitaries.createDate(2022, 11, 19) ;
		assertEquals(DateUtilitaries.isOnWeekEnd(realL), true) ;
		realL = DateUtilitaries.createDate(2022, 11, 18) ;
		assertEquals(DateUtilitaries.isOnWeekEnd(realL), false) ;

		date = DateUtilitaries.createDate(2022, 12, 13) ;
		d2 = DateUtilitaries.createDate(2023, 2, 3) ;
		List<Date> interval = DateUtilitaries.getDateInInterval(date, d2) ;
		for (Date d : interval) {
			assertEquals(date.before(d) || date.equals(d), true) ;
			assertEquals(d2.after(d) || d2.equals(d), true) ;
		}
		while (date.before(d2) || date.equals(d2)) {
			boolean isIn = false ;
			for (Date d : interval) {
				if (date.equals(d)) {
					isIn = true ;
					break ;
				}
			}
			assertEquals(isIn, true) ;
			date = DateUtilitaries.getNextDate(date) ;
		}
	}
	public void testDateModifying() throws Exception {
		Date date = DateUtilitaries.createDate(2022, 11, 15) ;
		Integer year = DateUtilitaries.getYear(date) ;
		year += 4 ;
		assertEquals(DateUtilitaries.getYear(date), 2022) ;
	}
	/**
	 * Test the limits of the valueObject date
	 * @throws Exception
	 */
	public void testDateLimits() throws Exception {
		try {
			DateUtilitaries.createDate(2022, -1, 11);
			fail("ForbiddenDateException not thrown with negative month") ;
		} catch(ForbiddenDateException e) {
			
		}
		try {
			DateUtilitaries.createDate(2022, 13, 11);
			fail("ForbiddenDateException not thrown with month > 12") ;
		} catch(ForbiddenDateException e) {
			
		}
		try {
			DateUtilitaries.createDate(2022, 12, 0);
			fail("ForbiddenDateException not thrown with negative day") ;
		} catch(ForbiddenDateException e) {
			
		}
		try {
			DateUtilitaries.createDate(2022, 12, 32);
			fail("ForbiddenDateException not thrown with day > 31") ;
		} catch(Exception e) {
			
		}
		Date d1 = DateUtilitaries.createDate(2000, 2, 29);
		Date d2 = DateUtilitaries.createDate(2016, 2, 29);
		DateUtilitaries.createDate(2010, 2, 28);
		DateUtilitaries.createDate(1900, 2, 28);
		try {
			DateUtilitaries.getDateInInterval(d2, d1) ;
			fail("ForbiddenDateIntervalException not throw with d1 > d2") ;
		} catch (ForbiddenDateIntervalException e) {
			
		}
		try {
			DateUtilitaries.createDate(1900, 2, 29);
			fail("ForbiddenDateException not thrown with day == 29 in non-leap february") ;
		} catch(ForbiddenDateException e) {
			
		}
		try {
			DateUtilitaries.createDate(1904, 2, 30);
			fail("ForbiddenDateException not thrown with day == 30 in leap february") ;
		} catch(ForbiddenDateException e) {
			
		}
	}
	
	public void testNameClassical() throws Exception {
		Name n = new Name("Foo fighters") ;
		assertEquals(n.getName(), "Foo fighters") ;
	}
	public void testnameModification() throws Exception {
		Name n = new Name("Foo fighters") ;
		String name = n.getName() ;
		name.replace('o', 'f') ;
		assertEquals(n.getName(), "Foo fighters") ;
	}
	
	public void testEvent() throws Exception {
		Date d = DateUtilitaries.createDate(2022, 11, 14) ;
		Name n = new Name("Foo fighters") ;
		Concert concert = new Concert(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), n.getName(), 1000) ;
		Date d1 = DateUtilitaries.createDate(2022, 11, 14) ;
		Date d2 = DateUtilitaries.createDate(2023, 11, 14) ;
		Name n1 = new Name("Le bourgeois gentilhomme") ;
		Drama t = new Drama(DateUtilitaries.getYear(d1), DateUtilitaries.getMonth(d1), DateUtilitaries.getDay(d1),
				DateUtilitaries.getYear(d2), DateUtilitaries.getMonth(d2), DateUtilitaries.getDay(d2), n1.getName(), 1000) ;
		assertEquals(concert.compareTo(t) < 0, true) ;
		assertEquals(t.compareTo(concert) > 0, true) ;

	}
	public void testConcert() throws Exception {
		Date d = DateUtilitaries.createDate(2022, 11, 14) ;
		Name n = new Name("Foo fighters") ;
		Concert concert = new Concert(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), n.getName(), 1000) ;
		assertEquals(d, concert.getDate()) ;
		assertEquals(n, concert.getArtistName()) ;
		assertEquals(1000, concert.getPlaceNumber()) ;
		try {
			concert = new Concert(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), n.getName(), -1) ;
			fail("NegativePlaceQuantityException not thrown with number of place < 0") ;
		} catch(NegativePlaceQuantityException e) {
			
		}

	}
	public void testDrama() throws Exception {
		Date d = DateUtilitaries.createDate(2022, 11, 14) ;
		Date d2 = DateUtilitaries.createDate(2023, 11, 14) ;
		Name n = new Name("Le bourgeois gentilhomme") ;
		Drama t = new Drama(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d),
				DateUtilitaries.getYear(d2), DateUtilitaries.getMonth(d2), DateUtilitaries.getDay(d2), n.getName(), 1000) ;
		assertEquals(d, t.getStartDate()) ;
		assertEquals(d2, t.getEndDate()) ;
		assertEquals(n, t.getTitleName()) ;
		assertEquals(1000, t.getPlaceNumber()) ;
		try {
			t = new Drama(DateUtilitaries.getYear(d2), DateUtilitaries.getMonth(d2), DateUtilitaries.getDay(d2),
					DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), n.getName(), 1000) ;
			fail("ForbiddenDateIntervalException not thrown with start date > end date") ;
		} catch(ForbiddenDateIntervalException e) {
			
		}
		try {
			t = new Drama(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d),
					DateUtilitaries.getYear(d2), DateUtilitaries.getMonth(d2), DateUtilitaries.getDay(d2), n.getName(), -1) ;
			fail("NegativePlaceQuantityException not thrown with place number < 0") ;
		} catch(NegativePlaceQuantityException e) {
			
		}
	}
	public void testOpenDate() throws Exception {
		Date d = DateUtilitaries.createDate(2022, 11, 30) ;
		OpenDate date = new OpenDate(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), 12),
					d2 = new OpenDate(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), 13),
					d3 = new OpenDate(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), 11);
		assertEquals(date.getOpenHour(), 12) ;
		assertEquals(date.getOpenDay(), d) ;
		OpenDate givenNext = date.next(), realNext = new OpenDate(2022, 12, 1, 12) ;
		assertEquals(givenNext, realNext) ;
		assertEquals(givenNext.equals(null), false) ;
		assertEquals(date.compareTo(givenNext), -1) ;
		assertEquals(givenNext.compareTo(date), 1) ;
		assertEquals(date.compareTo(d2), -1) ;
		assertEquals(date.compareTo(d3), 1) ;

		try {
			date = new OpenDate(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), -1) ;
			fail("ForbiddenHourException not thrown with hour < 0") ;
		} catch(ForbiddenHourException e) {
			
		}
		try {
			date = new OpenDate(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), 24) ;
			fail("ForbiddenHourException not thrown with hour >= 24") ;
		} catch(ForbiddenHourException e) {
			
		}
	}
}
