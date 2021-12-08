package infra;

import domain.City;
import domain.Concert;
import domain.DateUtilitaries;
import domain.Drama;
import domain.Event;
import domain.EventCatalog;
import domain.OpenDate;
import domain.Repository;
import domain.ShowRoom;
import exceptions.CapacityNegativeException;
import exceptions.EqualsDatesException;
import exceptions.ForbiddenDateException;
import exceptions.ForbiddenDateIntervalException;
import exceptions.ForbiddenHourException;
import exceptions.MultilangException;
import exceptions.NegativePlaceQuantityException;
import exceptions.NotEnoughPlaceException;
import exceptions.NotExistantCityException;
import exceptions.NotOpenedShowRoomException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.* ;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
public class RepositoryInXml implements Repository {
	private int nbCities = 0 ;


	public void putInXml(City city) {
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
		Element dramaEl = new Element("drama") ;
		
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
		Element concert = new Element("concert") ;
		
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
	public City findCityById(int id){
		
		SAXBuilder sxb = new SAXBuilder() ;
		Document doc = null ;
		try {
			String filename = "city" + id +".xml" ;
			doc = sxb.build(new File(filename)) ;
		} catch (Exception e) {
			System.err.println("City non enregistrée") ;
			return null ;
		}
		Element root = doc.getRootElement() ;
		// d'abord on récupère le catalog d'events
		
		List<Concert> concerts = new LinkedList<>() ;
		List<Drama> dramas = new LinkedList<>() ;
		
		try {
			Element catalogEl = root.getChild("EventCatalog") ;
			for (Element eventElement : catalogEl.getChildren()) {
				if (eventElement.getName().equals("concert")) {
					Concert c = recoverConcertFromElement(eventElement) ;
					concerts.add(c) ;
				} else if (eventElement.getName().equals("drama")) {
					Drama d = recoverDramaFromElement(eventElement) ;
					dramas.add(d) ;
				}
			}
			EventCatalog catalog = new EventCatalog(concerts, dramas) ;
			
			City city = new City(catalog) ;
			
			Element showroomsEl = root.getChild("showrooms") ;
			for (Element showroomEl : showroomsEl.getChildren()) {
				ShowRoom showroom = recoverShowroomFromElement(showroomEl) ;
				city.addShowRoom(showroom);
			}
			return city;

		} catch (MultilangException e) {
			System.err.println("Corrupted XML ...") ;
		}
		return null ;
	}

	private ShowRoom recoverShowroomFromElement(Element showroomEl)
			throws ForbiddenDateException, NegativePlaceQuantityException, NotOpenedShowRoomException, NotEnoughPlaceException, ForbiddenDateIntervalException, ForbiddenHourException, CapacityNegativeException, EqualsDatesException {
		
		
		int id = Integer.valueOf(showroomEl.getAttributeValue("id")) ;

		
		Element capacityEl = showroomEl.getChild("capacity") ;
		int capacity = Integer.valueOf(capacityEl.getContent(0).getValue()) ;
		
		Element opendatesEl = showroomEl.getChild("opendates") ;
		
		List<OpenDate> opendates = new LinkedList<>() ;
		
		for (Element odEl : opendatesEl.getChildren()) {
			
			
			Element yearEl = odEl.getChild("year"), monthEl = odEl.getChild("month"),
					dayEl = odEl.getChild("day"), hourEl = odEl.getChild("hour") ;
			
			int year = Integer.valueOf(yearEl.getContent(0).getValue()), month = Integer.valueOf(monthEl.getContent(0).getValue()),
					day = Integer.valueOf(dayEl.getContent(0).getValue()), hour = Integer.valueOf(hourEl.getContent(0).getValue()) ;
			
			OpenDate od = new OpenDate(year, month, day, hour) ;
			
			
			opendates.add(od) ;
		}
		
		ShowRoom showroom = new ShowRoom(opendates, capacity, id) ;
		
		Element eventsEl = showroomEl.getChild("events") ;

		for (Element eventEl : eventsEl.getChildren()) {
			if (eventEl.getName().equals("concert")) {
				Concert c = recoverConcertFromElement(eventEl) ;
				showroom.addEvent(c);
			} else if (eventEl.getName().equals("drama")) {
				Drama d = recoverDramaFromElement(eventEl) ;
				showroom.addEvent(d);
			}
		}
		
		return showroom;
	}

	private Drama recoverDramaFromElement(Element dramaEl) throws NegativePlaceQuantityException, ForbiddenDateIntervalException, ForbiddenDateException {
		// TODO Auto-generated method stub
		int ref = Integer.valueOf(dramaEl.getAttributeValue("ref")) ;
		
		
		Element placeNumber = dramaEl.getChild("placeNumber") ;
		int nbPlaces = Integer.valueOf(placeNumber.getContent(0).getValue()) ;
		
		Element nameEl = dramaEl.getChild("name") ;
		
		String name = nameEl.getContent(0).getValue() ;
		
		Element startDateEl = dramaEl.getChild("startDate") ;
		
		Element startYearEl = startDateEl.getChild("year"), startMonthEl = startDateEl.getChild("month"), startDayEl = startDateEl.getChild("day") ;
		
		int startYear = Integer.valueOf(startYearEl.getContent(0).getValue()), startMonth = Integer.valueOf(startMonthEl.getContent(0).getValue()), 
				startDay = Integer.valueOf(startDayEl.getContent(0).getValue()) ;
		
		
		Element endDateEl = dramaEl.getChild("endDate") ;
		
		Element endYearEl = endDateEl.getChild("year"), endMonthEl = endDateEl.getChild("month"), endDayEl = endDateEl.getChild("day") ;
		
		int endYear = Integer.valueOf(endYearEl.getContent(0).getValue()), endMonth = Integer.valueOf(endMonthEl.getContent(0).getValue()), 
				endDay = Integer.valueOf(endDayEl.getContent(0).getValue()) ;
		
		return new Drama(startYear, startMonth, startDay, endYear, endMonth, endDay, name, nbPlaces, ref) ;
	}

	private Concert recoverConcertFromElement(Element concertEl) throws ForbiddenDateException, NegativePlaceQuantityException {
		// TODO Auto-generated method stub
		int ref = Integer.valueOf(concertEl.getAttributeValue("ref")) ;
		
		
		Element placeNumber = concertEl.getChild("placeNumber") ;
		int nbPlaces = Integer.valueOf(placeNumber.getContent(0).getValue()) ;
		
		Element nameEl = concertEl.getChild("name") ;
		
		String name = nameEl.getContent(0).getValue() ;
		
		Element dateEl = concertEl.getChild("date") ;
		
		Element yearEl = dateEl.getChild("year"), monthEl = dateEl.getChild("month"), dayEl = dateEl.getChild("day") ;
		
		int year = Integer.valueOf(yearEl.getContent(0).getValue()), month = Integer.valueOf(monthEl.getContent(0).getValue()), 
				day = Integer.valueOf(dayEl.getContent(0).getValue()) ;
		
		
		return new Concert(year, month, day, name, nbPlaces, ref) ;
		
	}

	@Override
	public void updateCity(int id, City cityToUpdate) {
		this.putInXml(cityToUpdate);
	}

	@Override
	public int getNumberCities() {
		return nbCities;
	}

	@Override
	public void save(City city) {
		nbCities++ ;
		putInXml(city) ;
	}

}
