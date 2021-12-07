package infra;

import domain.City;
import domain.Concert;
import domain.DateUtilitaries;
import domain.Drama;
import domain.Event;
import domain.OpenDate;
import domain.Repository;
import domain.ShowRoom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom2.* ;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
public class RepositoryInXml implements Repository {


	@Override
	public void save(City city) {
		Element root = new Element("City") ;
		
		Document doc = new Document(root) ;
		
		Attribute idAttribute = new Attribute("id", Integer.toString(city.getId())) ;
		root.setAttribute(idAttribute) ;
		Element catalog = new Element("EventCatalog") ;
		
		saveEventsInCatalogInElement(catalog, city.getEventsInCatalog()) ;
		
		Element showrooms = new Element("showrooms") ;
		saveShowroomsInElement(showrooms, city.getShowrooms()) ;
		
		root.addContent(catalog) ;
		root.addContent(showrooms) ;
		
		String filename = "city" + Integer.toString(city.getId()) + ".xml" ;
		
		try {
			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat()) ;
			out.output(doc, new FileOutputStream(filename)) ;
		} catch (IOException e) {
			System.err.println(e) ;
		}

		
		
	}
	
	private void saveShowroomsInElement(Element showroomsEl, List<ShowRoom> showrooms) {
		for (ShowRoom shr : showrooms) {
			showroomsEl.addContent(saveShowroom(shr)) ;
		}
	}

	private Element saveShowroom(ShowRoom showroom) {
		Element showroomEl = new Element("showroom") ;
		Attribute idAttribute = new Attribute("id", Integer.toString(showroom.getId())) ;
		showroomEl.setAttribute(idAttribute) ;

		Element capacity = new Element("capacity") ;
		capacity.addContent(Integer.toString(showroom.getCapacity())) ;
		showroomEl.addContent(capacity) ;
		
		Element opendates = new Element("opendates") ;
		
		for (OpenDate od : showroom.getOpenDates()) {
			Element opendate = new Element("opendate") ;
			
			Element year = new Element("year") ;
			year.addContent(Integer.toString(DateUtilitaries.getYear(od.getOpenDay()))) ;
			Element month = new Element("month") ;
			month.addContent(Integer.toString(DateUtilitaries.getMonth(od.getOpenDay()))) ;
			Element day = new Element("day") ;
			day.addContent(Integer.toString(DateUtilitaries.getDay(od.getOpenDay()))) ;
			Element hour = new Element("hour") ;
			hour.addContent(Integer.toString(od.getOpenHour())) ;
			
			opendate.addContent(year) ;
			opendate.addContent(month) ;
			opendate.addContent(day) ;
			opendate.addContent(hour) ;
			
			opendates.addContent(opendate) ;
		}
		showroomEl.addContent(opendates) ;
		
		Element events = new Element("events") ;

		for (Event ev : showroom.getEvents()) {
			if (ev instanceof Drama) {
				events.addContent(saveDrama((Drama) ev)) ;
			} else if (ev instanceof Concert) {
				events.addContent(saveConcert((Concert) ev)) ;

			}
		}
		showroomEl.addContent(events) ;

		
		return showroomEl;
	}

	private Element saveDrama(Drama drama) {
		Element dramaEl = new Element("Drama") ;
		
		Attribute refAttribute = new Attribute("ref", Integer.toString(drama.getRef())) ;
		dramaEl.setAttribute(refAttribute) ;
		
		Element placeNumber = new Element("placeNumber") ;
		placeNumber.addContent(Integer.toString(drama.getPlaceNumber())) ;

		Element name = new Element("name") ;
		name.addContent(drama.getTitleName().getName()) ;
		
		Element startDate = new Element("startDate") ;
		Element startYear = new Element("year") ;
		startYear.addContent(Integer.toString(DateUtilitaries.getYear(drama.getStartDate()))) ;
		Element startMonth = new Element("month") ;
		startMonth.addContent(Integer.toString(DateUtilitaries.getMonth(drama.getStartDate()))) ;
		Element startDay = new Element("day") ;
		startDay.addContent(Integer.toString(DateUtilitaries.getDay(drama.getStartDate()))) ;
		startDate.addContent(startYear) ;
		startDate.addContent(startMonth) ;
		startDate.addContent(startDay) ;
		
		Element endDate = new Element("endDate") ;
		Element endYear = new Element("year") ;
		endYear.addContent(Integer.toString(DateUtilitaries.getYear(drama.getEndDate()))) ;
		Element endMonth = new Element("month") ;
		endMonth.addContent(Integer.toString(DateUtilitaries.getMonth(drama.getEndDate()))) ;
		Element endDay = new Element("day") ;
		endDay.addContent(Integer.toString(DateUtilitaries.getDay(drama.getEndDate()))) ;
		endDate.addContent(endYear) ;
		endDate.addContent(endMonth) ;
		endDate.addContent(endDay) ;
		
		dramaEl.addContent(name) ;
		dramaEl.addContent(startDate) ;
		dramaEl.addContent(endDate) ;
		dramaEl.addContent(placeNumber) ;
		
		return dramaEl ;
	}
	
	
	private Element saveConcert(Concert conc) {
		Element concert = new Element("Concert") ;
		
		Attribute refAttribute = new Attribute("ref", Integer.toString(conc.getRef())) ;
		concert.setAttribute(refAttribute) ;
		
		Element placeNumber = new Element("placeNumber") ;
		placeNumber.addContent(Integer.toString(conc.getPlaceNumber())) ;

		Element name = new Element("name") ;
		name.addContent(conc.getArtistName().getName()) ;
		
		Element date = new Element("date") ;
		Element year = new Element("year") ;
		year.addContent(Integer.toString(DateUtilitaries.getYear(conc.getDate()))) ;
		Element month = new Element("month") ;
		month.addContent(Integer.toString(DateUtilitaries.getMonth(conc.getDate()))) ;
		Element day = new Element("day") ;
		day.addContent(Integer.toString(DateUtilitaries.getDay(conc.getDate()))) ;
		date.addContent(year) ;
		date.addContent(month) ;
		date.addContent(day) ;
		
		concert.addContent(name) ;
		concert.addContent(date) ;
		concert.addContent(placeNumber) ;
		
		return concert ;
	}

	private void saveEventsInCatalogInElement(Element catalog, List<Event> eventsInCatalog) {
		for (Event evt : eventsInCatalog) {
			if (evt instanceof Concert) {
				catalog.addContent(saveConcert((Concert) evt)) ; 
			} else if (evt instanceof Drama) {
				catalog.addContent(saveDrama((Drama) evt)) ; 
			}
		}
		
	}

	@Override
	public City findCityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCity(int id, City cityToUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberCities() {
		// TODO Auto-generated method stub
		return 0;
	}

}
