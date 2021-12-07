package test;
import java.util.Date;

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
		DateUtilitaries.createDate(2000, 2, 29);
		DateUtilitaries.createDate(2016, 2, 29);
		DateUtilitaries.createDate(2010, 2, 28);
		DateUtilitaries.createDate(1900, 2, 28);
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
		OpenDate date = new OpenDate(DateUtilitaries.getYear(d), DateUtilitaries.getMonth(d), DateUtilitaries.getDay(d), 12) ;
		assertEquals(date.getOpenHour(), 12) ;
		assertEquals(date.getOpenDay(), d) ;
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
