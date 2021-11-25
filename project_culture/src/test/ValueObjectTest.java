package test;
import domain.Date;
import domain.Name;
import junit.framework.*;

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
			Date d = new Date(-1, 0, 11) ;
			fail("IllegalArgumentException not thrown with negative year") ;
		} catch(IllegalArgumentException e) {
		}

		try {
			Date d = new Date(2022, -1, 11) ;
			fail("IllegalArgumentException not thrown with negative month") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			Date d = new Date(2022, 13, 11) ;
			fail("IllegalArgumentException not thrown with month > 12") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			Date d = new Date(2022, 12, 0) ;
			fail("IllegalArgumentException not thrown with negative day") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			Date d = new Date(2022, 12, 32) ;
			fail("IllegalArgumentException not thrown with day > 31") ;
		} catch(IllegalArgumentException e) {
			
		}
		Date d = new Date(2000, 2, 29) ;
		d = new Date(2016, 2, 29) ;
		d = new Date(2010, 2, 28) ;
		d = new Date(1900, 2, 28) ;
		try {
			d = new Date(1900, 2, 29) ;
			fail("IllegalArgumentException not thrown with day == 29 in non-leap february") ;
		} catch(IllegalArgumentException e) {
			
		}
		try {
			d = new Date(1904, 2, 30) ;
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
}
