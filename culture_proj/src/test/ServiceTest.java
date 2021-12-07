package test;

import java.util.LinkedList;
import java.util.List;

import application.CQRS;
import application.CityService;
import application.CommandBag;
import application.Worker;
import domain.City;
import domain.Concert;
import domain.Drama;
import domain.Event;
import domain.OpenDate;
import domain.Repository;
import domain.ShowRoom;
import exceptions.BagNullException;
import exceptions.NotEnoughPlaceException;
import exceptions.NotExistantCityException;
import exceptions.NotExistantEventException;
import exceptions.NotExistantShowRoomException;
import exceptions.NotOpenedShowRoomException;
import infra.EventCatalog;
import infra.RepositoryInMemory;
import junit.framework.TestCase;
import user.controller.Controller;

public class ServiceTest extends TestCase {
	public void testCityService() throws Exception {
		List<Concert> concerts = new LinkedList<>() ;
		List<Drama> dramas = new LinkedList<>() ;
		concerts.add(new Concert(2022, 5, 13, "Joseph Allard", 100)) ;
		concerts.add(new Concert(2022, 5, 14, "La Bolduc", 400)) ;
		concerts.add(new Concert(2022, 5, 13, "Les Indiens", 12)) ;
		dramas.add(new Drama(2022, 5, 15, 2022, 5, 25, "Le barbier de Séville", 5)) ;
		dramas.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Kaboul", 500)) ;
		concerts.add(new Concert(2022, 6, 10, "Philippe Bruneau", 50)) ;
		concerts.add(new Concert(2022, 6, 10, "Les Charbonniers de l'Enfer", 20)) ;
		EventCatalog catalog = new EventCatalog(concerts, dramas) ;
		
		List<OpenDate> dates = new LinkedList<>() ;
		for (int i = 1 ; i < 31 ; i++) {
			dates.add(new OpenDate(2022, 5, i, 8)) ;
			dates.add(new OpenDate(2022, 6, i, 8)) ;
		}
		
		ShowRoom r1 = new ShowRoom(dates, 150) ;
		ShowRoom r2 = new ShowRoom(dates, 120) ;
		City city2 = new City() ;

		City city = new City() ;
		city.addShowRoom(r1);
		city.addShowRoom(r2);
		
		Repository repo = new RepositoryInMemory() ;
		repo.save(city);
		
		repo.save(city2);
		
		CityService service = new CityService(repo, catalog, new LinkedList<>()) ;
		
		// test getConcerts
		List<Concert> concertGets = service.getConcerts() ;
		for (Concert c : concerts) {
			assertEquals(concertGets.contains(c), true) ;
		}
		assertEquals(concerts.size(), concertGets.size()) ;
		
		// test getDramas
		List<Drama> dramasGet = service.getDramas() ;
		for (Drama d : dramas) {
			assertEquals(dramasGet.contains(d), true) ;
		}
		assertEquals(dramas.size(), dramasGet.size()) ;
		
		// test getEvent
		
		List<Event> eventsGet = service.getEvents() ;
		
		for (Concert c : concerts) {
			assertEquals(eventsGet.contains(c), true) ;
		}
		
		for (Drama d : dramas) {
			assertEquals(eventsGet.contains(d), true) ;
		}
		assertEquals(dramas.size() + concerts.size(), eventsGet.size()) ;
		
		// test getShowrooms
		List<ShowRoom> showrooms = service.getShowRooms(city.getId()) ;
		assertEquals(showrooms.size(), 2) ;
		assertEquals(showrooms.contains(r1), true) ;
		assertEquals(showrooms.contains(r2), true) ;
		assertEquals(service.getShowRooms(city.getId()+1), null) ;
		
		// test addEvent
		
		service.addEvent(city.getId(), r1.getId(), concerts.get(0).getRef()); // concert ok
		assertEquals(service.getError(city.getId()), null) ;
		assertEquals(service.getEvents().contains(concerts.get(0)), false) ;
		
		service.addEvent(city.getId(), r1.getId(), dramas.get(0).getRef()); // drama ok
		assertEquals(service.getError(city.getId()), null) ;
		assertEquals(service.getEvents().contains(dramas.get(0)), false) ;
		
		service.addEvent(city.getId(), r1.getId(), concerts.get(1).getRef()); // concert trop gros
		assertEquals(service.getError(city.getId()), new NotEnoughPlaceException(r1.getId(), r1.getCapacity(), concerts.get(1).getPlaceNumber()).getMessageFR()) ;
		
		service.addEvent(city.getId(), r1.getId(), concerts.get(2).getRef()); // salle pas ouverte
		assertEquals(service.getError(city.getId()), new NotOpenedShowRoomException(r1.getId(), concerts.get(2).getDate()).getMessageFR()) ;
		
		service.addEvent(city.getId() + 1, r1.getId(), concerts.get(2).getRef()); // city non existante
		assertEquals(service.getError(city.getId()+1), new NotExistantCityException(city.getId() + 1).getMessageFR()) ;
		
		Concert rC = new Concert(1, 2, 3, "tutu", 56) ;
		service.addEvent(city.getId(), r1.getId(), rC.getRef()); // event non existant
		assertEquals(service.getError(city.getId()), new NotExistantEventException(rC.getRef()).getMessageFR()) ;

		
		service.addEvent(city.getId(), r1.getId() + 14, concerts.get(2).getRef()); // showroom non existant
		assertEquals(service.getError(city.getId()), new NotExistantShowRoomException(r1.getId() + 14).getMessageFR()) ;
		
		// test removeEvent
		
		// il y a deja les concert et dramas 0 
				
		service.removeEvent(city.getId(), r1.getId(), concerts.get(0).getRef()); // remove concert OK
		assertEquals(service.getError(city.getId()), null) ;
		assertEquals(service.getEvents().contains(concerts.get(0)), true) ;
		
		service.removeEvent(city.getId(), r1.getId(), dramas.get(0).getRef()); // remove drama OK
		assertEquals(service.getError(city.getId()), null) ;
		assertEquals(service.getEvents().contains(dramas.get(0)), true) ;
		
		service.removeEvent(city.getId() + 1, r1.getId(), concerts.get(2).getRef()); // city non existante
		assertEquals(service.getError(city.getId()+1), new NotExistantCityException(city.getId() + 1).getMessageFR()) ;

		service.removeEvent(city.getId(), r1.getId() + 14, concerts.get(2).getRef()); // showroom non existant
		assertEquals(service.getError(city.getId()), new NotExistantShowRoomException(r1.getId() + 14).getMessageFR()) ;

		service.removeEvent(city.getId(), r1.getId(), concerts.get(1).getRef()); // concert non existant
		assertEquals(service.getError(city.getId()), new NotExistantEventException(concerts.get(1).getRef()).getMessageFR()) ;
		

		
		assertEquals(service.getNBCities(), 2) ;
		
		service.removeCity();
		
		assertEquals(service.getNBCities(), 1) ;
		
		List<Worker> workers = service.getWorkers() ;
		assertEquals(workers.size(), 0) ;
		
		assertEquals(service.verify(city.getId(), r1.getId()), false) ;
		
		this.assertNotSame("pas d'erreur dans la vérification de la showroom " + r1.getId(), service.getVerificationError(city.getId()));

	}
	
	
	public void testWorker() throws Exception {
		try {
			new Worker(null) ;
			fail("BagNullException not thrown with empty bag") ;
		} catch(BagNullException e) {
			
		}
		CommandBag b = new CommandBag() ;
		Worker w = new Worker(b) ;
		w.start() ;
		w.arrest();
		assertEquals(w.isInterrupted(), true);
	}


}
