package test;
import domain.Concert;
import domain.Date;
import domain.Drama;
import domain.Name;
import domain.OpenDate;
import junit.framework.TestCase;

public class ValueObjectTest extends TestCase {
	/**
	 * Test in a classical way the good use of date
	 * it means without limits
	 * @throws Exception
	 */
	public void testDateClassical() throws Exception {
		Date date = new Date(2022, 11, 15) ;
		assertEquals(date.getYear(), 2022) ;
		assertEquals(date.getMonth(), 11) ;
		assertEquals(date.getDay(), 15) ;
		assertEquals(date.toString(), "2022/11/15") ;
	  }
	public void testDateModifying() throws Exception {
		Date date = new Date(2022, 11, 15) ;
		Integer year = date.getYear() ;
		year += 4 ;
		assertEquals(date.getYear(), 2022) ;
	}
	/**
	 * Test the limits of the valueObject date
	 * @throws Exception
	 */
	public void testDateLimits() throws Exception {
		try {
			new Date(-1, 0, 11);
			fail("IllegalArgumentException not thrown with negative year") ;
		} catch(IllegalArgumentException e) {
		}

		try {
			new Date(2022, -1, 11);
			fail("IllegalArgumentException not thrown with negative month") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			new Date(2022, 13, 11);
			fail("IllegalArgumentException not thrown with month > 12") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			new Date(2022, 12, 0);
			fail("IllegalArgumentException not thrown with negative day") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			new Date(2022, 12, 32);
			fail("IllegalArgumentException not thrown with day > 31") ;
		} catch(IllegalArgumentException e) {
			
		}
		new Date(2000, 2, 29);
		new Date(2016, 2, 29);
		new Date(2010, 2, 28);
		new Date(1900, 2, 28);
		try {
			new Date(1900, 2, 29);
			fail("IllegalArgumentException not thrown with day == 29 in non-leap february") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			new Date(1904, 2, 30);
			fail("IllegalArgumentException not thrown with day == 30 in leap february") ;
		} catch(IllegalArgumentException e) {
			
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
		Date d = new Date(2022, 11, 14) ;
		Name n = new Name("Foo fighters") ;
		Concert concert = new Concert(d.getYear(), d.getMonth(), d.getDay(), n.getName(), 1000) ;
		assertEquals(d, concert.getDate()) ;
		assertEquals(n, concert.getArtistName()) ;
		assertEquals(1000, concert.getPlaceNumber()) ;
		try {
			concert = new Concert(d.getYear(), d.getMonth(), d.getDay(), n.getName(), -1) ;
			fail("IllegalArgumentException not thrown with number of place < 0") ;
		} catch(IllegalArgumentException e) {
			
		}

	}
	public void testDrama() throws Exception {
		Date d = new Date(2022, 11, 14) ;
		Date d2 = new Date(2023, 11, 14) ;
		Name n = new Name("Le bourgeois gentilhomme") ;
		Drama t = new Drama(d.getYear(), d.getMonth(), d.getDay(), d2.getYear(), d2.getMonth(), d2.getDay(), n.getName(), 1000) ;
		assertEquals(d, t.getStartDate()) ;
		assertEquals(d2, t.getEndDate()) ;
		assertEquals(n, t.getArtistName()) ;
		assertEquals(1000, t.getPlaceNumber()) ;
		try {
			t = new Drama(d2.getYear(), d2.getMonth(), d2.getDay(), d.getYear(), d.getMonth(), d.getDay(), n.getName(), 1000) ;
			fail("IllegalArgumentException not thrown with start date > end date") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			t = new Drama(d2.getYear(), d2.getMonth(), d2.getDay(), d.getYear(), d.getMonth(), d.getDay(), n.getName(), -1) ;
			fail("IllegalArgumentException not thrown with place number < 0") ;
		} catch(IllegalArgumentException e) {
			
		}
	}
	public void testOpenDate() throws Exception {
		Date d = new Date(2022, 11, 30) ;
		OpenDate date = new OpenDate(d.getYear(), d.getMonth(), d.getDay(), 12) ;
		assertEquals(date.getOpenHour(), 12) ;
		assertEquals(date.getOpenDay(), d) ;
		try {
			date = new OpenDate(d.getYear(), d.getMonth(), d.getDay(), -1) ;
			fail("IllegalArgumentException not thrown with hour < 0") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			date = new OpenDate(d.getYear(), d.getMonth(), d.getDay(), 24) ;
			fail("IllegalArgumentException not thrown with hour >= 24") ;
		} catch(IllegalArgumentException e) {
			
		}
	}
}
